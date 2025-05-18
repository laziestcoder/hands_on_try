#!/bin/bash

set -e

# Function to print a message with formatting
function print_message() {
  echo "========================================"
  echo "$1"
  echo "========================================"
}

# Update system packages
print_message "Updating system packages..."
sudo apt-get update -y

# Install required dependencies
print_message "Installing required dependencies..."
sudo apt-get install -y apt-transport-https ca-certificates curl

# Install kubectl
print_message "Installing kubectl..."
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

# Verify kubectl installation
if kubectl version --client &>/dev/null; then
  print_message "kubectl installed successfully!"
kubectl version --client
else
  echo "kubectl installation failed."
  exit 1
fi

# Install Minikube
print_message "Installing Minikube..."
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# Verify Minikube installation
if minikube version &>/dev/null; then
  print_message "Minikube installed successfully!"
  minikube version
else
  echo "Minikube installation failed."
  exit 1
fi

# Clean up downloaded files
print_message "Cleaning up..."
rm -f kubectl minikube-linux-amd64

print_message "Installation complete!"
echo "You can now use Minikube and kubectl. Start Minikube with: minikube start"
