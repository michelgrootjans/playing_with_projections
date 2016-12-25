var http = require("http");
var fs = require('fs');

function fetchFromUrl(hostname, port, path) {
    var options = {
        port,
        hostname,
        path: path,
        method: 'GET'
    };

    return new Promise((resolve, reject) => {
        var data = '';
        var req = http.request(options, (res) => {
            res.setEncoding('utf8');
            res.on('data', (chunk) => data += chunk);
            res.on('end', () => resolve(JSON.parse(data)))
        });

        req.on('error', e => reject(e));
        req.end();
    });
}

function fetchFromFile(stream) {
  return new Promise((resolve, reject) => {
    fs.readFile(stream, 'utf-8', (err, data) => {
      if(err) reject(err);

      resolve(JSON.parse(data));
    })
  });
}

// If you want to have the timestamps of the event as a Date object rather than a string, enable this
// transformation. This transformation mutates the events. After the transformation, the timestamp is a Date
// Also see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date for more info.
function transformTimestampToDate(events) {
  return events.map(event => {
    event.timestamp = new Date(event.timestamp);
    return event;
  });
}

// Write your projection here
function eventCounter(events) {
    return events.reduce((acc, event) => acc + 1, 0);
}

//fetchFromUrl('localhost', 8000, '/test_from_run.json')
var fileName = process.argv[2];
fetchFromFile(fileName)
    .then(events => transformTimestampToDate(events))
    .then(events => console.log(eventCounter(events)))
    .catch(error => console.log(error))
