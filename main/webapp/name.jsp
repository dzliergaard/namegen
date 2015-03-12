<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="NameGen">
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="js/resources/name-calls.js"></script>
<script type="text/javascript" src="js/services/name-store.js"></script>
<script type="text/javascript" src="js/controllers/name.js"></script>
<script type="text/javascript" src="js/directives/enter.js"></script>
<script type="text/javascript" src="js/directives/input-span.js"></script>
<script type="text/javascript" src="js/directives/name-line.js"></script>
<script type="text/javascript" src="js/directives/name-list.js"></script>
<title>DZL Name Gen</title>
<script type="text/javascript">
    var nameGen = angular.module("NameGen", ['dzl.controllers', 'dzl.directives']);

    // initialize saved name list
    nameGen.value("savedNames", angular.fromJson('${names}' || '{}'));
    nameGen.value("initTrainingName", angular.fromJson('${trainingName}'));
    nameGen.value("nameAttributes", angular.fromJson('${trainingAttributes}'));

    nameGen.value("userAuth", <%= UserServiceFactory.getUserService().getCurrentUser() != null %>);

    nameGen.config(function ($httpProvider) {
        $httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
        $httpProvider.defaults.headers.common['Accept'] = 'text/html,application/xhtml+xml,application/json';
    });
</script>

<body>
<div class='content row' ng-controller="NameCtrl">
    <div class='alert alert-error' ng-show='state.error'><span>{{state.error}}</span></div>
    <form class="form-inline col-mid-12">
        <fieldset>
            <div class="row">
                <div class="col-xs-6">
                    <legend>
                        Name Generator <strong ng-mouseover="showfooter = true"
                                               ng-mouseleave="showfooter = false">*</strong>
                    </legend>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-5">
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="label-input form-group">
                                <label for="how-many" class="col-xs-6">How Many?</label>
                                <input id="how-many" type='text' class='col-xs-4 input-lg'
                                       enter='generate(numNames)' ng-model="numNames" placeholder='10'/>
                            </div>
                        </div>
                        <div class="col-xs-3 col-xs-offset-1">
                            <button class="btn generate-button" ng-click='generate(numNames)'
                                    ng-disabled='state.generating'>
                                <span>Generate</span>
                            </button>
                        </div>
                    </div>
                </div>
                <img class="loading" ng-if="state.generating" src="resources/loading.gif" height="30px" width="30px">
            </div>
        </fieldset>
    </form>
    <div class="row">
        <div class="col-xs-5">
            <div name-list class="alert generated" list="names.generated"
                       ng-show="names.generated.length > 0" btn-action="saveName" btn-text="Save"></div>
        </div>
        <div class="col-xs-5 col-xs-offset-{{names.generated.length ? 1 : 6}}">
            <name-list class="alert stored" list="names.stored"
                       ng-if="names.stored.length > 0" btn-action="remove" btn-text="Remove"
                       edit-action="saveName"></name-list>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-11">
            <div class="alert learning">
                <h3 class="col-xs-11">Help train the name generator by choosing an option below.</h3>
                <div class="col-xs-11">
                    <span class="col-xs-6">The name <strong>{{names.training.name}}</strong> sounds {{names.training.attribute}}</span>
                    <button class="col-xs-1" ng-repeat="attribute in nameAttributes" ng-click="train(names.training.attribute = attribute)">{{attribute}}</button>
                </div>
            </div>
        </div>
    </div>
    <span class="footer note1" ng-show="showfooter">*results may vary</span>
</div>
</body>

</html>