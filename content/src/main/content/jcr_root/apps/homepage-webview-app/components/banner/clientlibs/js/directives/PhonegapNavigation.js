
;(function (angular,  undefined) {

    "use strict";

    angular.module('phonegapBanner')

        .directive('pgNavigation', ['aemPlugin', 'navigationConstants',

            function (aem, navConstants) {

                return {
                    compile: function($element, attr) {
                        var action = attr["pgNavigation"],
                            value = attr["pgNavigationValue"];
                        return function(scope, element, attr) {
                            element.on('click', function(event) {
                                scope.$apply(function() {
                                    aem.navigate(navConstants.actions[action], value,
                                        function(result) {
                                            console.log("navigation succeeded");
                                            console.log(result);
                                        },
                                        function(error) {
                                            console.log(error);
                                        }
                                    );
                                });
                            });
                        };
                    }
                };

            }]);

})(angular);