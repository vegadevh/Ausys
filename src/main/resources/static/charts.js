google.charts.load("current", { packages: ["corechart"] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  var mujer = document.getElementById("mujer");
  var hombre = document.getElementById("hombre");

  var mujerValue = mujer.innerText;
  var hombreValue = hombre.innerText;

  var data = google.visualization.arrayToDataTable([
    ['Titulo', 'Hombre y Mujeres por Rango de fecha'],
    ['Mujeres', parseInt(mujerValue)],
    ['Hombres', parseInt(hombreValue)]
  ]);

  var options = {
    title: 'Hombre y Mujeres por Rango de fecha',
    is3D: true,
  };

  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  chart.draw(data, options);
}