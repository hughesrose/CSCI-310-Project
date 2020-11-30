/*
* BankImplement.java
* @author Rose, Thomas, Patrick
*/
import java.util.Arrays;
public class BankImplement implements Bank{
    int numOfCustomers;         //number of customers
    int numOfResources;         //number of resources
    int minumimDemand = 1;
    int maximumDemand = 10;
    private int[] available;    //amount available for each resource
    private int[][] allocation; //amount currently allocated to each customer
    private int[][] maximum;    //maximum demand for each customer
    private int[][] need;       //remaining needs of each customer
    private int[] customerRequest;  //keep track of customer requests\
    private int[] safeSequence;     //keeps the safe sequence

    /**
     * Bank Constructor
     * @param resources
     */
    public BankImplement(int customers,int resources){
        numOfResources = resources;
        numOfCustomers = customers;

        available = new int[numOfResources];
        maximum = new int[numOfCustomers][numOfResources];
        allocation = new int[numOfCustomers][numOfResources];
        need = new int[numOfCustomers][numOfResources];
        customerRequest = new int[numOfResources];

        //radomly generate available
        for(int i = 0; i < numOfResources; i++){
            available[i] = (int)Math.round(Math.random()*(maximumDemand-minumimDemand)+minumimDemand);
        }

        //randomly generate maximum
        for(int i = 0; i < numOfCustomers; i++){
            for(int j = 0; j < numOfResources; j++){
                maximum[i][j] = (int)Math.round(Math.random()*(available[j] - minumimDemand)+minumimDemand);
            }
        }

        getState();
    }//end BankImplement

    /**
     * Add a customer and generate random request
     * @param customerNumber
     * 
     * 
     */
    public void addCustomer(int customerNumber){
        System.out.println("\nCustomer " + customerNumber + " is making a request.");
        //generate request
        for(int i = 0; i < numOfResources; i++){
            customerRequest[i] = (int)Math.round(Math.random()*(maximum[customerNumber][i]-minumimDemand)+minumimDemand);
        }

         //Print request
         for(int i = 0; i < numOfResources; i++){
            System.out.print(customerRequest[i] + " ");
        }

        //check if request can happen
        if(!canRun(customerNumber)){
            System.out.println("\nCustomer " + customerNumber + " must wait.");
        }
        else{
        for(int i = 0; i < numOfResources; i++){
            //customerRequest[i] = (int)Math.round(Math.random()*(maximum[customerNumber][i]-minumimDemand)+minumimDemand);
            available[i] -= customerRequest[i];
            allocation[customerNumber][i] = customerRequest[i];
            need[customerNumber][i] = maximum[customerNumber][i] - allocation[customerNumber][i];
        }
    }

    }//end addCustomer


    /**
     * Output the value of available, maximum,
     * allocation, and need
     */
    public void getState(){
        //outputs available
        System.out.println("\n\nBank - Resources Available:");
            System.out.println(Arrays.toString(available));
        
        //outputs maximum
        System.out.println("\nBank - Max");
        for(int i = 0; i < numOfCustomers; i++){
            System.out.print("[ ");
            for(int j = 0; j < numOfResources; j++){
                System.out.print(maximum[i][j]);
                System.out.print(" ");
            }
            System.out.print("]\n");
        }

        //outputs allocation
        System.out.println("\nBank - Allocation");
        for(int i = 0; i < numOfCustomers; i++){
                for(int j = 0; j < numOfResources; j++){
                    System.out.print(allocation[i][j] + " ");
                }
                System.out.println();
            }

        //outputs need
        System.out.println("\nBank - Need");
        for(int i = 0; i < numOfCustomers; i++){
            for(int j = 0; j < numOfResources; j++){
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }

    }//end getState

    /**
     * Releases resources held by a customer
     * 
     * @param customerNumber 
     * @param release
     */
    public synchronized void releaseResources(int customerNumber){
        for(int i = 0; i < numOfResources; i++){
            available[i] += allocation[customerNumber][i];
            allocation[customerNumber][i] -= allocation[customerNumber][i];
        }

        System.out.println("Customer " + customerNumber + " has released its resources.");

    }//end releaseResources

    
    /**
     * calculateNeed
     * Checks to see if customer can get resources
     * @param customerNumber
     */
    public void calculateNeed(int customerNumber){
        for(int i = 0; i < numOfResources; i++){
            int cusNeed = maximum[customerNumber][i] - allocation[customerNumber][i];
            need[customerNumber][i] = cusNeed = Math.abs(cusNeed);
        }
        //getState();
    }//end calculateNeed

    /**
     * Checks to see if customer can get resources
     * @param customerNumber
     * 
     */
    public boolean canRun(int customerNumber){
/*
        boolean safeToRun = false;

        for(int i = 0; i < numOfResources; i++){
            if(need[customerNumber][i] >= available[i]){
                safeToRun = true;
            }
            else{
                safeToRun = false;
                break;
            }
        }
        return safeToRun;
*/
        //check if enough resources
        for(int i = 0; i < numOfResources; i++){
            if(customerRequest[i] > available[i]){
                return false;
            }
        }

        System.out.println("\nCustomer " + customerNumber + " request granted.");
        //find way to finish requests
        boolean[] canFinish = new boolean[numOfCustomers];
        for(int i = 0; i < numOfCustomers; i++){
            canFinish[i] = false;
        }

        //copy available
        int[] tempAvailable = new int[numOfResources];
        System.arraycopy(available, 0, tempAvailable, 0, available.length);

        for(int i = 0; i < numOfResources; i++){
            tempAvailable[i] -= customerRequest[i];
            need[customerNumber][i] -= customerRequest[i];
            allocation[customerNumber][i] += customerRequest[i];
        }

        for(int i = 0; i < numOfCustomers; i++){
            //search for customer request that can be done
            for(int j = 0; j < numOfCustomers; j++){
                if(!canFinish[j]){
                    boolean check = true;
                    for(int k = 0; k < numOfResources; k++){
                        if(need[j][k] > tempAvailable[k])
                            check = false;
                    }
                    //if request can be done
                    if(check){
                        canFinish[j] = true;
                        for(int l = 0; l < numOfResources; l++)
                            tempAvailable[l] += allocation[j][l];
                    }
                }
            }
        }
        //get need and allocation for thread
        for(int i = 0; i < numOfResources; i++){
            need[customerNumber][i] += customerRequest[i];
            allocation[customerNumber][i] -= customerRequest[i];
        }

        //see if all requests could complete
        boolean value = true;
        for(int i = 0; i < numOfCustomers; i++) {
            if(!canFinish[i]){
                value = false;
                break;
            }
        }
        return value;
    }//end canRun

    /**
     * Checks to see if a thread can run
     * @param
     * customerNumber -The customer/thread
     * i              -The cycle the thread is in            
     */
    public void runThread(int customerNumber){
            canRun(customerNumber);
    getState();
  }//end runThread

    /**
     * Displays Final Available Vector and Final Allocation Matrix
     * @param
     * none
     */
    public void displayFinal(){
        System.out.println("\n\nFinal Available Vector:");
        System.out.println(Arrays.toString(available));

        System.out.println("Final Allocation Matrix: ");
        for(int i = 0; i < numOfCustomers; i++){
            for(int j = 0; j < numOfResources; j++){
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println();
        }
    }//end displayFinal


}//end BankImplement