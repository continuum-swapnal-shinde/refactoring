Feature: Agent Auto Update Feature

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate partner and manifest details with enabled manifest to Partners API-C3601661
    When User send a Registration POST for Multiple Partners "<RegistrationEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    When User send a Post request for Manifest API endpoint as "<POSTEndpoint>"
    Then User should validate the "<Statuscode>" as in response
    And User send a Get request and validate enabled PartnerManifest "<EndPoint>"

    Examples: 
      | RowIndex | POSTEndpoint              | Statuscode | EndPoint                             | RegistrationEndPoint   |
      |        1 | /agent/v1/partnerManifest |        200 | /agent/v1/partner/PartnerID/manifest | /agent/v1/registration |
      |        2 | /agent/v1/partnerManifest |        200 | /agent/v1/partner/PartnerID/manifest | /agent/v1/registration |

  @PlatformRegression @swa @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate enabling manifest for partners using invalid manifest url-C3601662
    When User send a Post request for Manifest API endpoint as "<POSTEndpoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | POSTEndpoint       | Statuscode |
      |        1 | /agent/v1/partnerM |        404 |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate Global Manifest Payload is getting created for N no of Days-C3604832
    Given SSH connection establishes with remote test machine from tag
    When User send a Post request for Manifest API endpoint as "<POSTEndpoint>"
    Then User should validate the "<Statuscode>" as in response
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the "<Status>" as in response
    And User should fetch response for API

    Examples: 
      | RowIndex | POSTEndpoint       | Statuscode | EndPoint                       | Status |
      |        1 | /agent/v1/manifest |        201 | /agent/v1/manifest?lastNDays=1 |    200 |

   @AgentAutoUpdate
  Scenario Outline: Validate AMS to return Global maifest file Structure for an valid endpoint-C2797459
    Given SSH connection established with remote test machine for "<OSEdition>"
    When User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    Then User send a Get request for API endpoint as "<EndPoint>"
    And User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the structure of Global Manifest file

    Examples: 
      | RowIndex | OSEdition  | ServiceStatus | EndPoint                               |
      |        1 | Win8_32Bit | RUNNING       | /agent/v1/endpoint/EndPointID/manifest |

  @AgentAutoUpdate
  Scenario Outline: Validate AMS to return 500 Error code for Global maifest file for an Invalid endpoint-C2797460
    Given SSH connection established with remote test machine for "<OSEdition>"
    When User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    Then User send a Get request for API endpoint as "<EndPoint>"
    And User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | OSEdition   | ServiceStatus | EndPoint                           | Statuscode |
      |        1 | Win10_32Bit | RUNNING       | /agent/v1/endpoint/ABCD12/manifest |        500 |

  @AgentAutoUpdate
  Scenario Outline: Validate AMS to return 404 Error code for Global maifest file for an Invalid Manifest URL-C2807004
    Given SSH connection established with remote test machine for "<OSEdition>"
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | OSEdition   | ServiceStatus | EndPoint                          | Statuscode |
      |        1 | Win10_32Bit | RUNNING       | /agent/v1/endpoint/ABCD12/maifest |        404 |


    Scenario Outline:Validate global manifest file is downloaded when POST mailbox is pushed-C3685609
	Given SSH connection establishes with remote test machine from tag
    When User fetch EndPointID of the machine from tag
    Then User fetch AgentCore EndPointID from agentCore config file from tag
	When User read global manifest file for tag
	Then User read data from excel with rowIndex "<RowIndex>" 
	When User send a configuration change Post request for mailbox API endpoint as "<EndPoint>" 
	Then User should validate the status code as "200"OK 
	When User read updated global manifest file for tag
	Then User validate global manifest file contents with manifest GET API response as "<GETENDPoint>"
	
	Examples: 
		| RowIndex | EndPoint                     | Component   | 	GETENDPoint 										|
		|        1 | /agent/v1/EndPointID/execute | version     | /agent/v1/endpoint/RandomEndPoint/manifest			|
		
		
	@platformSanity
    Scenario Outline:Validate global manifest file is downloaded upon request from RRT provided new manifest version is available via jenkins-C3685610
	Given SSH connection establishes with remote test machine from tag
    When User fetch EndPointID of the machine from tag
	Then User read data from excel with rowIndex "<RowIndex>"
	And User delete existing old manifest if any on endpoint machine for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User read global manifest file for tag
    And User wait for Service to stop
	When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
	When User read updated global manifest file for tag
	Then User validate global manifest file contents with manifest GET API response as "<GETENDPoint>"
	
	 Examples: 
      | RowIndex | JenkinsPOSTEndpoint | RRTPOSTEndpoint           | Component | GETENDPoint                                         |
      |        1 | /agent/v1/manifest  | /agent/v1/manifest/gman/partnerManifest | version   | /agent/v1/endpoint/versionSpecificEndpoint/manifest |

   
	Scenario Outline:Validate agent core to download the individual packages based on global manifest-C3685611
	Given SSH connection establishes with remote test machine from tag
	When User read data from excel with rowIndex "<RowIndex>"
	Then User delete existing old manifest if any on endpoint machine for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate individual packages are downloaded on endpoint based on global manifest for tag
    
    Examples: 
		| RowIndex | JenkinsPOSTEndpoint |RRTPOSTEndpoint              | 	
		|        1 | /agent/v1/manifest  |/agent/v1/partnerManifest    | 
   
   
	Scenario Outline:Validate agent core to download delta packages based on updated global manifest-C3685612
	Given SSH connection establishes with remote test machine from tag
	When User read data from excel with rowIndex "<RowIndex>"
	Then User delete existing old manifest if any on endpoint machine for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate individual packages are downloaded on endpoint based on global manifest for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsdeltaPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTdeltaPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate delta packages are downloaded on endpoint based on updated manifest for tag
    
    Examples: 
      | RowIndex | JenkinsdeltaPOSTEndpoint | RRTdeltaPOSTEndpoint            | JenkinsPOSTEndpoint | RRTPOSTEndpoint           |
      |        1 | /agent/v1/manifest+delta | /agent/v1/manifest/gman/partnerManifest+delta | /agent/v1/manifest  | /agent/v1/manifest/gman/partnerManifest |


	Scenario Outline:Validate agent core not to download delta packages with updated global manifest version only-C3685613
	Given SSH connection establishes with remote test machine from tag
	When User read data from excel with rowIndex "<RowIndex>"
	Then User delete existing old manifest if any on endpoint machine for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate individual packages are downloaded on endpoint based on global manifest for tag
	When User send a Post request for Manifest API endpoint as "<JenkinsdeltaPOSTEndpoint>"
    Then User should validate the "201" as in response
	When User send a Post request for Manifest API endpoint as "<RRTdeltaPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate delta packages are not downloaded on endpoint based on updated manifest for tag
    
    Examples: 
      | RowIndex | JenkinsdeltaPOSTEndpoint | RRTdeltaPOSTEndpoint            | JenkinsPOSTEndpoint | RRTPOSTEndpoint           |
      |        1 | /agent/v1/manifest+delta | /agent/v1/manifest/gman/partnerManifest+delta | /agent/v1/manifest  | /agent/v1/manifest/gman/partnerManifest |


	 @platformSanity 
      Scenario Outline: E2E Validation of AMS to expose APIs to receive installation success/failure-C3688088
      Given SSH connection establishes with remote test machine from tag
      When User read data from excel with rowIndex "<RowIndex>"
      Then User fetch EndPointID of the machine from tag
      When User send a Post request for InstallStatus API endpoint as "<POSTENDPoint>"
      Then User should validate the "201" as in response
 	  When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
      Then User should validate the status code as "200"OK
      And User should fetch response for API
      And User should validate installStatus API response
      When User read GET response from a micro-service and store in file with GET as "<GETENDPointPerVersion>"
      Then User should validate the status code as "200"OK
      And User should fetch response for API
      And User should validate installStatus API response
      
     Examples: 
      | RowIndex | POSTENDPoint                                    | GETENDPoint                                     | GETENDPointPerVersion                                       |
      |        1 | /agent/v1/endpoint/RandomEndPoint/installStatus | /agent/v1/partner/RandomPartnerID/installStatus | /agent/v1/partner/RandomPartnerID/version/gmf/installStatus |

    
	@platformSanity
	Scenario Outline:Validate auto update functionality E2E
	Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch EndPointID of the machine from tag
    When User fetch version Information from the system
    Then User delete existing old manifest if any on endpoint machine for tag
    When User validate if system ready for autoupdate feature
    Then User send a Post request for Manifest API endpoint as "<JenkinsPOSTEndpoint>"
    And User should validate the "201" as in response
    When User send a Post request for Manifest API endpoint as "<RRTPOSTEndpoint>"
    Then User should validate the "200" as in response
    And User validate individual packages are downloaded on endpoint based on global manifest for tag
    When User read GET response from a micro-service and store in file with GET as "<GETENDPointPerVersion>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate installStatus API response wrt global manifest
    When User read GET response from a micro-service and store in file with GET as "<GETENDPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User validate package versions and tmp directory details for tag
    
    
    Examples: 
      | RowIndex | JenkinsPOSTEndpoint | RRTPOSTEndpoint           | GETENDPointPerVersion                                       | GETENDPoint                                                            |
      |        1 | /agent/v1/manifest  | /agent/v1/manifest/gman/partnerManifest | /agent/v1/partner/RandomPartnerID/version/gmf/installStatus | /agent/v1/partner/RandomPartnerID/endpoint/RandomEndPoint/agentversion |
 
     
  @testAwss3bucket
  Scenario Outline: Validate Package manifest and upload in s3 bucket
    Given SSH connection established with remote test machine for "<OSEdition>"
    When User downloads packages from artifactory for creating zip files
    Then User creates package manifest and zip files for S3 Upload
    And User upload zip file into S3 Bucket

   Examples: 
      | RowIndex | OSEdition  |
      |        1 | Win8_32Bit |
