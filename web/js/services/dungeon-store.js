angular.module('dzl.services')
    .service('DungeonStore', function (DungeonCalls) {
    var dungeon = {};
    var state = {};

    function generate(maxRooms) {
        state.generating = true;
        DungeonCalls.generate({
                maxRooms: maxRooms
            },
            function (response) {
                angular.copy(response.data, dungeon);
                state.generating = false;
            },
            function (response) {
                state.error = "Error attempting to generate dungeon: " + response.status;
                state.generating = false;
            }
        );
    }

    return {
        generate: generate,
        dungeon: dungeon,
        state: state
    };
});