Conference Schedule App
==================

A native Android example running a Cordova WebView home page connected to AEM6.

##Installation (Basic)

###Experience Manager

* First a package that contains the content to be managed by AEM needs to be installed.

        cd content
        mvn package content-package:install

* Then download the PhoneGap compatible content sync ZIP of the content.

        http://localhost:4502/content/phonegap/geometrixx/content/ng-geometrixx-webview/geometrixx-webview-cli.zip

* Unzip to your local file system and run phonegap build

        phonegap build android

###Android

####Setup Android development environment with maven

Before being able to build this example Android app ensure your development environment is set up correctly.

####AEM Integration


* Go to the Android platform of the phonegap project you just built

        cd platforms/android

* Copy the following files/directories to platforms/android/geometrixx-app
    * assets
    * res/xml
    * libs
    * src/com -> src/main/java/com
    * src/org -> src/main/java/org

* Install libs to local maven repository

        cd platforms/android/geometrixx-app/libs
        mvn install:install-file -Dfile=cordova-3.4.0.jar -DgroupId=org.apache.cordova -DartifactId=cordova -Dversion=3.4.0 -Dpackaging=jar
        mvn install:install-file -Dfile=adobeMobileLibrary.jar -DgroupId=com.adobe.mobile -DartifactId=mobile-services -Dversion=4.1.1 -Dpackaging=jar

* Build Android APK

        cd platforms/android/geometrixx-app
        mvn clean install

* Deploy and run app

        mvn android:deploy android:run


##Installation (Advanced)

###OTA Updates

In order to support OTA updates the content package needs to be replicated to a publish server.

* Change the server address used by the app
* Re-build package
* Replicate package
* Download content sync ZIP from publish server
        http://localhost:4503/content/phonegap/geometrixx/content/ng-geometrixx-webview/geometrixx-webview-cli.zip
