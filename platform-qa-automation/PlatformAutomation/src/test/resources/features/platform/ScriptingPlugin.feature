Feature: Scripting Plugin Scenarios

  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Agent Core is able to update and communicate with the schedule config file with scripting plugin-C2312027
    Given SSH connection establishes with remote test machine from tag
    When User Starts Script Execution Result Consumer Topic
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for scripting plugin as "<EndPoint>" and "<ScriptCommand>"
    Then User should validate the status code as "200"OK
    When User Stops Script Execution Result Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the Script Execution Result kafka topic as "<ExpectedMessage>"

    Examples: 
      | RowIndex | EndPoint                     | ExpectedMessage          | ScriptCommand |
      |        1 | /agent/v1/EndPointID/execute | Windows IP Configuration | ipconfig      |

  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify that scripting plugin returns Failed if script has syntax error-C2620485
    Given SSH connection establishes with remote test machine from tag
    When User Starts Script Execution Result Consumer Topic
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for scripting plugin as "<EndPoint>" and "<ScriptCommand>"
    Then User should validate the status code as "200"OK
    When User Stops Script Execution Result Consumer Topic and Fetch Data
    Then User Validate the Data fetch from the Script Execution Result kafka topic as "<ExpectedMessage>"

    Examples: 
      | RowIndex | EndPoint                     | ExpectedMessage  | ScriptCommand    |
      |        1 | /agent/v1/EndPointID/execute | ipconfig_invalid | ipconfig_invalid |

  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify scripting plugin is killeed by agent core after plugin timeout-C3059828
    Given SSH connection establishes with remote test machine from tag
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for scripting plugin as "<EndPoint>" and "<ScriptCommand>"
    Then User should validate the status code as "200"OK
    And User fetch Process ID of Scripting Plugin as "<ProcessStatus>"
    Then User wait for 60sec and validate whether process is killed by agent core and status is "<ProcessStatusOne>"

    Examples: 
      | RowIndex | EndPoint                     | ProcessStatus | ProcessStatusOne          |
      |        1 | /agent/v1/EndPointID/execute | ProcessId     | No Instance(s) Available. |
