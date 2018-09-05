Feature: Mock Data Creation For System's Asset

@Win7_32Bit @Win7_64Bit @Win8_32Bit @Win8_64Bit @Win10_32Bit @Win10_64Bit @Win8.1_32Bit @WinXP_32Bit @WinXP_64Bit @WinVista_32Bit @WinVista_64Bit @WinServer8_64Bit @WinServer12_64Bit @WinServer16_64Bit @WinServer12R2_64Bit
Scenario Outline: Mock Data Creation pertaining to Asset Information-
      Given SSH connection established with remote test machine from tag
      Given I read data from excel with rowIndex "<RowIndex>"
      And I execute mock set of data using Jmeter for "<csvFilePath>" and "<jmxFilePath>"
      
      Examples:
     |RowIndex|csvFilePath                                     |jmxFilePath                                |
     |1       |C:\MockDataPFTest\MockTestDataForAttributes.csv |C:\MockDataPFTest\MockDataSetUpTestPlan.jmx|