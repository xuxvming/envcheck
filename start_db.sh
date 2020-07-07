#!/usr/bin/env sh

docker build -t moneytransfer-postgres:1.0 .
docker run -p 5432:5432 --rm moneytransfer-postgres:1.0
