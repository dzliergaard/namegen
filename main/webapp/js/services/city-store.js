var cityStore = angular.module('dzl.services');
cityStore.service('CityStore', function (CityCalls) {
    var city = {};
    var state = {};
    var cityForm = {};

    function generate(inputCityForm) {
        state.generating = true;
        cityForm = inputCityForm;
        CityCalls.generate({
                size: inputCityForm.size,
                race: inputCityForm.spec,
                diversity: inputCityForm.div
            },
            function (response) {
                angular.copy(response.data, city);
                state.generating = false;
            },
            function (response) {
                state.error = "Error attempting to generate city: " + response.status;
                state.generating = false;
            }
        );
    }

    function getNew(attr){
        state.generating = true;
        CityCalls.generate({
                size: cityForm.size,
                race: cityForm.spec,
                diversity: cityForm.div
            },
            function (response) {
                city[attr] = response.data[attr];
                state.generating = false;
            },
            function () {
                state.generating = false;
            }
        );
    }

    return {
        generate: generate,
        getNew: getNew,
        city: city,
        state: state
    }
});