-- Deux bases logiques sur la même instance MySQL, accessibles à l'utilisateur "dogs"
CREATE DATABASE IF NOT EXISTS dogsdb;
CREATE DATABASE IF NOT EXISTS logsdb;
GRANT ALL PRIVILEGES ON dogsdb.* TO 'dogs'@'%';
GRANT ALL PRIVILEGES ON logsdb.* TO 'dogs'@'%';
FLUSH PRIVILEGES;
