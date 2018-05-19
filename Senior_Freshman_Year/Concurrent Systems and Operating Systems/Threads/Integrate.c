#include <pthread.h>
#include <stdio.h>

void *inc_x(void *c_void_ptr)
{
   int *x_ptr = (int *)c_void_ptr;
   while(++(*x_ptr) < 100);
   printf("x increment is finished!\n");
   return NULL;
}

int main(int argc, char* argv[])
{
    int x, y;
    x = y = 0;
    printf("x: %d y: %d\n", x, y);
    /**
     * This is a pointer to the second thread
     */
    pthread_t inc_x_thread;
    if(pthread_create(&inc_x_thread,NULL,inc_x,&x))
    {
        fprintf(stderr, "Error Creating Thread!\n");
        return 1;
    }
    while(++y < 1000);
    printf("y increment is finished!\n");
    if(pthread_join(inc_x_thread, NULL))
    {
        fprintf(stderr, "Error Joining Thread!\n");
        return 2;
    }
    printf("x: %d y: %d\n", x, y);
    return 0;
}


