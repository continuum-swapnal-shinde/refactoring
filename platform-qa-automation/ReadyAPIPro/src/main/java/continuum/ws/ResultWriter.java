package continuum.ws;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.testng.Assert;

import continuum.ws.listeners.CustomTestRunListener;

public class ResultWriter {
	
	public static void  writeToDB(){
		
		String testResult ="";
		String testDesc ="";
		
		String applicationName = Utility.getMavenProperties("ProjectFolderName");
		String [] coulmnNames = {"testcase_id","testsuite_id", "TestCase_Name","testcase_status",
				"execution_date", "timeexecution", "prod_name", "description" };
		String executionTime = "";
		DBUtilities.connectDB("mysql");
		String tableName = "execution_status";
		
		String testRun=Utility.getMavenProperties("TestRun");
		//String testRun="R1252";
		if(testRun.contains("R"))
		           testRun=testRun.split("R")[1];
		
		Map<String, String> apiResults=CustomTestRunListener.getReport();
		
		if(!apiResults.isEmpty())
		{
		  for(Map.Entry<String, String> testC : apiResults.entrySet()) {
			  testResult = getTestStatusForDB(testC.getValue());
			  testDesc = getTestDescription(testC.getKey());
			    
			     executionTime = String.valueOf(getDuration(testC.getValue()));
			     
			     String [] coulmnValues = {getTcID(testC.getKey()),testRun, testDesc, testResult, getTimestamp() 
							,executionTime, applicationName ,getErrorMsg(testC.getValue())};
			     
			     DBUtilities.insertValues(tableName, coulmnNames, coulmnValues);
			     
			     
		     }
		}
		else
		{
			System.out.println("*****Results not updated in Test Rail  ***** ");
		}
	}
	
	//This Method is added to write the DB Status in String
		/**
		 * 
		 * @param result
		 * @return testStatus as String
		 */
		public static String getTestStatusForDB(String result){
			String status=result.split("-")[0];
			System.out.println(result);
		    String testStatus="";
			switch(status){
	        case "OK":
	        	testStatus = "Pass";
	        	break;
	        case "FAILED":
	        	testStatus = "Fail";
	        	break;
	        case "UNKNOWN":
	        	testStatus = "Blocked";
	        	break;
	        }
			return testStatus;
		}
		
		//This Method is added to write the DB Status in String
				/**
				 * 
				 * @param result
				 * @return testStatus as String
				 */
				public static String getTestDescription(String key){
					String testDesc=key.split("-")[1].trim();
					if(testDesc.contains("\'")){
						testDesc=testDesc.replace("\'", "");
					}
					System.out.println("Test case Description= "+testDesc);
					
					return testDesc;
				}
				
		private static String getTimestamp(){
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		//	System.out.println("Writing result at :: " + formatter.format(date));
			
			return formatter.format(date);
		}
		
		private static String getTcID(String key) {
			if(key.contains("-"))
			{
				 
			String tcID=key.split("-")[0].trim();
			
			if(tcID.contains("C"))
				tcID=tcID.split("C")[1];
			System.out.println("Test case id= "+tcID);
			return tcID;
			}
			else
				 Assert.fail("Test case id is not mentioned against Scenario");
		     return null;	  
		}
		

		private static String getErrorMsg(String result) {
			String errormsg=result.split("-")[2];
			return errormsg;
			
		}


		private static long getDuration(String result) {
			String duration=result.split("-")[1];
			
			return Long.valueOf(duration);
			
			
		}
	
	

}
