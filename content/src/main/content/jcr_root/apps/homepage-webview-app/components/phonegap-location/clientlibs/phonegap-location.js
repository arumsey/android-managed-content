;(function (angular, document, undefined) {

    angular.module('phonegapLocation', ["cqServices", "cqMaps"])

        .factory('locationService', function () {

            /** Converts numeric degrees to radians */
            if (typeof Number.prototype.toRad == 'undefined') {
                Number.prototype.toRad = function() {
                    return this * Math.PI / 180;
                }
            }

            function calculateDistance(start, end) {
                var d = 0;
                if (start && end) {
                    var R = 6371;
                    var lat1 = start.lat.toRad(), lon1 = start.lng.toRad();
                    var lat2 = end.lat.toRad(), lon2 = end.lng.toRad();
                    var dLat = lat2 - lat1;
                    var dLon = lon2 - lon1;

                    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(lat1) * Math.cos(lat2) *
                            Math.sin(dLon/2) * Math.sin(dLon/2);
                    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    d = R * c;
                }
                return d;
            }

            return {

                getDistance: function(start, end) {
                    return calculateDistance(start, end);
                }
            };
        })

        .controller('LocationCtrl', ['$scope', '$timeout', 'cqDeviceUtils', 'locationService', function($scope, $timeout, cqDeviceUtils, locationService) {

            $scope.showMap = false;
            $scope.origin = null;
            $scope.locations = [];

            //conference location
            $scope.locations.push({
                "coordinates": {
                    "lat": 47.5542490,
                    "lng": 7.5893580
                }
            });

            //get current position
            cqDeviceUtils.getPosition(function(position) {
                $scope.origin = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                $scope.distance = locationService.getDistance($scope.origin, $scope.locations[0].coordinates);

                //reverse geocode current location
                var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[1]) {
                            $scope.origin.label = results[1].formatted_address;
                        } else {
                            $scope.origin.label = "No location found";
                        }
                        $scope.$apply();
                    } else {
                        $scope.error = 'Geocoder failed due to: ' + status;
                    }
                });
            }, function(error){
                if (error.POSITION_UNAVAILABLE == error.code || error.PERMISSION_DENIED == error.code) {
                    console.log("Please enable location services and try again.");
                } else {
                    console.log('Location error code: ' + error.code + '\n'+ 'message: ' + error.message);
                }
                $scope.error = "Location unavailable";
            });

        }])

}(angular, document));