#!/usr/bin/env bash
# Exercice 4 — Deux conteneurs qui communiquent via un réseau Docker
set -e

# 1. Réseau dédié
docker network create exo04-net

# 2. Premier conteneur + installation de ping
docker run -d --name exo04-base --network exo04-net ubuntu:24.04 sleep infinity
docker exec exo04-base bash -c "apt-get update && apt-get install -y iputils-ping"

# 3. Sauvegarde de l'image contenant ping
docker commit exo04-base ubuntu-ping

# 4. Second conteneur depuis cette image, sur le même réseau
#    (un conteneur déjà lancé se rattache avec : docker network connect exo04-net <nom>)
docker run -d --name exo04-b --network exo04-net ubuntu-ping sleep infinity
docker network inspect exo04-net --format '{{range .Containers}}{{.Name}} {{end}}'

# 5. Test de la résolution DNS interne, dans les deux sens
docker exec exo04-base ping -c2 exo04-b
docker exec exo04-b ping -c2 exo04-base

# Nettoyage : docker rm -f exo04-base exo04-b && docker network rm exo04-net
