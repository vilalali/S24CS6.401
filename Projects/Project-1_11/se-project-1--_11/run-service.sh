#!/bin/bash
docker rm -f sismics_books
docker run \
    -d --name=sismics_books --restart=always \
    -v sismics_books_data:/data \
    -e 'VIRTUAL_HOST_SECURE=books.sismics.com' -e 'VIRTUAL_PORT=80' \
    sismics/books:latest
