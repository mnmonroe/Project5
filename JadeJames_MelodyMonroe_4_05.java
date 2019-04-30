import java.util.Scanner;     // Access the Scanner class
import java.io.*;             // Access PrintWriter and related classes
import java.io.FileReader;    // Access FileReader
import java.text.DecimalFormat; // Access decimal format class
import java.util.ArrayList;   // Access array list class


public class JadeJames_MelodyMonroe_4_05 {
  
   static Toolkit tools = new Toolkit(); 
  
   public static void main(String[ ] args) throws IOException {
   
     
      // Declarations
      final String INPUT_FILE = "JadeJames_MelodyMonroe_4_05_Input.txt";
      final String OUTPUT_FILE = "JadeJames_MelodyMonroe_4_05_Output.txt";
      final String EMPLOYEE_INPUT  = "EmployeeInput.txt";
      final String EMPLOYEE_PARAMETERS = "EmployeeParameters.txt";
      
      int maxEmployees;
      int numRead = 0;
      int sortResult = 0;
      double savingsRate;
      double iraRate;
      double fedRate;
      double stateRate;
      String warning;
      
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
      
      // Store parameters in local variables
      maxEmployees = param.maxEmployees;
      savingsRate = param.savingsRate;
      iraRate = param.iraRate;
      fedRate = param.federalWithholdingRate;
      stateRate = param.stateWithholdingRate;
      
      // Dispay the employee parameters
      param.displayEmployeeParameters();
      System.out.println();
               
      grossPay (empl, numRead);
   
      calcDeductions (empl, iraRate, fedRate, stateRate, savingsRate, numRead);
      
      // Output data to the console and output file
      outputMaster (outputFile, "Input", empl, numRead); 
      
      // Sort data by employee name
      outputMaster (outputFile,"Name",empl,numRead);
      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "Name");
      
      // Sort data by ascending gross pay
      outputMaster (outputFile, "Gross Pay", empl, numRead);
      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "Gross Pay");
   
      outputDataFile.close(); // Close output file
        
      System.exit(0);  // Exit program
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
   // Calculate deductions
   public static void calcDeductions(Employee[] empl,double ira,double fedRate,
        double stateRate,double saveRate,int numRead) {
        
      double grossPay = 0.0;
      double taxes = 0.0;
        
      taxes = (fedRate / 100.0) + (stateRate / 100.0); 
        
      for (int i = 0; i < numRead; ++i) {
           
         grossPay = empl[i].grossPay;
         empl[i].iraAmount = empl[i].grossPay * (ira / 100);
         empl[i].adjustedGrossPay = empl[i].grossPay - ira;
         empl[i].taxAmount = empl[i].adjustedGrossPay * taxes;
         empl[i].netPay = empl[i].adjustedGrossPay - empl[i].taxAmount;
         empl[i].savingsAmount = empl[i].netPay * (empl[i].savingsAmount / 100.0);
      }
   }

   //**************************************************************************************************
   //header method 6a
   public static void printHeader (PrintWriter output, String order) {
         
      String reportHeading;
         
      reportHeading = // Input order
         "\r\nPrinted in " + order.toLowerCase() + " order.\r\n" + "\r\n" +
         // Table title 
         tools.padString("Mobile Apps Galore, Inc. - Payroll Report", 65, " ", "") +
         "\r\n" + "\r\n" +
         // table headers
         tools.padString("Name", 21) +
         " " + tools.padString("Gross Pay", 10) +
         " " + tools.padString("Net Pay", 8) +
         " " + tools.padString("Wealth", 10) +
         " " + tools.padString("Taxes", 8) +
         " " + tools.padString("Hours", 7) +
         " " + tools.padString("Pay Rate", 0) +
         " " + "\r\n" +
         tools.padString("---------------", 21) +
         " " + tools.padString("---------", 10) +
         " " + tools.padString("-------", 8) +
         " " + tools.padString("-------", 10) +
         " " + tools.padString("-------", 8) +
         " " + tools.padString("-------", 6) +
         " " + tools.padString("--------", 0) +
         " ";
         
      output.println(reportHeading);
      System.out.println(reportHeading);
   } // End printHeader
   
   //**************************************************************************************************
   // Calculate the totals
   public static void getTotals(Employee[] empl, PrintWriter output, int numRead) {
   
      final String DOLLAR = "##,##0.00";
   
      String str;
      double sumGrossPay = 0.0;
      double sumNetPay = 0.0;
      double sumWealth = 0.0;
      double sumTaxes = 0.0;
      double sumHours = 0.0;
      double sumPayRate = 0.0;
      double avgPayRate = 0.0;
   
    // Store each of the array items in the local vars
      for(int i = 0; i < numRead; i++) {
         sumGrossPay += empl[i].grossPay;
         sumNetPay += empl[i].netPay;
         sumWealth += empl[i].savingsAmount + empl[i].iraAmount;
         sumTaxes += empl[i].taxAmount;
         sumHours += empl[i].hoursWorked;
         sumPayRate += empl[i].payRate;
      
      }
   
    // Check to make sure there are payrates to calc avg
      if(sumPayRate >= 1) {
         avgPayRate = sumPayRate / numRead;
      }
   
   // Print out all the sums and the average
      str = "Totals: " +
            tools.leftPad(sumGrossPay, 22, DOLLAR) +
            tools.leftPad(sumNetPay, 13, DOLLAR) +
            tools.leftPad(sumWealth, 11, DOLLAR) +
            tools.leftPad(sumTaxes, 12, DOLLAR) +
            tools.leftPad(sumHours, 11, DOLLAR) +
            "\r\n" + tools.padString("Average: ", 83, " ", "") +
            tools.leftPad(avgPayRate, 5, DOLLAR) +
            "\r\n\r\n" +
            "The total number of employees processed: " +
            
            numRead;
   
      System.out.println(str);
      output.println(str);
   
   }
   //**************************************************************************************************
   
// Run all the methods to output data
   public static void outputMaster(PrintWriter output, String order, Employee[] empl, int numRead) {
   
      printHeader(output, order);
      outputData(empl, output, numRead);
      getTotals(empl, output, numRead);
   
    }
   //************************************************************************************************** 
// Print out data in a table
   public static void outputData(PrintWriter output, Employee[] empl, int numRead) {
   
      final String DOLLAR = "##,##0.00";
   
      double wealth = 0.0;
   
      for(int i = 0; i < numRead; i++) {
      
         wealth = empl[i].savingsAmount + empl[i].iraAmount;
      
         String str;
      
         str = tools.padString(empl[i].name, 19) + " " + tools.leftPad(empl[i].grossPay, 8, DOLLAR) +
            " " + tools.leftPad(empl[i].netPay, 10, DOLLAR) + " " + tools.leftPad(wealth, 8, DOLLAR) +
            " " + tools.leftPad(empl[i].taxAmount, 9, DOLLAR) + 
            " " + tools.leftPad(empl[i].hoursWorked, 8, DOLLAR) +
            " " + tools.leftPad(empl[i].payRate, 8, DOLLAR) +
            " ";
      
         output.println(str);
         System.out.println(str);
      
      }
   } // End outputData



} // End class