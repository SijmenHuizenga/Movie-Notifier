# Movie Notifier
[![License](https://img.shields.io/github/license/sijmenhuizenga/movie-notifier.svg)](https://github.com/SijmenHuizenga/Movie-Notifier/blob/develop/license.txt)
[![Docker](https://img.shields.io/badge/docker%20image-available-brightgreen.svg)](https://hub.docker.com/r/sijmenhuizenga/movienotifier/)

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

# Settings for mail notifications using the Rollbar mailing api.
mailgun.domnain=
mailgun.apikey=
mailgun.from.mail=
mailgun.from.name=

# The api key of the Pathe Cinema Api
cinema.pathe.apikey=

# The path to a google cloud api json keyfile that gives access to firebase cloud messaging sending services.
fcm.serviceaccountkeyfile=
```
Copy the above example and fill in the required properties. Movie-notifier uses Spring Boot which means that that there are many more options that can be configured in this file. These properties are described [here](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

