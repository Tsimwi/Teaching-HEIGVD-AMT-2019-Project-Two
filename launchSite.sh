APP_DIR=swagger/unicorns-api
AUTH_DIR=swagger/auth-api
DOCKER_DIR=topology/topology-amt-prod
AUTH_IMAGE_DIR=images/auth_backend
APP_IMAGE_DIR=images/app_backend
if [ $# -eq 1 ]
  then
    if [ $1 = "-f" ]
      then

	fullBuild=true
        echo "\n\nWe remove old target folders\n\n"
        mvn -f ${APP_DIR}/pom.xml clean
        mvn -f ${AUTH_DIR}/pom.xml clean

        echo "\n\nWe create jar files for both api\n\n"
        mvn -f ${APP_DIR}/pom.xml install
        mvn -f ${AUTH_DIR}/pom.xml install
        
	cp ${APP_DIR}/target/*.jar ${APP_IMAGE_DIR}
        cp ${AUTH_DIR}/target/*.jar ${AUTH_IMAGE_DIR}
    fi
fi

echo "\n\nLaunching docker\n\n"
docker-compose -f ${DOCKER_DIR}/docker-compose.yml down
if [ "$fullBuild" = true ]
  then	
    docker-compose -f ${DOCKER_DIR}/docker-compose.yml up --build
  else
    docker-compose -f ${DOCKER_DIR}/docker-compose.yml up
fi

