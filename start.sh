#!/bin/bash
set -e

echo "Starting containers..."
docker-compose up -d

# Wait for container to be healthy
wait_for_container_healthy() {
  local container_name="$1"
  local interval_seconds="${2:-2}"
  local timeout_seconds="${3:-30}"

  local elapsed=0

  while true; do
    # Get the health status
    status=$(docker inspect --format='{{.State.Health.Status}}' "$container_name" 2>/dev/null || echo "unknown")

    if [[ "$status" == "healthy" ]]; then
      echo "$container_name is healthy!"
      break
    elif [[ "$status" == "unhealthy" ]]; then
      echo "$container_name is unhealthy. Waiting..."
    else
      echo "$container_name status: $status. Waiting..."
    fi

    sleep "$interval_seconds"
    elapsed=$((elapsed + interval_seconds))

    if (( elapsed >= timeout_seconds )); then
      echo "Timeout waiting for $container_name to become healthy after ${timeout_seconds}s."
      break
    fi
  done
}

# Wait for backend container to become healthy
wait_for_container_healthy "quiz-backend" 5 300

echo "Waiting a few more seconds for frontend..."
sleep 5

URL="http://localhost:3000"
echo "Opening $URL"

# Detect OS and open browser
case "$OSTYPE" in
  linux-gnu*) xdg-open "$URL" >/dev/null 2>&1 ;;
  darwin*)    open "$URL" >/dev/null 2>&1 ;; # macOS
  cygwin* | msys* | mingw*) start "$URL" ;;
  *) echo "Please open $URL manually" ;;
esac
