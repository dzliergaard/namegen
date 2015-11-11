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
    nameGen.value("savedNames", angular.fromJson('${names}' || '[]'));
    nameGen.value("trainingName", angular.fromJson('${trainingName}'));
    nameGen.value("nameAttributes", '${nameAttributes}'.match(/(\w*)/));

    nameGen.value("userAuth", <%= UserServiceFactory.getUserService().getCurrentUser() != null %>);

    nameGen.config(function ($httpProvider) {
        $httpProvider.defaults.headers.common['Accept'] = '*/*';
    });
</script>

<body>
    <div class='content row' ng-controller="NameCtrl">
        <div class='alert alert-error row' ng-show='state.error'><span class="col-xs-12">{{state.error}}</span></div>
        <form class="form-inline col-mid-12">
            <fieldset>
                <div class="row">
                    <div class="col-xs-6">
                        <legend>
                            Name Generator <strong ng-mouseover="showfooter = true" ng-mouseleave="showfooter = false">*</strong>
                        </legend>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-5">
                        <div class="row">
                            <div class="col-xs-8">
                                    <div class="label-input form-group">
                                        <label for="how-many" class="col-xs-7">How Many?</label>
                                        <input id="how-many" type='text' class='col-xs-4 input-lg'
                                               enter='generate(numNames)' ng-model="numNames" placeholder='10'/>
                                    </div>
                                </div>
                            <div class="col-xs-3 col-xs-offset-1">
                                <button class="btn generate-button" ng-click='generate(numNames)' ng-disabled='state.generating'>
                                    <span>Generate</span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <img class="loading" ng-if="state.generating" src="resources/static/loading.gif" height="30px" width="30px">
                </div>
            </fieldset>
        </form>
        <div class="row">
            <div class="col-xs-5">
                <name-list class="alert generated" list="names.generated"
                     ng-show="names.generated.length > 0" btn-action="saveName" btn-text="Save"></name-list>
            </div>
            <div class="col-xs-5 col-xs-offset-{{names.generated.length ? 1 : 6}}">
                <name-list class="alert stored" list="names.stored"
                     ng-if="names.stored.length > 0" btn-action="remove" btn-text="Remove" edit-action="saveName"></name-list>
            </div>
        </div>
        <div class="row">
            <div class="alert learning col-xs-12">
                <div class="col-xs-12">
                    <span>Want to help train the name generator? Click the attribute that best describes the training name:</span>
                </div>
                <span class="col-xs-5">{{trainingName.text}}</span>
                <div class="col-xs-7 row">
                    <button class="col-xs-1 btn" ng-repeat="attribute in nameAttributes" ng-click="trainName(attribute)">
                        {{attribute}}
                    </button>
                </div>
            </div>
        </div>
        <span class="footer note1" ng-show="showfooter">*results may vary</span>
    </div>
</body>

</html>