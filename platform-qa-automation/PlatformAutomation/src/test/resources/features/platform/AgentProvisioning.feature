Feature: Provisioning and Registration API

  @PlatformRegressionneedtocheck @Win10_64Bit
  Scenario Outline: Validate system attributes of agent-C1933160
    Given SSH connection establishes with remote test machine from tag
    When User fetch system attributes from agentCore from tag
    Then User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the system info of the response of registration API

    Examples: 
      | EndPoint                                                     |
      | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  @Regressionedidrun
  Scenario Outline: Validate REST endpoint for Registration POST API on the basis of system information score
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    And User should validate response with EndPointID when sys info algo score is "<Score>"

    Examples: 
      | RowIndex | EndPoint                                                     | POSTEndPoint           | Score |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        2 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        3 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        4 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        5 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        6 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        7 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        8 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |        9 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |       10 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |       11 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |       12 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |       13 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    15 |
      |       14 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    14 |
      |       15 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    12 |
      |       16 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    12 |
      |       17 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    11 |
      |       18 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    10 |
      |       19 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |    10 |
      |       20 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |     9 |
      |       21 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |     9 |

  @AgentProvisioning_ns
  Scenario Outline: Validate REST endpoint for Registration POST API when system info score is greater than 10
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request with random uuid for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    And User should validate response with EndPointID when sys info algo score is "<Score>"

    Examples: 
      | RowIndex | EndPoint                                                     | POSTEndPoint           | Sysattribute | Score |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |              |    15 |

  @AgentProvisioning_ns
  Scenario Outline: Validate REST endpoint for Registration POST API when system info score is greater than 10
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    And User should validate response with EndPointID when sys info algo score is "<Score>"

    Examples: 
      | RowIndex | EndPoint                                                     | POSTEndPoint           | Sysattribute | Score |
      |        2 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | /agent/v1/registration |              |    15 |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API-C1795049,C1795050,C1795053
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    When User send a Get request for registration API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Registration of Agent Microservice

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | EndPoint                                                       |
      |        1 | /agent/v1/registration |        200 | /agent/v1/partner/ITSPlatform/endpoint/EndPointID/registration |

  @AgentProvisioning
  Scenario Outline: Validate REST endpoint for Registration GET API
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Registration of Agent Microservice
    And User should validate the Data of Registration API of Agent Microservice

    Examples: 
      | RowIndex | EndPoint                                                                       |
      |        1 | /agent/v1/partner/1/endpoint/150cda60-6807-4c07-b939-2099aa200e0b/registration |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate the Mapping of Old RMM Agent with New Juno Agent-C1933173,C1933167
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the Data of the response of registration API

    Examples: 
      | RowIndex | EndPoint                                                     |
      |        1 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration |

  Scenario Outline: Validate the MSI package is running with same version number-C1836911
    Given SSH connection establishes with remote test machine from tag
    When User install MSI Package through command prompt
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    And User fetch and validate entry from event viewer as "<Version>"

    Examples: 
      | RowIndex | EndPoint                       | Version |
      |        1 | /agent/v1/EndPointID/heartbeat | 0.0.1   |

  @Sample
  Scenario Outline: Sample Event Creation through Automation and Validation
    Given SSH connection establishes with remote test machine from tag
    And I create an sample event

    #Then I validate whether event is created successfully
    Examples: 
      | RowIndex |
      |        1 |

  @PlatformBVT @PlatformRegression @Win10_64Bit 
  Scenario Outline: Validate REST endpoint for Registration POST API for a given valid partnerID and tokenID-C2473224
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for generate token API endpoint as "<TokenPOSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch token for API
    When User send a POST request for agent registration API endpoint with token as "<POSTEndPoint>" and partner as "<partnerID>"
    Then User should validate the "<request>" as in response
    And User should fetch response for API
    When User send a Get request for registration API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Registration of Agent Microservice

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | TokenPOSTEndPoint       | partnerID | request | EndPoint                                                    |
      |        1 | /agent/v1/registration |        200 | /agent/v1/generatetoken |  50001795 |     200 | /agent/v1/partner/50001795/endpoint/EndPointID/registration |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API for a given invalid partnerID-C2473226
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for generate token API endpoint as "<TokenPOSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    #And User should fetch token for API
    When User send a POST request for agent registration API endpoint with Invalid token as "<POSTEndPoint>" and partner as "<partnerID>"
    Then User should validate the "<request>" as in response

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | TokenPOSTEndPoint       | partnerID | request |
      |        1 | /agent/v1/registration |        200 | /agent/v1/generatetoken |  50001796 |     400 |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API with invalid tokenID for a given partnerID-C2473227
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for agent registration API endpoint with Invalid token as "<POSTEndPoint>" and partner as "<partnerID>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | partnerID |
      |        1 | /agent/v1/registration |        400 |  50001795 |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API for a given tokenID provided no RMM1.0 is installed-C2473228
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for generate token API endpoint as "<TokenPOSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch token for API
    When User send a POST request for agent registration API endpoint with token as "<POSTEndPoint>" and partner as "<partnerID>"
    Then User should validate the "<request>" as in response
    And User should fetch response for API
    When User send a Get request for registration API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    And User should validate the format of Registration of Agent Microservice

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | TokenPOSTEndPoint       | partnerID | request | EndPoint                                                    |
      |        1 | /agent/v1/registration |        200 | /agent/v1/generatetoken |           |     200 | /agent/v1/partner/50001795/endpoint/EndPointID/registration |

  @PlatformBVT @PlatformRegression @Win10_64Bit
  Scenario Outline: Validate REST endpoint for Registration POST API for a given invalid tokenID provided no RMM1.0 is installed-C2473229
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a POST request for agent registration API endpoint with Invalid token as "<POSTEndPoint>" and partner as "<partnerID>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | partnerID |
      |        1 | /agent/v1/registration |        400 |           |

 	@PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response for Registration POST API on the basis of partnerID-C2473192
    # Given SSH connection establishes with remote test machine from tag
    Given User read data from excel with rowIndex "<RowIndex>"
    #When User fetch registry values of old RMM agent
    # And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch endpointID from API response
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API

    #And I should validate the response of registration API
    # And I should validate response with EndPointID when sys info algo score is "<Score>"
    Examples: 
      | RowIndex | EndPoint                           | POSTEndPoint           |
      |        1 | /agent/v1/partner/118/registration | /agent/v1/registration |
      |        2 | /agent/v1/partner/118/registration | /agent/v1/registration |
      |        3 | /agent/v1/partner/118/registration | /agent/v1/registration |

  @PlatformRegression @Win10_32Bit
  Scenario Outline: Validate REST endpoint for Registration POST API-C179500
    Given SSH connection establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    # And User fetch system attributes from agentCore from tag
    Then User fetch registry values of old RMM agent
    And User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Get request for API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch response for API
    When User send a POST request for agent registration API endpoint as "<POSTEndPoint>"
    Then User should validate the "<Statuscode>" as in response
    And User should fetch response for API
    #And I should compare EndPointID as "<Status>"

    # When I send a Get request for registration API endpoint as "<EndPoint>"
    #Then I should validate the status code as "200"OK
    # And I should fetch response for API
    # And I should validate the format of Registration of Agent Microservice
    #And I should validate the system info of the response of registration API
    Examples: 
      | RowIndex | POSTEndPoint           | Statuscode | EndPoint                                                     | Status |
      |        1 | /agent/v1/registration |        200 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | true   |
      |        2 | /agent/v1/registration |        200 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | true   |
      |        3 | /agent/v1/registration |        200 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | false  |
      |        4 | /agent/v1/registration |        200 | /agent/v1/partner/PartnerID/endpoint/EndPointID/registration | true   |
