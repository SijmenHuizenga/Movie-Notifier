# Movie Notifier
[![GitHub tag](https://img.shields.io/github/release/sijmenhuizenga/Movie-Notifier.svg)](https://github.com/SijmenHuizenga/Movie-Notifier/releases)
[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/SijmenHuizenga/Movie-Notifier/blob/develop/license.txt)
[![Travis](https://travis-ci.org/SijmenHuizenga/Movie-Notifier.svg?branch=develop)](https://travis-ci.org/SijmenHuizenga/Movie-Notifier)
[![Docker](https://img.shields.io/badge/docker%20image-available-brightgreen.svg)](https://hub.docker.com/r/sijmenhuizenga/movienotifier/)
[![Codebeat](https://codebeat.co/badges/02e75a98-a0e0-4afe-aada-30b8a28beb12)](https://codebeat.co/projects/github-com-sijmenhuizenga-movie-notifier-develop)
[![SonarCloud Lines of Code](https://sonarcloud.io/api/badges/measure?key=it.sijmen:movie-notifier:develop&metric=ncloc)](https://sonarcloud.io/dashboard?id=it.sijmen%3Amovie-notifier%3Adevelop)
[![SonarCloud Coverage](https://sonarcloud.io/api/badges/measure?key=it.sijmen:movie-notifier:develop&metric=coverage)](https://sonarcloud.io/dashboard?id=it.sijmen%3Amovie-notifier%3Adevelop)
[![SonarCloud Code Smells](https://sonarcloud.io/api/badges/measure?key=it.sijmen:movie-notifier:develop&metric=code_smells)](https://sonarcloud.io/dashboard?id=it.sijmen%3Amovie-notifier%3Adevelop)


Movie Notifier monitors local cinema's and notifies users when new shows are available. Users can register and configure their notification settings. Registered users can create watchers with a number of filters to be notified of new showings in their aria. Using this application gives users the opportunity to always have the best seats for every show!

This repository contains the backend system of the Movie Notifier system. This backend is accessible via a HTTP API. The full specification of this API can be found in the `swagger.yaml` file in the root of this repository.
A frontend android app implementation can be found in [this repository](https://github.com/jpelgrom/Movie-Notifier-Android)

# Deployment

Whatever way of deployment is chosen, a `movie-notifier.properties` file is required. This file contains all settings that the movie-notifier application requires. This file has the following structure:
```properties
# Connection string for the mongo database as described here:
# https://docs.mongodb.com/manual/reference/connection-string/
# This property is required.
spring.data.mongodb.uri=

# Settings for AWS SNS api to notify users through SMS.
notification.awssns.key=
notification.awssns.secret=

# Settings for facebook messenger notifications using the Facebook api.
notification.facebook.token=

# Settings for mail notifications using the Rollbar mailing api.
notification.mailgun.domnain=
notification.mailgun.apikey=
notification.mailgun.from.mail=
notification.mailgun.from.name=

# The api key of the Pathe Cinema Api
cinema.pathe.apikey=

# The token for rollbar to enable external logging.
# If the apikey has the value 'disable' than rollbar logging is disabled.
notification.rollbar.apikey=disable
# The rollbar environment. Is ignored if rollbar logging is disabled.
notification.rollbar.environment=disable
```
Copy the above example and fill in the required properties. Movie-notifier uses Spring Boot which means that that there are many more options that can be configured in this file. These properties are described [here](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

## Manual run jar. 
To mannually run this application download the latest runnable .jar file from [github releases](https://github.com/SijmenHuizenga/Movie-Notifier/releases). The only requirement to run the movie-notifier is the java runtime environment 8. Run the following command to start the app:

``java -jar movie-notifier.jar --spring.config.location=movie-notifier.properties``

The option `spring.config.location` specifies the location of the `movie-notifier.properties` file.


## Docker Compose
The easyest way to run this app is using Docker compose. The below `docker-compose.yml` file configures the movie-notifier app and a mongodb to run together. 

```yaml
version: '3'
services:
  movie-notifier-db:
    image: mongo
    volumes:
      - [replace with path to persistant db store location]:/data/db
    restart: on-failure
  movie-notifier:
    image: sijmenhuizenga/movienotifier:3.0-SNAPSHOT
    restart: on-failure
    volumes:
      - [replace with path to movie-notifier.properties]:/movie-notifier.properties
    depends_on:
      - movie-notifier-db
    ports:
      - "80:80"
```

Note: when using this configuration the `spring.data.mongodb.uri` in the `movie-notifier.properties` file should have the value `mongodb://movie-notifier-db/movie-notifier`

## Docker Compose + SSL
To run this application with an ssl certificate the `docker-compose.yml` in the above example should be extended. The below configuration creates a nginx reverse proxy as well as a letsencrypt generation companion. 
```yaml
version: '2'
services:
  nginx-proxy:
    image: jwilder/nginx-proxy
    labels:
        com.github.jrcs.letsencrypt_nginx_proxy_companion.nginx_proxy: "true"
    container_name: nginx
    restart: on-failure
    ports:
      - "443:443"
      - "80:80"
    volumes:
      - /etc/nginx/certs:ro
      - /etc/nginx/vhost.d
      - /usr/share/nginx/html
      - /var/run/docker.sock:/tmp/docker.sock:ro
  nginx-letsencrypt:
    image: jrcs/letsencrypt-nginx-proxy-companion
    container_name: nginx-letsencrypt
    restart: on-failure
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock:ro
    volumes_from:
      - nginx-proxy
  movie-notifier-db:
    image: mongo
    volumes:
      - [replace with path to persistant db store location]:/data/db
    restart: on-failure
  movie-notifier:
    image: sijmenhuizenga/movienotifier:3.0-SNAPSHOT
    restart: on-failure
    volumes:
      - [replace with path to movie-notifier.properties]:/movie-notifier.properties
    environment:
      - VIRTUAL_HOST=[replace with external hostname]
      - LETSENCRYPT_HOST=[replace with external hostname]
      - LETSENCRYPT_EMAIL=[replace with email of administrator]
```