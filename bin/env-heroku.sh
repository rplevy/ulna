#!/bin/sh

. $(dirname $0)"/env.sh"

heroku config:add \
    # LOG_LEVEL=DEBUG \
    appsecret="$appsecret" \
    apikey="$apikey" \
    title="$title" \
    baseuri="$baseuri" \
    testuser="$testuser"
