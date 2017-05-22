const fs = require('fs');
const projections = require('./projections.js');

const filename = process.argv[2];
const resultFile = '../data/results.json';

// write your projections in ./projections.js
Promise.all([readFile(filename), readFile(resultFile)])
    .then(([file, results]) => [parseEvents(file), JSON.parse(results)])
    .then(([events, results]) => exec(results[filename], events, projections))
    .catch(error => console.log(error));

function exec(results, events, projections) {
    for(let name in projections) {
        if(projections.hasOwnProperty(name)) {
            const start = Date.now();
            const res = projections[name](events);
            const end = Date.now();
            if(results && results[name]) {
                if(JSON.stringify(res) === JSON.stringify(results[name])) {
                    console.log(name + ' OK ('+(end-start)+' ms)');
                } else {
                    console.error(name + ' ERROR ('+(end-start)+' ms)');
                    console.error('result: ' + JSON.stringify(res, null, 2));
                    return events; // break the loop
                }
            } else {
                console.log(name + ' ('+(end-start)+' ms) results : ' + JSON.stringify(res));
            }
        }
    }
    return events;
}


function parseEvents(file) {
    return JSON.parse(file).map(event => {
        event.timestamp = new Date(event.timestamp);
        return event;
    });
}

function readFile(filename) {
    return new Promise((resolve, reject) => {
        fs.readFile(filename, 'utf-8', (err, data) => {
            if(err) reject(err);
            else resolve(data);
        });
    });
}
