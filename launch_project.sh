APP_DIR=swagger/unicorns-api
AUTH_DIR=swagger/auth-api
DOCKER_DIR=topology/topology-amt-prod
AUTH_IMAGE_DIR=images/auth_backend
APP_IMAGE_DIR=images/app_backend
rm error.log
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
    docker-compose -f ${DOCKER_DIR}/docker-compose.yml --log-level ERROR  up -d --build > /dev/null 2>error.log
  else
    docker-compose -f ${DOCKER_DIR}/docker-compose.yml --log-level ERROR  up -d > /dev/null 2>error.log
fi
if [ $? -eq 0 ]
  then
    echo "\n\e[32mEnvironment launched, wait 10 seconds.\e[0m\n"
    printf "Authentication API swagger :\t\e[1mhttp://localhost/auth/swagger-ui.html\e[0m\n"

    printf "Application API swagger:\t\e[1mhttp://localhost/app/swagger-ui.html\e[0m\n"
    read -p "Do you want to launch tests (y/n)?" choice
    case "$choice" in
      y|Y ) ./run_tests.sh;;
      n|N ) echo "no";;
      * ) echo "invalid";;
    esac
  else
    echo "\e[31mDocker-compose up failed. You can have more details in ./error.log\n\n"
fi
