#!/usr/bin/env bash

set -Eeuo pipefail

cd api-gateway

./gradlew clean build
docker build -t api-gateway:latest .

cd ../hash-unlocker
./gradlew clean build
docker build -t hash-unlocker:latest .

docker image prune -f --filter "dangling=true"
