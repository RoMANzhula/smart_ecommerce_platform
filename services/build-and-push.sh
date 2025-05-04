#!/bin/bash

DOCKER_USERNAME="romanzhula"
VERSION="latest"

SERVICES=(
  "product-service"
  "user-service"
  "recommendation-service"
  "payment-service"
  "order-service"
  "notification-service"
  "inventory-service"
)

for SERVICE in "${SERVICES[@]}"; do
  echo "Processing $SERVICE..."

  if [ -d "$SERVICE" ]; then
    cd $SERVICE || exit
    mvn clean package -DskipTests || { echo "Maven build failed for $SERVICE"; exit 1; }
    docker build . -t $DOCKER_USERNAME/$SERVICE:$VERSION || { echo "Docker build failed for $SERVICE"; exit 1; }
    docker push $DOCKER_USERNAME/$SERVICE:$VERSION || { echo "Docker push failed for $SERVICE"; exit 1; }
    cd ..
    echo "Done: $SERVICE"
  else
    echo "Directory $SERVICE not found, skipping..."
  fi

  echo "-------------------------"
done

echo "All services have been built and pushed."
