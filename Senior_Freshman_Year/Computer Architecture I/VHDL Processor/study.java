public static void bubbleSort(int numberArray[]){

        int temp;

         /**
          * Compare the element in index 0 with the element in index 1
          * The index i is to go through the whole array
          * The index j is to go through the comparison
          * */

        for(int i = 0; i < numberArray.length; i++){
            for(int j =1; j < (numberArray.length - i); j++){

                if(numberArray[j - 1] > numberArray[j]){
                    temp = numberArray[j-1];
                    numberArray[j-1] = numberArray[j];
                    numberArray[j] = temp;
                }
            }
        }
     }