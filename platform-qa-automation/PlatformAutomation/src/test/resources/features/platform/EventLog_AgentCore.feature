Feature: Verification of Events are getting created in the system

  @Event2
  Scenario Outline: Verify Disk File System Error Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition  | LogName | Source | EventID | EntryType | Message                                     | Rule | ExpectedMessage |
      |        1 | Win7_64Bit | System  | Ntfs   |      55 | Error     | Disk File System Error Event Via Powershell | 1a   | Ntfs            |

  @Event1
  Scenario Outline: Verify Disk File System Information Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition   | LogName     | Source         | EventID | EntryType   | Message                                           | Rule | ExpectedMessage |
      |        1 | Win10_32Bit | Application | Windows Backup |    4097 | Information | Disk File System Information Event Via PowerShell | 1b   |            4097 |

  @Event1
  Scenario Outline: Verify Error on Disk File System Information Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition   | LogName     | Source                   | EventID | EntryType   | Message                                                    | Rule  | ExpectedMessage |
      |        1 | Win10_32Bit | Application | Microsoft-Windows-Backup |     753 | Information | Error on Disk File System Information Event Via PowerShell | 1b(2) |             753 |

  @Event1
  Scenario Outline: Verify Error on Disk File System Information Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition   | LogName     | Source         | EventID | EntryType   | Message                                                    | Rule | ExpectedMessage |
      |        1 | Win10_32Bit | Application | Windows Backup |    4098 | Information | Error on Disk File System Information Event Via PowerShell | 1c   |            4098 |

  @Event1
  Scenario Outline: Verify Error on Disk File System Information Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition   | LogName     | Source                   | EventID | EntryType   | Message                                                    | Rule  | ExpectedMessage |
      |        1 | Win10_32Bit | Application | Microsoft-Windows-Backup |     754 | Information | Error on Disk File System Information Event Via PowerShell | 1c(1) |             754 |

  @Event1
  Scenario Outline: Verify Error on Disk File System Information Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition   | LogName     | Source         | EventID | EntryType | Message                                                    | Rule  | ExpectedMessage |
      |        1 | Win10_32Bit | Application | Windows Backup |    4104 | Error     | Error on Disk File System Information Event Via PowerShell | 1c(2) |            4104 |

  @Event3
  Scenario Outline: Verify Machine Restart is Required Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition  | LogName | Source                                | EventID | EntryType   | Message                                                        | Rule  | ExpectedMessage |
      |        1 | Win7_64Bit | System  | Microsoft-Windows-WindowsUpdateClient |      21 | Information | Event for Microsoft-Windows-WindowsUpdateClient via PowerShell | 1c(2) |              21 |

  @Event6
  Scenario Outline: Verify System Crash or Unexpected Reboot Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an event with "<LogName>","<Source>","<EventID>","<EntryType>" and "<Message>"
    Then User validate whether event is created successfully with "<LogName>","<SourceName>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition  | LogName | Source   | EventID | EntryType | Message                                          | Rule  | ExpectedMessage | SourceName                                 |
      |        1 | Win7_64Bit | System  | Bugcheck |    1001 | Error     | Event log crash-unexpected reboot via Powershell | 1c(2) |            1001 | Microsoft-Windows-WER-SystemErrorReporting |

  @Event4
  Scenario Outline: Verify Security Event are getting generated in the system
    Given SSH connection establishes with remote test machine for "<OSEdition>"
    When User create an security event
    Then User validate whether event is created successfully with "<LogName>","<Source>","<EventID>" and "<ExpectedMessage>"

    Examples: 
      | RowIndex | OSEdition  | LogName  | Source                              | EventID | EntryType   | Message                                               | ExpectedMessage |
      |        1 | Win7_64Bit | Security | Microsoft-Windows-Security-Auditing |    4732 | Information | A member was added to a security-enabled local group. |            4732 |
