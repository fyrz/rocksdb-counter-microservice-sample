# RocksDB Counter Microservice example
[![Build Status](https://travis-ci.org/fyrz/rocksdb-counter-microservice-sample.svg)](https://travis-ci.org/fyrz/rocksdb-counter-microservice-sample) [![Coverage Status](https://coveralls.io/repos/fyrz/rocksdb-counter-microservice-sample/badge.svg?branch=master)](https://coveralls.io/r/fyrz/rocksdb-counter-microservice-sample?branch=master)

This sample project outlines the usage of RocksDB (using RocksJava) and a Microservice framework called Spark to provide Counter functionality via REST.

## Building and execution
```
# Start build
$> ./gradlew shadow

# Run application
$> java -jar build/libs/RocksDB-Microservice-Counter-1.0-all.jar  /tmp/dbLocation
```
The microservice is now available, listening to `localhost:4567`

## Rest interfaces
After starting the service, three REST interfaces are available:
```
#Increment counter by 1
$> curl http://localhost:4567/incrementCounter/someCounter

# Retrieve counter (value: 1)
$> curl http://localhost:4567/getCounter/someCounter

# Reset counter back to 0
$> curl http://localhost:4567/resetCounter/someCounter
```
