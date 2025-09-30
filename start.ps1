Write-Output "🚀 Starting containers..."
docker-compose up -d

Write-Output "⏳ Waiting for containers to start..."
Start-Sleep -Seconds 5

$URL = "http://localhost:3000"
Start-Process $URL