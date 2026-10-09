#!/usr/bin/env bash
# Exercice 1 — Image Ubuntu + NGINX via docker commit
set -e

# 1. Créer un conteneur Ubuntu (maintenu en vie)
docker run -d --name exo01-ubuntu ubuntu:24.04 sleep infinity

# 2-4. Entrer dans le conteneur, mettre à jour les paquets, installer NGINX, sortir
#      (interactif : docker exec -it exo01-ubuntu bash)
docker exec exo01-ubuntu bash -c "apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y nginx"
docker exec exo01-ubuntu nginx -v

# 5. Sauvegarder l'état du conteneur en nouvelle image
docker commit -c 'EXPOSE 80' -c 'CMD ["nginx","-g","daemon off;"]' exo01-ubuntu ubuntu-nginx
docker rm -f exo01-ubuntu

# Vérification
docker run -d --name exo01-test -p 8101:80 ubuntu-nginx
sleep 2
curl -I http://localhost:8101
docker rm -f exo01-test
