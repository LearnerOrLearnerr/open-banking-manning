# Running Acme Testnet as a conatainer

The docker image being used here is just a testnet for the *Exchange Data Security* course offered at Manning. URL for downloading the image is https://hub.docker.com/r/nathanbcrocker/acme-testnet.

The YAML has been picked up from [the github repo](https://github.com/nathanbcrocker/service-testnet/blob/main/docker-compose.yml) albeit some modifications were needed, such as changing the port mapping to `9080->8080`.

See docker-compose.yaml for specific detail. It can be run from console as follows:

```sh
docker-compose up -d
```

## Source code
Github project has the source code of [testnet service](https://github.com/nathanbcrocker/service-testnet/).

## Dependency on mysql
Testnet docker container requires a running [mysql db](https://hub.docker.com/r/mysql/mysql-server/).

The services defined in the `docker-compose.yaml` also includes mysql, alongwith root password.

`acme-banking-db` is started first, and `acme-banking` depends on the db to come in a healthy state.

# Testing the services
Following example uses port `9080` for the `acme-banking` container:

```sh
curl -X POST -H "Authorization: Basic YmV0dGVyLWJhbmtpbmc6N3lyYlI4WHBZNDViY0tQUA==" --data "grant_type=client_credentials" http://localhost:9080/oauth/token
```

If you have `jq` installed, output can be simplified/ formatted as follows:

```sh
curl -s -X POST -H "Authorization: Basic YmV0dGVyLWJhbmtpbmc6N3lyYlI4WHBZNDViY0tQUA==" --data "grant_type=client_credentials" http://localhost:9080/oauth/token| jq .access_token
```

Token can be used to get transaction data:

```sh
curl -s -X GET -H "Authorization: Bearer f62be0e3-6327-4da0-bc24-b93533fee513" http://localhost:9080/accounts/1234567/transactions -H "accept: application/json" | jq
```