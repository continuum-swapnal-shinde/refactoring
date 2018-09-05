Feature: Performance Micro-services-Plugin Data Validation-Windows platform


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Asset MicroService with Partner Id EndPoint Id with GZIP Compression-C2571185
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
       | RowIndex | POSTENDPoint                                   | GETENDPoint                                                                      |  BatchFile                | Type                 |
       | 1        | /agent/v1/updatemapping                        | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/process          | perfProcessTrigger.bat    |performance           |
       | 2        | /agent/v1/updatemapping                        | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors       | perfProcessorTrigger.bat  |performance           |
       | 3        | /agent/v1/updatemapping                        | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/network          | perfNetworkTrigger.bat    |performance           |
       | 4        | /agent/v1/updatemapping                        | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/storage          | perfStorageTrigger.bat    |performance           |
       | 5        | /agent/v1/updatemapping                        | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory           | perfMemoryTrigger.bat     |performance           |
      
 
 
 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance Process MicroService with Partner Id EndPoint Id-C2148028
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
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcess   | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/process   | perfProcessTrigger.bat  |performance     |

      

 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance Processor MicroService with Partner Id EndPoint Id-C2223492
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
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcessor | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors| perfProcessorTrigger.bat|performance     |

 
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @PFD @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
     Scenario Outline: Time Range Validation for Performance Memory MicroService-C2487750
     Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "200"OK
      And I should fetch the response for API
      And I fetch LocalTime from the system
      And I convert the Time to UTC Format
      And I validate the response between stipulated time
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type                   |GETENDPointQueryParameter                                                             |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcessors    | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors    | perfMemoryTrigger.bat   |performance     | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors?minutes=50    |
       


 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance Network MicroService with Partner Id EndPoint Id for-C2223493
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
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceNetwork   | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/network   | perfNetworkTrigger.bat  |performance     |

    
    
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance Memory MicroService with Partner Id EndPoint Id-C2223494
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
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceMemory    | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory    | perfMemoryTrigger.bat   |performance     |

     
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Time Range Validation for Performance Memory MicroService-C2487750
     Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "200"OK
      And I should fetch the response for API
      And I fetch LocalTime from the system
      And I convert the Time to UTC Format
      And I validate the response between stipulated time
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |GETENDPointQueryParameter                                                             |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceMemory    | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory    | perfMemoryTrigger.bat   |performance     | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory?minutes=50    |
       

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: E2E Validation for Performance Storage MicroService with Partner Id EndPoint Id-C2223495
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
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceStorage   | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/storage   | perfStorageTrigger.bat  |performance     |

      
      
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease  @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Time Range Validation for Performance Processor MicroService with incorrect data-C2487748
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "400"Bad Request
    
     Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |GETENDPointQueryParameter                                                              |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcessor | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors| perfProcessorTrigger.bat|performance     | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors?minutes=-10|
      
       
   @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @ReleaseTest @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Time Range Validation for Performance Processor MicroService with both count and duration-C2487749
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "400"Bad Request
    
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |GETENDPointQueryParameter                                                                    |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceProcessor | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors| perfProcessorTrigger.bat|performance     |/performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/processors?minutes=10&count=5|
    
       


 
  @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Time Range Validation for Performance Memory MicroService with incorrect data-C2487776
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "400"Bad Request
    
      
      Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |GETENDPointQueryParameter                                                              |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceMemory    | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory    | perfMemoryTrigger.bat   |performance     | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory?minutes=-10    |
       
       
   @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @DesktopRelease @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
      Scenario Outline: Time Range Validation for Performance Memory MicroService with both count and duration-C2487804
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service and store in file with GET as "<GETENDPointQueryParameter>"
      Then I should validate the status code as "400"Bad Request
    
      
     Examples:
       | RowIndex | RegistrationEndPoint    |POSTENDPoint                                   | GETENDPoint                                                               |  BatchFile              | Type           |GETENDPointQueryParameter                                                                    |
       | 1        | /agent/v1/registration  | /agent/v1/RandomEndPoint/performanceMemory    | /performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory    | perfMemoryTrigger.bat   |performance     |/performance/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/memory?minutes=10&count=5    |