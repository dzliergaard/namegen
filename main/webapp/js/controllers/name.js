var nameCtrl = angular.module('dzl.controllers');

nameCtrl.controller('NameCtrl', function ($scope, NameStore, nameAttributes) {
    angular.extend($scope, {
        nameAttributes: trainingAttributes,
        state: NameStore.state,
        names: NameStore.names,
        saveName: NameStore.save,
        generate: NameStore.generate,
        remove: NameStore.remove,
        train: NameStore.train
    });
});