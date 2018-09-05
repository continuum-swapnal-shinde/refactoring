Feature: Asset Micro-services-Plugin Data Validation-Windows platform


@Regression1 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService For Site Migration-C3003690
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
       | RowIndex | POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |
       | 1        | /agent/v1/updatemapping                        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint                | assetTrigger.bat        |asset           |
      


@Regression @Desktopbvt @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id with invalid EndPoint Id-C2626507
      Given I read GET response from a micro-service as "<GETENDPoint>"
      Then I should validate the status code as "<404>"Not Found
      
     Examples:
       | RowIndex | GETENDPoint                                |                              
       | 1        | /asset/v1/partner/50001794/endpoints/6778  | 
       | 2        | /asset/v1/partner/@345/endpoints/6778      | 
           


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validate SITE REST API of asset MS for a given partner and invalid site Id.
      When I read GET response from a micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "404"Not Found
     
      
       Examples:
       | RowIndex | GETENDPoint                                         |                              
       | 1        | /asset/v1/partner/50001794/sites/5011052sd/summary  | 
       
       
 @Regression  @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of asset MS for realtime capture when user details are modified.
      Given SSH connection established with remote test machine from tag
      And I fetch AgentCore EndPointID from agentCore config file from tag
      And I read data from excel with rowIndex "<RowIndex>"
      When I fetch registry values of old RMM agent
      And I configure details on machine with "<changes>"
      When I read GET response from micro-service and store in file with GET as "<GETENDPoint>" 
 	  Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I should validate the Data of the response of asset API for real time changes with "<changes>"
      
     Examples:
      | RowIndex | changes	          | GETENDPoint                                                 |
      | 1        | userAdd.bat        |/asset/v1/partner/PartnerID/endpoints/EndPointID?field=users |
      | 2        | userDelete.bat     | /asset/v1/partner/PartnerID/endpoints/EndPointID?field=users|
 

 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of asset MS for realtime capture when service status is changed.
      Given SSH connection established with remote test machine from tag
      And I fetch AgentCore EndPointID from agentCore config file from tag
      And I read data from excel with rowIndex "<RowIndex>"
      When I fetch registry values of old RMM agent
      And I configure details on machine with "<serviceState>"
	  When I read GET response from micro-service and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I should validate the Data of the response of asset API for real time changes with "<serviceState>"

     Examples:
      | RowIndex | serviceState       | GETENDPoint 									                  |
      | 1        | stop               | /asset/v1/partner/PartnerID/endpoints/EndPointID?field=services |
      | 2        | start              | /asset/v1/partner/PartnerID/endpoints/EndPointID?field=services |
      
      
 
 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of asset MS for realtime capture when service itself is modified.
      Given SSH connection established with remote test machine from tag
      And I fetch AgentCore EndPointID from agentCore config file from tag
      And I read data from excel with rowIndex "<RowIndex>"
      When I fetch registry values of old RMM agent
      And I configure details on machine with "<serviceMod>"
	  When I read GET response from micro-service and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I should validate the Data of the response of asset API for real time changes with "<serviceMod>"
      
     Examples:
      | RowIndex | serviceMod      		| GETENDPoint 									                |
      | 1        | create               | /asset/v1/partner/PartnerID/endpoints/EndPointID?field=services |
      | 2        | delete               | /asset/v1/partner/PartnerID/endpoints/EndPointID?field=services |

 	
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id EndPoint Id with GZIP Compression-C2571185
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>" with Content Encoding
      Then I should validate the status code as "200"OK
      And I should validate Content Encoding for API
     
     
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type                   |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/asset                | /asset/v1/partner/RandomPartnerID/endpoints?field=os                              | assetTrigger.bat        |asset           |
  

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id EndPoint Id-C2223496
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
      And I validate the contract of Asset MS
      And I write the details to JSON File
      
      
     
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/asset                | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint                | assetTrigger.bat        |asset           |
    
      

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset   @WinServer8_Standard  @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id EndPoint Id without Service Pack-C2622070
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
      And I validate the DG Attributes
      And I write the details to JSON File
      
    
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/asset                | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint                | assetTrigger.bat        |asset           |


 

 @Regression @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of Friendly Name for Asset MicroService via PUT Method for Empty FriendlyName-C2473189
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<FriendlyName>"
      Then I should validate the status code as "400"Bad Request
    
    Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile              | Type           |FriendlyName    |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/friendlyname    | assetTrigger.bat        |asset           |                |


 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of Friendly Name for Asset MicroService via PUT Method for UnMapped Partner and EndPointID-C2473190
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<FriendlyName>"
      Then I should validate the status code as "404"Not Found
     
     Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile    | Type           |FriendlyName         |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/ABCD/friendlyname    | assetTrigger.bat        |asset           |Destop Team System   |

     

