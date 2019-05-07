/** 
   Purpose: This program calculates employee gross pay, net pay, 
   wealth, taxes, hours and pay rate based on data 
   input file to an output file and console.
   
    @author Jade James & Melody Monroe
    Project #5, CS 1050, Section 4
    IDE: jGrasp
    
    Bumbershoot: noun informal - an umbrella. 
    Source: https://www.dictionary.com/browse/bumbershoot
    
    “A single conversation across the table with a wise man is better 
    than ten years mere study of books." 
    - Henry Wadsworth Longfellow (1807 – 1882)
    Source: https://www.brainyquote.com/citation/authors/henry_wadsworth_longfello
    

*/

import java.util.Scanner;     // Access the Scanner class
import java.io.*;             // Access PrintWriter and related classes
import java.io.FileReader;    // Access FileReader
import java.text.DecimalFormat; // Access decimal format class
import java.util.ArrayList;   // Access array list class


public class JadeJames_MelodyMonroe_4_05 {
   
   // Accesses the toolkit
   static Toolkit tools = new Toolkit(); 
  
   public static void main(String[ ] args) throws IOException {
   
     
      // Declarations
      final String INPUT_FILE = "JadeJames_MelodyMonroe_4_05_Input.txt";
      final String OUTPUT_FILE = "JadeJames_MelodyMonroe_4_05_Output.txt";
      final String EMPLOYEE_INPUT  = "EmployeeInput.txt";
      final String EMPLOYEE_PARAMETERS = "EmployeeParameters.txt";
      
      int maxEmployees; // Counts employee entries
      int numRead = 0; // Counts number of values read
      int sortResult = 0; // Sorts results using Employee class
      double savingsRate; // Savings rate from Employee parameters
      double iraRate; // IRA rate from Employee parameters
      double fedRate; // Federal tax rate from Employee parameters
      double stateRate; // State tax rate from Employee parameters
      
      // Access toolkit for formatting data
      DecimalFormat dollars = new DecimalFormat ("$#,##0.00");
      DecimalFormat leadingBlank = new DecimalFormat ("#.00");
      
      // Access input data
      File inputDataFile = new File(INPUT_FILE);
      Scanner inputFile  = new Scanner(inputDataFile);
          
      // Output data to file and console
      FileWriter outputDataFile = new FileWriter(OUTPUT_FILE);
      PrintWriter outputFile = new PrintWriter(outputDataFile);

      // Access employee array
      EmployeeParameters param = new EmployeeParameters();
      
      param.getEmployeeParameters();
        
      // Store parameters in local variables
      maxEmployees = param.maxEmployees;
      savingsRate = param.savingsRate;
      iraRate = param.iraRate;
      fedRate = param.federalWithholdingRate;
      stateRate = param.stateWithholdingRate;
      
      // Collect each value as instatiation of Employee array      
      Employee[] empl = new Employee[maxEmployees]; 
      
      // Reads the values from the input file
      numRead = readData (inputFile, empl);
              
      // Dispay the employee parameters
      param.displayEmployeeParameters();
      outputFile.println();
      System.out.println();
      
      // Passes values to the grossPay method         
      grossPay (empl, numRead);
      
      // Passes employee data to calcDeductions method 
      calcDeductions (empl, iraRate, fedRate, stateRate, savingsRate, numRead);
      
      // Output data to the console and output file
      printReport(outputFile,"Input", empl, numRead); 
      
      // Sort data by employee name
      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "Name");
      printReport (outputFile,"Name", empl, numRead);
      
      // Sort data by ascending gross pay
      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "Gross Pay");
      printReport (outputFile, "Gross Pay", empl, numRead);
      
      inputFile.close(); // Close input file
      outputDataFile.close(); // Close output file
        
      System.exit(0);  // Exit program
   } // End main

// Reads employee data provided in input file
   //***********************************************************************************
   public static int readData (Scanner input, Employee[] empl) {
         
      int numRead = 0; // Number of lines read
         
      while(input.hasNext() && numRead < empl.length) { 
         empl[numRead] = new Employee(); // New instance of Employee for each entry
         empl[numRead].payRate = input.nextDouble();
         empl[numRead].hoursWorked = input.nextDouble();
         empl[numRead].name = input.nextLine();
            
         numRead++;
      } // End while
      return numRead;
   } // End readData 

// Calculates gross pay values for each employee    
   //*************************************************************************************
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

// Calculates employee deductions
   //*****************************************************************************************
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

