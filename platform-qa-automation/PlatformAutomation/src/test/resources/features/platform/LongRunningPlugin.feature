Feature: Long Running Plugin Scenarios

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Agent Core is able update the config file of LRP-C2145807,C2145808
    Given SSH connection establishes with remote test machine from tag
    And User fetch AgentCore EndPointID from agentCore config file from tag
    And User send a Post request for mailbox API LRP endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User fetch and validate event log plugin json from tag "<LogLevel>"

    Examples: 
      | RowIndex | ServiceStatus | EndPoint                     | LogLevel |
      |        1 | RUNNING       | /agent/v1/EndPointID/execute | DEBUG    |

 @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Agent Core is able to communicate with LRP-C2145806
    Given SSH connection establishes with remote test machine from tag
    When User Starts Event Log Consumer Topic
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    When User Stops Event Log Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the Event Log kafka topic as "<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | LogName | Source | EventID | EntryType | Message                                     | ServiceStatus | ExpectedMessage | EndPoint                     |
      |        1 | System  | Ntfs   |      55 | Error     | Disk File System Error Event Via Powershell | RUNNING       | Ntfs            | /agent/v1/EndPointID/execute |

  @PlatformRestart @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Agent Core is able to invoke the LRP and the Normal Shutdown Functionality-C2145805,C2145809,C1836910
    Given SSH connection establishes with remote test machine from tag
    When User stop the Agent Core Service
    And User fetch Process ID of AgentCore as "<ProcessStatusOne>"
    And User fetch Process ID of EventLogPlugin as "<ProcessStatusOne>"
    And User start the Agent Core Service

    Examples: 
      | RowIndex | ServiceStatus | ProcessStatus | ServiceStatusTwo | ProcessStatusOne          |
      |        1 | RUNNING       | ProcessId     | Stopped          | No Instance(s) Available. |
