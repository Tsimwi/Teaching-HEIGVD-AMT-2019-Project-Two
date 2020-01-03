## Environnement de production

1. lancer la commande `./launchSite.sh -f`
  Lors du premier lancement il va y avoir des erreurs car les bases de données sont pas encore créer

2. Se connecter sur [localhost:8888](localhost:8888) (mail: amt@amt.ch, password: password)

  - Clique droit sur Servers>Create>server...
    General-> Name: app
    Connexion-> Host: app_db
    Port: 5432
    Username: postgres
    Password: password
    Puis sauvegarder

  - Clique droit sur Servers>Create>server...
    General-> Name: auth
    Connexion-> Host: auth_db
    Port: 5432
    Username: postgres
    Password: password
    Puis sauvegarder

  Puis cliquer sur "app" puis clique droit sur Databases>Create>Database...
  Genaral -> Database: projectTwo

  Puis cliquer sur "auth" puis clique droit sur Databases>Create>Database...
  Genaral -> Database: projectTwo

3. Lancer la commande `./launchSite.sh`



## Environnement de code (InteliJ)

1. Se rendre dans le dossier `topology/topology-amt-dev/` faire un `docker-compose up --build`

2. Se connecter sur [localhost:8888](localhost:8888) (mail: amt@amt.ch, password: password)

   - Clique droit sur Servers>Create>server...
     General-> Name: app
     Connexion-> Host: app_db
     Port: 5432
     Username: postgres
     Password: password
     Puis sauvegarder

   - Clique droit sur Servers>Create>server...
     General-> Name: auth
     Connexion-> Host: auth_db
     Port: 5432
     Username: postgres
     Password: password
     Puis sauvegarder

   Puis cliquer sur "app" puis clique droit sur Databases>Create>Database...
   Genaral -> Database: projectTwo

   Puis cliquer sur "auth" puis clique droit sur Databases>Create>Database...
   Genaral -> Database: projectTwo

3.  Lancer intellij puis ouvrer le projet `sagger/full-project`

