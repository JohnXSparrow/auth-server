#!/bin/bash

PROJECT_NAME="auth-server"
DESTINATION_FOLDER="~/$PROJECT_NAME"
AUTHORIZED_KEYS="/var/lib/jenkins/.ssh/authorized_keys/key.pem"

DevelopmentBuild() {
  SERVER_IP="192.168.0.113"

  # Creating destination folder
  ssh -i $AUTHORIZED_KEYS ec2-user@$SERVER_IP \
    mkdir -p $DESTINATIION_FOLDER

  # Copying Dockerfile and related
  scp -i $AUTHORIZED_KEYS \
    Dockerfile \
    ci/deploy.sh \
    ec2-user@$SERVER_IP:$DESTINATIION_FOLDER

  scp -i $AUTHORIZED_KEYS \
    target/$PROJECT_NAME.jar \
    ec2-user@$SERVER_IP:$DESTINATIION_FOLDER
    #    target/classes/application.properties \

  # Running deploy in Docker
	ssh -i $AUTHORIZED_KEYS ec2-user@$SERVER_IP \
		"cd $DESTINATIION_FOLDER && \
    	DOCKER_ENV=development \
    	sh deploy.sh"
}

# Param for choose env
case $1 in
   "-dev") DevelopmentBuild
         ;;
   "-prod") ProductionBuild
         ;;
   *) echo "Invalid option!"
      exit 1
      ;;
esac
