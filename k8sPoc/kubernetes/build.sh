#!/bin/bash

# Set variables
# Replace with your image name
IMAGE_NAME="test"
# You can modify this tag as needed
IMAGE_TAG="latest"
# Path to your Dockerfile (use "." if it's in the current directory)
DOCKERFILE_PATH="/home/testOrg.com/towfiqul.exabyting/workspace/testOrg-test/"
# Kubernetes namespace (change if needed)
NAMESPACE="default"

# 1. Build the Docker image
echo "Building Docker image..."
docker stop test
docker rm test
docker rmi $IMAGE_NAME:$IMAGE_TAG
DOCKER_BUILDKIT=1 docker build -t $IMAGE_NAME:$IMAGE_TAG $DOCKERFILE_PATH
