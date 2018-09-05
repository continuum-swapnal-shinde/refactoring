Feature: Integration E2E for Agent Installation/Uninstallation

   @Agent-Installation @WinServer3_64Bit @WinServer3_32Bit @WinServer8_Standard @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @WinServer8_Standard
  Scenario Outline: Verify Juno Agent is Installed and asset is invoked-C3059830,C3059831,C3059832,C3059833
    Given SSH connection establishes with remote test machine from tag
    And Agent Silent setup copied to remote machine
    When User Starts M_E_C Consumer Topic
    Then User Download Agent Setup and Install Agent On Machine
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User Stops M_E_C Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the kafka topic
    When User send a Get request for Asset API endpoint as "<GETENDPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User Validate the Asset API whether it is invoked with the updated timestamp

    Examples: 
      | RowIndex | OSEdition           | EndPoint                     | LogLevel | GETENDPoint                                      |
      |        1 | WinServer12R2_64Bit | /agent/v1/EndPointID/execute | DEBUG    | /asset/v1/partner/PartnerID/endpoints/EndPointID |

  @Agent-UnInstallation @WinServer3_64Bit @WinServer3_32Bit @WinServer8_Standard @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @WinServer8_Standard
  Scenario Outline: Verify agent gets Uninstalled through mailbox uninstall message-C2473230
    Given SSH connection establishes with remote test machine from tag
    When User Starts M_E_C Consumer Topic
    Then User fetch registry values of old RMM agent
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User uninstall exe to remove MSI Package through command prompt
    When User Stops M_E_C Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the kafka topic

    Examples: 
      | RowIndex | EndPoint                     | EndPoint                     |
      |        1 | /agent/v1/EndPointID/execute | /agent/v1/EndPointID/execute |

  #@AgentInstallation @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Juno Agent is Installed-C2018903
    Given SSH connection establishes with remote test machine from tag
    When Agent Silent setup copied to remote machine
    Then User Download Agent Setup and Install Agent On Machine

    Examples: 
      | RowIndex | OSEdition  | ServiceStatus | EndPoint                                                  | AvailabilityFlag | RegistrationEndPoint                                         |
      |        1 | Win8_64Bit | RUNNING       | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | true             | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  @LinuxAgentInstallation
  Scenario Outline: Verify Juno Agent is Installed on Linux-C2018903
    Given SSH connection establishes with remote test machine from tag
    When Linux Agent Silent setup copied to remote machine
    When User send a POST request for generate token API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch token for API
    When User downloads debian package from artifactory
    And User fetch AgentCore EndPointID from agentCore config file from tag
    

    Examples: 
      | RowIndex | OSEdition   | POSTEndPoint            |
      |        1 | Linux_64Bit | /agent/v1/generatetoken |
