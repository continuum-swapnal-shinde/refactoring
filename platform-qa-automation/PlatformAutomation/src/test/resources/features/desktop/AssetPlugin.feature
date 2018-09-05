Feature: Validate Asset Information From The System


@DesktopRegression @WinServer3R2_64Bit @Win8.1_32Bit 
 Scenario Outline: E2E Validation of Information related to Software Installation-C3780410
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type            |AssetComponent           |GETENDPoint                                                            |
 | 1        | assetTrigger.bat     | asset           |SoftwareInstall          |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=installedSoftwares|
 
 @DesktopRegression @WinServer3R2_64Bit @Win8.1_32Bit 
 Scenario Outline: E2E Validation of Information related to Software UnInstallation-C3780411
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
    #  When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
    #  Then I should validate the status code as "200"OK
    # And I should fetch response for API
    # And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type            |AssetComponent           |GETENDPoint                                                            |
 | 1        | assetTrigger.bat     | asset           |SoftwareUnInstall          |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=installedSoftwares|
 

@DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset User Addition-C3627602
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type            |AssetComponent           |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset           |UserAddition             |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=users|
 
 @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset User Deletion-C3627603
     Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type            |AssetComponent            |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset           |UserDeletion              |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=users|
 
 
  @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Folder Share-C3627604
     Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type             |AssetComponent            |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset            |FolderShare               |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=shares|
 
 
  @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Folder UnShare-C3627605
    Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      Then I fetch EndPointID of the machine from tag
      When I read GET response from a micro-service for realtime and store in file with GET as "<GETENDPoint>" 
      Then I should validate the status code as "200"OK
      And I should fetch response for API
      And I validate system information with micro-service information for "<AssetComponent>"
    
      
     Examples:
 | RowIndex | BatchFile            | Type            |AssetComponent              |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset           |FolderUnShare               |/asset/v1/partner/PartnerIdForSpecificEndpoint?field=shares|
 

@DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Shared Folders-C2679856
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
 | RowIndex | BatchFile            | Type           |AssetComponent     |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset          |SharedFolders      |/asset/v1/partner/PartnerIdForSpecificEndpoint|
    

@DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Shared Folders-C2679856
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
 | RowIndex | BatchFile            | Type        |AssetComponent     |GETENDPoint                                   |
 | 1        | assetTrigger.bat     | asset       |SharedFolders      |/asset/v1/partner/PartnerIdForSpecificEndpoint|
  
 
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease
 Scenario Outline: E2E Validation of Information related to Asset Processor-C2164066
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
     
      
      
     Examples:
      | RowIndex | BatchFile            | Type         |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset        |Processor         |/asset/v1/partner/PartnerIdForSpecificEndpoint|
   
     
     
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease
 Scenario Outline: E2E Validation of Information related to Asset BaseBoard-C2223480
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
        Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |BaseBoard         |/asset/v1/partner/PartnerIdForSpecificEndpoint|
   
      
      
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease     
Scenario Outline: E2E Validation of Information related to Asset System-C2223482
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
       And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
      Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |System            |/asset/v1/partner/PartnerIdForSpecificEndpoint|
    
      
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease
Scenario Outline: E2E Validation of Information related to Asset Drives-C2223483
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
      
      Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |Drives            |/asset/v1/partner/PartnerIdForSpecificEndpoint|

    
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease
Scenario Outline: E2E Validation of Information related to Asset OS-C2223484
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
      
      Examples:
      | RowIndex | BatchFile            | Type          |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset         |OS                |/asset/v1/partner/PartnerIdForSpecificEndpoint|

    
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit @DesktopRelease
Scenario Outline: E2E Validation of Information related to Asset BIOS-C2223487
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
     
      
      Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent    |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |BIOS              |/asset/v1/partner/PartnerIdForSpecificEndpoint|
    


@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit 
 Scenario Outline: E2E Validation of Information related to Asset KeyBoard-C2245341
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
       And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
     
      
     Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent    |GETENDPoint                                  |
      | 1        | assetTrigger.bat     | asset          |KeyBoard         |/asset/v1/partner/PartnerIdForSpecificEndpoint|

    

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Mouse-C2245343
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
       And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
   
      
      
     Examples:
      | RowIndex | BatchFile            | Type          |AssetComponent   |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset         |Mouse            |/asset/v1/partner/PartnerIdForSpecificEndpoint|

    

 @Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit  @Win8_64Bit @Win10_32Bit  @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Monitor-C2245342
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
       And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
      | RowIndex | BatchFile            | Type          |AssetComponent     |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset         |Monitor            |/asset/v1/partner/PartnerIdForSpecificEndpoint|

    
@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset CDROM-Drive-C2245344
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
       And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent     |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |CD-ROM            |/asset/v1/partner/PartnerIdForSpecificEndpoint|

   
@Regression @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Floppy Drive-C2245345
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
      | RowIndex | BatchFile            | Type          |AssetComponent     |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset         |Floppy-Drive       |/asset/v1/partner/PartnerIdForSpecificEndpoint|   

@Regression @DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset UserAccount-C2245345
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
      | RowIndex | BatchFile            | Type           |AssetComponent     |GETENDPoint                                   |
      | 1        | assetTrigger.bat     | asset          |UserAccount        |/asset/v1/partner/PartnerIdForSpecificEndpoint| 

     
      
@DesktopRegression @WinServer3R2_64Bit @WinServer3_64Bit @WinServer3_32Bit @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
 Scenario Outline: E2E Validation of Information related to Asset Services-C2517224
      Given SSH connection established with remote test machine from tag
      And I read data from excel with rowIndex "<RowIndex>"
      And I fetch Information for "<AssetComponent>" from the system
      And I fetch plugin payload from console for windows as "<BatchFile>" for "<Type>" 
      And I validate system information with plugin information
    
      
     Examples:
     | RowIndex | BatchFile            | Type         |AssetComponent     |GETENDPoint                                   |
     | 1        | assetTrigger.bat     | asset        |Services           |/asset/v1/partner/PartnerIdForSpecificEndpoint| 

