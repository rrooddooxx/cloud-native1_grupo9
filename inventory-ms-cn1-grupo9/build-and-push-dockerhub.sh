#!/bin/bash

set -e

APP_NAME="cn1g9-inventory"
DOCKERHUB_USERNAME="widew3b"
IMAGE_TAG="latest"

FULL_IMAGE_NAME="$DOCKERHUB_USERNAME/$APP_NAME:$IMAGE_TAG"

echo "🛠️ Building multi-arch Docker image (linux/amd64)..."
docker buildx create --use --name amd64-builder || true
docker buildx build \
  --platform=linux/amd64 \
  --builder=amd64-builder \
  -t $FULL_IMAGE_NAME \
  . \
  --push

echo "✅ Image pushed to Docker Hub: $FULL_IMAGE_NAME"