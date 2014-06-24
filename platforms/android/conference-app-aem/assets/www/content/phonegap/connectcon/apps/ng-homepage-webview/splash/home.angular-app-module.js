;(function (angular, undefined) {

    "use strict";

    // Cache killer is used to ensure we get the very latest content after an app update
    var cacheKiller = '?ck=' + (new Date().getTime());

    /**
     * Main app module
     */
    angular.module('ConnectConApp', [
'ngRoute',
'ngTouch',
'ngAnimate',
'ngSanitize',
'cqContentSyncUpdate',
'cqAppControllers',
'cqAppNavigation',
'phonegapLocation',
'phonegapBanner'
])

        .controller('AppController', ['$scope', '$rootScope', 'cqContentSyncUpdate',
            function ($scope, $rootScope, cqContentSyncUpdate) {
                
                    
                    $scope.wcmMode = false;
                

                // URI for requesting OTA updates
                var contentSyncUpdateUri = 'http\x3A\x2F\x2Flocalhost\x3A4503\x2Fcontent\x2Fphonegap\x2Fconnectcon\x2Fcontent\x2Fng\x2Dhomepage\x2Dwebview\x2Fhomepage\x2Dapp\x2Dcli.zip';

                // Initialize the app updater
                cqContentSyncUpdate.setContentSyncUpdateConfiguration(contentSyncUpdateUri);
            }
        ])
        .config(['$routeProvider',
            function($routeProvider) {
                $routeProvider
                .when('/content/phonegap/connectcon/apps/ng-homepage-webview/splash/home', {
                    templateUrl: 'home.template.html' + cacheKiller,
                    controller: 'contentphonegapconnectconappsnghomepagewebviewsplashhome'
                })

                    .otherwise({
                        redirectTo: '/content/phonegap/connectcon/apps/ng-homepage-webview/splash/home'
                    });
            }
        ]);

}(angular));
