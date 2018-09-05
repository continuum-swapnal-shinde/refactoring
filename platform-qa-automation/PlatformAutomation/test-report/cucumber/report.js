$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("platform/versionPlugin.feature");
formatter.feature({
  "line": 1,
  "name": "VersionPluginToAMS Validation",
  "description": "",
  "id": "versionplugintoams-validation",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 4,
  "name": "Validation of information returned by version plugin-C3685604",
  "description": "",
  "id": "versionplugintoams-validation;validation-of-information-returned-by-version-plugin-c3685604",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@platformSanity"
    },
    {
      "line": 3,
      "name": "@swa"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "SSH connection establishes with remote test machine from tag",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "User read data from excel with rowIndex \"\u003cRowIndex\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "User fetch EndPointID of the machine from tag",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "User fetch version Information from the system",
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "User fetch plugin payload from console for windows as \"\u003cBatchFile\u003e\" for \"\u003cType\u003e\"",
  "keyword": "Then "
});
formatter.step({
  "line": 10,
  "name": "User validate system information with version plugin information",
  "keyword": "And "
});
formatter.examples({
  "line": 12,
  "name": "",
  "description": "",
  "id": "versionplugintoams-validation;validation-of-information-returned-by-version-plugin-c3685604;",
  "rows": [
    {
      "cells": [
        "RowIndex",
        "BatchFile",
        "Type"
      ],
      "line": 13,
      "id": "versionplugintoams-validation;validation-of-information-returned-by-version-plugin-c3685604;;1"
    },
    {
      "cells": [
        "1",
        "versionTrigger.bat",
        "version"
      ],
      "line": 14,
      "id": "versionplugintoams-validation;validation-of-information-returned-by-version-plugin-c3685604;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 68547294,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "Validation of information returned by version plugin-C3685604",
  "description": "",
  "id": "versionplugintoams-validation;validation-of-information-returned-by-version-plugin-c3685604;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@platformSanity"
    },
    {
      "line": 3,
      "name": "@swa"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "SSH connection establishes with remote test machine from tag",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "User read data from excel with rowIndex \"1\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "User fetch EndPointID of the machine from tag",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "User fetch version Information from the system",
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "User fetch plugin payload from console for windows as \"versionTrigger.bat\" for \"version\"",
  "matchedColumns": [
    1,
    2
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 10,
  "name": "User validate system information with version plugin information",
  "keyword": "And "
});
formatter.match({
  "location": "CommonSteps.sshConnectionEstablishedWithRemoteTestMachineFromTag()"
});
formatter.write("\u003cfont color\u003d\"blue\"\u003e\u003cb\u003eConnection Establish for : 10.2.114.113:22\u003c/b\u003e\u003c/font\u003e\u003cbr/\u003e");
formatter.result({
  "duration": 678032469,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 41
    }
  ],
  "location": "CommonSteps.i_read_data_from_excel_with_rowIndex(String)"
});
formatter.result({
  "duration": 5324598,
  "status": "passed"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.after({
  "duration": 17003127,
  "status": "passed"
});
});