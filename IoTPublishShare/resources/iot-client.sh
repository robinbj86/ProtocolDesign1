﻿#!/bin/bash
# iot-client.sh: Shell script to start iot clients
clear
echo "##################################################################################"
echo "                           Starting IoT Clients                                   "
echo "##################################################################################"

# define usage function
usage(){
	echo "Incorrect Usage"
	echo "Usage: ./iot-client <server ip> <server port> <client port> <version> [list of sensor ids]"
	echo "                                or alternatively                            "
	echo "       ./iot-client <server ip> <server port> <client port> <version> find                "
	echo "Example: ./iot-client.sh 127.0.0.1 5061 5070 1 find                              "
}

curr_time=`date +%s`
echo "Starting client at :"$curr_time
mkdir ./logs 1>./null 2>./null
# You need atleast arguments to the shell script
if [ $# -lt 5 ]
then
  usage
  exit 1
fi

if [ $4 == "find" ]
then
  echo "Searching for available sensors in the server with version:" $3
  echo "Results will be shown in ten seconds"
  java -cp iotps.jar com.aalto.protocol.design.iotps.start.IoTPSClientStarter $@ 
  sleep 10
  exit 1
else
  echo "Subscribing to server with IP:"$1 " Port:" $2
  #java -cp iotps.jar com.aalto.protocol.design.iotps.start.IoTPSClientStarter $@ 1>./logs/client_info_$curr_time.log 2>./logs/client_error_$curr_time.log &
  java -cp iotps.jar com.aalto.protocol.design.iotps.start.IoTPSClientStarter $@
fi


