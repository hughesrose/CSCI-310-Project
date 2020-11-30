/**
 * Bank.java
 * 
 * @author Rose, Thomas, Patrick
 */


public interface Bank{


    /**
     * Add a customer
     * customerNumber   -The number of the customer
     * maximumDemand    -The maximum demand for this customer
     * 
     */
    public void addCustomer(int customerNumber);

    /**
     * Output the value of available, maximum,
     * allocation, and need
     */
    public void getState();


    /**
     * Release resources
     * customerNumber   -The customer releasing resources
     * 
     */
    public void releaseResources(int customerNumber);

    /**
     * Calculates the need
     * customerNumber -The customer requesting resource
     */
    public void calculateNeed(int customerNumber);
    
    /**
     * Checks to see if customer can get resources
     * customerNumber -The customer requesting resource
     */
    public boolean canRun(int customerNumber);

    /**
     * Runs customer thread
     * @param
     * customerNumber -The customer/thread
     * cycle            -What cycle the customer/thread is in
     */
    public void runThread(int customerNumber);

    /**
     * Displays Final Available Vector and Final Allocation Matrix
     * @param
     * none
     */
    public void displayFinal();
    
    
}//end Bank