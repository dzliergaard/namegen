
/*
 *  RPToolkit - Tools to assist Role-Playing Game masters and players
 *  Copyright (C) 2016  Dane Zeke Liergaard
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

System.config({
    paths: {
        'app/': '/js/app/',
        'city/': '/js/city/',
        'mat/': '/js/mat/',
        'name/': '/js/name/',
        'util/': '/js/util/',
        'underscore': '/js/node_modules/underscore/underscore-min.js'
    },
    packages: {
        app: {
            formatter: 'register',
            defaultExtension: 'js'
        },
        city: {
            formatter: 'register',
            defaultExtension: 'js'
        },
        mat: {
            formatter: 'register',
            defaultExtension: 'js'
        },
        name: {
            formatter: 'register',
            defaultExtension: 'js'
        },
        util: {
            formatter: 'register',
            defaultExtension: 'js'
        }
    },
    main: "main",
    meta: {}
});

System.import('/js/app/main');