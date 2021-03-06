package checkmeController;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.omg.CORBA.portable.OutputStream;

import application.myApp;

public class ConnectToServer {

	public static String connect(String  POST_PARAMS,String URL)
	{
	    URL obj = null;
	    HttpURLConnection con = null;
		try {
	        obj = new URL(URL); // url/server to get a connection to
	        con = (HttpURLConnection) obj.openConnection(); // open a new connection to server
	        con.setRequestMethod("POST");  // POST method has been chosen

	        // For POST only - BEGIN
	        con.setDoOutput(true);
	        java.io.OutputStream os =  con.getOutputStream();
	        os.write(POST_PARAMS.getBytes());
	        os.flush();
	        os.close();
	        // For POST only - END

	        int responseCode = con.getResponseCode(); // getting the response code from server
	        System.out.println( "POST Response Code :: " + responseCode);

	        if (responseCode == HttpURLConnection.HTTP_OK) { //success
	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) { //checking response from server
	                response.append(inputLine);
	                POST_PARAMS=inputLine;
	            }
	            System.out.println( " response buffer :" +response);
	            in.close();
	            inputLine = response.toString();
	            return inputLine;
	        }
		}
		 catch (IOException e) {
  	       
 	        System.out.println("Exception : " + e.getMessage());
 	    }
		return null;
	}
	
	public static String connectget(String  hash_id,String URL)
	{
	    URL obj = null;
	    HttpURLConnection con = null;
		try {
	        obj = new URL(URL+"?hash="+hash_id); // url/server to get a connection to
	        con = (HttpURLConnection) obj.openConnection(); // open a new connection to server
	        con.setRequestMethod("GET");  // POST method has been chosen

	        // For POST only - BEGIN
	        con.setDoOutput(true);
	        java.io.OutputStream os =  con.getOutputStream();
	       // os.write(POST_PARAMS.getBytes());
	        os.flush();
	        os.close();
	        // For POST only - END

	        int responseCode = con.getResponseCode(); // getting the response code from server
	        System.out.println( "POST Response Code :: " + responseCode);

	        if (responseCode == HttpURLConnection.HTTP_OK) { //success
	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) { //checking response from server
	                response.append(inputLine);
	               // POST_PARAMS=inputLine;
	            }
	            System.out.println( " response buffer :" +response);
	            in.close();
	            inputLine = response.toString();
	            return inputLine;
	        }
		}
		 catch (IOException e) {
  	       
 	        System.out.println("Exception : " + e.getMessage());
 	    }
		return null;
	}
	public static String connectwriting (String username ,String pass , String mail  )
	{
		String POST_PARAMS="&username=" +username+"&password="+pass+"&mail="+mail;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"register.php");
		
		System.out.println(inputLine);
		  
			return inputLine;
		

	}
	  
	public static String connectwritingcheck (String checknum ,String banknum , String branchnum, String accountnum,String amount,String date,String personid,String   uploader, String  hash)
	{
		String POST_PARAMS="&checknum=" +checknum + "&banknum=" +banknum+ "&branchnum=" + branchnum+ "&accountnum=" +accountnum+"&amount=" + amount+ "&date=" + date+ "&personid=" + personid+ "&uploader=" + uploader + "&hash=" + hash;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"validate.php");
		
		System.out.println(inputLine);
		  
			return inputLine;
		

	}
	
	
	
	 
	public static boolean Checkuser(String userName, String password)
	{
		String POST_PARAMS="&username=" +userName+ "&password="+password;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"login.php");
        if (inputLine.contains("User Found")) 
        	return true;
        else
		return false;
	}
	
	public static String getDatachecks( String CheckNum)
	
	{
		//String POST_PARAMS="&hash=" +CheckNum;
		return connectget(CheckNum,myApp.getUrl()+"get_check_details.php");
		 
	}
	public static String  getforgetpassword(String mail)
	{
		String POST_PARAMS="&mail=" +mail;
		String password = connect(POST_PARAMS,myApp.getUrl()+"forgotpassword.php");
		return password;
	}
	
	
	public static String  getCheckhistory(String username)
	{
		String POST_PARAMS="&uploader=" +username;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"GetAllChecks.php");
		return inputLine;
	}
	public static String  gethistoryCheckfeedback(String hash)
	{
		String POST_PARAMS="&hash=" +hash;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"get_single_check_history.php");
		return inputLine;
	}
	
	public static String  savehistorysCheckfeedback(String hash,String date,String checkstatus)
	{
		String POST_PARAMS="&hash=" +hash+"&date=" +date+"&checkstatus=" +checkstatus;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"AddCheckHistory.php");
		return inputLine;
	}
	
	
	
	public static String  savestatusCheckfeedback(String hash,String amount,String date,String checkstatus)
	{
		String POST_PARAMS="&hash=" +hash+"&amount=" +amount+"&date=" +date+"&checkstatus=" +checkstatus;
		String inputLine = connect(POST_PARAMS,myApp.getUrl()+"update_check.php");
		return inputLine;
	}
	
	
	
	
	
	
	
}