/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.*;

public class Task2 {
    Set<String> typeSet=new TreeSet<>();            //new entries are added in alphabetical order to a tee set
    Map<Integer,Double> priceMap=new TreeMap<>();   //new entries are added sorted order(by key) to a tee map
    double totalValueOfProperties=0;    //total value of properties for sale
    ArrayList<String> propertyId=new ArrayList<>(); //to store property id s
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Task2 report=new Task2();
        report.readfile();          //read input text file
        report.writeToFileApp_A();  //write output file by application A
        report.writeToFileApp_B();  //write output file by Application B
    }
    
    void readfile() throws IOException{     //this method reads the listings.txt file line by line
        String fileName;                    //variable to store user given file name
        System.out.println("Please Enter the file name of the input file(eg-listings.txt):");   //prompt user for fle name
        Scanner sc=new Scanner(System.in);  //scanner to rad keyboard input
        try{
            fileName=sc.next();             //read user given file name
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            System.out.println("File read successfull");
            while ((line = reader.readLine()) != null) {    //check until end of file
                process(line);      //process data
            }
            System.out.println("Processing Data with application A: successful");
            System.out.println("Processing Data with application B: successful");
            
        }catch(Exception e){
            System.out.println("invalid input or file not found. Try again");       //prompt again if user given file is not found
            readfile();
        }
        
    }
    void process(String line){
        try{
            StringTokenizer st=new StringTokenizer(line);   //tokkenize each line into property ID, type, price and agent ID
            String propertyID=st.nextToken(); //extract propertyID as a string
            String type=st.nextToken();     //extract type as a string
            String strPrice=st.nextToken(); //extract price as a string
            String strAgentID=st.nextToken();   //extract agent ID as a string
            
            type=type.toUpperCase();        // Convert property type to upper case before adding to the Set
            int agentID=Integer.parseInt(strAgentID);   //convert(parse) agent ID from string to Integer
            double price=Double.parseDouble(strPrice);  //convert(parse) price from string to double
            
            processApp_A(type, agentID, price);     //process data with application A
            processApp_B(propertyID, price);        //process dat with application B
        }
        
        catch(Exception e){     //exception raised if expected data format is not in the user given file.
            System.out.println("Incorrect data format in given file.System will now exit");
            System.out.println(e);
            System.exit(0);
        }
    }
    void processApp_A(String type,int agentID,double price){
        typeSet.add(type);              //add property type to the set
        if(priceMap.containsKey(agentID)){          //value should be accumulated total. append if previous entry is found
            double prev=priceMap.get(agentID);  //get previous accumulated total
            price=price+prev;           //append
        }
        priceMap.put(agentID,price);        //add (back) to the map 
    }
    
    void processApp_B(String propertyID,double price){
        totalValueOfProperties+=price; //increment the total value of property by price of each property read from file
        propertyId.add(propertyID);     //add property ID to an arraylist
    }
    
    void writeToFileApp_A(){        //creates output file with application A
        System.out.println("\n\t****| Creating Output with Application B |****");
        System.out.println("Opening file to write output Data:agentreport.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("agentreport.txt");
        } catch (Exception ex) {
            System.out.println("Error Writing to file");
        }
        System.out.println("File Opened successfully!");
        
        Iterator iterator = typeSet.iterator();  //iterator to iterate through the set of property types
        while (iterator.hasNext()){  
                String val = (String)iterator.next();  
                writer.println(val);            //prints the property types to the file in alphabetical order(already sorted)
        }
        
        writer.println();   //optional.to organize layout
        for (Iterator<Entry<Integer, Double>> it = priceMap.entrySet().iterator(); it.hasNext();) { //iterate through the tree map 
            Map.Entry entry = it.next();
            writer.println(entry.getKey() + "  "+ entry.getValue());        //write line by line to the file,the pairs (agentID,accumulated total)
        }
        writer.close(); 
        System.out.println("Output data was written to agentreport.txt successfully.End of Application A");
    }
    
    void writeToFileApp_B(){        //creates output file with application B
        System.out.println("\n\t****| Creating Output with Application B |****");
        System.out.println("Writing data to outputfile \"overview.txt\" initiated!");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("overview.txt");       //setup printwriter and new file
        } catch (Exception ex) {
            System.out.println("Error Writing to file");
        }
        System.out.println("File opened successfully!");
        writer.println("Total properties listed: "+propertyId.size());                  //total number of listings print to file
        writer.println("Total Value of properties listed: "+totalValueOfProperties);    // total value of properties print to file
        
        writer.println("");//optional to adjust layout
        
        Collections.sort(propertyId);   //sort the arraylist for property id s
        for(String s: propertyId){      //for each iterator to print property id s to the file
            writer.println(s);
        }
        System.out.println("Processed data was written to overview.txt with Application B successfully.\nSystem will now exit!");
        writer.close(); 
    }
    
}
