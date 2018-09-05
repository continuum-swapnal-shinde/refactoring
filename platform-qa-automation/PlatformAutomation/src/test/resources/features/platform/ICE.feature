Feature: Provisioning and Registration API


@ICE   @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API-C1795049,C1795050,C1795053
    Given User read data from excel with rowIndex "<RowIndex>"
    When ICE User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then ICE User should validate the "<Statuscode>" as in response
    And ICE User should fetch response for API
    When ICE User send a Get request for registration API endpoint as "<EndPoint>"
    Then ICE User should validate the status code as "200"OK
    And ICE User should fetch response for API

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | EndPoint                                                       |
      |        1 | /agent/v1/registration |        200 | /agent/v1/partner/ITSPlatform/endpoint/EndPointID/registration |
      
      
 @ICE  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for Registration GET API
    When ICE User send a Get request for API endpoint as "<EndPoint>"
#    Then I should validate the status code as "200"OK
    And ICE User should fetch response for API
    And ICE User should validate the format of Registration of Agent Microservice

    Examples: 
      | RowIndex | EndPoint                                                                       |
      |        1 | /agent/v1/partner/1/endpoint/150cda60-6807-4c07-b939-2099aa200e0b/registration |
      
      
  @ICE  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate system attributes of agent-C1933160
    Given SSH connection establishes with remote test machine from tag
    And User fetch system attributes from agentCore from tag
    When User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When ICE User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And ICE User should fetch response for API
    And ICE User should validate the format of Registration of Agent Microservice

    Examples: 
      | EndPoint                                                     |
      | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |