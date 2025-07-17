// Grafico de lineas para actividades por dia
fetch('/api/actividades_por_dia')
    .then(response => response.json())
    .then(data => {
        Highcharts.chart('grafico-lineas', {
            chart: { type: 'line' },
            title: { text: 'Actividades por Dia' },
            xAxis: { categories: data.map(d => d.fecha) },
            yAxis: { title: { text: 'Cantidad de Actividades' } },
            series: [{
                name: 'Actividades',
                data: data.map(d => d.cantidad)
            }]
        });
    });

// Grafico de torta para actividades por tipo
fetch('/api/actividades_por_tipo')
    .then(response => response.json())
    .then(data => {
        Highcharts.chart('grafico-torta', {
            chart: { type: 'pie' },
            title: { text: 'Actividades por Tipo' },
            series: [{
                name: 'Cantidad',
                colorByPoint: true,
                data: data.map(d => ({ name: d.tipo, y: d.cantidad }))
            }]
        });
    });

// Grafico de barras para actividades por momento del dia
fetch('/api/actividades_por_momento_del_dia')
    .then(response => response.json())
    .then(data => {
        Highcharts.chart('grafico-barras', {
            chart: { type: 'column' },
            title: { text: 'Actividades por Momento del Dia y Mes' },
            xAxis: {
                categories: data.map(d => d.mes),
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: { text: 'Cantidad de Actividades' }
            },
            tooltip: { shared: true },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [
                {
                    name: 'Mañana',
                    data: data.map(d => d.mañana || 0)
                },
                {
                    name: 'Mediodia',
                    data: data.map(d => d.mediodía || 0)
                },
                {
                    name: 'Tarde',
                    data: data.map(d => d.tarde || 0)
                }
            ]
        });
    });
