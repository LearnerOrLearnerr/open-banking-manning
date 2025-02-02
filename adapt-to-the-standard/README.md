# OpenAPI generator

Based on [this documentation](https://openapi-generator.tech/docs/installation/), below commands show `npm` usage to generate `Spring` based code.

## npm upgrade

First things first.

```sh
npm install -g npm@latest
```

## Install openapi-generator-cli

```sh
npm install @openapitools/openapi-generator-cli -g
```

## Run the CLI tool

Following command generates Spring based code (on MS Windows).

```sh
npx @openapitools/openapi-generator-cli generate -i .\account-info-openapi.yaml -g spring -o .\gencode
```

# Using OpenAPI Generator with Spring and Gradle

Details are available in [this article](https://medium.com/@barendnieuwoudt/generating-api-code-with-java-springboot-and-the-openapi-generator-f2e232ea0612). The following is using latest versions of the plugins/ dependencies.

## Basic setup of OpenAPI Generator

Plugin needs to be added in `build.gradle`:

```kotlin
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.2'
    id 'io.spring.dependency-management' version '1.1.7'
    id 'org.openapi.generator' version '7.11.0'
}
```

A task needs to be added. The following code picks up OpenAPI yaml file from the `src/main/resources/yaml` folder:

```kotlin
openApiGenerate {
    inputSpec.set ('$rootDir/src/main/resources/yaml/account-info-openapi.yaml')
    generatorName = 'spring'
}
```

Gradle taks `openApiGenerate` can be used to build code, which will be  stored in `build/generated-sources` folder.

```sh
gradlew openApiGenerate
```