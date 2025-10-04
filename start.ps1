Write-Output "Starting containers..."
docker-compose up -d

# Function to check container health
function Wait-ForContainerHealthy {
    param(
        [string]$containerName,
        [int]$intervalSeconds = 2,
        [int]$timeoutSeconds = 30
    )

    $elapsed = 0
    while ($true) {
        $status = docker inspect --format='{{.State.Health.Status}}' $containerName 2>$null
        if ($status -eq "healthy") {
            Write-Output "$containerName is healthy!"
            break
        }
        elseif ($status -eq "unhealthy") {
            Write-Output "$containerName is unhealthy. Waiting..."
        }
        else {
            Write-Output "$containerName status: $status. Waiting..."
        }

        Start-Sleep -Seconds $intervalSeconds
        $elapsed += $intervalSeconds

        if ($elapsed -ge $timeoutSeconds) {
            Write-Output "Timeout waiting for $containerName to become healthy."
            break
        }
    }
}

Wait-ForContainerHealthy -containerName "quiz-backend" -intervalSeconds 5 -timeoutSeconds 300

Write-Output "Waiting a few more seconds for frontend..."
Start-Sleep -Seconds 5

$URL = "http://localhost:3000"
Write-Output "Opening $URL"
Start-Process $URL