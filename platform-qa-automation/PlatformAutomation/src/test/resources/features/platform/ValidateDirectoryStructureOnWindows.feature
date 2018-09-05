Feature: Validate Directory Structure of Agent

  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario: Validate directory structure on installation of agent-C1836898
    Given SSH connection establishes with remote test machine from tag
    When MSI is installed on windows machine
    Then Verify the directory structure on windows platform from tag