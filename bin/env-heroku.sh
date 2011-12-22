#!/bin/sh

. $(dirname $0)"/env.sh"

heroku config:add \
    appsecret="$appsecret" \
    apikey="$apikey" \
    title="$title" \
    baseuri="$baseuri" 
