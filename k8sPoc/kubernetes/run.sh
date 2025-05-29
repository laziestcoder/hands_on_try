#!/bin/bash

# Replace with your image name
IMAGE_NAME="test"
# You can modify this tag as needed
IMAGE_TAG="latest"
# Path to your Dockerfile (use "." if it's in the current directory)
DOCKERFILE_PATH="/home/testOrg.com/towfiqul.exabyting/workspace/testOrg-test/"
# Kubernetes namespace (change if needed)
NAMESPACE="default"

# Deploy the image using Kubernetes
echo "Deploying the image to Kubernetes..."
kubectl delete -f deployment.yaml -n $NAMESPACE
kubectl delete -f test-service.yaml
minikube image unload test:latest

# Load image in kubernetes
minikube image load $IMAGE_NAME:$IMAGE_TAG

# Update the deployment.yaml with the correct image
sed -i "s|image:.*|image: $IMAGE_NAME:$IMAGE_TAG|" deployment.yaml

# Apply the deployment.yaml to Kubernetes
kubectl apply -f deployment.yaml -n $NAMESPACE

# Verify the deployment status
echo "Verifying the deployment status..."
kubectl get pods -n $NAMESPACE

# Apply the service.yaml to Kubernetes
kubectl apply -f test-service.yaml
kubectl get service -n $NAMESPACE
