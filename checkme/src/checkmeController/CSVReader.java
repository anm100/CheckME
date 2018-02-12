package checkmeController;
 import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
		 import java.io.IOException;

public class CSVReader {
	
	
		     public static String[] reader() {

		    	 String csvFile = "C:/IDPMicr/Log.CSV";
		         String line = "";
		         String cvsSplitBy = ",";

		         try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

		             while ((line = br.readLine()) != null) {
		            	 String[] country=line.split(cvsSplitBy);
		            	  System.out.println("check number " + country[0] + " , bank number=" + country[1]
              		 			+  "  branch number " + country[2] + " , account number=" +country[3] +"]");
		                 // use comma as separator
		               return  line.split(cvsSplitBy);

		               

		             }

		         } catch (IOException e) {
		             e.printStackTrace();
		         }
				return null;

		     }
		     
		     
		     public static void delete() {
		    	 
		    	 try{

		     		File file = new File("C:/IDPMicr/Log.CSV");

		     		if(file.delete()){
		     			System.out.println(file.getName() + " is deleted!");
		     		}else{
		     			System.out.println("Delete operation is failed.");
		     		}

		     	}catch(Exception e){

		     		e.printStackTrace();

		     	}

		     }

		     
		     public static String urlreader() {
		    	 String urlFile = "C:/finalProject/CheckME/url.txt";
		    	 
		         String line = "";
		         String cvsSplitBy = ",";

		         try (BufferedReader br = new BufferedReader(new FileReader(urlFile))) {

		             while ((line = br.readLine()) != null) {
		            	 String[] country=line.split(cvsSplitBy);
		         

		               return line.toString();

		             }

		         } catch (IOException e) {
		             e.printStackTrace();
		         }
				return null;

		     }
		     
		
	}

