
;(function (angular, contentUpdate, undefined) {

    "use strict";

    /**
     * Module to handle general navigation in the app
     */
    angular.module('cqAppNavigation', ['btford.phonegap.ready'])

        .controller('AppNavigationController', ['$scope', '$window', '$location', '$timeout',

            function ($scope, $window, $location, $timeout) {

                $scope.transition = '';
                $scope.updating = false;
                var contentUpdater = contentUpdate();

                // Start a timeout
                var timer = $timeout(function() {
                    $scope.initApp();
                }, 100);

                /**
                 * Trigger an app update
                 */
                $scope.updateApp = function( ) {
                    // don't start updating again if we're already updating.
                    if($scope.updating) return;

                    // Prevent this event from propagating
                    $scope.updating = true;

                    if( window.ADB ) {
                        ADB.trackAction( 'updateApp', {} );
                        ADB.trackTimedActionStart( 'updateAppTimed', {} );
                    }

                    // Check if an update is available
                    contentUpdater.isContentPackageUpdateAvailable($scope.contentPackageName,
                        function callback(error, isUpdateAvailable) {
                            if (error) {
                                if( window.ADB ) {
                                    ADB.trackAction( 'updateAppFailed', {} );
                                }
                                // Alert the error details.
                                console.log(error);
                                //return navigator.notification.alert(error, null, 'ContentSync Error');
                            }

                            if (isUpdateAvailable) {
                                $scope.updating = true;
                                contentUpdater.updateContentPackageByName($scope.contentPackageName,
                                    function callback(error, pathToContent) {
                                        if (error) {
                                            return navigator.notification.alert(error, null, 'Error');
                                        }
                                        // else
                                        console.log('Update complete; reloading app.');
                                        $scope.updating = false;
                                        if( window.ADB ) {
                                            ADB.trackTimedActionEnd( 'updateAppTimed' );
                                        }
                                    });
                            }
                        }
                    );

                };

                /**
                 * Initialize app on first load
                 */
                $scope.initApp = function() {
                    $timeout.cancel(timer);
                    $scope.updateApp();
                }

            }
        ]);

})(angular, CQ.mobile.contentUpdate);