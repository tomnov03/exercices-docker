FROM mysql:8.4
# Tout script .sql de ce dossier est exécuté au premier démarrage (base vide)
COPY initdb/ /docker-entrypoint-initdb.d/
