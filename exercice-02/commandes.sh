#!/usr/bin/env bash
# Exercice 2 — Jeu 2048 depuis DockerHub
set -e

docker search 2048
docker pull kubespheredev/2048

# Port 8080 puis 8090 déjà occupés sur ma machine -> 8082 (remplacer par 8080:80 si libre)
docker run -d --name exo02-2048 -p 8082:80 kubespheredev/2048

# Navigateur : http://localhost:8082
sleep 2
curl -I http://localhost:8082

# Nettoyage : docker rm -f exo02-2048
