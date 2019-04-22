import java.util.Scanner;     // Access the Scanner class
import java.io.*;             // Access PrintWriter and related classes
import java.io.FileReader;    // Access FileReader
import java.text.DecimalFormat; // Access decimal format class
import java.util.ArrayList;   // Access array list class


public class JadeJames_MelodyMonroe_4_05 {
   
   public static void main(String[ ] args) throws IOException {

    
      // Declarations
      final String INPUT_FILE = "JadeJames_MelodyMonroe_4_05_Input.txt";
      final String OUTPUT_FILE = "JadeJames_MelodyMonroe_4_05_Output.txt";
      final String EMPLOYEE_INPUT  = "EmployeeInput.txt";
      final String EMPLOYEE_PARAMETERS = "EmployeeParameters.txt";
   
      
      // Input error checking
      try {
         File inputDataFile = new File(INPUT_FILE);
         Scanner inputFile  = new Scanner(inputDataFile);
      }
      catch(IOException e) {
         System.out.print ("Input file not found.");
      }
      
      // Output data to file and console
      FileWriter outputDataFile = new FileWriter(OUTPUT_FILE);
      PrintWriter outputFile = new PrintWriter(outputDataFile);
      
      // Access array
      EmployeeParameters param = new EmployeeParameters();
      param.getEmployeeParameters();
      Employee[] empl = new Employee[param.maxEmployees]; 
      
      outputDataFile.close();
      } // End main

      //***********************************************************************************
      // Reads employee data provided in input file
      public static int readData (Scanner input, Employee[] empl) {
         int numRead = 0; // Number of lines read
         
         while(input.hasNext() && numRead < empl.length) { 
            empl[numRead] = new Employee(); // Creates new instance of Employee for each entry
            empl[numRead].payRate = input.nextDouble();
            empl[numRead].hoursWorked = input.nextDouble();
            empl[numRead].name = input.nextLine();
            
            numRead++;
         } // End while
         return numRead;
    } // End readData  
    
    //*************************************************************************************
       // Gross pay calculation
      public static void grossPay(Employee[] empl, int numRead) {
         
         for (int i = 0; i < numRead; i++) {
         
            if (empl[i].hoursWorked <= 40) {
               empl[i].grossPay = empl[i].hoursWorked * empl[i].payRate;
            }
         
            else if (empl[i].hoursWorked <= 50) {
               empl[i].grossPay = (40 * empl[i].payRate) + ((empl[i].hoursWorked - 40) * 
               (empl[i].payRate * 1.5));
            } 
         
            else {
               empl[i].grossPay = (40 * empl[i].payRate) + ((50 - 40) * 
               (empl[i].payRate * 1.5)) + ((empl[i].hoursWorked - 50) * 
               (empl[i].payRate * 2.0));
         } 
         }
      } //end grossPay
      //*****************************************************************************************
      //calculate deductions
     public static void calcDeductions(Employee[] empl, int numRead) {
        for (int i = 0; i < numRead, ++i) {
           empl[i].iraAmount = empl[i].grossPay * IRA Investment;
           empl[i].adjustedGrossPay = empl[i].grossPay - empl[i].iraAmount;
           empl[i].taxAmount = empl[i].adjustedGrossPay * (the sum of the tax rates decimals);
           empl[i].netPay = empl[i].adjustedGrossPay - empl[i].taxAmount;
           empl[i].savingsAmount = empl[i].netPay * empl[i].savingsRate / 100.0;
        }
     
   
} // End class
