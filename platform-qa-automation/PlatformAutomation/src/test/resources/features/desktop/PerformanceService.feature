Feature: Performance Service API

  Background: 
    Given SSH connection established with remote test machine
    And Check Cassandra Status

  @Smoke12 @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST endpoint for Memory Performance API
    And I Start platform service
    When I send a Get request for API endpoint as "<EndPoint>"
    Then I should validate the status code as "200"OK
    #And I Close SSH Connection

    Examples: 
      | EndPoint                              |
      | 8083/performance/v1/endpoint/1/memory |

  @Smoke12 @Win7_32Bit  @WinServer8_Standard   @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
  Scenario Outline: Validate REST response format for Memory Performance API
    Given I Start platform service
    When I send a Get request for API endpoint as "<EndPoint>"
    Then I should validate the status code as "200"OK
    And I should fetch response for API
    And I should validate the format of Memmory performance response
    #And I Close SSH Connection

    Examples: 
      | EndPoint                              |
      | 8083/performance/v1/endpoint/1/memory |
