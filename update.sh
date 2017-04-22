#!/usr/bin/env bash
docker pull sijmenhuizenga/movienotifier:notifier-service
docker pull sijmenhuizenga/movienotifier:configurator-service
docker-compose up -d