@Regression  @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of Friendly Name for Asset MicroService via PUT Method-C2454345
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<FriendlyName>"
      Then I should validate the status code as "204"No Content
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate "<FriendlyName>" in the get response 
      When I send a PUT request as "<PUTEndPoint>" for "<FriendlyName>" again
      Then I should validate the status code as "400"Bad Request
      When I send a PUT request as "<PUTEndPoint>" with system name
      Then I should validate the status code as "204"No Content 
    
     Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile              | Type           |FriendlyName    |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/friendlyname    | assetTrigger.bat        |asset           |Juno-Automation-Test    |
         

@Regression @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of endpoint type for Asset MicroService via PUT Method for Empty endpoint type-C3904290
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<EndpointType>"
      Then I should validate the status code as "400"Bad Request
    
    Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile              | Type           |EndpointType    |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/endpointtype   | assetTrigger.bat        |asset           |                |


 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of end point type for Asset MicroService via PUT Method for UnMapped Partner and EndPointID-C3904291
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<EndpointType>"
      Then I should validate the status code as "404"Not Found
     
     Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile    | Type           |EndpointType         |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/ABCD/endpointtype    | assetTrigger.bat        |asset           |Destop Team System   |

     

@Regression  @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation of endpoint type for Asset MicroService via PUT Method-C3904292
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<EndpointType>"
      Then I should validate the status code as "204"No Content
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate "<EndpointType>" in the get response 
      When I send a PUT request as "<PUTEndPoint>" for "<EndpointType>" again
      Then I should validate the status code as "400"Bad Request
      When I send a PUT request as "<PUTEndPoint>" with system name
      Then I should validate the status code as "204"No Content 
    
     Examples:
       | RowIndex |GETENDPoint                                                  | PUTEndPoint                                                               |  BatchFile              | Type           |EndpointType    |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/endpointtype    | assetTrigger.bat        |asset           |DESKTOP         |         
       | 2        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint  | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/endpointtype    | assetTrigger.bat        |asset           |SERVER          |
     
     
  
  @Regression @Desktopbvt @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation for Projection of Attributes For Asset MicroService-C2517063
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate the contents of the Service for "<Attribute>"
     
     
  Examples:
       | RowIndex |GETENDPointQueryParameter                                                            |Attribute      |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=os                 |os             |
       | 2        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=drives             |drives         |
       | 3        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=networks           |networks       |
       | 4        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=processors         |processors     |     
       | 5        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=physicalMemory     |physicalMemory |
       | 6        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=installedSoftwares |installedSoftwares |
       | 7        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=keyboards          |keyboards |
       | 8        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=mouse              |mouse |
       | 10       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=physicalDrives     |physicalDrives |
       | 11       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=baseBoard          |baseBoard |
       | 12       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=bios               |bios |
       | 13       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=system       |system |
       | 14       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=os&field=baseBoard |osBaseBoard  |
       | 15       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=drives&field=networks |drivesNetworks |
       | 16       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=keyboards&field=mouse |keyboardsMouse |
       | 17       | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=installedSoftwares&field=users&field=friendlyName|installedSoftwaresUsers |
      
      
      @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit  @Win8_64Bit @Win10_32Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation for Projection of Attributes For Asset MicroService-C2517063
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate the contents of the Service for "<Attribute>"
      
       
        Examples:
       | RowIndex |GETENDPointQueryParameter                                                            |Attribute      |
       | 9        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=monitors           |monitors |
      
     @Regression @Desktopbvt @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validation for Projection of Attributes For Asset MicroService For Incorrect Attributes-C2517223
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "400"Bad Request
      
      Examples:
       | RowIndex |GETENDPointQueryParameter                                                            |
       | 1        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=os1                |             
       | 2        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=drives@            |
       | 3        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=networks2          |
       | 4        | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint?field=processorss        |
       
       
     @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id EndPoint Id for uninstalled Agent-C2679857
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I hit DELETE as "<DeleteENDPoint>" to remove the agent
      Then I should validate the status code as "200"OK
      When I read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then I should validate the status code as "404"Not Found
      
  
      Examples:
         | RowIndex |DeleteENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |
         | 1        | /agent/v1/RandomEndPoint/heartbeat              | /asset/v1/partner/RandomPartnerID/endpoints/RandomEndPoint                | assetTrigger.bat        |asset           |

                