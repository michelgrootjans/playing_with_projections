function fetchJson(name) {
    return fetch(`https://playing-with-projections.herokuapp.com/stream/${name}`, {method: 'GET'})
        .then(resp => resp.json())
}

function mapEventTypes(events) {
    return events.reduce((acc, event) => {
        if (acc.indexOf(event.type) == -1) {
            acc.push(event.type);
        }
        return acc;
    }, [])
}

function makeEventMap(events) {
    const eventTypes = mapEventTypes(events);

    return events.reduce((acc, event) => {
        const dateString = moment(event.timestamp).format("YYYYMM");
        if (!acc[dateString]) {
            acc[dateString] = {};
        }
        if (!acc[dateString][event.type]) {
            acc[dateString][event.type] = 0;
        }
        acc[dateString][event.type] += 1;
        
        return acc;
    }, {});
}

function visualizationData(jsonData) {
    const eventMap = makeEventMap(jsonData);
    const eventTypes = mapEventTypes(jsonData);

    const header = [['Date'].concat(eventTypes)]

    data = Object.keys(eventMap).map(key => {
        numberOfEventsPerType = eventTypes.map(type => eventMap[key][type]);
        return [key].concat(numberOfEventsPerType);
    });

    return header.concat(data);
}

function drawAnnotations(dataTable) {
    var data = google.visualization.arrayToDataTable(dataTable);

    var options = {
          chart: {
            title: 'Playing with projections',
            subtitle: 'Events',
          },
          bars: 'vertical',
          vAxis: {format: 'decimal'},
          height: 700,
          colors: ['#1b9e77', '#d95f02', '#7570b3'],
          explorer: {}
    };

    var chart = new google.charts.Bar(document.getElementById('chart_div')); 
    chart.draw(data, google.charts.Bar.convertOptions(options));
}

function init() {
    fetchJson('1').then(data => {
        const dataToVisualize = visualizationData(data);
        drawAnnotations(dataToVisualize);
    })

}

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(init);