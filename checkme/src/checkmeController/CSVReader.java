package checkmeController;
 import java.io.BufferedReader;
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
		            	  System.out.println("check number " + country[0] + " , bank number=" +country[1]
              		 			+"branch number " + country[2] + " , account number=" +country[3] +"]");
		                 // use comma as separator
		               return  line.split(cvsSplitBy);

		               

		             }

		         } catch (IOException e) {
		             e.printStackTrace();
		         }
				return null;

		     }

		
	}

