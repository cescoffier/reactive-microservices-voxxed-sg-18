#!/usr/bin/env bash

IP=$(minishift ip)
PROJECT=voxxed-singapore
http POST http://shopping-backend-${PROJECT}.${IP}.nip.io/shopping name=coffee quantity:=12
http POST http://shopping-backend-${PROJECT}.${IP}.nip.io/shopping name=eggs quantity:=12
http POST http://shopping-backend-${PROJECT}.${IP}.nip.io/shopping name=bacon


