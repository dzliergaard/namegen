angular.module('dzl.services')
    .service('NameStore', function NameStore(NameCalls, savedNames) {
    var state = {};
    var names = {
        generated: [],
        stored: savedNames
    };

    function getNames() {
        NameCalls.get({},
            function (response) {
                for (var i = 0; i < response.data.length; i++) {
                    response.data[i] = JSON.parse(response.data[i]);
                }
                names.stored.replace(angular.fromJson(response.data));
            }
        );
    }
    function save(newName) {
        var i = names.stored.indexOf(newName);
        NameCalls.save(
            newName,
            function (response) {
                if (i > -1) {
                    names[i] = angular.fromJson(response.data);
                } else {
                    names.stored.push(angular.fromJson(response.data));
                }
                names.stored.sort(function (a, b) {
                    return a.text > b.text
                });
            },
            function () {
                state.error = "Error attempting to save name " + newName.text + "!!";
            }
        );
    }
    function generate(num) {
        state.generating = true;
        NameCalls.generate(
            {numNames: num},
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    response[i] = angular.fromJson(response[i]);
                }
                names.generated.replace(response);
                state.generating = false;
            },
            function (response) {
                state.error = "Error attempting to generate names: " + response.status;
                state.generating = false;
            }
        );
    }
    function remove(name) {
        NameCalls.remove(
            {},
            name,
            function (response) {
                names.stored.remove(name);
            },
            function (response) {
                state.error = "Error attempting to delete name " + name.text + "!!";
            }
        );
    }
    return {
        names:    names,
        state:    state,
        save:     save,
        generate: generate,
        remove:   remove
    }
});