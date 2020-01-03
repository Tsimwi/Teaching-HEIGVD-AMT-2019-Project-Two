lancer le script launchSite
Lors du premier lancement il va y avoir des erreur car les base de données sont pas encore creer
De connecter sur localhost:8888 (mail: amt@amt.com, password: password)
Clique droit sur Servers>Create>server...
General-> Name: app
Connexion-> Host: app_db
Port: 5432
Username: postgres
Password: password
Puis sauvegarder

Clique droit sur Servers>Create>server...
General-> Name: auth
Connexion-> Host: auth_db
Port: 5432
Username: postgres
Password: password
Puis sauvegarder

Puis cliquer sur app puis clique droit sur Databases>Create>Database...
Genaral -> Database: projectTwo

Puis cliquer sur auth puis clique droit sur Databases>Create>Database...
Genaral -> Database: projectTwo


Relancer le script launchSite

