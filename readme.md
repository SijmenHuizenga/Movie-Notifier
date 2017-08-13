# Movie Notifier

Movie Notifier monitors local cinema's and notifies users when new shows are available. Users can register and configure their notification settings. Registered users can create watchers with a number of filters to be notified of new showings in their aria. Using this application gives users the opportunity to always have the best seats for every show!

This repository contains the backend system of the Movie Notifier system. This backend is accessible via a HTTP API. The full specification of this API can be found in the `swagger.yaml` file in the root of this repository.
A frontend android app implementation can be found in [this repository](https://github.com/jpelgrom/Movie-Notifier-Android)

## Running this app with Docker
A mongodb container is required. The mongodb container can be started with the following command:
`` docker run --name movie-notifier-db
-p 27017:27017
-v /path/to/persistent/storage:/data/db
-e MONGO_INITDB_ROOT_USERNAME=[INSERT dB USER HERE]
-e MONGO_INITDB_ROOT_PASSWORD=[INSERT dB PASS HERE]
-d mongo `` 

The built docker image is available at [docker hub](https://hub.docker.com/r/sijmenhuizenga/movienotifier/). To run it you must first have a valid configuration file. An example properties configuration file can found in the file `movie-notifier.properties`. Copy this file to your machine and fill in all required properties. 

Than run the following comand to get the docker container up and running:

``docker run --name movie-notifier \
-p 80:80 \
-v movie-notifier.properties:movie-notifier.properties``