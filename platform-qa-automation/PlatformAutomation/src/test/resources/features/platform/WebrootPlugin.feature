Feature: Webroot Plugin Scenarios

   @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
   Scenario Outline: Verify Agent Microservice is able to push messages in Webroot Plugin Kafka Topic-C3621661
    Given SSH connection establishes with remote test machine from tag
    When User Starts Webroot Consumer Topic
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for webroot plugin as "<EndPoint>" and "<ScriptCommand>"
    Then User should validate the status code as "200"OK
    When User Stops Webroot Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the Webroot kafka topic as "<ExpectedMessage>"
 
 	 Examples: 
      | RowIndex | EndPoint                     | ExpectedMessage | ScriptCommand |
      |        1 | /agent/v1/EndPointID/webroot | deepScan        | deepScan      |
