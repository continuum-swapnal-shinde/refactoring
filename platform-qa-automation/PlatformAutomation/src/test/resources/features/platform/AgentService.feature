Feature: Agent Service API

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for agent Register API-C1756740
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should validate the status code as "200"OK

    Examples: 
      | RowIndex | GETENDPoint                                                  |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  @Regression1
  Scenario Outline: Validate REST response format for agent Register API-C1756741
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Registration of Agent Microservice

    Examples: 
      | RowIndex | GETENDPoint                   |
      |        1 | /agent/v1/EndPointID/register |

  #C1082848,
  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify agent micro services processes error messages from kafka queue to cassandra DB-C1082848,C2005228,C1082846,C1082847
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    And User read data from excel with rowIndex "<RowIndex>"
    When User send a Post request for API endpoint as "<POSTEndpoint>"
    Then User should validate the status code as "200"OK
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API

    #And User should validate the data of errormessage in response
    Examples: 
      | RowIndex | EndPoint                                               | POSTEndpoint                |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/errors | /agent/v1/EndPointID/errors |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify agent micro services not processes error messages with Partner ID-C2005229
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    And User read data from excel with rowIndex "<RowIndex>"
    When User send a Post request for API endpoint as "<EndPoint>"
    Then User should verify the status code as "<StatusCode>"

    Examples: 
      | RowIndex | EndPoint                                               | StatusCode         |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/errors | Method Not Allowed |

  @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify POST config/schedule request sent to Agent Core is handled when agent is online-C1320105,C1320098
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User fetch and validate updated plugin schedule for "<Component>"

    Examples: 
      | RowIndex | EndPoint                     | MailBoxEndpoint               | Component |
      |        1 | /agent/v1/EndPointID/execute | /agent/v1/EndPointID/mailbox/ | Processor |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify error code generated on sending(POST) config/schedule with invalid schedule message-C1349818,C1320106
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for errors for API endpoint as "<EndPoint>"
    Then User should verify the status code as "<StatusCode>"
    And User validate the error details with "<ExpectedResult>"

    Examples: 
      | RowIndex | EndPoint                     | StatusCode  | ExpectedResult             |
      |        1 | /agent/v1/EndPointID/execute | Bad Request | ErrInvalidScheduleDuration |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify error code generated on sending(POST) config/schedule with invalid type-C1349817
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for errors for API endpoint as "<EndPoint>"
    Then User should verify the status code as "<StatusCode>"
    And User validate the error details with "<ExpectedResult>"

    Examples: 
      | RowIndex | EndPoint                     | StatusCode  | ExpectedResult        |
      |        1 | /agent/v1/EndPointID/execute | Bad Request | ErrInvalidMessageType |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify error code generated on sending(POST) config/schedule with invalid path in request payload-C1349819
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for errors for API endpoint as "<EndPoint>"
    Then User should verify the status code as "<StatusCode>"
    And User validate the error details with "<ExpectedResult>"

    Examples: 
      | RowIndex | EndPoint                     | StatusCode  | ExpectedResult        |
      |        1 | /agent/v1/EndPointID/execute | Bad Request | ErrInvalidMessagePath |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for Versioning of Agent Microservice API-C1225543
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK

    Examples: 
      | EndPoint       |
      | /agent/version |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response format for Versioning of Agent Microservice API-C1225544
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Versioning of Agent Microservice

    Examples: 
      | EndPoint       |
      | /agent/version |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response Data for Versioning of Agent Microservice API-C1225547
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the Data of Versioning of Agent Microservice

    Examples: 
      | RowIndex | EndPoint       |
      |        1 | /agent/version |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for HealthCheck of Agent Microservice API-C1227422
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK

    Examples: 
      | EndPoint      |
      | /agent/health |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response format for HealthCheck of Agent Microservice API-C1227423
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of HealthCheck of Agent Microservice

    Examples: 
      | EndPoint      |
      | /agent/health |

  @PlatformBVT @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response Data for HealthCheck of Agent Microservice API-C1227424
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the Data of HealthCheck of Agent Microservice

    Examples: 
      | EndPoint      |
      | /agent/health |

  @PlatformBVT @PlatformRegression @Win10_64Bit 
  Scenario Outline: Validate REST response format for Heartbeat API-C948095
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User fetch registry values of old RMM agent
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should fetch response for API
    And User should validate the format of Heartbeat API of Agent Core

    #And User Close SSH Connection
    Examples: 
      | RowIndex | GETENDPoint                                               |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat |

  @E2E_Heartbeat
  Scenario Outline: Validate REST response format for Heartbeat API
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User fetch registry values of old RMM agent
    When User send a POST request for API endpoint as "<POSTENDPoint>"
    Then User should validate the status code as "200"OK
    #And User should fetch response for API
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should fetch response for API
    And User should validate the format of Heartbeat API of Agent Core
    And User should validate the HeartBeatCounter of Heartbeat API as as "<ExpectedResult>"

    Examples: 
      | RowIndex | GETENDPoint                                               | POSTENDPoint                   | ExpectedResult |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | /agent/v1/EndPointID/heartbeat |           1200 |

  #@PlatformRegression @Win7_64Bit
  Scenario Outline: Verify agent gets Uninstalled when installed with invalid token-C2473234
    Given Connection with test machine for "<Host>"
    #When User fetch the successful build no and download setup from artifactory
    When User install MSI Package through command prompt with Invalid Token
    Then User fetch Blank EndPointID from agentCore config file for "<OSEdition>"
    And User fetch Process ID of AgentCore as "<ProcessStatusOne>"
    And User fetch Process ID of EventLogPlugin as "<ProcessStatusOne>"

    Examples: 
      | RowIndex | GETENDPoint                                               | Host        | OSEdition  | ProcessStatusOne          |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | 10.2.114.89 | Win7_64Bit | No Instance(s) Available. |

  #@PlatformRestart @Win7_64Bit
  Scenario Outline: Verify agent gets Uninstalled through mailbox uninstall message-C2473230
    Given Connection with test machine for "<Host>"
    #When User fetch the successful build no and download setup from artifactory
    When User read data from excel with rowIndex "<RowIndex>"
    Then User send a POST request for generate token API endpoint as "<POSTEndPoint>"
    And User should validate the "<Statuscode>" as in response
    And User should fetch token for API
    When User install MSI Package through command prompt with valid TOKEN
    Then User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    When User send a configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User wait for Service to stop
    And User fetch Process ID of AgentCore as "<ProcessStatusOne>"
    And User fetch Process ID of EventLogPlugin as "<ProcessStatusOne>"

    Examples: 
      | RowIndex | GETENDPoint                                               | Host        | OSEdition  | ProcessStatusOne          | EndPoint                     | POSTEndPoint            |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | 10.2.114.89 | Win7_64Bit | No Instance(s) Available. | /agent/v1/EndPointID/execute | /agent/v1/generatetoken |

  #@PlatformRegression @Win7_64Bit
  Scenario Outline: Verify agent gets installed with valid token-C2473232,C2018900,C2018901
    Given Connection with test machine for "<Host>"
    #When User fetch the successful build no and download setup from artifactory
    When User read data from excel with rowIndex "<RowIndex>"
    Then User send a POST request for generate token API endpoint as "<POSTEndPoint>"
    And User should validate the "<Statuscode>" as in response
    And User should fetch token for API
    When User install MSI Package through command prompt with valid TOKEN
    Then User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    And User validate the configuration file is environment specific as "<ExpectedURL>" and "<OSEdition>"

    Examples: 
      | RowIndex | GETENDPoint                                               | Host        | OSEdition  | Statuscode | POSTEndPoint            | ExpectedURL                                                 |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | 10.2.114.89 | Win7_64Bit |        200 | /agent/v1/generatetoken | https://integration.agent.service.itsupport247.net/agent/v1 |

  Scenario Outline: Verify agent gets Uninstalled when server sends DELETE request to Heartbeat API-C2425456
    Given Connection with test machine for "<Host>"
    #When User fetch the successful build no and download setup from artifactory
    #And User install MSI Package through command prompt
    When User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    Then User send a delete request for API endpoint as "<DELETEENDPoint>"
    And User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"
    And User should validate the response of Heartbeat API with Installed flag "<ExpectedResult>"
    When User fetch registry values of old RMM agent
    Then User wait for Service to stop
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should fetch response for API
    And User should validate the response of Heartbeat API with Installed flag "<ExpectedResult>"
    And User should validate the response of Heartbeat API as "<ExpectedResult>"
    When User fetch Process ID of AgentCore as "<ProcessStatusOne>"
    Then User fetch Process ID of EventLogPlugin as "<ProcessStatusOne>"

    Examples: 
      | RowIndex | GETENDPoint                                               | Host        | OSEdition   | DELETEENDPoint                 | ExpectedResult | ProcessStatusOne          |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | 10.2.114.79 | Win10_32Bit | /agent/v1/EndPointID/heartbeat | false          | No Instance(s) Available. |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate REST endpoint for generate token POST API-C2473195
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for generate token API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch token for API

    Examples: 
      | RowIndex | POSTEndPoint            | Statuscode |
      |        1 | /agent/v1/generatetoken |        200 |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate partnerID in generate token POST API-C2473196
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for generate token API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | POSTEndPoint            | Statuscode |
      |        1 | /agent/v1/generatetoken |        400 |

  @PlatformRegression @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate heartbeat data for invalid EndpointID-C2432675
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User fetch registry values of old RMM agent
    When User send a POST request for API endpoint with header as "<POSTENDPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | GETENDPoint                                                                         | POSTENDPoint                                             | ExpectedResult | Statuscode |
      |        1 | /agent/v1/partner/PartnerID/endpoint/80c6d631-2ae2-4ed1-9e9a-cd332eaa0d76/heartbeat | /agent/v1/13cbff88-374d-47d6-90bd-7c3e17c308ad/heartbeat |           1200 |        412 |

  Scenario Outline: Validate heartbeat data for invalid EndpointID for Uninstall condition
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User fetch registry values of old RMM agent
    When User send a delete request for API endpoint as "<DELETEENDPoint>"
    Then User should validate the status code as "200"OK
    When User send a POST request for API endpoint with header as "<POSTENDPoint>"
    Then User should validate the "<Statuscode>" as in response

    #And User Close SSH Connection
    Examples: 
      | RowIndex | OSEdition   | POSTENDPoint                   | Statuscode | DELETEENDPoint                 |
      |        1 | Win10_64Bit | /agent/v1/EndPointID/heartbeat |        412 | /agent/v1/EndPointID/heartbeat |

  @AgentInstallation12
  Scenario Outline: Verify agent gets Installed-C2018903
    Given Connection with test machine for "<Host>"
    #And User connect to cassandra DB
    #When User fetch registry values of old RMM agent
    #And User fetch token table entries from cassandra DB
    When User fetch the successful build no and download setup from artifactory
    Then User install MSI Package through command prompt

    Examples: 
      | RowIndex | GETENDPoint                                               | Host         | OSEdition  | ServiceStatus | EndPoint                     |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | 10.2.114.113 | Win7_64Bit | RUNNING       | /agent/v1/EndPointID/execute |

  @PlatformRegression @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for update mapping POST API-C2497689
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a invalid data to update mapping POST API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    And User validate "<Errormessage>" in response

    Examples: 
      | RowIndex | POSTEndPoint            | Statuscode | Errormessage            | GETENDPoint                                                  |
      |        1 | /agent/v1/updatemapping |        200 | RequiredPayloadNotFound | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |
      |        2 | /agent/v1/updatemapping |        200 | NoRecordFound           | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  @PlatformRegression @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: E2E Validation of update mapping POST API-C2497635
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a POST request to update mapping API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    When User send a Get request for API endpoint as "<GETENDPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate mapping with registration API

    Examples: 
      | RowIndex | POSTEndPoint            | Statuscode | GETENDPoint                                                  |
      |        1 | /agent/v1/updatemapping |        200 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  @PlatformRegression @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate GET API to return Endpoint based on Partner and Legacy RegID-C2517221
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User fetch registry values of old RMM agent
    When User send a Get request for API based on partner and legacy regID "<GETENDPoint>"
    Then User should fetch response for API
    And User validate the response with actual endpoint details

    Examples: 
      | RowIndex | GETENDPoint                                                    | Statuscode |
      |        1 | /agent/v1/partner/PartnerID/legacy/LegacyRegID/endpointmapping |        200 |
      |        2 | /agent/v1/partner/PartnerID/legacy/LegacyRegID/endpointmapping |        200 |
      |        3 | /agent/v1/partner/PartnerID/legacy/LegacyRegID/endpointmapping |        200 |

  @PlatformRestart @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate Availability Status feature of Juno Agent-C1941432,C1941433
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"
    #When User stop the Agent Core Service
    #And User send a Get request for API endpoint as "<EndPoint>"
    #Then User should fetch response for API
    #And User should validate the response of Heartbeat API as "<ExpectedResultOne>"
    When User start the Agent Core Service
    Then User send a Get request for API endpoint as "<EndPoint>"
    And User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"

    Examples: 
      | RowIndex | EndPoint                                                  | ExpectedResult | ExpectedResultOne |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | true           | false             |

  @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate Availability Status feature with Only Partner ID-C2053348
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    Then User fetch registry values of old RMM agent
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"
    When SSH connection establishes with remote test machine for "<OSEdition2>"
    Then User fetch AgentCore EndPointID from second agentCore config file for "<OSEdition2>"
    When User send a Get request second agent endpoint as "<EndPoint2>"
    Then User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"
    When User send a Get request for API endpoint as "<PartnerEndPoint>"
    Then User should fetch response for API
    And User should validate the response Multiple Endpoints based on PartnerID "<ExpectedResult>"

    Examples: 
      | RowIndex | OSEdition   | OSEdition2 | EndPoint                                                  | ExpectedResult | EndPoint2                                                  | PartnerEndPoint                       |
      |        1 | Win10_64Bit | Win8_64Bit | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | true           | /agent/v1/partner/PartnerID/endpoint/EndPointID2/heartbeat | /agent/v1/partner/PartnerID/heartbeat |

  @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate Availability Status feature with Partner ID for multiple endpoints-C2620464
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User fetch AgentCore EndPointID from agentCore config file for "<OSEdition>"
    Then User fetch registry values of old RMM agent
    When SSH connection establishes with remote test machine for "<OSEdition2>"
    Then User fetch AgentCore EndPointID from second agentCore config file for "<OSEdition2>"
    When User send a Get request second agent endpoint as "<EndPoint>"
    Then User should fetch response for API
    And User should validate the response Multiple Endpoints based on PartnerID "<ExpectedResult>"

    Examples: 
      | RowIndex | OSEdition   | OSEdition2 | EndPoint                                                                   | ExpectedResult |
      |        1 | Win10_64Bit | Win8_64Bit | /agent/v1/partner/PartnerID/endpoint/EndPointIDOne,EndPointIDTwo/heartbeat | true           |

  @PlatformBVT @PlatformRegression @Win10_64Bit 
  Scenario Outline: Validate agent is online with Heartbeat API-C1941432
    Given SSH connection establishes with remote test machine from tag
    When User fetch registry values of old RMM agent
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should fetch response for API
    And User should validate the response of Heartbeat API as "<ExpectedResult>"

    Examples: 
      | RowIndex | EndPoint                                                  | ExpectedResult |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/heartbeat | true           |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validating MD5 checksum values returned from AMS-C2823612
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User send a agent configuration change Post request with header and endpoint as "<POSTENDPOINT>" and hashcode as "<HASHCODE>"
    And User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | POSTENDPOINT                 | Statuscode | HASHCODE |
      |        1 | /agent/v1/EndPointID/execute |        200 | Valid    |
      |        2 | /agent/v1/EndPointID/execute |        200 | Valid    |
      |        3 | /agent/v1/EndPointID/execute |        200 | Valid    |
      |        4 | /agent/v1/EndPointID/execute |        200 | Valid    |
      |        5 | /agent/v1/EndPointID/execute |        200 | Invalid  |
      |        6 | /agent/v1/EndPointID/execute |        200 | Invalid  |
      |        7 | /agent/v1/EndPointID/execute |        200 | InValid  |
      |        8 | /agent/v1/EndPointID/execute |        200 | Invalid  |
