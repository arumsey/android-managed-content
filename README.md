Conference Schedule App
===========

A specific example of <https://github.com/Adobe-Marketing-Cloud/app-sample-android-phonegap> used for conferences I have presented at.

* [Connect Web Experience](http://www.connectcon.ch/2014/en.html) : [(slides)](http://me.planetrumsey.ca/assets/aem-apps-in-native)
* [AEM Gem](http://dev.day.com/content/ddc/en/gems/delivering-managed-content-to-your-native-apps-.html) : [(slides)](http://dev.day.com/content/ddc/en/gems/delivering-managed-content-to-your-native-apps-/_jcr_content/par/download/file.res/9%2010%202014%20Delivering%20Managed%20Content%20to%20your%20Native%20Apps.pdf),[(recording)](http://www.adobe.com/cfusion/event/index.cfm?event=register_no_session&id=2537553&loc=en_us)

##### You can also follow along with all the steps over at the [demo branch](https://github.com/arumsey/android-managed-content/tree/step-0).

##Background

A native Android example running a Cordova (ie. PhoneGap) WebView home page connected to AEM6.

This example demonstrates how a Cordova WebView can be added to an existing Android application that uses fragments.  Once the Cordova WebView
has been added and configured it will then be possible to supply any web content to this view for displaying within your Android app.  Furthermore,
it is also possible to include Cordova plugins with your Android app that the web content can take advantage of in order to access device features.

In this example AEM App web content is added to the Android app assets folder and delivered to the Cordova WebView.  The web content from AEM takes
advantage of the Cordova plugins for accessing the device filesystem, unzipping packages and geolocation.

##Installation

This example contains two projects that need to be built and deployed. First there is the Android app itself.  The app
consists of one activity that uses a FragmentPager for moving between views.  The Home Page view fragment includes a CordovaWebView and
the remaining views are native Android. Second, there is an AEM compatible package that contains all the required content and components for
rendering the web content that will be displayed by the app. Installing this package will allow you to manage all your web content within AEM
and have it delivered to your Android app via content sync.

Experience Manager (AEM 6.0)
----------------------------

* First a package that contains the content to be managed by AEM needs to be installed.

```
cd content
mvn package content-package:install
```

* Then download the PhoneGap compatible content sync ZIP of the content.

```
npm run fetch
```

* Merge ZIP contents with your Android project

```
npm run merge
```

Android
-------

###Setup

See: <https://github.com/Adobe-Marketing-Cloud/app-sample-android-phonegap>

###Build

* Install additional libs to local maven repository

```
cd platforms/android/conference-app-aem/libs
mvn install:install-file -Dfile=cordova-3.6,4.jar -DgroupId=org.apache.cordova -DartifactId=cordova -Dversion=3.6.4 -Dpackaging=jar
mvn install:install-file -Dfile=adobeMobileLibrary-4.4.0.jar -DgroupId=com.adobe.mobile -DartifactId=mobile-services -Dversion=4.4.0 -Dpackaging=jar
```

* Build Android APK

```
        cd platforms/android/conference-app-aem
        mvn clean install
```

* Deploy and run app

```
        mvn android:deploy android:run
```

##Tutorials

The following tutorials provide more details on how components of this example were created.

1. [Embedding a Cordova WebView in an Android Fragment](https://github.com/Adobe-Marketing-Cloud/app-sample-android-phonegap/wiki/Embed-Webview-in-Android-Fragment)
