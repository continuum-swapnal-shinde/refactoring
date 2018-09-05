Feature: Plugin (Asset,Performance,SystemState) Scheduling,Invocation and service response verification 
	
@Regression  @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
Scenario Outline:Validate performance plugin schedule configuartion is changed whenever schedule POST message is posted-C1773017
	Given SSH connection established with remote test machine from tag
    And I fetch AgentCore EndPointID from agentCore config file from tag
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration change Post request for mailbox API endpoint as "<EndPoint>" 
	Then I should validate the status code as "200"OK 
	And I fetch and validate updated plugin schedule for "<Component>"
	When I fetch registry values of old RMM agent 
	When I read GET response from performance micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch previous timestamp for "<Component>" API 
	When I read GET response from performance micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch new timestamp for "<Component>" API 
	And I validate a minute difference between subsequent timestamps
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration restore Post request for mailbox API endpoint as "<EndPoint>"
	Then I should validate the status code as "200"OK 
	#And I Close SSH Connection 
	
	Examples: 
		| RowIndex | EndPoint                     | Component | 	GETENDPoint 														| 
		|        1 | /agent/v1/EndPointID/execute | Processor | /performance/v1/partner/PartnerID/endpoint/EndPointID/processors		| 
		|        2 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/partner/PartnerID/endpoint/EndPointID/memory       	    | 
		|        3 | /agent/v1/EndPointID/execute | Storage   | /performance/v1/partner/PartnerID/endpoint/EndPointID/storage		    | 
		|        4 | /agent/v1/EndPointID/execute | Network   | /performance/v1/partner/PartnerID/endpoint/EndPointID/network 			| 
		|        5 | /agent/v1/EndPointID/execute | Process   | /performance/v1/partner/PartnerID/endpoint/EndPointID/process 			| 
		
		
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
Scenario Outline:Validate asset plugin schedule configuartion is changed whenever schedule POST message is posted-C1045089
	Given SSH connection established with remote test machine from tag
    And I fetch AgentCore EndPointID from agentCore config file from tag
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration change Post request for mailbox API endpoint as "<EndPoint>" 
	Then I should validate the status code as "200"OK 
	And I fetch and validate updated plugin schedule for "<Component>"
	When I fetch registry values of old RMM agent
	When I read GET response from micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch previous timestamp for "<Component>" API 
	When I read GET response from micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch new timestamp for "<Component>" API 
	And I validate a minute difference between subsequent timestamps
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration restore Post request for mailbox API endpoint as "<EndPoint>"
	Then I should validate the status code as "200"OK 
	#And I Close SSH Connection 
	
	Examples: 
	
		| RowIndex | EndPoint                     | Component | 	GETENDPoint 								    |	
		|        1 | /agent/v1/EndPointID/execute | Asset     | /asset/v1/partner/PartnerID/endpoints/EndPointID	|
		

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
Scenario Outline:Validate systemstate plugin schedule configuartion is changed whenever schedule POST message is posted-C2364252
	Given SSH connection established with remote test machine from tag
  And I fetch AgentCore EndPointID from agentCore config file from tag
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration change Post request for mailbox API endpoint as "<EndPoint>" 
	Then I should validate the status code as "200"OK 
	And I fetch and validate updated plugin schedule for "<Component>"
	When I fetch registry values of old RMM agent 
	When I read GET response from ss micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch previous timestamp for "<Component>" API 
	When I read GET response from ss micro-service and store in file with GET as "<GETENDPoint>" 
	And I should fetch new timestamp for "<Component>" API 
	And I validate a minute difference between subsequent timestamps
	Given I read data from excel with rowIndex "<RowIndex>" 
	And I send a configuration restore Post request for mailbox API endpoint as "<EndPoint>"
	Then I should validate the status code as "200"OK 
	#And I Close SSH Connection 
	
	Examples: 
		| RowIndex | EndPoint                     | Component   | 	GETENDPoint 														|
		|        1 | /agent/v1/EndPointID/execute | SystemState | /systemstate/v1/partner/PartnerID/endpoints/EndPointID				|
