/**
 * Created by DZL on 6/9/14.
 */
var DungeonCtrl = function ($scope, DungeonStore, locations) {
    $scope.generate = DungeonStore.generate;
    $scope.dungeon = DungeonStore.dungeon;

    $scope.locations = locations;
};
angular.module('dzl.directives')
    .directive('dungeon', function(){
        var dim = 1500;
        var blue = "#0000ff";
        var colors = {
            F: "#004400",
            M: "#552211",
            P: "#333000",
            S: "#000"
        };

        var cultureColors = [
            "#55AA00",
            "#AA00AA",
            "#5500AA",
            "#00AA00",
            "#0055AA",
            "#AA5500",
            "#AAAA00",
            "#AA0055",
            "#00AAAA",
            "#AA0000",
            "#00AA55",
            "#0000AA"
        ];
        return {
            restrict: "A",
            controller: DungeonCtrl,
            link: function(scope, elem, attrs){
                var unit = 10;
                var dirs = {
                    "Left": Math.PI / 2,
                    "Right": Math.PI * 3/2,
                    "Back": Math.PI,
                    "Forward": 0
                };

                function getNext(type){
                    if(scope.locations[type].length == 0){ return; }
                    return scope.locations[type].shift();
                }

                function getChamber(){ return getNext("Chamber"); }

                function getPassage(){ return getNext("Passage"); }

                function getDoor(){ return getNext("Door"); }

                function getPassageOrChamber(){
                    return Math.random() < .75 ? getPassage() : getChamber();
                }

                var room = scope.locations["Location"][0];

                //outline
                var paper = Raphael(200, 200, 1000, 1000);
                var rect = paper.rect(0, 0, 1000, 1000);
                rect.attr('stroke', 'black');

                //keep rooms in a set to apply stroke style all at once
                paper.setStart();
                var roomShape;
                var width = room.width * unit, length = room.length * unit;
                if(room.shape == "Circle"){
                    roomShape = paper.circle(500, 500, width);
                } else {
                    roomShape = paper.rect(500 - width / 2, 500 - length / 2, width, length);
                }

                function drawChildRooms(room) {
                    if(!room){ return; }
                    angular.forEach(room.bends, function(bend, conn){
                        context.save();
                        context.rotate(dirs[conn.dir]);
                        var dx = getExitDX(room.width, room.length, conn.dir);
                        context.translate(getExitDist(conn.dist, conn.dir), dx);

                        context.clearRect(-bend.width / 2, 0, bend.width, bend.length);
                        context.strokeRect(-bend.width / 2, 0, bend.width, bend.length);
                        rooms.push(bend);
                        context.restore();
                    });
                    angular.forEach(room.exits, function (exit) {
                        context.save();
                        context.rotate(dirs[exit.dir]);
                        var dx = getExitDX(room.width, room.length, exit.dir);
                        context.translate(getExitDist(exit.dist, exit.dir), dx);

                        // draw the next passage/room
                        // first, generate next passage/room
                        var loc = getPassageOrChamber();
                        if (!loc) {
                            context.restore();
                            return;
                        }
                        rooms.push(loc);
                        context.clearRect(-loc.width / 2, 0, loc.width, loc.length);
                        context.strokeRect(-loc.width / 2, 0, loc.width, loc.length);

                        // draw the door or opening
                        if (exit.loc == "Door") {
                            context.strokeStyle = "#ee9922";
                            context.strokeRect(0, 0, 1, 0);
                        } else if (exit.loc == "SecretDoor") {
                            context.strokeStyle = "#2299ee";
                            context.strokeRect(0, 0, 1, 0);
                        } else {
                            if (room.shape == "Circle") {
                                context.clearRect(-.5, -.2, 1, .4);
                            } else {
                                context.clearRect(-(Math.min(room.width, loc.width) / 2) + .1, -.5, Math.min(room.width, loc.width) - .2, .9);
                            }
                        }
                        context.restore();
                    });
                }

                var rooms = paper.setFinish();
                rooms.attr('stroke', 'black');

//                function drawChildRooms(room) {
//                    if(!room){ return; }
//                    drawBends(room);
//                    angular.forEach(room.exits, function (exit) {
//                        context.save();
//                        context.rotate(dirs[exit.dir]);
//                        var dx = getExitDX(room.width, room.length, exit.dir);
//                        context.translate(getExitDist(exit.dist, exit.dir), dx);
//
//                        // draw the next passage/room
//                        // first, generate next passage/room
//                        var loc = getPassageOrChamber();
//                        if (!loc) {
//                            context.restore();
//                            return;
//                        }
//                        rooms.push(loc);
//                        context.clearRect(-loc.width / 2, 0, loc.width, loc.length);
//                        context.strokeRect(-loc.width / 2, 0, loc.width, loc.length);
//
//                        // draw the door or opening
//                        if (exit.loc == "Door") {
//                            context.strokeStyle = "#ee9922";
//                            context.strokeRect(0, 0, 1, 0);
//                        } else if (exit.loc == "SecretDoor") {
//                            context.strokeStyle = "#2299ee";
//                            context.strokeRect(0, 0, 1, 0);
//                        } else {
//                            if (room.shape == "Circle") {
//                                context.clearRect(-.5, -.2, 1, .4);
//                            } else {
//                                context.clearRect(-(Math.min(room.width, loc.width) / 2) + .1, -.5, Math.min(room.width, loc.width) - .2, .9);
//                            }
//                        }
//                        context.restore();
//                    });
//                }
//
//                while(rooms.length > 0){
//                    drawChildRooms(rooms.shift());
//                }
            }
        };
    });