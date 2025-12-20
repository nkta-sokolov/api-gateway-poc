#!/usr/bin/env bash

set -Eeuo pipefail

docker compose up -d redis postgres hash-unlocker api-gateway