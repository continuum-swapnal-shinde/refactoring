Feature: VersionPluginToAMS Validation

     @platformSanity 
     Scenario Outline: Validation of information returned by version plugin-C3685604
      Given SSH connection establishes with remote test machine from tag
      When User read data from excel with rowIndex "<RowIndex>"
      Then User fetch EndPointID of the machine from tag
      When User fetch version Information from the system
      Then User fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And User validate system information with version plugin information
      
     Examples:
      | RowIndex | BatchFile              | Type            |
      | 1        | versionTrigger.bat     | version         | 
      
      
     @platformSanity 
      Scenario Outline: E2E Validation for version plugin information with agent MicroService-C3685605
      Given SSH connection establishes with remote test machine from tag
      When User read data from excel with rowIndex "<RowIndex>"
      Then User fetch EndPointID of the machine from tag
      When User fetch version Information from the system
      Then User fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When User pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then User should validate the status code as "200"OK
 	  When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then User should validate the status code as "200"OK
      And User should fetch response for API
      And User compare version contents
     
      Examples:
       | RowIndex | EndPoint                     | Component | GETENDPoint                                                            |
       |        1 | /agent/v1/EndPointID/execute | version   | /agent/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/agentversion |

    
   @platformSanity
    Scenario Outline:Validate version plugin is invoked whenever executeNow POST message is processed-C3685606
	Given SSH connection establishes with remote test machine from tag
    When User fetch EndPointID of the machine from tag
   	Then User read data from excel with rowIndex "<RowIndex>" 
	When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>" 
	Then User should validate the status code as "200"OK
	And User should fetch current timestamp for version
	When User stop the Agent Core Service
    Then User start the Agent Core Service 
	When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>" 
	Then User should fetch new timestamp for version and validate
	
	Examples: 
		 | RowIndex | Component | GETENDPoint                                                            |
      	 |        1 | version   | /agent/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/agentversion |
      	 
      	 
    @platformSanity
    Scenario Outline:Validate version plugin is invoked whenever agent core is restarted-C3685607
	Given SSH connection establishes with remote test machine from tag
    When User fetch EndPointID of the machine from tag
    Then User read data from excel with rowIndex "<RowIndex>" 
	When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>" 
	Then User should validate the status code as "200"OK
	And User should fetch current timestamp for version
    When User stop the Agent Core Service
    Then User start the Agent Core Service
	When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>" 
	Then User should fetch new timestamp for version and validate
	
	Examples: 
      | RowIndex | Component | GETENDPoint                                                            |
      |        1 | version   | /agent/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/agentversion |


		
    @platformSanity
    Scenario Outline:Validate version API wrt partnerIds-C3685608
	Given SSH connection establishes with remote test machine from tag
    When User fetch EndPointID of the machine from tag
    Then User read data from excel with rowIndex "<RowIndex>" 
	When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>" 
	Then User should validate the status code as "200"OK
	And User should validate version API response
	
	Examples: 
		| RowIndex | Component | GETENDPoint                                    |
        |        1 | version   | /agent/v1/partner/RandomPartnerID/agentversion |

	
