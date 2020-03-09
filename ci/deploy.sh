#!/bin/bash

APP_FOLDER="$(pwd)"
PROJECT_NAME="auth-server"
DESTINATION_FOLDER="~/$PROJECT_NAME"

# Setting up env
if [ "$DOCKER_ENV" = "production" ]
then
	APP_FOLDER=DESTINATION_FOLDER
  DOCKER_PORTS="81:5000"
	DOCKER_MEMORY="1024m"
else
  DOCKER_PORTS="82:8082"
	DOCKER_MEMORY="1024m"
fi

createDockerContainer() {
	# Access project folder
	cd $APP_FOLDER

	# Build Image
	docker image build \
		-t $PROJECT_NAME .

	# Remove old docker container
	docker container rm \
		-f $PROJECT_NAME

	# Run docker container
	docker container run \
		--name $PROJECT_NAME \
		--memory=$DOCKER_MEMORY \
		--restart=always \
		-p $DOCKER_PORTS \
		-d $PROJECT_NAME
}

createDockerContainer
