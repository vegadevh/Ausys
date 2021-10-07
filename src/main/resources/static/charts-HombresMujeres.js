google.charts.load("current", { packages: ["corechart"] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	var titulo = document.getElementById("titulo");
	var mujer = document.getElementById("mujer");
	var hombre = document.getElementById("hombre");

	var tituloValue = titulo.innerText;
	var mujerValue = mujer.innerText;
	var hombreValue = hombre.innerText;

	var data = google.visualization.arrayToDataTable([
		['Titulo', tituloValue],
		['Mujeres', parseInt(mujerValue)],
		['Hombres', parseInt(hombreValue)]
	]);

	var options = {
		title: tituloValue,
		is3D: true,
	};

	var chart = new google.visualization.PieChart(document.getElementById('piechart'));
	chart.draw(data, options);
}