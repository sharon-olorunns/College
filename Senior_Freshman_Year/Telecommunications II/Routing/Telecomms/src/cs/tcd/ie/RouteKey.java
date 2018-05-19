package cs.tcd.ie;

public class RouteKey {

    int hopCounter;
    int nextDest;


    public RouteKey(int hopCounter, int nextDest) {
        this.hopCounter=hopCounter;
        this.nextDest = nextDest;
    }

    public void setNextHop(int nextHop) {
        this.nextDest = nextHop;
    }
}
