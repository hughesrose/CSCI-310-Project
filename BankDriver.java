/*
* BankDriver.java
* @author Rose, Thomas, Patrick
*/
import java.util.Scanner;

public class BankDriver{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of customers: ");
        int customerNum = scan.nextInt();
        System.out.println("Enter the number of resources: ");
        int resourceNum = scan.nextInt();

        //create new bank
        BankImplement bank = new BankImplement(customerNum,resourceNum);

        //create customer threads
        Customer[] customers = new Customer[customerNum];
        for(int i = 0; i < customerNum; i++){
            customers[i] = new Customer(i, bank);
        }

        for(Customer i: customers){
            i.start();
        }

        scan.close(); // close scanner after using
    }//end main
}//end BankDriver
