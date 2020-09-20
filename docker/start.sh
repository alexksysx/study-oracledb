#!/usr/bin/env sh
docker run -d --env-file ./envfile -p 1527:1521 -p 5500:5500 -it --name oracleDB --shm-size="4g" container-registry.oracle.com/database/standard