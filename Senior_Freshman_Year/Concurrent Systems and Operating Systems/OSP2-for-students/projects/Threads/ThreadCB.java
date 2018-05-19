package osp.Threads;
import java.util.Vector;
import java.util.Enumeration;
import osp.Utilities.*;
import osp.IFLModules.*;
import osp.Tasks.*;
import osp.EventEngine.*;
import osp.Hardware.*;
import osp.Devices.*;
import osp.Memory.*;
import osp.Resources.*;
//Need this for the priority queue
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Random;

public class ThreadCB extends IflThreadCB {
  //implement a comparator for the threads priority queue
  private static class ThreadComparator implements Comparator<ThreadCB> {
    @Override
    public int compare(ThreadCB x, ThreadCB y) {
      return x.getPriority() - y.getPriority();
    }
  }

  //define thread comparator
  private static Comparator<ThreadCB> comparator = new ThreadComparator();
  //define priority queue for threads
  private static PriorityQueue<ThreadCB> readyQueue;  

  public ThreadCB() {
    super();
  }

  public static void init() {
    readyQueue = new PriorityQueue<ThreadCB>(1,comparator);
    System.out.println("Running the pre-emptive priority scheduling");
  }

  //Create a thread and places it on the queue
  static public ThreadCB do_create(TaskCB task) {
    if(task == null) {
      dispatch();
      return null;
    }
    
    if (task.getThreadCount() >= MaxThreadsPerTask) {
      MyOut.print("osp.Threads.ThreadCB", "Failed to create new thread " +
                  " -- maximum number if threads for " + task + "reached");
      dispatch();
      return null;
    }

    ThreadCB newThread = new ThreadCB();
    MyOut.print("osp.Threads.ThreadCB", "Created " + newThread);

    newThread.setStatus(ThreadReady);
    newThread.setTask(task);
    Random rand = new Random();
    newThread.setPriority(rand.nextInt(3)); 

    if(task.addThread(newThread) != SUCCESS) {
      MyOut.print("osp.Threads.ThreadCB", 
                  "Could not add thread "+ newThread +"to task"+ task);
      dispatch();
      return null;
    }

    readyQueue.add(newThread);
    MyOut.print("osp.Threads.ThreadCB",
                "successfully added "+ newThread +" to "+ task);
    dispatch();
    return newThread;
  }

  public void do_kill() {
    MyOut.print(this, "Entering do_kill("+this+")");
    TaskCB task = getTask();
    switch(getStatus()) {
      case ThreadReady:
          readyQueue.remove(this);
          break;
      case ThreadRunning:
          if(this == MMU.getPTBR().getTask().getCurrentThread())
            MMU.getPTBR().getTask().setCurrentThread(null);
          break;
      default:
          break;
    }

    if(task.removeThread(this) != SUCCESS) {
      MyOut.print(this, "Could not remove thread"+this+"from task"+task);
      return;
    }

    MyOut.print(this, this+" is set to be destroyed");

    setStatus(ThreadKill);

    for(int i=0; i < Device.getTableSize(); i++) {
      MyOut.print(this, "Purging IORBs on Device "+i);
      Device.get(i).cancelPendingIO(this);
    }

    ResourceCB.giveupResources(this);
    dispatch();

    if(this.getTask().getThreadCount()==0) {
      MyOut.print(this, "Destroying "+this+": "+this.getTask() +
                        " leaves no threads left; destroying the task");
      this.getTask().kill();
    }
  }
  
  public void do_suspend(Event event) {
    int oldStatus = this.getStatus();
    MyOut.print(this, "Entering suspend("+this+","+event+")");

    ThreadCB runningThread=null;
    TaskCB runningTask=null;
    try {
      runningTask=MMU.getPTBR().getTask();
      runningThread = runningTask.getCurrentThread();
    } catch(NullPointerException e){}

    //check if this is the running thread
    if(this == runningThread)
      this.getTask().setCurrentThread(null);
    
    //Set status
    if(this.getStatus() == ThreadRunning)
      setStatus(ThreadWaiting);
    else if (this.getStatus() >= ThreadWaiting)
      setStatus(this.getStatus()+1);

    readyQueue.remove(this);
    event.addThread(this);

    dispatch();
  }

  public void do_resume() {
    if(getStatus() < ThreadWaiting) {
      MyOut.print(this, "Attempt to resume "+this+
                        ", which wasn't waiting");
      return;
    }
    
    MyOut.print(this, "Resuming "+this);
    
    //set thread status
    if(getStatus()==ThreadWaiting)
      setStatus(ThreadReady);
    else if (getStatus() > ThreadWaiting)
      setStatus(getStatus() - 1);
    
    //Put thread on queue if appropriate
    if(getStatus()==ThreadReady)
      readyQueue.add(this);
    dispatch();
  }

  public static int do_dispatch() {
    ThreadCB threadToDispatch=null;
    ThreadCB runningThread=null;
    TaskCB runningTask=null;

    try {
      runningTask = MMU.getPTBR().getTask();
      runningThread = runningTask.getCurrentThread();
    } catch(NullPointerException e) {}


    if(runningThread != null) {
      MyOut.print("osp.Threads.ThreadCB",
                  "Preempting currently runnung " + runningThread);
      runningTask.setCurrentThread(null);
      MMU.setPTBR(null);

      runningThread.setStatus(ThreadReady);
      readyQueue.add(runningThread);
    }

    threadToDispatch = (ThreadCB)readyQueue.poll();
    //If the queue is empty spit out an error
    if(threadToDispatch == null) {
      MyOut.print("osp.Threads.ThreadCB",
                  "Can't find suitable thread to dispatch");
      MMU.setPTBR(null);
      return FAILURE;
    }   

    //put thread on processor.
    MMU.setPTBR(threadToDispatch.getTask().getPageTable());

    //set thread to dispatch as the current thread of its task
    threadToDispatch.getTask().setCurrentThread(threadToDispatch);

    //set Thread's status.
    threadToDispatch.setStatus(ThreadRunning);

    MyOut.print("osp.Threads.ThreadCB",
                "Dispatching " + threadToDispatch);
    
    HTimer.set(150);

    return SUCCESS;
  }

  public static void atError() {}

  public static void atWaring() {}
}
