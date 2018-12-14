#!/bin/bash

set -x 

echo -n "Creating services..."
{
  cf create-service p-service-registry standard service-registry
  cf cs -c '{"git":{"uri":"https://github.com/devtdeng/config-server-configurations"},"count":1}' p-config-server standard config-server
  cf create-service p-circuit-breaker-dashboard standard cbd
} &> /dev/null

until [ `cf service cbd | grep -c "succeeded"` -eq 1  ]
do
  echo -n "."
done
until [ `cf service service-registry | grep -c "succeeded"` -eq 1  ]
do
  echo -n "."
done
until [ `cf service config-server | grep -c "succeeded"` -eq 1  ]
do
  echo -n "."
done


echo "Services created. Building and pushing applications."

pushd producer-demo
    mvn package -Dmaven.test.skip
    cf push
popd

pushd consumer-demo
    mvn package -Dmaven.test.skip
    cf push
popd

echo "" && echo "Done!" && echo ""
