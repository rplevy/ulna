#!/bin/bash

# copy this file and create an env.sh that sets env variables

export \
    appsecret=21321832183213821312321093809123 \
    apikey=21321321321311 \
    title="Radio Ulna"

heroku config:add \
    appsecret="$appsecret" \
    apikey="$apikey" \
    title="$title"


