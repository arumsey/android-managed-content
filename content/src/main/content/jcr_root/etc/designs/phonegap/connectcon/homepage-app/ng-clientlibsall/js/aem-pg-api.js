;(function (angular, undefined) {

    "use strict";

    angular.module('aem-pg-api', ['irisnet.phonegap'])

        .constant('navigationConstants', {
            actions: {
                INTERNAL_NAV: "INTERNAL_NAV",
                EXTERNAL_LINK: "EXTERNAL_LINK"
            }
        })
        .factory('aemPlugin', ['$window', 'deviceready', function($window, deviceready) {

            return {

                navigate: function(action, value, success, failure) {
                    deviceready().then(function() {
                        cordova.exec(success, failure, 'Navigation', 'navigate', [action, value]);
                    } );
                }

            };
        }])


}(angular));
