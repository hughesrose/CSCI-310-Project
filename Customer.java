/*
* Customer.java
* @author Rose, Thomas, Patrick
*/
public class Customer extends Thread{
    Bank banks;
    private int customerNumbers;

    public Customer(int customerNumber, Bank bank){
        banks = bank;
        customerNumbers = customerNumber;
          

    }//end bank

    public void run(){
        int i;
        for(i = 0; i < 3; i++){
            try{
                synchronized(banks){
                    banks.addCustomer(customerNumbers);           //generate request
                    banks.calculateNeed(customerNumbers);         //calculate need
                    banks.runThread(customerNumbers);             //
                }
                //holds resources for a random amount of time (1-5 seconds)
                int time = (int)(5*Math.random());
                Thread.sleep(time *1000);
                
                //release resources
                
            }catch(Exception e){
                System.out.println("\nException has been caught");
            }

        }
       banks.releaseResources(customerNumbers);
       /**
        * Issue: Displays more than once sometimes
        */
        banks.displayFinal();
    }//end run
}
