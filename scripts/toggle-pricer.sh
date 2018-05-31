#!/usr/bin/env bash
IP=$(minishift ip)
PROJECT=voxxed-singapore
http http://pricer-service-${PROJECT}.${IP}.nip.io/toggle
