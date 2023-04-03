# Demo Animal API 

## Table of content
- [Description](#description)
- [Assumptions](#assumptions)
- [Architecture and Design](#architecture-and-design)
- [Environment](#environment)
- [Package](#package)
- [Build and Run](#build-and-run)
- [Suggestions](#suggestions)

## Description

This application is an API responsible to store data from pets(Animals) of the partners.
This API contains the following methods:
  - Create animals
  - Update Status
  - Find animals by filter
  - 
This application receive needs to configure one environment variable.

## Assumptions

- You will need Java 17 version because of use Spring Boot 3.
- We are considering to run application o Intellij.


## Architecture and Design

- In this application we choose a Hexagonal Architecture
- Isolate class in layers:
    - Layer for business rules (package domain.adapter.services)
    - Layer for input (read files) classes (package application.controller) for rest apis
    - Layer for infrastructure classes (package infrastructure) for files and database access
    - Layer for domain classes (package domain)
    


## Environment
This application requires the following requirements to build and run:
- **[Required]** [Java17](https://www.java.com/en/download/help/whatis_java.html) - Programming language version 11
- [IntelliJ](https://www.jetbrains.com/idea/) - Recommended IDE
- **[Required]** [Gradle](https://gradle.org/) - Dependency Management

This application can run in multiple operational systems, we recommend to use Linux or Windows.


## Build and Run

This application needs one environment variables:
    - images_folder - this is used to set a folder to store image files.
You should set this variable on Intellij or if you want you can change on build.gradle on 'test' session.

``` 
test {
	environment 'images_folder','/home/ronan/files'
}

bootRun {
	systemProperty 'images_folder','/home/ronan/files'
}
```

### Build

After clone the repository developer needs to run the following commands:

1. ``` ./gradlew build ``` this command can run the entire build process, if you need any other command please check Maven Documentation.

### Running Application


- Run via Command line:
    - ``` ./gradlew  bootRun ```


## Suggestions
- [Working with multiple versions of java](https://docs.azul.com/core/zulu-openjdk/manage-multiple-zulu-versions/linux)


