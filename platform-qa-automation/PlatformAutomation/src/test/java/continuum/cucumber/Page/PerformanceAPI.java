package continuum.cucumber.Page;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.webservices.JSonAssertionUtility;

public class PerformanceAPI {
	
	
	/**
	 * 
	 * @param objConTemp
	 * @return response of webservice in String format
	 * @throws Exception
	 * This method is used to fetch response of webservice
	 */
	public String getResponseOfWebService(HttpURLConnection objConTemp) throws Exception{
		try {
		 BufferedReader in  = new BufferedReader(new InputStreamReader(objConTemp.getInputStream()));
	        String inputLine = "";
	        StringBuffer response = new StringBuffer();
	        
	        while ((inputLine = in.readLine()) != null) {response.append(inputLine);}
	        in.close();
	        
	        return response.toString();
		}catch(Exception e) {
			JSonAssertionUtility.throwErrorOnDefaultTimeout(e, GlobalVariables.apiData.getUri(), GlobalVariables.apiData.getResponseStatusCode() ,GlobalVariables.apiData.getPayLoad(),
					GlobalVariables.apiData.getResponseMessage(), GlobalVariables.apiData.getAssertionType(), GlobalVariables.apiData.getActualValue(), GlobalVariables.apiData.getExpectedValue(),
					GlobalVariables.apiData.getCustomMessage());
		}
		return null;
	       
	 }
	
	/**
	 * 
	 * @param response
	 * @throws JSONException
	 * This method is used to validate the memory format
	 */
	 public void memoryFormatValidation(String response) throws JSONException{
		 JSONArray jsonArr = new JSONArray(response);
	        
	        for(int i=0; i<jsonArr.length(); i++){
	        	JSONObject jsonObj = jsonArr.getJSONObject(i);
	        	List<String> actual = new ArrayList<String>();
	        	Iterator<String> iterator = jsonObj.keys();
	            while (iterator.hasNext()) {
	                String actualValue = iterator.next();
	                actual.add(actualValue);
	            }
	            
	            Assert.assertEquals(jsonObj.length(), 16);
	            Assert.assertTrue(actual.contains("virtualTotalBytes"), "virtualTotalBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("percentCommittedInUseBytes"), "percentCommittedInUseBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("physicalInUseBytes"), "physicalInUseBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("physicalTotalBytes"), "physicalTotalBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("index"), "index is not found in reponse");
	            Assert.assertTrue(actual.contains("virtualInUseBytes"), "virtualInUseBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("type"), "type is not found in reponse");
	            Assert.assertTrue(actual.contains("poolNonPagedBytes"), "poolNonPagedBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("createTimeUTC"), "createTimeUTC is not found in reponse");
	            Assert.assertTrue(actual.contains("createdBy"), "createdBy is not found in reponse");
	            Assert.assertTrue(actual.contains("virtualAvailableBytes"), "virtualAvailableBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("physicalAvailableBytes"), "physicalAvailableBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("pagesPerSecondBytes"), "pagesPerSecondBytes is not found in reponse");
	            Assert.assertTrue(actual.contains("name"), "name is not found in reponse");
	            Assert.assertTrue(actual.contains("committedBytes"), "committedBytes is not found in reponse");
	           
	        }   
	       
		
	}
	 
	 //Write Response in Json File
	 public void insertJSONObjectInJSONFile(String filePathToWriteTo, JSONObject jsonObjectToWrite) throws IOException {
		 FileWriter file = new FileWriter(filePathToWriteTo);
         try {
                file.write(jsonObjectToWrite.toString());
         } catch (IOException e) {
                e.printStackTrace();
         }
         finally{
			  file.flush();
			  file.close();
		  }
  }

	 public int getTimeStampDifference(String response, String uTCFormatSystemTime) {
			 String[] responseToArray = response.split(",");
		        String strTimeUTC="";
		        int minutes=0;
		        for(int i=0;i<responseToArray.length;i++){
		            if(responseToArray[i].contains("createTimeUTC")){
		            strTimeUTC = responseToArray[i].substring(responseToArray[i].indexOf(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))),responseToArray[i].length()-2);
		            System.out.println("strTimeUTC "+strTimeUTC);

		           minutes = calculateTimeStampDifference(strTimeUTC.replace("T", " "),uTCFormatSystemTime.concat("00").replace("T"," "));
	                System.out.println("Minute Difference "+minutes);
		            Assert.assertFalse(minutes>50, "Incorrect TimeStamp");
		            }
		            
		        }
		        return minutes;
		  }

	 public int calculateTimeStampDifference(String timeStampAfter, String timeStampBefore) {
			// TODO Auto-generated method stub
			 Timestamp timestamp1 = Timestamp.valueOf(timeStampAfter);

		        // create a calendar and assign it the same time
		        Calendar cal = Calendar.getInstance();
		        cal.setTimeInMillis(timestamp1.getTime());

		        // add a bunch of seconds to the calendar
		        cal.add(Calendar.SECOND, 98765);

		        // Fetch a  second time stamp
		        Timestamp timestamp2 = Timestamp.valueOf(timeStampBefore);
		        // get time difference in seconds
		        long milliseconds = timestamp2.getTime() - timestamp1.getTime();
		        int seconds = (int) milliseconds / 1000;

		        // calculate hours minutes and seconds
		        int hours = seconds / 3600;
		        int minutes = (seconds % 3600) / 60;
		        if(hours>=1){
		        	minutes = minutes + (hours*60);
		        }
		        seconds = (seconds % 3600) % 60;
		        return minutes;
		    }

	public String getResponseOfWebServiceWithGZIPDecompression(HttpURLConnection conn) throws IOException {
		// TODO Auto-generated method stub
		  Reader in=null;
		  in  = new InputStreamReader(new GZIPInputStream(conn.getInputStream()));
		  int ch= in.read();
	     while(true){
	    	 
	    	 if(ch==-1){
	    		 break;
	    	 }
	     }
	        
	        return String.valueOf(ch) ;
	}

	 
}
