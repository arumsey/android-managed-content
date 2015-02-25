#!/usr/bin/env node

var sjs = require('shelljs')
  , cp = sjs.cp
  , rm = sjs.rm;
var platformPath = "platforms/android",
    deployPath = platformPath+"/conference-app-aem";

//reset platform
rm('-rf', deployPath+'/assets/www');
// copy assets to android app
cp('-Rf', 'tmp/'+platformPath+'/assets', deployPath);
//cp('-Rf', 'tmp/'+platformPath+'/res/xml', deployPath+'/res');
cp('-Rf', 'tmp/'+platformPath+'/src/com', deployPath+'/src/main/java');
cp('-Rf', 'tmp/'+platformPath+'/src/org', deployPath+'/src/main/java');
cp('-Rf', 'tmp/'+platformPath+'/libs/*.jar', deployPath+'/libs');
rm('-rf', deployPath+'/src/main/java/com/adobe/myconference/app/aem');
