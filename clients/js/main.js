const http = require("http");

function fetchStream(stream) {
    const options = {
        hostname: 'localhost',
        port: 4000,
        path: `/api/stream/${stream}`,
        method: 'GET'
    };

    return new Promise((resolve, reject) => {
        let data = '';
        const req = http.request(options, (res) => {
            res.setEncoding('utf8');
            res.on('data', (chunk) => data += chunk);
            res.on('end', () => resolve(JSON.parse(data)))
        });

        req.on('error', e => reject(e));
        req.end();
    });
}

function registeredPlayersProjection(events) {
    return events.reduce((acc, {event, data}) => {
        switch (event) {
            case 'PlayerHasRegistered': {
                acc[data.player_id] = {first_name: data.first_name, last_name: data.last_name}
                return acc;
            }
            default: return acc
        }
    }, {});
}

fetchStream(1)
    .then(events => console.log(registeredPlayersProjection(events)))
    .catch(error => console.log(error));
