#!/bin/bash
set -e

APP_NAME="cn1g9-bff"
APP_DIR="/home/ec2-user/$APP_NAME"

echo "📦 Installing Docker and Docker Compose..."
sudo yum update -y
sudo yum install -y docker
sudo service docker start
sudo usermod -aG docker ec2-user
newgrp docker


DOCKER_CONFIG=${DOCKER_CONFIG:-$HOME/.docker}
mkdir -p $DOCKER_CONFIG/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.38.2/docker-compose-linux-x86_64 -o $DOCKER_CONFIG/cli-plugins/docker-compose
chmod +x $DOCKER_CONFIG/cli-plugins/docker-compose
echo "📁Docker compose success!: $(docker compose version)"

echo "📁 Preparing app directory: $APP_DIR"
mkdir -p $APP_DIR
cd $APP_DIR

curl https://raw.githubusercontent.com/rrooddooxx/cloud-native1_grupo9/refs/heads/main/bff/docker-compose.ec2.yml >> $APP_DIR/docker-compose.yml
echo "📁 Docker compose file downloaded! At: $APP_DIR/docker-compose.yml"
cat docker-compose.yml