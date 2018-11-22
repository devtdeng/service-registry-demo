#!/bin/bash

cf cs p-service-registry standard service-registry
cf cs -c '{"git":{"uri":"https://github.com/devtdeng/config-server-configurations"},"count":1}' p-config-server standard config-server
cf cs  p-circuit-breaker-dashboard standard cbd

sleep 60

pushd consumer-demo
    mvn package -Dmaven.test.skip
    cf push
popd

pushd producer-demo
    mvn package -Dmaven.test.skip
    cf push
popd