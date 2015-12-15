var Name = (function () {
    function Name(text, key) {
        this.text = text;
        this.key = key;
    }
    return Name;
})();
exports.Name = Name;
var Names = (function () {
    function Names() {
        this.generated = [];
        this.saved = [];
    }
    Names.prototype.getGenerated = function () {
        return this.generated;
    };
    Names.prototype.getSaved = function () {
        return this.saved;
    };
    Names.prototype.setSaved = function () {

    };
    Names.prototype.remove = function (item) {
        this.saved = _.reject(this.saved, item);
    };
    return Names;
})();
exports.Names = Names;
//# sourceMappingURL=name.js.map