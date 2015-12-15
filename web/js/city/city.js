var RacePop = (function () {
    function RacePop(race, population) {
        this.race = race;
        this.population = population;
    }
    return RacePop;
})();
exports.RacePop = RacePop;
var Population = (function () {
    function Population(people, tot, searchMod) {
        this.people = people;
        this.tot = tot;
        this.searchMod = searchMod;
    }
    return Population;
})();
exports.Population = Population;
var Ruler = (function () {
    function Ruler(name, race) {
        this.name = name;
        this.race = race;
    }
    return Ruler;
})();
exports.Ruler = Ruler;
var City = (function () {
    function City(name, ruler, population, inns) {
        this.name = name;
        this.ruler = ruler;
        this.population = population;
        this.inns = inns;
    }
    return City;
})();
exports.City = City;
//# sourceMappingURL=city.js.map