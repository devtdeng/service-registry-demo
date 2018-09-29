#!/bin/bash

cf cs p-service-registry standard service-registry

pushd consumer-demo
    mvn package -Dmaven.test.skip
    cf push
popd

pushd producer-demo
    mvn package -Dmaven.test.skip
    cf push
popd