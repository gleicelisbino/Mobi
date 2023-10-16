#!/bin/sh

./wait-for-it.sh db:5432 --strict --timeout=300

exec java -jar /app.jar
