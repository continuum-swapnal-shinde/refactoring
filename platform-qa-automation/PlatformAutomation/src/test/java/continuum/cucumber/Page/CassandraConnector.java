package continuum.cucumber.Page;

import org.testng.Assert;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Class used for connecting to Cassandra database.
 */
public class CassandraConnector {

	/** Cassandra Cluster. */  
	private Cluster cluster;

	/** Cassandra Session. */  
	private Session cassandraSession;

	/** 
	 * Connect to Cassandra Cluster specified by provided node IP 
	 * address and port number. 
	 * 
	 * @param node Cluster node IP address. 
	 * @param port Port of cluster host. 
	 */ 

	public void connect(String node, int port,String db)  
	{  
			this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
			cassandraSession = cluster.connect(db); 
	}  
	
	/** 
	 * Provide my Session. 
	 * 
	 * @return My session. 
	 */ 

	public Session getSession()  
	{  
		return this.cassandraSession;  
	} 

	/** Close cluster. */  
	public void close()  
	{  
		cluster.close();  
	}
	
	/**
	 * 
	 * @param strName
	 * @param expValue
	 * @param actValue
	 * @param relation
	 * 
	 * This method compares two given strings based on the relation provided
	 */
	public void stringComp(String strName, String expValue, String actValue, String relation){
		expValue = expValue.trim();
		actValue = actValue.trim();
		
		switch(relation.toUpperCase()){
		
		case "EQUALS":
			if(expValue.equals(actValue)){
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected value is "+expValue+" Pass Actual Value is "+actValue);
				Assert.assertEquals(expValue, actValue);
			}
			else{
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected value is "+expValue+" Fail Actual Value is "+actValue);
				Assert.assertEquals(expValue, actValue);
			}
			break;
		
		case "EQUALSIGNORECASE":
			if(expValue.equalsIgnoreCase(actValue)){
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected value is "+expValue+" Pass Actual Value is "+actValue);
				Assert.assertEquals(expValue, actValue);
			}
			else{
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected value is "+expValue+" Fail Actual Value is "+actValue);
				Assert.assertEquals(expValue, actValue);
			}
			break;
		case "CONTAINS":
			if(expValue.contains(actValue)){
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected "+expValue+"contain"+actValue+" Pass "+expValue+"contain "+actValue);
				Assert.assertTrue(expValue.contains(actValue), "expValue found in actValue");
			}
			else{
				GlobalVariables.scenario.write("Verify text value of "+strName+ " Expected "+expValue+"contain"+actValue+" Fail "+expValue+"doesn't contain "+actValue);
				Assert.assertTrue(expValue.contains(actValue), "expValue is not found in actValue");
			}
			break;
		
		
		}
	}


}
