# MongoDB docker setup on Windows

This guide will walk you through setting up a local MongoDB instance using Docker on Windows.

- Ensure **Docker Desktop** is installed and running on your Windows machine. You can download it from the [official Docker website](https://www.docker.com/products/docker-desktop).

## Pull the `mongo` docker image

```sh
docker pull mongo
```

If you get an error, ensure that your Docker Desktop is running.


## Run the container

Run the following command to start a MongoDB container:
```sh
docker run --name mongodb -d -p 27017:27017 mongo
```

where
 - `--name mongodb`: Names the container `mongodb`.
 - `-d`: Runs the container in detached mode.
 - `-p 27017:27017`: Maps port 27017 on your host to port 27017 in the container

Check if the MongoDB container is running with:

```sh
docker ps
```

## Stopping and starting the MongoDB container

```sh
docker stop mongodb
```

# Install mongosh

A slightly modified stackoverflow [solution](https://stackoverflow.com/questions/74883695/how-to-install-mongoshell-of-mongodb-4-4-0-image-in-docker-container) is given below:

```bash
apt-get update

apt-get install wget, gnupg

wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -

echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list

apt-get install -y mongodb-mongosh
```


# Next steps

Use the following `docker-compose.yml`:

```yml
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    container_name: mongodb
    restart: always
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: adminpassword
      MONGO_INITDB_DATABASE: admin
    ports:
      - 27017:27017
    volumes:
      - mongodb_data:/data/db
volumes:
  mongodb_data:
```

Note that a volume is specified here; hence, data will remain available even if the container is restarted. The volume can be deleted using Docker Desktop.

## Running the docker-compose command

The folder where above mentioned `docker-compose.yml` is kept, run the following command:

```sh
docker-compose up -d
```

This will start a new container with the name `mongodb` based on the image `mongo:latest`.

### Getting terminal connected

The following command can be used to open up the bash terminal in the running container.

```sh
docker exec -it mongodb bash
```

### Creating a mongosh admin session

Once bash session is established, the following command will connect to the database (using the credentials from `docker-compose.yml`):

```sh
mongosh --username admin --password adminpassword
```

### Create a new database and collection

Following example creates a database, and adds a collection.

```js
use betterbanking
db.createCollection ("transactions")
```

### Testing the collection

Following is just to validate or troubleshoot collections:

```js
db.getCollection("transactions").insertOne ({amount: 35.5, currency: "GBP", accountNumber: 123});
db.getCollection("transactions").insertOne ({amount: 230.0, currency: "GBP", accountNumber: 123});
db.getCollection("transactions").find( { accountNumber: 123 });
```

### Optional: Creating users

By default, the user creation would be done in the `admin` database. However, it's also possible to create them in another database as shown below.

```js
db.getSiblingDB("better-banking").createUser({
    user: "admin",
    pwd: "adminpassword",
    roles: [{ role: "root", db: "admin" }]
});
```