/**
 * Created by DZL on 6/9/14.
 */
angular.module('dzl.directives')
        .directive('appCanvas', function () {
            var dim = 7;
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
                restrict: "AE",
                scope: {
                    color: "=",
                    culture: "="
                },
                templateUrl: "templates/app-canvas.html",
                replace: true,
                link: function (scope, elem, attrs) {
                    var ctx = elem[0].getContext("2d");
                    scope.colors = colors;
                    var color = colors[scope.color] || blue;
                    ctx.beginPath();
                    ctx.fillStyle = color;
                    ctx.rect(0, 0, dim, dim);
                    ctx.fill();

                    if (scope.culture) {
                        ctx.beginPath();
                        ctx.fillStyle = cultureColors[scope.culture.color % cultureColors.length];
                        ctx.arc(4, 4, 3, 0, 2 * Math.PI);
                        ctx.fill();
                    }
                }
            };
        });