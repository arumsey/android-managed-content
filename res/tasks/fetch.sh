#!/bin/sh
rm -rf tmp
mkdir -p tmp
curl -u admin:admin \
-L http://localhost:4502/content/phonegap/myconference/shell/jcr:content/pge-app/ConferenceWebviewDev.zip > tmp/app.zip
unzip tmp/app.zip -d tmp
rm tmp/app.zip
#mv tmp/phonegap* tmp/app-events
sleep 5
