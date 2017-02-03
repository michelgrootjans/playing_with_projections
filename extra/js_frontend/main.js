function fetchJson(url) {
    return fetch(url, {method: 'GET'})
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

    return {table: header.concat(data), eventTypes};
}

const chartOptions = {
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

function filter(typeIndex, chart, dataTable, options) {
    const view = new google.visualization.DataView(dataTable);
    const columnsToHide = typeIndex > -1 ? view.getViewColumns().filter(row => row != 0 && row != typeIndex + 1) : [];
    view.hideColumns(columnsToHide);
    chart.draw(view, chartOptions);
}

function renderWithData(data) {
    const {table, eventTypes} = visualizationData(data);

    const dataTable = google.visualization.arrayToDataTable(table);

    const chart = new google.charts.Bar(document.getElementById('chart_div'));
    chart.draw(dataTable, google.charts.Bar.convertOptions(chartOptions));

    const options = eventTypes.map(type => ({text: type, value: type}));
    options.push({text: 'Show all', value: -1});

    const app = new Vue({
      el: '#vue',
      data: {
        selected: 'all',
        options
      },
      methods: {
          updateDraw: function(event) {
              const typeIndex = eventTypes.indexOf(event.target.value);
              filter(typeIndex, chart, dataTable, chartOptions);
          }
      }
    })

}

function init() {
    const url = 'http://localhost:8000/';
    const urlToFetch = `${url}5_2015_01_2017_01.json`;
    fetchJson(urlToFetch).then(renderWithData);
}

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(init);

