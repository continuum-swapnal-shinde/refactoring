Feature: MSI Installation and Uninstallation

	@PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario: Verify agent build version on machines-C1836911
    Given SSH connection establishes with remote test machine from tag
    And User fetch and validate entry from event viewer as "<Version>"
	
	@PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario: Verify Existing RMM Agent is running on machine-C1836892
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent

	@PlatformRestart @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify Juno Platform Agent Core Service is getting restarted in timely fashion-C1836917
    Given SSH connection establishes with remote test machine from tag
    And User fetch service status "<ServiceStatusOne>"
    When User fetch process ID and Stopped the Service
    Then User fetch service status "<ServiceStatusTwo>"
    And User wait for Service to restart
    And User fetch service status "<ServiceStatusOne>"
    

    Examples: 
      | RowIndex |  ServiceStatusOne | ServiceStatusTwo |
      |        1 |  RUNNING          | Stopped          |
      
	@PlatformRestart @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify ITSPlatform service is getting started by ITSPlatformManager when stopped
    Given SSH connection establishes with remote test machine from tag
    When User stop the Agent Core Service
    Then User fetch service status "<ServiceStatusTwo>"
    And User wait for Service to start
    And User fetch service status "<ServiceStatusOne>"
    

    Examples: 
      | RowIndex |  ServiceStatusOne | ServiceStatusTwo |
      |        1 |  RUNNING          | Stopped          |

	#@PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate Old RMM Agent is installed on the Machine-C1836891
    Given SSH connection establishes with remote test machine from tag
    And User fetch service status "<ServiceStatus>"

    Examples: 
      | RowIndex |  ServiceStatus |
      |        1 |  RUNNING       |
	
 @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate Agent core config file contains all the plugins-C1836898
    Given SSH connection establishes with remote test machine from tag
    And User fetch and Validate plugins from agentCore config file for "<Component>"

    Examples: 
      | RowIndex |  Component   |
      |        1 |  asset       |
      |        2 |  performance |
      |        3 |  scripting   |
      |        4 |  eventlog    |
      
   @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit 
  Scenario Outline: Validate all the plugins except asset plugin are disabled in Agent core config file
    Given SSH connection establishes with remote test machine from tag
    And User fetch and Validate plugins from agentCore config file for "<Component>"
    

    Examples: 
      | RowIndex |  Component   |
      |        1 |  asset       |
      |        2 |  performance |
      |        3 |  scripting   |
      |        4 |  eventlog    |