<%@include file="/libs/foundation/global.jsp" %><%
%><%@ page session="false" %><%
%>
<div class="location-details" ng-controller="LocationCtrl">
    <div class="location-container">
        <h3 ng-hide>Your current location is: {{origin.label}}</h3>
        <p ng-hide>You are <b>{{distance|number:2}} km</b> away from the venue.</p>
        <div class="location-map">
            <cq-map zoom="12" maptype="roadmap" center="origin" markers="locations" refresh="showMap">
                Loading map...
            </cq-map>
        </div>
    </div>
</div>
