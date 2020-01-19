# Teaching-HEIGVD-AMT-2019-Project-Two
Author : Caroline Monthoux, Rémi Poulard

## Introduction

Welcome on our second AMT project. The main objective of this is to apply the patterns and techniques presented during the lectures, and to create APIs. The second objective is to show the difference between coding with a framework and coding without. In this second project we used _Spring_ Framework, _Spring Boot_ and _Spring Data_. 

## Requirements

You'll need to have `docker` and `docker-compose` installed on your machine in order to launch the containers. You will also need `maven` to generate _.jar_ files.

## Build the project

The script `launch_project.sh` is used to start the production environment. The script accept parameters like `-f` to make a full build (`mvn clean install` on both API modules, `docker-compose up --build`).

```bash
$./launch_project.sh [-f]
```

Once the topology is ready you can have the swagger documentation for both APIs:

- Authentication API http://localhost/auth/swagger-ui.html
- Unicorns API http://localhost/app/swagger-ui.html

When the topology is ready the script ask you if you want to run test. If you want to run tests after you can simply use the script below : 

```bash
$./run_tests.sh
```

The script `./cleanDocker.sh` will remove folders created to make volume on database container for data persistence and down the topology.

```bash
$./cleanDocker.sh
```

**Note : ** Tests will add some testing data in database, if you don't want these data in the environment you can run the script `./cleanDocker.sh` and `./launch_project.sh` again.

## Report

* [**Part 1.**](doc/apiDocumentation.md) What has been implemented
* **[Part 2.](doc/apiImplementation.md)** How it has been implemented
* **[Part 3.](doc/apiTests.md)** The testing strategy behind it and the experiment
* **[Part 4.](doc/apiBugs.md)** Known bugs and limitations





