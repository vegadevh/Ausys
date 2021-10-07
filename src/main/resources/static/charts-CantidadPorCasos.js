google.charts.load("current", { packages: ["corechart"] });
google.charts.setOnLoadCallback(drawChart1);

function drawChart1() {
  var caso1 = document.getElementById("caso1");
  var caso2 = document.getElementById("caso2");
  var caso3 = document.getElementById("caso3");
  var caso4 = document.getElementById("caso4");
  var caso5 = document.getElementById("caso5");

  var caso1Value = caso1.innerText;
  var caso2Value = caso2.innerText;
  var caso3Value = caso3.innerText;
  var caso4Value = caso4.innerText;
  var caso5Value = caso5.innerText;

  var gdata = google.visualization.arrayToDataTable([
    ['Titulo', 'Cantidad de casos por tipo de caso'],
    ['Análisis toxicólogico', parseInt(caso1Value)],
    ['Análisis toxicólogico Medio ambientales', parseInt(caso2Value)],
    ['Asistencia a vistas públicas', parseInt(caso3Value)],
    ['Examenes odontológicos', parseInt(caso4Value)],
    ['Desaparecido', parseInt(caso5Value)]
  ]);

  var options = {
    title: 'Cantidad de casos por tipo de caso',
    is3D: true,
  };

  var chart = new google.visualization.PieChart(document.getElementById('piechart-casos'));
  chart.draw(gdata, options);
}