DOCKER_DIR=topology/topology-amt-prod
sudo rm -rf ${DOCKER_DIR}/data_app
sudo rm -rf ${DOCKER_DIR}/data_auth
docker-compose -f ${DOCKER_DIR}/docker-compose.yml down
docker-compose -f ${DOCKER_DIR}/docker-compose.yml down
