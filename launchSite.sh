APP_DIR=swagger/unicorns-api
AUTH_DIR=swagger/auth-api
DOCKER_DIR=topology/topology-amt-prod
AUTH_IMAGE_DIR=images/auth_backend
APP_IMAGE_DIR=images/app_backend

if [ $1 -ne "-s" ]
then
  echo "\n\nWe remove old target folders\n\n"
  mvn -f ${APP_DIR}/pom.xml clean
  mvn -f ${AUTH_DIR}/pom.xml clean

  echo "\n\nWe create jar files for both api\n\n"
  mvn -f ${APP_DIR}/pom.xml install
  mvn -f ${AUTH_DIR}/pom.xml install
fi

cp ${APP_DIR}/target/*.jar ${APP_IMAGE_DIR}
cp ${AUTH_DIR}/target/*.jar ${AUTH_IMAGE_DIR}


docker-compose -f ${DOCKER_DIR}/docker-compose.yml down
docker-compose -f ${DOCKER_DIR}/docker-compose.yml up --build


