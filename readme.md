# Movie Notifier

Movie Notifier monitors local cinema's and notifies users when new shows are available. Users can register and configure their notification settings. Registered users can create watchers with a number of filters to be notified of new showings in their aria. Using this application gives users the opportunity to always have the best seats for every show!

This repository contains the backend system of the Movie Notifier system. This backend is accessible via a HTTP API. The full specification of this API can be found in the `swagger.yaml` file in the root of this repository.

## Architectural Overview
The architecture of this system is shown below:
![Architecture Diagram](https://www.lucidchart.com/publicSegments/view/d70e9819-bff2-4185-be26-d8353d0c80f6/image.png)

This repository contains the backend. The backend is used by the Android app using a HTTP API. Later a Web App can be added using the same HTTP API the Android app uses. The backend stores all data in a [mongo](https://www.mongodb.com) database. The backend talks to API's of local cinema's to retrieve live movie showings information.

## Configuration 
spring.data.mongodb.uri=mongodb://user:secret@mongo1.example.com:12345,mongo2.example.com:23456/test
