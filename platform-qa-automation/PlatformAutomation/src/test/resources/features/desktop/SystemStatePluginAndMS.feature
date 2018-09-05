Feature: System State Micro-services-Plugin Data Validation-Windows platform


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance MS For Site Migration-C3003692
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I pass payload for site mapping POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I compare the siteIdValue for the above endpoint
      When I pass payload for site mapping POST as "<POSTENDPoint>" again
      Then I should validate the status code as "200"OK
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I again compare the siteIdValue for the above endpoint
      
      
      
     
      Examples:
       | RowIndex | POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type                            |
       | 1        | /agent/v1/updatemapping                        | /systemstate/v1/partner/RandomPartnerID/endpoints/RandomEndPoint          | assetTrigger.bat        |systemStateTrigger.bat           |
      


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for System State MicroService with Partner Id EndPoint Id-C2311724
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I compare the file contents for "<Type>"
      
      
      
     
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/systemstate          | /systemstate/v1/partner/RandomPartnerID/endpoints/RandomEndPoint          | systemStateTrigger.bat  |systemState     |
