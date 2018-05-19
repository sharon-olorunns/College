#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
   char* string;
   pthread_mutex_t mutex;
   pthread_cond_t cond;
   pthread_cond_t cond_p;
   int touched_by;
   int value_needs_printing;
   int finished;
} data_t;

data_t input_data;

void* consumer_thread(void* arg) 
{
    data_t* data_ptr = &input_data;
    while(!data_ptr->finished)
    {
        pthread_mutex_lock(&data_ptr->mutex);
        if(!data_ptr->value_needs_printing)
            pthread_cond_wait(&data_ptr->cond, &data_ptr->mutex);   
        if(!data_ptr->finished)
        {
            data_ptr->touched_by = *(int*)arg;
            pthread_cond_signal(&data_ptr->cond_p);
        }
        pthread_mutex_unlock(&data_ptr->mutex);
    }
    pthread_exit(0);
}

void* producer_thread(void* arg) 
{
    data_t* data_ptr = &input_data;
    while(!data_ptr->finished)
    {
        pthread_mutex_lock(&data_ptr->mutex);
        if(!data_ptr->value_needs_printing)
            pthread_cond_wait(&data_ptr->cond_p, &data_ptr->mutex);
        if(!data_ptr->finished)
        {
            int thread_id = (int)data_ptr->touched_by;
            printf("%d thread handled: %s\n", thread_id, data_ptr->string);
            data_ptr->value_needs_printing = 0;
        }
        pthread_mutex_unlock(&data_ptr->mutex);
    }
    pthread_exit(0);
}

int main(int argc, char** argv) 
{
    input_data.finished =0;
    input_data.value_needs_printing =0;

    pthread_mutex_init(&input_data.mutex, NULL);
    pthread_cond_init(&input_data.cond, NULL);
    pthread_cond_init(&input_data.cond_p, NULL);
    
    size_t number_of_consumers = 3;
    int* thread_nums = malloc(number_of_consumers * sizeof(int));
    pthread_t consum_th[number_of_consumers];
    pthread_t produc_th;

    for(int i = 0; i < number_of_consumers; i++)
    {
        thread_nums[i] = i + 1;
        pthread_create(&consum_th[i], NULL, consumer_thread, (void *)&thread_nums[i]);
    }
    pthread_create(&produc_th, NULL, producer_thread, NULL);

    int finished = 0;
    while(!finished) 
    {
        char* buffer;
        size_t bufsize = 50;
        buffer = (char*)malloc(bufsize * sizeof(char));
        fflush(stdin);
        fgets(buffer, bufsize, stdin);
        if(strcmp("q!\n", buffer) == 0)
        {
            pthread_mutex_lock(&input_data.mutex);
            input_data.finished = 1;
            pthread_cond_broadcast(&input_data.cond);
            pthread_cond_signal(&input_data.cond_p);
            finished = 1;
            pthread_mutex_unlock(&input_data.mutex);
        }
        else if(strcmp("", buffer) != 0)
        {
            pthread_mutex_lock(&input_data.mutex);
            input_data.string = buffer;
            input_data.value_needs_printing = 1;
            pthread_cond_signal(&input_data.cond);
            pthread_mutex_unlock(&input_data.mutex);
        }
        free(buffer);    
    }
            
    for(int i = 0; i < number_of_consumers; i++)
        pthread_join(consum_th[i], NULL);
    pthread_join(produc_th,NULL);

    return 0;
}
