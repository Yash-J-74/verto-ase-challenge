#!/bin/bash

echo "🚀 Starting containers..."
docker-compose up -d

echo "⏳ Waiting for containers to start..."
sleep 5

URL="http://localhost:3000"

# Detect OS and open browser
case "$OSTYPE" in
  linux-gnu*) xdg-open $URL ;;
  darwin*)    open $URL ;; # macOS
  cygwin* | msys* | mingw*) start $URL ;; # Windows Git Bash
  *) echo "👉 Please open $URL manually" ;;
esac
