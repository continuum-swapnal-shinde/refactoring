Feature: Mailbox Validation Execute API

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate status code of Execute POST API for name field-C1820370
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        1 | /agent/v1/EndPointID/execute |        200 |

  Scenario Outline: Validate status code of Execute POST API for name field as numeric data type-C1820371
    Given SSH connection establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        400 |
      |        1 | /agent/v1/EndPointID/execute |        400 |

  Scenario Outline: Validate status code of Execute POST API for name field as special characters data type-C1820372
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        400 |
      |        1 | /agent/v1/EndPointID/execute |        400 |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate status code of Execute POST API for name field with min length < 4-C1820373
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        400 |
      |        1 | /agent/v1/EndPointID/execute |        400 |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate status code of Execute POST API for name field with min length > 20-C1820374
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        400 |
      |        1 | /agent/v1/EndPointID/execute |        400 |

  @Regression_mail
  Scenario Outline: Validate REST endpoint for Execute POST API with "type" as "SCHEDULE" in request payload-C1820375
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |

  @Regression_Mailboxnew
  Scenario Outline: Validate status code of Execute POST API for path field if message type is schedule
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        200 |
      |        3 | /agent/v1/EndPointID/execute |        200 |
      |        4 | /agent/v1/EndPointID/execute |        200 |
      |        5 | /agent/v1/EndPointID/execute |        200 |
      |        6 | /agent/v1/EndPointID/execute |        200 |
      |        7 | /agent/v1/EndPointID/execute |        200 |
      |        8 | /agent/v1/EndPointID/execute |        400 |
      |        9 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox1
  Scenario Outline: Validate REST endpoint for Execute POST API with "type" as "CONFIGURATION" in request payload-C1820376
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |

  @Regression_Mailbox1
  Scenario Outline: Validate status code of Execute POST API for path field if message type is configuration for asset plugin
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |

  @Regression_Mailbox1
  Scenario Outline: Validate status code of Execute POST API for path field if message type is configuration for performance plugin
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |

  @Regression_Mailbox1
  Scenario Outline: Validate status code of Execute POST API for version field-C1820378,C1826890,C1820379,C1820380,C1826891
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        400 |
      |        3 | /agent/v1/EndPointID/execute |        400 |
      |        4 | /agent/v1/EndPointID/execute |        400 |
      |        5 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API for timestampUTC field-C1820382,C1820383,C1826892,C1826893
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        400 |
      |        3 | /agent/v1/EndPointID/execute |        400 |
      |        4 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API to chage LogLevel config of agent core-C1820403,C1820404,C1820405,C1820406,C1820407,C1826895,C1826896,C1826897,C1826898,C1826899,C1826901,C1826899,C1826900
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        200 |
      |        3 | /agent/v1/EndPointID/execute |        200 |
      |        4 | /agent/v1/EndPointID/execute |        200 |
      |        5 | /agent/v1/EndPointID/execute |        200 |
      |        6 | /agent/v1/EndPointID/execute |        400 |
      |        7 | /agent/v1/EndPointID/execute |        400 |
      |        8 | /agent/v1/EndPointID/execute |        400 |
      |        9 | /agent/v1/EndPointID/execute |        400 |
      |       10 | /agent/v1/EndPointID/execute |        400 |
      |       11 | /agent/v1/EndPointID/execute |        400 |
      |       12 | /agent/v1/EndPointID/execute |        400 |
      |       13 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API to chage config of agent core
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        403 |
      |        2 | /agent/v1/EndPointID/execute |        403 |
      |        3 | /agent/v1/EndPointID/execute |        403 |
      |        4 | /agent/v1/EndPointID/execute |        403 |
      |        5 | /agent/v1/EndPointID/execute |        403 |
      |        6 | /agent/v1/EndPointID/execute |        403 |
      |        7 | /agent/v1/EndPointID/execute |        403 |
      |        8 | /agent/v1/EndPointID/execute |        403 |
      |        9 | /agent/v1/EndPointID/execute |        403 |
      |       10 | /agent/v1/EndPointID/execute |        403 |
      |       11 | /agent/v1/EndPointID/execute |        403 |
      |       12 | /agent/v1/EndPointID/execute |        403 |
      |       13 | /agent/v1/EndPointID/execute |        403 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API to chage PluginsLocation config of agent core
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        400 |
      |        3 | /agent/v1/EndPointID/execute |        400 |
      |        4 | /agent/v1/EndPointID/execute |        400 |
      |        5 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API to chage config of agent core
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        403 |
      |        2 | /agent/v1/EndPointID/execute |        403 |
      |        3 | /agent/v1/EndPointID/execute |        403 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API to chage config of agent core
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        403 |
      |        2 | /agent/v1/EndPointID/execute |        403 |
      |        3 | /agent/v1/EndPointID/execute |        403 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API for task field
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        200 |
      |        3 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API for executeNow field
    Given User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        200 |
      |        3 | /agent/v1/EndPointID/execute |        200 |
      |        4 | /agent/v1/EndPointID/execute |        200 |
      |        5 | /agent/v1/EndPointID/execute |        400 |
      |        6 | /agent/v1/EndPointID/execute |        400 |

  @Regression_Mailbox
  Scenario Outline: Validate status code of Execute POST API for schedule message field
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the "<Statuscode>" as in response

    Examples: 
      | RowIndex | EndPoint                     | Statuscode |
      |        1 | /agent/v1/EndPointID/execute |        200 |
      |        2 | /agent/v1/EndPointID/execute |        200 |
      |        3 | /agent/v1/EndPointID/execute |        400 |

  @Regressionedid1
  Scenario Outline: Validate agent config json manifest different log levels
    Given SSH connection  establishes with remote test machine for "<OSEdition>"
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When SSH connection  establishes with remote test machine for "<OSEdition1>"
    Then User read data from excel with rowIndex "<RowIndex>"
    When User fetch AgentCore EndPointID from second agentCore config file for "<OSEdition1>"
    Then User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    And User should validate the status code as "200"OK
    And User fetch and validate performance plugin json from "<OSEdition1>" for log level changes as "<LogLevel>"
    When SSH connection  establishes with remote test machine for "<OSEdition>"
    Then User fetch and validate performance plugin json from "<OSEdition>" for log level changes as "<LogLevel>"

    Examples: 
      | RowIndex | EndPoint          | LogLevel | OSEdition  | OSEdition1 |
      |        1 | /agent/v1/execute | ERROR    | Win7_32Bit | Win7_64Bit |

  @Regressionedid_12
  Scenario Outline: Validate plugin schedule is changed whenever schedule POST message is posted
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User fetch and validate updated plugin schedule for "<Component>"
    When User read GET response from performance micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch previous timestamp for "<Component>" API
    When User read GET response from performance micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch new timestamp for "<Component>" API
    And User validate a minute difference between subsequent timestamps

    Examples: 
      | RowIndex | EndPoint                     | Component | GETENDPoint                                |
      |        1 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory |

  @PlatformRegressionneedtocheck @Win10_64Bit
  Scenario Outline: Validate agent config json correctly updates with OldLogFileToKeep for diff endpoints-C1962300
    Given SSH connection  establishes with remote test machine for "<OSEdition>"
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When SSH connection  establishes with remote test machine for "<OSEdition1>"
    Then User read data from excel with rowIndex "<RowIndex>"
    And User fetch AgentCore EndPointID from second agentCore config file for "<OSEdition1>"
    When User send a agent configuration change Post request for multiple endpoints to mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User fetch and validate agent config json from "<OSEdition1>" for FileCount changes as "<FileCount>"
    When SSH connection  establishes with remote test machine for "<OSEdition>"
    Then User fetch and validate agent config json from "<OSEdition>" for FileCount changes as "<FileCount>"

    Examples: 
      | RowIndex | EndPoint                     | FileCount | OSEdition  | OSEdition1  |
      |        1 | /agent/v1/EndPointID/execute |         4 | Win8_64Bit | Win10_64Bit |
      |        2 | /agent/v1/EndPointID/execute |         5 | Win8_64Bit | Win10_64Bit |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate agent config json correctly updates with OldLogFileToKeep for single endpoint-C1962298
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    #Given SSH connection  establishes with remote test machine for "<OSEdition1>"
    # And User read data from excel with rowIndex "<RowIndex>"
    #And User fetch AgentCore EndPointID from second agentCore config file for "<OSEdition1>"
    When User send a agent configuration change Post request for single endpoints to mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    # And User fetch and validate agent config json from "<OSEdition1>" for FileCount changes as "<FileCount>"
    # Given SSH connection  establishes with remote test machine for "<OSEdition>"
    And User fetch and validate agent config json from "<OSEdition>" for FileCount changes as "<FileCount>"

    Examples: 
      | RowIndex | EndPoint          | FileCount |
      |        1 | /agent/v1/execute |         4 |
      |        2 | /agent/v1/execute |         5 |

  @Regressionmws
  Scenario Outline: Validate performance plugin config changes for LRP-C1962299
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a performance plugin configuration change Post request for single endpoint to mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User fetch and validate performance plugin json for log level changes as "<LogLevel>" on "<OSEdition>"

    Examples: 
      | RowIndex | EndPoint          | LogLevel | GETENDPoint                                |
      |        1 | /agent/v1/execute | INFO     | /performance/v1/endpoint/EndPointID/memory |
      |        2 | /agent/v1/execute | DEBUG    | /performance/v1/endpoint/EndPointID/memory |
      |        3 | /agent/v1/execute | ERROR    | /performance/v1/endpoint/EndPointID/memory |

  @Regressionasset
  Scenario Outline: Validate asset plugin config changes for LRP
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a asset plugin configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User fetch and validate asset plugin json for log level changes as "<LogLevel>" on "<OSEdition>"

    Examples: 
      | RowIndex | EndPoint                     | LogLevel | GETENDPoint                                |
      |        1 | /agent/v1/EndPointID/execute | INFO     | /performance/v1/endpoint/EndPointID/memory |
      |        2 | /agent/v1/EndPointID/execute | DEBUG    | /performance/v1/endpoint/EndPointID/memory |
      |        3 | /agent/v1/EndPointID/execute | ERROR    | /performance/v1/endpoint/EndPointID/memory |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: E2E_Validate mailbox message with messageID-C2228018
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch messageID from API response for API
    When User send GET reuest to mailbox API with messageID as "<MailboxGET>"
    Then User should validate the status code in resonse as "404"

    Examples: 
      | RowIndex | EndPoint                     | Component | GETENDPoint                                | MailboxGET                             |
      |        1 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |

  @Regressionmailb
  Scenario Outline: Validate Agent Core support multiple schedule for same Path via Mailbox foe performance plugin
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    When User fetch and validate new plugin schedule for "<Component>"
    Then User fetch registry values of old RMM agent
    When User read GET response from performance micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch previous timestamp for "<Component>" API
    When User read GET response from performance micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch new timestamp for "<Component>" API
    And User validate a minute difference between subsequent timestamps

    Examples: 
      | RowIndex | EndPoint                     | Component | GETENDPoint                                                  |
      |        1 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/partner/PartnerID/endpoint/EndPointID/memory |

  @Regressionmailb
  Scenario Outline: Validate Agent Core support multiple schedule for same Path via Mailbox foe asset plugin
    Given SSH connection  establishes with remote test machine from tag
    When User fetch AgentCore EndPointID from agentCore config file from tag
    Then User read data from excel with rowIndex "<RowIndex>"
    When User send a configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    When User fetch and validate new plugin schedule for "<Component>"
    Then User fetch registry values of old RMM agent
    When User read GET response from micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch previous timestamp for "<Component>" API
    When User read GET response from micro-service and store in file with GET as "<GETENDPoint>"
    Then User should fetch new timestamp for "<Component>" API
    And User validate a minute difference between subsequent timestamps

    Examples: 
      | RowIndex | EndPoint                     | Component | GETENDPoint                                      |
      |        1 | /agent/v1/EndPointID/execute | Asset     | /asset/v1/partner/PartnerID/endpoints/EndPointID |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate mailbox name field for Internalization Languages-C2393560
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a agent configuration change Post request for mailbox API endpoint as "<EndPoint>"
    Then User should validate the status code as "200"OK
    And User should fetch messageID from API response for API
    When User send GET reuest to mailbox API with messageID as "<MailboxGET>"
    Then User should validate the status code in resonse as "404"

    Examples: 
      | RowIndex | EndPoint                     | Component | GETENDPoint                                | MailboxGET                             |
      |        1 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |
      |        2 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |
      |        3 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |
      |        4 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |
      |        5 | /agent/v1/EndPointID/execute | Memory    | /performance/v1/endpoint/EndPointID/memory | /agent/v1/EndPointID/mailbox/messageID |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify bulk mailbox messages are getting processed when agent is online for Scripting Plugin-C2944129
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for bulk messages"<EndPoint>" and "<BulkCount>"
    Then User send a Post request for search mailbox"<POSTEndPoint>" and "<BulkCount>"

    Examples: 
      | RowIndex | EndPoint                     | POSTEndPoint                        | BulkCount |
      |        1 | /agent/v1/EndPointID/execute | /agent/v1/EndPointID/mailbox/search |       100 |

  @PlatformRegression @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify bulk mailbox messages are getting processed when agent is online for LRP-C2944130
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User send a Post request for bulk messages"<EndPoint>" and "<BulkCount>"
    Then User send a Post request for search mailbox"<POSTEndPoint>" and "<BulkCount>"

    Examples: 
      | RowIndex | EndPoint                     | POSTEndPoint                        | BulkCount |
      |        1 | /agent/v1/EndPointID/execute | /agent/v1/EndPointID/mailbox/search |       100 |

  @PlatformRestart @Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Verify bulk mailbox messages are getting processed after agent comes online back from offline mode-C2944131
    Given SSH connection  establishes with remote test machine from tag
    When User read data from excel with rowIndex "<RowIndex>"
    Then User fetch AgentCore EndPointID from agentCore config file from tag
    When User stop the Agent Core Service
    Then User send a Post request for bulk messages"<EndPoint>" and "<BulkCount>"
    And User start the Agent Core Service
    And User send a Post request for search mailbox"<POSTEndPoint>" and "<BulkCount>"

    Examples: 
      | RowIndex | EndPoint                     | OSEdition   | POSTEndPoint                        | BulkCount |
      |        1 | /agent/v1/EndPointID/execute | Win10_64Bit | /agent/v1/EndPointID/mailbox/search |        30 |
