# Teaching-HEIGVD-AMT-2019-Project-Two
Author : Caroline Monthoux, Rémi Poulard

## Introduction

Welcome on our second AMT project. The main objective of this is to apply the patterns and techniques presented during the lectures, and to create an API. The second objective is to show the difference between coding with a framework and coding without. In this second project we used _Spring_ Framework, _Spring Boot_ and _Spring Data_. 

## Requirements

You'll need to have `docker` and `docker-compose` installed on your machine in order to launch the containers. You will also need `maven` to generate a .jar files.

## Build the project

The script `launch_project.sh` is used to start the production environment. The script accept parameters like `-f` to make a full build (`mvn clean install` on both API modules, `docker-compose up --build`).

```bash
$./launch_project.sh -f
```

Once the environment is up you can launch tests. To do that you simply have to run the script :

```bash
$./run_tests.sh
```



**Note : **Tests will add some testing data in database, if you don't want these data in the environment you can run the script `./cleanDocker.sh` and `./launch_project.sh` again.

## Report

* [**Part 1.**](doc/apiDocumentation.md) What has been implemented
* **[Part 2.](doc/apiImplementation.md)** How it has been implemented
* **[Part 3.](doc/apiTests.md)** The testing strategy behind it
* **[Part 4.](doc/EXPERIMENT.md)** The experiment made to answer the performance tests
* **[Part 5.](doc/BUGS_LIMITATIONS.md)** Known bugs and limitations





