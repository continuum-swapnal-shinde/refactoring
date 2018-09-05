Feature: Scenarios for Build Verification Testing

@Desktopbvt @Win10_32Bit
Scenario Outline: As a partner I should see the updated site after site migration-C3883233
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I pass payload for site mapping POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
Examples:
       | RowIndex | POSTENDPoint                                   | 
       | 1        | /agent/v1/updatemapping                        | 
      
@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent data related to asset should be pushed to Kafka-C3883234
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
 Examples:
       | RowIndex |POSTENDPoint                                   | 
       | 1        | /agent/v1/RandomEndPoint/asset                |   


@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data of a specific endpoint of a partner from Kafka via Asset MS-C3883235
      Given I read GET response from a micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                            | 
       | 1        | /asset/v1/partner/50016367/endpoints/16747c6e-7351-4172-973f-57753ef081be               | 
       
@Win10_32Bit @Desktopbvt     
Scenario Outline: As a partner I should get the data of all the endpoints of a specific partner via Asset MS-C3883236
      Given I read GET response from a micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                            | 
       | 1        | /asset/v1/partner/50016367/endpoints                                                  | 
       

@Desktopbvt @Win10_32Bit
Scenario Outline: As a partner I should be able to update friendlyname of an endpoint-C3883237
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I send a PUT request as "<PUTEndPoint>" for "<FriendlyName>"
      Then I should validate the status code as "204"No Content
      
Examples:
       | RowIndex   | PUTEndPoint                                                                              |FriendlyName | 
       | 1          | /asset/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/friendlyname                   | Win10-32ACT |
       
@Regression @Desktopbvt @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validate SITE REST API of asset MS for a given partner and site Id-C3883238
      When I read GET response from a micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I should validate API model for Site API
      
       Examples:
       | RowIndex | GETENDPoint                                        |                              
       | 1        | /asset/v1/partner/50001794/sites/50110521/summary  | 

@Regression @Desktopbvt @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @RegressionAsset @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Validate SITE REST API of asset MS for a given partner and multiple site Ids-C3883239
      When I read GET response from a micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK
      
       Examples:
       | RowIndex | GETENDPoint                                                 |                              
       | 1        | /asset/v1/partner/50001794/sites/50110524,50110521/summary  | 
  
@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent I should be able to post data related to performance processor on Kafka-C3883240
Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcessor | perfProcessorTrigger.bat|performance     |

@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent I should be able to post data related to performance memory on Kafka-C3883241
Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceMemory | perfMemoryTrigger.bat|performance     |
      
@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent I should be able to post data related to performance network on Kafka-C3883242
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceNetwork   | perfNetworkTrigger.bat|performance     |
      
@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent I should be able to post data related to performance storage on Kafka-C3883243
Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceStorage   | perfStorageTrigger.bat|performance     |
      

@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data related to processor of a specific endpoint of a partner from Kafka via Performance MS-C3883244
      Given I read GET response from a performance micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                                              | 
       | 1        | /performance/v1/partner/50016367/endpoint/16747c6e-7351-4172-973f-57753ef081be/processors               | 

@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data related to memory of a specific endpoint of a partner from Kafka via Performance MS-C3883245
      Given I read GET response from a performance micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                                              | 
       | 1        | /performance/v1/partner/50016367/endpoint/16747c6e-7351-4172-973f-57753ef081be/memory               | 

@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data related to storage of a specific endpoint of a partner from Kafka via Performance MS-C3883246
      Given I read GET response from a performance micro-service as "<GETENDPoint>"
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                                              | 
       | 1        | /performance/v1/partner/50016367/endpoint/16747c6e-7351-4172-973f-57753ef081be/storage               | 

@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data related to network of a specific endpoint of a partner from Kafka via Performance MS-C3883247
      Given I read GET response from a performance micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                                              | 
       | 1        | /performance/v1/partner/50016367/endpoint/16747c6e-7351-4172-973f-57753ef081be/network               | 


@Win10_32Bit @Desktopbvt
Scenario Outline: As an agent I should be able to post data related to system state on Kafka-C3883248
Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>"
      When I pass content as payload in agent-service POST as "<POSTENDPoint>"
      Then I should validate the status code as "200"OK
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   |  BatchFile              | Type           |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/systemstate          | systemStateTrigger.bat  |systemState     |
      
@Win10_32Bit @Desktopbvt
Scenario Outline: As a partner I should get the data related to systemstate of a specific endpoint of a partner from Kafka via SystemState MS-C3883250
      Given I read GET response from a systemstate micro-service as "<GETENDPoint>"  
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                                              | 
       | 1        | /systemstate/v1/partner/50016367/endpoints/16747c6e-7351-4172-973f-57753ef081be                         | 

@Win10_32Bit @Desktopbvt     
Scenario Outline: As a partner I should get the data of all the endpoints of a specific partner via SystemState MS-C3883251
      Given I read GET response from a systemstate micro-service as "<GETENDPoint>"   
      Then I should validate the status code as "200"OK  
      
Examples:
       | RowIndex |GETENDPoint                                                                            | 
       | 1        | /systemstate/v1/partner/50016367/endpoints                                                  | 
       
    