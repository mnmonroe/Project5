import java.util.Scanner;  // Access the Scanner class
import java.io.*;          // Access PrintWriter and related classes

public class Bones {
   public static void main(String[] args) throws IOException {
      
      String INPUT_FILE = "Jonathon_Fiorini_4_05_Input.txt";
      String OUTPUT_FILE = "Jonathon_Fiorini_4_05_Output.txt";
      
      // Tests to see if input text file is in directory
      try{
         File inputDataFile = new File(INPUT_FILE);
         Scanner inputFile  = new Scanner(inputDataFile);
      }
      catch(IOException e){
         System.out.print("Input file \"" + INPUT_FILE + "\" not found");
      }
      
      File inputDataFile = new File(INPUT_FILE);
      Scanner inputFile  = new Scanner(inputDataFile);
      
      FileWriter outputDataFile = new FileWriter(OUTPUT_FILE);
      PrintWriter outputFile = new PrintWriter(outputDataFile);
      
      // Acces EmployeeParameters class
      EmployeeParameters param = new EmployeeParameters();
      
      
      param.getEmployeeParameters();
      Employee[] empl = new Employee[param.maxEmployees];
      
      readData(inputFile, empl); // Find number of employees, establish employees
      
      outputFile.close(); 
     //hello
     
   } // End main
   



 /*********************************************************************************/
   
   public static int readData(Scanner input, Employee[] empl) {
      int numRead = 0; // Number of lines read
      
      while(input.hasNext() && numRead < empl.length) { 
         empl[numRead] = new Employee();
         empl[numRead].payRate = input.nextDouble();
         empl[numRead].hoursWorked = input.nextDouble();
         empl[numRead].name = input.nextLine();
         numRead++;
      } // End while
      return numRead;
   } // End readData
   
   /*********************************************************************************/
   
   public static double calculateGrossPay(Employee[] empl, int numRead) {
      return 0.0;
   } // End calculateGrossPay
   
   /*********************************************************************************/

   public static double calculateDeductions(Employee[] empl, int numRead) {
      return 0.0;
   } // End calculateDeductions
   
   /*********************************************************************************/
   
   public static void createReport(PrintWriter output, Employee[] empl, int numRead) {
      printHeading(output);
      printDetails(output, empl, numRead);
      printTotals(output, empl, numRead);
   }
   
   /*********************************************************************************/
   
   public static void printHeading(PrintWriter output){
   }
   
   /*********************************************************************************/
   
   public static void printDetails(PrintWriter output, Employee[] empl, int numRead) {
   }
   
   /*********************************************************************************/
   
   public static void printTotals(PrintWriter output, Employee[] empl, int numRead) {
   }
   
   
} // End class