// Prints heading row for report to console and file
   //**************************************************************************************************
   public static void printHeader (PrintWriter output, String order) {
         
      String reportHeading;
         
      reportHeading = // Input order
         "\r\nPrinted in " + order.toLowerCase() + " order.\r\n" + "\r\n" +
         // Table title 
         tools.padString("Mobile Apps Galore, Inc. - Payroll Report", 65, " ", "") +
         "\r\n" + "\r\n" +
         // table headers
         tools.padString("Name", 21) +
         " " + tools.padString("Gross Pay", 12) +
         " " + tools.padString("Net Pay", 12) +
         " " + tools.padString("Wealth", 11) +
         " " + tools.padString("Taxes", 10) +
         " " + tools.padString("Hours", 8) +
         " " + tools.padString("Pay Rate", 8) +
         " " + "\r\n" +
         tools.padString("---------------", 21) +
         " " + tools.padString("---------", 12) +
         " " + tools.padString("-------", 12) +
         " " + tools.padString("-------", 11) +
         " " + tools.padString("-------", 10) +
         " " + tools.padString("-------", 8) +
         " " + tools.padString("--------", 8);
         
      output.println(reportHeading);
      System.out.println(reportHeading);
   } // End printHeader

// Calculates totals for all employee data
   //**************************************************************************************************
   public static void getTotals(Employee[] empl, PrintWriter output, int numRead) {
   
      DecimalFormat dollars = new DecimalFormat ("$#,##0.00");
      DecimalFormat leadingBlank = new DecimalFormat ("#.00"); 
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
      str = "Totals: " + "\t\t\t\t\t" +
            dollars.format(sumGrossPay) + "\t" +
            dollars.format(sumNetPay) + "\t" +
            dollars.format(sumWealth) + "\t " + 
            dollars.format(sumTaxes) + " \t\t" + 
            leadingBlank.format(sumHours) + "\t\t" +
            "\r\n" + tools.padString("Average: ", 83, " ", "") +
            dollars.format(avgPayRate) +
            "\r\n\r\n" +
            "The total number of employees processed: " +
            
            numRead;
   
      System.out.println(str.trim());
      output.println(str.trim());
   
   }// End getTotals

//Prints full report with employee data and calculations   
   //**************************************************************************************************
   public static void printReport(PrintWriter output,String order,Employee[] empl, 
                     int numRead) {
   
      printHeader(output, order);
      outputData(output,empl,numRead);
      getTotals(empl, output, numRead);
   
    }
   
// Prints employee pay, deductions and savings  
   //************************************************************************************************** 
   public static void outputData(PrintWriter output, Employee[] empl, int numRead) {
   
      DecimalFormat dollars = new DecimalFormat ("$#,##0.00");
      DecimalFormat leadingBlank = new DecimalFormat ("#.00");
   
      final String DOLLAR = "$##,##0.00";
       
      double wealth = 0.0;
      
   
      for(int i = 0; i < numRead; i++) {
      
         wealth = empl[i].savingsAmount + empl[i].iraAmount;
      
         String str;
      
         str = tools.padString(empl[i].name, 21) + "\t\t" +
            dollars.format(empl[i].grossPay) + "\t\t" + 
            dollars.format(empl[i].netPay) + "\t\t" + 
            dollars.format(wealth) + "\t\t" + 
            dollars.format(empl[i].taxAmount) + "\t\t" + 
            leadingBlank.format(empl[i].hoursWorked) + "\t\t" + 
            dollars.format(empl[i].payRate);
      
         output.println(str.trim());
         System.out.println(str.trim());
      
      }
   } // End outputData

// Prints details to the console and to output file   
  //*************************************************************************************************************
     public static void printDetails(Employee[] empl, PrintWriter output, int numRead) {
        
        final String DOLLAR = "$##,##0.00"; 
        double wealth = 0.0;
      
        
        for (int i = 0; i < numRead; ++i) 
        {
        
        wealth = empl[i].savingsAmount + empl[i].iraAmount;
        String details = (tools.padString(empl[i].name,16) + tools.leftPad(empl[i].grossPay,8, DOLLAR) + 
                         tools.leftPad(empl[i].netPay, 10, DOLLAR) + tools.leftPad(empl[i].wealth, 8, DOLLAR) + 
                         tools.leftPad(empl[i].taxAmount, 9, DOLLAR) +
                         tools.leftPad(empl[i].hoursWorked,8, DOLLAR) + tools.leftPad(empl[i].payRate,8,DOLLAR));
               
        System.out.println(details.trim());
        output.println(details.trim());  
        
        }   
             }



} // End class
