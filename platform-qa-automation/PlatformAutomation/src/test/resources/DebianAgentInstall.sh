#!/bin/bash

linuxbuildno=$1
build_ENV=$2
token_ID=$3

echo "----Create Folders---"
cd /home/qaadmin/
if [ -d /home/qaadmin/Juno-Agent ]; then
	rm -rf Juno-Agent
fi

cd /home/qaadmin
mkdir Juno-Agent
cd /home/qaadmin/Juno-Agent
echo "----Download and Install Debian Package---"


attempt_counter=0
max_attempts=5



until $(curl http://artifact.corp.continuum.net:8081/artifactory/int-dev_platform-agent-debian-sbm/${linuxbuildno}/ContinuumPlatformAgent_2.0.${linuxbuildno}_amd64_Full.deb -o /home/qaadmin/Juno-Agent/ContinuumPlatformAgent_2.0.${linuxbuildno}_amd64_Full.deb); do
    if [ ${attempt_counter} -eq ${max_attempts} ];then
      echo "Max attempts reached"
      exit 1
    fi

    printf '.'
    attempt_counter=$(($attempt_counter+1))
    sleep 30
done

echo root | sudo -S BUILD_ENVIRONMENT=${build_ENV} TOKEN=${token_ID} dpkg -i /home/qaadmin/Juno-Agent/ContinuumPlatformAgent_2.0.${linuxbuildno}_amd64_Full.deb


echo "----End Script---"