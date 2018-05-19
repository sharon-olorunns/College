#ifndef PHIL_NUM
#define PHIL_NUM 5
#endif

//fork represents which of the diners is using it
byte fork[PHIL_NUM];
//count of diners eating
byte p_eating;

//initalise here
init {
  atomic {
    byte i = 0;
    do
    ::i < PHIL_NUM -> run P(i); i++;
    ::else -> break;
    od;
  }
}

proctype P(byte id) {
//see if one of the forks is available
THINK:
  if
  ::atomic {
    fork[id] == 0 -> 
    fork[id] = id + 1;
  }
  fi;
//check to see if the other fork is available
GETFORK:
  if
  ::atomic {
    fork[id] == id + 1 -> 
    fork[(id + 1)%PHIL_NUM] == 0 -> 
    fork[(id + 1)%PHIL_NUM] = id + 1;
    p_eating++;
  }
  fi;
//when both forks are held eat for then put forks down
EAT:
  d_step {
    p_eating--;
    fork[(id + 1)%PHIL_NUM] = 0;
    fork[id] = 0;
  }
  goto THINK;
}

