Array.prototype.contains = function (targ) {
    if (!targ) {
        return false;
    }

    var contains = false;
    this.forEach(function (it) {
        contains = contains || it == targ;
        return contains;
    });
    return contains;
};

Array.prototype.remove = function (targ) {
    if (!targ) {
        return;
    }

    var inds = [];
    this.forEach(function (it, i) {
        if (it == targ) {
            inds.push(i);
        }
    });
    var minus = 0, self = this;
    inds.forEach(function (it) {
        self.splice(it - minus, 1);
        minus++;
    });
    return this;
};

Array.prototype.replace = function (newArray) {
    var rep = this.splice(0, this.length);
    Array.prototype.push.apply(this, newArray);
    return rep;
};