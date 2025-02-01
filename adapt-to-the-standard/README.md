# OpenAPI generator

Taken from [this documentation](https://openapi-generator.tech/docs/installation/), below commands show `npm` usage to generate `Spring` based code.

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

Follow command generates Spring based code (on MS Windows).

```sh
npx @openapitools/openapi-generator-cli generate -i .\account-info-openapi.yaml -g spring -o .\gencode
```