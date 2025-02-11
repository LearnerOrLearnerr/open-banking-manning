# Running Keycloak as a conatainer

Keycloak admin screens undergo modifications from one release to another. The following notes are written for the docker image `quay.io/keycloak/keycloak:26.1.2`.

See docker-compose.yaml for specific detail. It can be run from console as follows:

```sh
docker-compose up -d
```

# Defining a realm

Based on how ports are defined in the docker compose, the web console should be accessible as http://localhost:9000.

Login with the credentials (again, as specified in the YAML file).

Define the new realm, which will be used in the rest of this README: `open-banking-realm`. Swith to this realm.

## Add client

Clients can access authentication of a user. Add the client `open-banking`. Important fields are

* Client ID: Use `open-banking`
* Root URL: `http://localhost:9001`
* As part "Capability config", enable
  * Client Authentication
  * Authorization
  * Ensure "Direct Access Grant" is also enabled

In Credentials tab, note down the *Client Secret*.

## Define usres

Keycloak newer versions require not only the username but also first name and last name. If these are not provided, later HTTP POST calls for user authentication will fail with the following error: `Account is not fully set up`

Follow these steps for creating users:

* Provide username, email, first name and last name
* Don't select any *Required user actions*
* Under *Credentials* tab add the password for this user

## Change realm settinsg

Realm settinsg are available under *Configure* option. Enable the following:

* User registration
* Forgot password
* Remember me
* Login with email

# Test with curl command

The following example is using MS Windows new line continuation character `^`:

```sh
curl -X POST ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "client_id=open-banking" ^
  -d "client_secret=BxfDdnv47dGz3p9kzRenMx5NmqcYkfNi" ^
  -d "username=ali" ^
  -d "password=baba" ^
  -d "grant_type=password" ^
  "http://localhost:9001/realms/open-banking-realm/protocol/openid-connect/token"
```

Note the following:

* `Content-Type` is header
* Remaining arguments with `-d` are part of the request body

A JSON reponse with tokens such as `access_token` and `refresh_token` should be returned.

