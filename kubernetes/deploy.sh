#!/bin/bash
set -e

echo "Applying Kubernetes configurations..."
kubectl apply -k ./kubernetes/

echo "Waiting for database to be ready..."
kubectl wait --for=condition=ready pod -l app=landing-db --timeout=120s

echo "Waiting for application to be ready..."
kubectl wait --for=condition=ready pod -l app=landing-app --timeout=120s

echo "Getting service URL..."
echo "Application should be accessible at:"
kubectl get service landing-landing-app-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}'
echo ":8080"

echo "Deployment completed successfully!"