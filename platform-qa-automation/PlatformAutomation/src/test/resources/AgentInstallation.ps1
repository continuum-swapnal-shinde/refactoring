Param([string]$buildNumber,[string]$ENV_NAME)
$folder = Test-Path C:\Juno-Agent2.0\Downloads
$destinationFolder = "C:\Juno-Agent2.0\Downloads\Platform-Agent-Exe.exe"

$sourceURL = "http://artifact.corp.continuum.net:8081/artifactory/int-dev_platform-windows-agent-package/"

$ServiceName = "ITSPlatform"
$maxRepeat = 20
$status = "Running" 

$sourceURL += $buildNumber
$sourceURL +="/Platform-Agent-Exe.exe"

Write-Output $sourceURL


if($folder){
	Write-Output "Folder is present"
	cd C:\Juno-Agent2.0\Downloads
	#rm C:\Juno-Agent2.0\Downloads -Recurse
	#Remove-Item C:\Juno-Agent2.0\Downloads -force -Recurse
	#md C:\Juno-Agent2.0\Downloads
	Write-Output "Deleting and Creating New Folder"
	
}
else{
	md C:\Juno-Agent2.0\Downloads
	Write-Output "Setup Folder is not present created new folder"
}
Try
{
  
$res = get-WmiObject -Class Win32_Share -Filter(new-object System.Net.WebClient).DownloadFile($sourceURL,$destinationFolder)

if($res -ne $Null){
	Write-Output "Setup Download Completed........."
	cd C:\Juno-Agent2.0\Downloads
	Write-Output "Starting Agent Installation Process........."
	.\Platform-Agent-Exe.exe /s ENV=$ENV_NAME
	Write-Host ".\Platform-Agent-Exe.exe /s ENV=$ENV_NAME"
	$datetime = (get-date).ToUniversalTime().ToString("yyyy-MM-ddTHH:mm:ss:fffZ")
	echo $datetime
	echo "UTC time is: $dateTime"
	Write-Host "Platform-Agent-Exe.exe /s ENV=$ENV_NAME"
	sleep 60
	do 
	{
    $count = (Get-Service -Name $ServiceName | ? {$_.status -eq $status}).count
    $maxRepeat--
    sleep -Milliseconds 4000
	Write-Host $maxRepeat
	} until (
		
	$count -eq 0 -or $maxRepeat -eq 0
	
	)
	
	Write-Host "Setup Installation Completed Successfully"
	$svc1 = Get-Service -Name $ServiceName
	$ServiceStatus = "Service Status is "
	$ServiceStatus += $svc1.Status
	Write-Host $ServiceStatus
	}
	
}

Catch
{
	$ErrorMessage = $_.Exception.Message
	Write-Output $ErrorMessage
    $FailedItem = $_.Exception.ItemName
	Write-Output $FailedItem
	Break
}
