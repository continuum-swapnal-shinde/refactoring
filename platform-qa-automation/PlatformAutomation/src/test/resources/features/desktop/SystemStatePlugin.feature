Feature: Validate System State Information From The System

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Test  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to LastBootUpTime-C2311727
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<SystemStateComponent>" from the system
      And I convert time to UTC Format
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
     And I validate system information with plugin information for system state  
      
     Examples:
      | RowIndex | BatchFile                  | Type           |SystemStateComponent|
      | 1        | systemStateTrigger.bat     | systemState    |LastBootUpTime      |


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to lastLoggedOnUser.
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch registry values of last logged on user
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information wrt lastLoggedOnUser
    
     Examples:
     
		 | RowIndex | BatchFile                 | Type                  |
 		 | 1        | systemStateTrigger.bat    | systemState           | 
 		 

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Logged In User-C2473188
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<SystemStateComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information for system state
    
      
     Examples:
      | RowIndex | BatchFile                  | Type           |SystemStateComponent|
      | 1        | systemStateTrigger.bat     | systemState    |LoggedOnUser        |