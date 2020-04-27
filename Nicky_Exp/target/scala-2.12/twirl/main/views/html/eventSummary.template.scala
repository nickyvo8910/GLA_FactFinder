
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object eventSummary extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import play.mvc.Http.Context.Implicit._


Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>

"""),format.raw/*4.1*/("""
"""),format.raw/*5.1*/("""<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
<title>Fact Extraction Web App</title>
<link rel="stylesheet" href=""""),_display_(/*9.31*/routes/*9.37*/.Assets.versioned("stylesheets/bootstrap.min.css")),format.raw/*9.87*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*10.47*/routes/*10.53*/.Assets.versioned("stylesheets/all.min.css")),format.raw/*10.97*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*11.47*/routes/*11.53*/.Assets.versioned("stylesheets/open-iconic-bootstrap.min.css")),format.raw/*11.115*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*12.47*/routes/*12.53*/.Assets.versioned("stylesheets/DivTable.css")),format.raw/*12.98*/("""">


<link href="https://fonts.googleapis.com/css?family=Varela+Round"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<script src=""""),_display_(/*21.15*/routes/*21.21*/.Assets.versioned("javascripts/jquery.min.js")),format.raw/*21.67*/(""""></script>
<script src=""""),_display_(/*22.15*/routes/*22.21*/.Assets.versioned("javascripts/jquery.easing.min.js")),format.raw/*22.74*/(""""></script>
<script src=""""),_display_(/*23.15*/routes/*23.21*/.Assets.versioned("javascripts/bootstrap.min.js")),format.raw/*23.70*/(""""></script>
<script src=""""),_display_(/*24.15*/routes/*24.21*/.Assets.versioned("javascripts/all.min.js")),format.raw/*24.64*/(""""></script>
<script src=""""),_display_(/*25.15*/routes/*25.21*/.Assets.versioned("javascripts/canvasjs.min.js")),format.raw/*25.69*/(""""></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body id="myBody"
	wsdata=""""),_display_(/*32.11*/routes/*32.17*/.HomeController.socket.webSocketURL(request)),format.raw/*32.61*/(""""
	onload="initalize();">

	<div class="container">

		<div class="row">
			<div class="col-md-12">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<h4 style="font-family: 'Varela Round'">Welcome to Fact
							Extraction Web App / Event Summary</h4>
					</div>
				</nav>
			</div>
		</div>

		<div class="row">
			<div id="searchbar" class="col-md-12">
				<div class="input-group mb-3">
					<div class="input-group-append">
						<a href="/" class="btn btn-primary"> Back to Home</a>
					</div>
					<button type="button" class="btn btn-primary"
						onclick="fetchEvent()">Fetch Details</button>
				</div>
			</div>

		</div>

		<div class="nickytg-wrap">
			<table class="table table-striped" id="eventTable"
				style="display: inline-table; width: 100%;">
				<tbody id="resultTableBody" style="display: none; width: 100%; align-content: center;">
					<tr>
						<th class="nickytg-fymr"  style="display: flex;"></th>
						<th class="nickytg-f8tv"></th>
						<th class="nickytg-uog8"></th>
					</tr>
					<tr>
						<th class="nickytg-fymr">More info : </th>
						<td class="nickytg-0pky" colspan="2">
							<div id="wikiTag"></div> <br>
						</td>

					</tr>

					<tr>
						<th class="nickytg-6gib">Damage captured :</th>
						<td class="nickytg-6gib">Total Death: ###</td>
						<td class="nickytg-6gib">Total Injury: ###</td>
					</tr>


					<tr>
						<td class="nickytg-fymr">Overall Visualisation:</td>
						<td class="nickytg-0pky" colspan="2"><div id="chartContainer1"
								style="height: 370px; width: inherit;"></div></td>
						
					</tr>
					<tr>
						<td class="nickytg-fymr">Overall Visualisation:</td>
						
						<td class="nickytg-0pky" colspan="2"><div id="chartContainer2"
								style="height: 370px; width: inherit;"></div></td>
					</tr>
					<tr>
						<td class="nickytg-fymr">Overtime Visualisation:</td>
						<td class="nickytg-0pky" colspan="2"><div id="chartContainer3"
								style="height: 370px; width: inherit;"></div></td>
					
					</tr>
					<tr>
						<td class="nickytg-fymr">Overtime Visualisation:</td>
		
						<td class="nickytg-0pky"colspan="2"><div id="chartContainer4"
								style="height: 370px; width: inherit;"></div></td>
					</tr>

					<tr id="cloneStatementElement" style="display:none">
						<td class="nickytg-fymr">Statements:</td>
						<td class="nickytg-0pky">&lt;entity 1&gt;<br></td>
						<td class="nickytg-0pky" colspan="2">&lt;statements1&gt;</td>
					</tr>

				</tbody>
			</table>
		</div>

	</div>

	<script type="text/javascript">
		function initalize() """),format.raw/*124.24*/("""{"""),format.raw/*124.25*/("""
			"""),format.raw/*125.4*/("""openWebSocketConnection();
		"""),format.raw/*126.3*/("""}"""),format.raw/*126.4*/("""

		"""),format.raw/*128.3*/("""var ws; // websocket to the backend

		// ######################################################################################
		// Top level control logic
		// ######################################################################################

		function openWebSocketConnection() """),format.raw/*134.38*/("""{"""),format.raw/*134.39*/("""

			"""),format.raw/*136.4*/("""var wsURL = document.getElementById("myBody")
					.getAttribute("wsdata");

			ws = new WebSocket(wsURL);
			ws.onmessage = function(event) """),format.raw/*140.35*/("""{"""),format.raw/*140.36*/("""
				"""),format.raw/*141.5*/("""var message;
				var nodeData;
				var sameCatEvents;
				var sameYrEvents;
				message = JSON.parse(event.data);
				switch (message.messageType) """),format.raw/*146.34*/("""{"""),format.raw/*146.35*/("""
				case "init":
					break;
				case "queryResult":
					nodeData = JSON.parse(message["nodeData"]);
					updatePageContent(nodeData);
					break;
				case "sameCatMsg":
					sameCatEvents = JSON.parse(message["sameCatEvents"]);
					makeGraph1_2(sameCatEvents);
					break;
				case "sameYrMsg":
					sameYrEvents = JSON.parse(message["sameYrEvents"]);
					makeGraph2(sameYrEvents);
					break;
				default:
					return console.log(message);

				"""),format.raw/*164.5*/("""}"""),format.raw/*164.6*/("""
			"""),format.raw/*165.4*/("""}"""),format.raw/*165.5*/(""";
			ws.onclose = function(event) """),format.raw/*166.33*/("""{"""),format.raw/*166.34*/("""
				"""),format.raw/*167.5*/("""// do nothing
			"""),format.raw/*168.4*/("""}"""),format.raw/*168.5*/(""";
		"""),format.raw/*169.3*/("""}"""),format.raw/*169.4*/("""

		"""),format.raw/*171.3*/("""function updateStatementEntities(eventEntities)"""),format.raw/*171.50*/("""{"""),format.raw/*171.51*/("""
			"""),format.raw/*172.4*/("""//Create a HTML Table element.
			var tableBody = document.getElementById("resultTableBody");
			var tableRow = document.getElementById("cloneStatementElement");

			for(key in eventEntities)"""),format.raw/*176.29*/("""{"""),format.raw/*176.30*/("""

				"""),format.raw/*178.5*/("""var newRow = tableRow.cloneNode(true);
				newRow.id = "clonedStatementElement";
				newRow.style.display = "";

				var crrKey = key;
				var value =eventEntities[key];
				var testArr = Array.from(new Set(value.split('\n')));
			    var valueArr =testArr.join("<br/>");

				console.log("crrKey: "+ crrKey +" --- Value: "+ valueArr);
				newRow.children[1].innerHTML = crrKey.toString();
				newRow.children[2].innerHTML = valueArr.toString();

				tableBody.appendChild(newRow);
			"""),format.raw/*192.4*/("""}"""),format.raw/*192.5*/(""";



		"""),format.raw/*196.3*/("""}"""),format.raw/*196.4*/("""

		"""),format.raw/*198.3*/("""function updatePageContent(message)"""),format.raw/*198.38*/("""{"""),format.raw/*198.39*/("""
			"""),format.raw/*199.4*/("""var tableBody = document.getElementById("eventTable").children[0];
			tableBody.style.display = "inline-table";
			tableBody.children[0].children[0].innerHTML += message.eventTitle.replace(/\b\w/g, l => l.toUpperCase());
			tableBody.children[0].children[1].innerHTML += message.eventType.replace(/\b\w/g, l => l.toUpperCase());
			var utcStart = message.eventStart;
			var utcEnd = message.eventEnd;
			var dStart = new Date(0); // The 0 there is the key, which sets the date to the epoch
			var dEnd = new Date(0); // The 0 there is the key, which sets the date to the epoch
			dStart.setUTCSeconds(utcStart);
			dEnd.setUTCSeconds(utcEnd);

			tableBody.children[0].children[2].innerHTML = "Start on: "+ dStart.toLocaleDateString()+" - End on: "+dEnd.toLocaleDateString();

			tableBody.children[2].children[1].innerHTML = message.eventDeaths + " deaths";
			tableBody.children[2].children[2].innerHTML = message.eventInjuries + " injuries";
			console.log(message.eventDes.split("/")[message.eventDes.split("/").length-1]);

			defGetWikiMeta(message.eventDes.split("/")[message.eventDes.split("/").length-1]);
			updateStatementEntities(message.eventEntities);
			makeGraph3(message.eventInjuryMap);
			makeGraph4(message.eventDeathMap);
		"""),format.raw/*220.3*/("""}"""),format.raw/*220.4*/("""

		"""),format.raw/*222.3*/("""function makeGraph(sameCatEvents)"""),format.raw/*222.36*/("""{"""),format.raw/*222.37*/("""

			"""),format.raw/*224.4*/("""var inData = [];

			var chart = new CanvasJS.Chart("chartContainer1", """),format.raw/*226.54*/("""{"""),format.raw/*226.55*/("""
				"""),format.raw/*227.5*/("""animationEnabled: true,
				theme: "light2",
				title: """),format.raw/*229.12*/("""{"""),format.raw/*229.13*/("""
					"""),format.raw/*230.6*/("""text: "Compare to events in the category",
					fontSize:24
				"""),format.raw/*232.5*/("""}"""),format.raw/*232.6*/(""",
				axisX: """),format.raw/*233.12*/("""{"""),format.raw/*233.13*/("""
					"""),format.raw/*234.6*/("""valueFormatString: "DD, MMM, YYYY",
					labelAngle: -20,
					interval: 3,
			        intervalType: "month"
				"""),format.raw/*238.5*/("""}"""),format.raw/*238.6*/(""",
				axisY: """),format.raw/*239.12*/("""{"""),format.raw/*239.13*/("""
					"""),format.raw/*240.6*/("""title: "Total Damage : Deaths + Injuries",
					titleFontSize: 12
				"""),format.raw/*242.5*/("""}"""),format.raw/*242.6*/(""",
				data: ["""),format.raw/*243.12*/("""{"""),format.raw/*243.13*/("""
					"""),format.raw/*244.6*/("""type: "column",
					yValueFormatString: "#,### Persons",
					dataPoints: inData
				"""),format.raw/*247.5*/("""}"""),format.raw/*247.6*/("""]
			"""),format.raw/*248.4*/("""}"""),format.raw/*248.5*/(""");

			function addData(sameCatEvents) """),format.raw/*250.36*/("""{"""),format.raw/*250.37*/("""
				"""),format.raw/*251.5*/("""console.log(sameCatEvents);
				for (var i = 0; i < sameCatEvents.length; i++) """),format.raw/*252.52*/("""{"""),format.raw/*252.53*/("""
					"""),format.raw/*253.6*/("""var utcStart = sameCatEvents[i].eventStart;
					var dStart = new Date(0); // The 0 there is the key, which sets the date to the epoch
					dStart.setUTCSeconds(utcStart);

					inData.push("""),format.raw/*257.18*/("""{"""),format.raw/*257.19*/("""
						"""),format.raw/*258.7*/("""x: dStart,
						y: parseInt(sameCatEvents[i].eventDeaths, 10) + parseInt(sameCatEvents[i].eventInjuries, 10)
					"""),format.raw/*260.6*/("""}"""),format.raw/*260.7*/(""");
				"""),format.raw/*261.5*/("""}"""),format.raw/*261.6*/("""
				"""),format.raw/*262.5*/("""chart.render();
			"""),format.raw/*263.4*/("""}"""),format.raw/*263.5*/("""
			"""),format.raw/*264.4*/("""addData(sameCatEvents);
		"""),format.raw/*265.3*/("""}"""),format.raw/*265.4*/("""

		"""),format.raw/*267.3*/("""function makeGraph1_2(sameCatEvents)"""),format.raw/*267.39*/("""{"""),format.raw/*267.40*/("""
			"""),format.raw/*268.4*/("""var injuriesData = [];
			var deathsData = [];

			var chart = new CanvasJS.Chart("chartContainer1", """),format.raw/*271.54*/("""{"""),format.raw/*271.55*/("""
				"""),format.raw/*272.5*/("""animationEnabled: true,
				theme: "light2",
				title:"""),format.raw/*274.11*/("""{"""),format.raw/*274.12*/("""
					"""),format.raw/*275.6*/("""text: "Compare to events in the category",
					fontSize:24
				"""),format.raw/*277.5*/("""}"""),format.raw/*277.6*/(""",
				axisX: """),format.raw/*278.12*/("""{"""),format.raw/*278.13*/("""
					"""),format.raw/*279.6*/("""valueFormatString: "DD-MMM",
						interval: 3,
				        intervalType: "month"
				"""),format.raw/*282.5*/("""}"""),format.raw/*282.6*/(""",
				axisY: """),format.raw/*283.12*/("""{"""),format.raw/*283.13*/("""
					"""),format.raw/*284.6*/("""valueFormatString: "#,### Persons",
				"""),format.raw/*285.5*/("""}"""),format.raw/*285.6*/(""",
				toolTip: """),format.raw/*286.14*/("""{"""),format.raw/*286.15*/("""
					"""),format.raw/*287.6*/("""shared: true
				"""),format.raw/*288.5*/("""}"""),format.raw/*288.6*/(""",
				legend:"""),format.raw/*289.12*/("""{"""),format.raw/*289.13*/("""
					"""),format.raw/*290.6*/("""cursor: "pointer",
					itemclick: toggleDataSeries
				"""),format.raw/*292.5*/("""}"""),format.raw/*292.6*/(""",
				data: ["""),format.raw/*293.12*/("""{"""),format.raw/*293.13*/("""
					"""),format.raw/*294.6*/("""type: "stackedBar",
					name: "Injury",
					showInLegend: "true",
					xValueFormatString: "DD, MMM",
					yValueFormatString: "#,### Persons",
					dataPoints: injuriesData
				"""),format.raw/*300.5*/("""}"""),format.raw/*300.6*/(""",
				"""),format.raw/*301.5*/("""{"""),format.raw/*301.6*/("""
					"""),format.raw/*302.6*/("""type: "stackedBar",
					name: "Deaths",
					showInLegend: "true",
					xValueFormatString: "DD, MMM",
					yValueFormatString: "#,### Persons",
					dataPoints: deathsData
				"""),format.raw/*308.5*/("""}"""),format.raw/*308.6*/("""]
			"""),format.raw/*309.4*/("""}"""),format.raw/*309.5*/(""");

			function addData(sameCatEvents) """),format.raw/*311.36*/("""{"""),format.raw/*311.37*/("""
				"""),format.raw/*312.5*/("""console.log(sameCatEvents);
				for (var i = 0; i < sameCatEvents.length; i++) """),format.raw/*313.52*/("""{"""),format.raw/*313.53*/("""
					"""),format.raw/*314.6*/("""var utcStart = sameCatEvents[i].eventStart;
					var dStart = new Date(0); // The 0 there is the key, which sets the date to the epoch
					dStart.setUTCSeconds(utcStart);

					injuriesData.push("""),format.raw/*318.24*/("""{"""),format.raw/*318.25*/("""
						"""),format.raw/*319.7*/("""x: dStart,
						y: parseInt(sameCatEvents[i].eventInjuries, 10)
					"""),format.raw/*321.6*/("""}"""),format.raw/*321.7*/(""");

					deathsData.push("""),format.raw/*323.22*/("""{"""),format.raw/*323.23*/("""
						"""),format.raw/*324.7*/("""x: dStart,
						y: parseInt(sameCatEvents[i].eventDeaths, 10)
					"""),format.raw/*326.6*/("""}"""),format.raw/*326.7*/(""");
				"""),format.raw/*327.5*/("""}"""),format.raw/*327.6*/("""
				"""),format.raw/*328.5*/("""chart.render();
			"""),format.raw/*329.4*/("""}"""),format.raw/*329.5*/("""
			"""),format.raw/*330.4*/("""addData(sameCatEvents);


			function toggleDataSeries(e) """),format.raw/*333.33*/("""{"""),format.raw/*333.34*/("""
				"""),format.raw/*334.5*/("""if(typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) """),format.raw/*334.78*/("""{"""),format.raw/*334.79*/("""
					"""),format.raw/*335.6*/("""e.dataSeries.visible = false;
				"""),format.raw/*336.5*/("""}"""),format.raw/*336.6*/("""
				"""),format.raw/*337.5*/("""else """),format.raw/*337.10*/("""{"""),format.raw/*337.11*/("""
					"""),format.raw/*338.6*/("""e.dataSeries.visible = true;
				"""),format.raw/*339.5*/("""}"""),format.raw/*339.6*/("""
				"""),format.raw/*340.5*/("""chart.render();
			"""),format.raw/*341.4*/("""}"""),format.raw/*341.5*/("""
		"""),format.raw/*342.3*/("""}"""),format.raw/*342.4*/("""



		"""),format.raw/*346.3*/("""function makeGraph2(sameYrEvents)"""),format.raw/*346.36*/("""{"""),format.raw/*346.37*/("""
			"""),format.raw/*347.4*/("""var injuriesData = [];
			var deathsData = [];

			var chart2 = new CanvasJS.Chart("chartContainer2", """),format.raw/*350.55*/("""{"""),format.raw/*350.56*/("""
				"""),format.raw/*351.5*/("""animationEnabled: true,
				theme: "light2",
				title:"""),format.raw/*353.11*/("""{"""),format.raw/*353.12*/("""
					"""),format.raw/*354.6*/("""text: "Compare to events in the year",
					fontSize:24
				"""),format.raw/*356.5*/("""}"""),format.raw/*356.6*/(""",
				axisX: """),format.raw/*357.12*/("""{"""),format.raw/*357.13*/("""
					"""),format.raw/*358.6*/("""valueFormatString: "DD-MMM",
						interval: 3,
				        intervalType: "month"
				"""),format.raw/*361.5*/("""}"""),format.raw/*361.6*/(""",
				axisY: """),format.raw/*362.12*/("""{"""),format.raw/*362.13*/("""
					"""),format.raw/*363.6*/("""valueFormatString: "#,### Persons",
				"""),format.raw/*364.5*/("""}"""),format.raw/*364.6*/(""",
				toolTip: """),format.raw/*365.14*/("""{"""),format.raw/*365.15*/("""
					"""),format.raw/*366.6*/("""shared: true
				"""),format.raw/*367.5*/("""}"""),format.raw/*367.6*/(""",
				legend:"""),format.raw/*368.12*/("""{"""),format.raw/*368.13*/("""
					"""),format.raw/*369.6*/("""cursor: "pointer",
					itemclick: toggleDataSeries
				"""),format.raw/*371.5*/("""}"""),format.raw/*371.6*/(""",
				data: ["""),format.raw/*372.12*/("""{"""),format.raw/*372.13*/("""
					"""),format.raw/*373.6*/("""type: "stackedBar",
					name: "Injury",
					showInLegend: "true",
					xValueFormatString: "DD, MMM",
					yValueFormatString: "#,### Persons",
					dataPoints: injuriesData
				"""),format.raw/*379.5*/("""}"""),format.raw/*379.6*/(""",
				"""),format.raw/*380.5*/("""{"""),format.raw/*380.6*/("""
					"""),format.raw/*381.6*/("""type: "stackedBar",
					name: "Deaths",
					showInLegend: "true",
					xValueFormatString: "DD, MMM",
					yValueFormatString: "#,### Persons",
					dataPoints: deathsData
				"""),format.raw/*387.5*/("""}"""),format.raw/*387.6*/("""]
			"""),format.raw/*388.4*/("""}"""),format.raw/*388.5*/(""");

			function addData(sameCatEvents) """),format.raw/*390.36*/("""{"""),format.raw/*390.37*/("""
				"""),format.raw/*391.5*/("""console.log(sameCatEvents);
				for (var i = 0; i < sameCatEvents.length; i++) """),format.raw/*392.52*/("""{"""),format.raw/*392.53*/("""
					"""),format.raw/*393.6*/("""var utcStart = sameCatEvents[i].eventStart;
					var dStart = new Date(0); // The 0 there is the key, which sets the date to the epoch
					dStart.setUTCSeconds(utcStart);

					injuriesData.push("""),format.raw/*397.24*/("""{"""),format.raw/*397.25*/("""
						"""),format.raw/*398.7*/("""x: dStart,
						y: parseInt(sameCatEvents[i].eventInjuries, 10)
					"""),format.raw/*400.6*/("""}"""),format.raw/*400.7*/(""");

					deathsData.push("""),format.raw/*402.22*/("""{"""),format.raw/*402.23*/("""
						"""),format.raw/*403.7*/("""x: dStart,
						y: parseInt(sameCatEvents[i].eventDeaths, 10)
					"""),format.raw/*405.6*/("""}"""),format.raw/*405.7*/(""");
				"""),format.raw/*406.5*/("""}"""),format.raw/*406.6*/("""
				"""),format.raw/*407.5*/("""chart2.render();
			"""),format.raw/*408.4*/("""}"""),format.raw/*408.5*/("""
			"""),format.raw/*409.4*/("""addData(sameYrEvents);


			function toggleDataSeries(e) """),format.raw/*412.33*/("""{"""),format.raw/*412.34*/("""
				"""),format.raw/*413.5*/("""if(typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) """),format.raw/*413.78*/("""{"""),format.raw/*413.79*/("""
					"""),format.raw/*414.6*/("""e.dataSeries.visible = false;
				"""),format.raw/*415.5*/("""}"""),format.raw/*415.6*/("""
				"""),format.raw/*416.5*/("""else """),format.raw/*416.10*/("""{"""),format.raw/*416.11*/("""
					"""),format.raw/*417.6*/("""e.dataSeries.visible = true;
				"""),format.raw/*418.5*/("""}"""),format.raw/*418.6*/("""
				"""),format.raw/*419.5*/("""chart2.render();
			"""),format.raw/*420.4*/("""}"""),format.raw/*420.5*/("""
		"""),format.raw/*421.3*/("""}"""),format.raw/*421.4*/("""

		"""),format.raw/*423.3*/("""function makeGraph3(inInjuries)"""),format.raw/*423.34*/("""{"""),format.raw/*423.35*/("""
			"""),format.raw/*424.4*/("""var injuriesData = [];

			var chart3 = new CanvasJS.Chart("chartContainer3", """),format.raw/*426.55*/("""{"""),format.raw/*426.56*/("""
				"""),format.raw/*427.5*/("""animationEnabled: true,
				title:"""),format.raw/*428.11*/("""{"""),format.raw/*428.12*/("""
					"""),format.raw/*429.6*/("""text: "New Injury Registered"
				"""),format.raw/*430.5*/("""}"""),format.raw/*430.6*/(""",
				axisX:"""),format.raw/*431.11*/("""{"""),format.raw/*431.12*/("""
					"""),format.raw/*432.6*/("""valueFormatString: "DD"
				"""),format.raw/*433.5*/("""}"""),format.raw/*433.6*/(""",
				axisY: """),format.raw/*434.12*/("""{"""),format.raw/*434.13*/("""
					"""),format.raw/*435.6*/("""title: "Persons",
					includeZero: false,
					scaleBreaks: """),format.raw/*437.19*/("""{"""),format.raw/*437.20*/("""
						"""),format.raw/*438.7*/("""autoCalculate: true
					"""),format.raw/*439.6*/("""}"""),format.raw/*439.7*/("""
				"""),format.raw/*440.5*/("""}"""),format.raw/*440.6*/(""",
				data: ["""),format.raw/*441.12*/("""{"""),format.raw/*441.13*/("""
					"""),format.raw/*442.6*/("""type: "line",
					xValueFormatString: "DD MMM - HH:mm",
					yValueFormatString: "#,### Persons",
					color: "#F08080",
					dataPoints: injuriesData
				"""),format.raw/*447.5*/("""}"""),format.raw/*447.6*/("""]
			"""),format.raw/*448.4*/("""}"""),format.raw/*448.5*/(""");
			function addData(inInjuries) """),format.raw/*449.33*/("""{"""),format.raw/*449.34*/("""
				"""),format.raw/*450.5*/("""for(key in inInjuries)"""),format.raw/*450.27*/("""{"""),format.raw/*450.28*/("""
					"""),format.raw/*451.6*/("""var crrTimeStamp = key;
					var crrDateTime = new Date(0); // The 0 there is the key, which sets the date to the epoch
					crrDateTime.setUTCSeconds(crrTimeStamp);

					injuriesData.push("""),format.raw/*455.24*/("""{"""),format.raw/*455.25*/("""
						"""),format.raw/*456.7*/("""x: crrDateTime,
						y: inInjuries[key]
					"""),format.raw/*458.6*/("""}"""),format.raw/*458.7*/(""");

				"""),format.raw/*460.5*/("""}"""),format.raw/*460.6*/("""
				"""),format.raw/*461.5*/("""chart3.render();
			"""),format.raw/*462.4*/("""}"""),format.raw/*462.5*/("""
			"""),format.raw/*463.4*/("""addData(inInjuries);

		"""),format.raw/*465.3*/("""}"""),format.raw/*465.4*/("""
		"""),format.raw/*466.3*/("""function makeGraph4(inDeaths)"""),format.raw/*466.32*/("""{"""),format.raw/*466.33*/("""
			"""),format.raw/*467.4*/("""var deathsData = [];

			var chart4 = new CanvasJS.Chart("chartContainer4", """),format.raw/*469.55*/("""{"""),format.raw/*469.56*/("""
				"""),format.raw/*470.5*/("""animationEnabled: true,
				title:"""),format.raw/*471.11*/("""{"""),format.raw/*471.12*/("""
					"""),format.raw/*472.6*/("""text: "New Casualty Registered"
				"""),format.raw/*473.5*/("""}"""),format.raw/*473.6*/(""",
				axisX:"""),format.raw/*474.11*/("""{"""),format.raw/*474.12*/("""
					"""),format.raw/*475.6*/("""valueFormatString: "DD"
				"""),format.raw/*476.5*/("""}"""),format.raw/*476.6*/(""",
				axisY: """),format.raw/*477.12*/("""{"""),format.raw/*477.13*/("""
					"""),format.raw/*478.6*/("""title: "Persons",
					includeZero: false,
					scaleBreaks: """),format.raw/*480.19*/("""{"""),format.raw/*480.20*/("""
						"""),format.raw/*481.7*/("""autoCalculate: true
					"""),format.raw/*482.6*/("""}"""),format.raw/*482.7*/("""
				"""),format.raw/*483.5*/("""}"""),format.raw/*483.6*/(""",
				data: ["""),format.raw/*484.12*/("""{"""),format.raw/*484.13*/("""
					"""),format.raw/*485.6*/("""type: "line",
					xValueFormatString: "DD MMM - HH:mm",
					yValueFormatString: "#,### Persons",
					color: "#F08080",
					dataPoints: deathsData
				"""),format.raw/*490.5*/("""}"""),format.raw/*490.6*/("""]
			"""),format.raw/*491.4*/("""}"""),format.raw/*491.5*/(""");
			function addData(inDeaths) """),format.raw/*492.31*/("""{"""),format.raw/*492.32*/("""
				"""),format.raw/*493.5*/("""for(key in inDeaths)"""),format.raw/*493.25*/("""{"""),format.raw/*493.26*/("""
					"""),format.raw/*494.6*/("""var crrTimeStamp = key;
					var crrDateTime = new Date(0); // The 0 there is the key, which sets the date to the epoch
					crrDateTime.setUTCSeconds(crrTimeStamp);

					deathsData.push("""),format.raw/*498.22*/("""{"""),format.raw/*498.23*/("""
						"""),format.raw/*499.7*/("""x: crrDateTime,
						y: inDeaths[key]
					"""),format.raw/*501.6*/("""}"""),format.raw/*501.7*/(""");

				"""),format.raw/*503.5*/("""}"""),format.raw/*503.6*/("""
				"""),format.raw/*504.5*/("""chart4.render();
			"""),format.raw/*505.4*/("""}"""),format.raw/*505.5*/("""
			"""),format.raw/*506.4*/("""addData(inDeaths);

		"""),format.raw/*508.3*/("""}"""),format.raw/*508.4*/("""

		"""),format.raw/*510.3*/("""function fetchEvent() """),format.raw/*510.25*/("""{"""),format.raw/*510.26*/("""
			"""),format.raw/*511.4*/("""var urlParams = new URLSearchParams(window.location.search);
			var eventQuery = urlParams.get('eventId');
			console.log(eventQuery)
			console.log(ws.toString())
			ws.send(JSON.stringify("""),format.raw/*515.27*/("""{"""),format.raw/*515.28*/("""
				"""),format.raw/*516.5*/("""messageType : "getEventDetails",
				query : eventQuery
			"""),format.raw/*518.4*/("""}"""),format.raw/*518.5*/("""));
		"""),format.raw/*519.3*/("""}"""),format.raw/*519.4*/("""
		"""),format.raw/*520.3*/("""function defGetWikiMeta(query)"""),format.raw/*520.33*/("""{"""),format.raw/*520.34*/("""
			 """),format.raw/*521.5*/("""$.ajax("""),format.raw/*521.12*/("""{"""),format.raw/*521.13*/("""
			        """),format.raw/*522.12*/("""type: "GET",
			        url: "http://en.wikipedia.org/w/api.php?action=parse&format=json&prop=text&section=0&page="+query+"&callback=?",
			        contentType: "application/json; charset=utf-8",
			        async: true,
			        dataType: "json",
			        success: function (data, textStatus, jqXHR) """),format.raw/*527.56*/("""{"""),format.raw/*527.57*/("""

			            """),format.raw/*529.16*/("""var markup = data.parse.text["*"];
			            var blurb = $('<div></div>').html(markup);

			            // remove links as they will not work
			            blurb.find('a').each(function() """),format.raw/*533.48*/("""{"""),format.raw/*533.49*/(""" """),format.raw/*533.50*/("""$(this).replaceWith($(this).html()); """),format.raw/*533.87*/("""}"""),format.raw/*533.88*/(""");

			            // remove any references
			            blurb.find('sup').remove();

			            // remove cite error
			            blurb.find('.mw-ext-cite-error').remove();
			            $('#wikiTag').html($(blurb).find('p'));

			        """),format.raw/*542.12*/("""}"""),format.raw/*542.13*/(""",
			        error: function (errorMessage) """),format.raw/*543.43*/("""{"""),format.raw/*543.44*/("""
			        """),format.raw/*544.12*/("""}"""),format.raw/*544.13*/("""
			    """),format.raw/*545.8*/("""}"""),format.raw/*545.9*/(""");

		"""),format.raw/*547.3*/("""}"""),format.raw/*547.4*/("""


	"""),format.raw/*550.2*/("""</script>





</body>
<footer align="center">
 © 2019 Developed by Nicky Vo at University Of Glasgow.
</footer>

</html>
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Mar 27 15:07:39 GMT 2019
                  SOURCE: /media/nicky/STUDY/3_GU/4thYr/PRJ/factfinderbackend/Nicky_Exp/app/views/eventSummary.scala.html
                  HASH: b37e6e24c6b2645bb0e5fa5e8e2829a7e80a13ee
                  MATRIX: 797->20|865->0|910->61|938->63|1122->221|1136->227|1206->277|1283->327|1298->333|1363->377|1440->427|1455->433|1539->495|1616->545|1631->551|1697->596|1981->853|1996->859|2063->905|2117->932|2132->938|2206->991|2260->1018|2275->1024|2345->1073|2399->1100|2414->1106|2478->1149|2532->1176|2547->1182|2616->1230|2815->1402|2830->1408|2895->1452|5684->4212|5714->4213|5747->4218|5805->4248|5834->4249|5868->4255|6191->4549|6221->4550|6256->4557|6430->4702|6460->4703|6494->4709|6675->4861|6705->4862|7202->5331|7231->5332|7264->5337|7293->5338|7357->5373|7387->5374|7421->5380|7467->5398|7496->5399|7529->5404|7558->5405|7592->5411|7668->5458|7698->5459|7731->5464|7955->5659|7985->5660|8021->5668|8548->6167|8577->6168|8616->6179|8645->6180|8679->6186|8743->6221|8773->6222|8806->6227|10100->7493|10129->7494|10163->7500|10225->7533|10255->7534|10290->7541|10392->7614|10422->7615|10456->7621|10543->7679|10573->7680|10608->7687|10702->7753|10731->7754|10774->7768|10804->7769|10839->7776|10984->7893|11013->7894|11056->7908|11086->7909|11121->7916|11221->7988|11250->7989|11293->8003|11323->8004|11358->8011|11475->8100|11504->8101|11538->8107|11567->8108|11637->8149|11667->8150|11701->8156|11810->8236|11840->8237|11875->8244|12098->8438|12128->8439|12164->8447|12309->8564|12338->8565|12374->8573|12403->8574|12437->8580|12485->8600|12514->8601|12547->8606|12602->8633|12631->8634|12665->8640|12730->8676|12760->8677|12793->8682|12926->8786|12956->8787|12990->8793|13076->8850|13106->8851|13141->8858|13235->8924|13264->8925|13307->8939|13337->8940|13372->8947|13489->9036|13518->9037|13561->9051|13591->9052|13626->9059|13695->9100|13724->9101|13769->9117|13799->9118|13834->9125|13880->9143|13909->9144|13952->9158|13982->9159|14017->9166|14103->9224|14132->9225|14175->9239|14205->9240|14240->9247|14454->9433|14483->9434|14518->9441|14547->9442|14582->9449|14794->9633|14823->9634|14857->9640|14886->9641|14956->9682|14986->9683|15020->9689|15129->9769|15159->9770|15194->9777|15423->9977|15453->9978|15489->9986|15589->10058|15618->10059|15674->10086|15704->10087|15740->10095|15838->10165|15867->10166|15903->10174|15932->10175|15966->10181|16014->10201|16043->10202|16076->10207|16166->10268|16196->10269|16230->10275|16332->10348|16362->10349|16397->10356|16460->10391|16489->10392|16523->10398|16557->10403|16587->10404|16622->10411|16684->10445|16713->10446|16747->10452|16795->10472|16824->10473|16856->10477|16885->10478|16923->10488|16985->10521|17015->10522|17048->10527|17182->10632|17212->10633|17246->10639|17332->10696|17362->10697|17397->10704|17487->10766|17516->10767|17559->10781|17589->10782|17624->10789|17741->10878|17770->10879|17813->10893|17843->10894|17878->10901|17947->10942|17976->10943|18021->10959|18051->10960|18086->10967|18132->10985|18161->10986|18204->11000|18234->11001|18269->11008|18355->11066|18384->11067|18427->11081|18457->11082|18492->11089|18706->11275|18735->11276|18770->11283|18799->11284|18834->11291|19046->11475|19075->11476|19109->11482|19138->11483|19208->11524|19238->11525|19272->11531|19381->11611|19411->11612|19446->11619|19675->11819|19705->11820|19741->11828|19841->11900|19870->11901|19926->11928|19956->11929|19992->11937|20090->12007|20119->12008|20155->12016|20184->12017|20218->12023|20267->12044|20296->12045|20329->12050|20418->12110|20448->12111|20482->12117|20584->12190|20614->12191|20649->12198|20712->12233|20741->12234|20775->12240|20809->12245|20839->12246|20874->12253|20936->12287|20965->12288|20999->12294|21048->12315|21077->12316|21109->12320|21138->12321|21172->12327|21232->12358|21262->12359|21295->12364|21404->12444|21434->12445|21468->12451|21532->12486|21562->12487|21597->12494|21660->12529|21689->12530|21731->12543|21761->12544|21796->12551|21853->12580|21882->12581|21925->12595|21955->12596|21990->12603|22082->12666|22112->12667|22148->12675|22202->12701|22231->12702|22265->12708|22294->12709|22337->12723|22367->12724|22402->12731|22591->12892|22620->12893|22654->12899|22683->12900|22748->12936|22778->12937|22812->12943|22863->12965|22893->12966|22928->12973|23151->13167|23181->13168|23217->13176|23293->13224|23322->13225|23360->13235|23389->13236|23423->13242|23472->13263|23501->13264|23534->13269|23588->13295|23617->13296|23649->13300|23707->13329|23737->13330|23770->13335|23877->13413|23907->13414|23941->13420|24005->13455|24035->13456|24070->13463|24135->13500|24164->13501|24206->13514|24236->13515|24271->13522|24328->13551|24357->13552|24400->13566|24430->13567|24465->13574|24557->13637|24587->13638|24623->13646|24677->13672|24706->13673|24740->13679|24769->13680|24812->13694|24842->13695|24877->13702|25064->13861|25093->13862|25127->13868|25156->13869|25219->13903|25249->13904|25283->13910|25332->13930|25362->13931|25397->13938|25618->14130|25648->14131|25684->14139|25758->14185|25787->14186|25825->14196|25854->14197|25888->14203|25937->14224|25966->14225|25999->14230|26051->14254|26080->14255|26114->14261|26165->14283|26195->14284|26228->14289|26451->14483|26481->14484|26515->14490|26604->14551|26633->14552|26668->14559|26697->14560|26729->14564|26788->14594|26818->14595|26852->14601|26888->14608|26918->14609|26960->14622|27298->14931|27328->14932|27376->14951|27603->15149|27633->15150|27663->15151|27729->15188|27759->15189|28046->15447|28076->15448|28150->15493|28180->15494|28222->15507|28252->15508|28289->15517|28318->15518|28354->15526|28383->15527|28418->15534
                  LINES: 24->3|27->1|29->4|30->5|34->9|34->9|34->9|35->10|35->10|35->10|36->11|36->11|36->11|37->12|37->12|37->12|46->21|46->21|46->21|47->22|47->22|47->22|48->23|48->23|48->23|49->24|49->24|49->24|50->25|50->25|50->25|57->32|57->32|57->32|149->124|149->124|150->125|151->126|151->126|153->128|159->134|159->134|161->136|165->140|165->140|166->141|171->146|171->146|189->164|189->164|190->165|190->165|191->166|191->166|192->167|193->168|193->168|194->169|194->169|196->171|196->171|196->171|197->172|201->176|201->176|203->178|217->192|217->192|221->196|221->196|223->198|223->198|223->198|224->199|245->220|245->220|247->222|247->222|247->222|249->224|251->226|251->226|252->227|254->229|254->229|255->230|257->232|257->232|258->233|258->233|259->234|263->238|263->238|264->239|264->239|265->240|267->242|267->242|268->243|268->243|269->244|272->247|272->247|273->248|273->248|275->250|275->250|276->251|277->252|277->252|278->253|282->257|282->257|283->258|285->260|285->260|286->261|286->261|287->262|288->263|288->263|289->264|290->265|290->265|292->267|292->267|292->267|293->268|296->271|296->271|297->272|299->274|299->274|300->275|302->277|302->277|303->278|303->278|304->279|307->282|307->282|308->283|308->283|309->284|310->285|310->285|311->286|311->286|312->287|313->288|313->288|314->289|314->289|315->290|317->292|317->292|318->293|318->293|319->294|325->300|325->300|326->301|326->301|327->302|333->308|333->308|334->309|334->309|336->311|336->311|337->312|338->313|338->313|339->314|343->318|343->318|344->319|346->321|346->321|348->323|348->323|349->324|351->326|351->326|352->327|352->327|353->328|354->329|354->329|355->330|358->333|358->333|359->334|359->334|359->334|360->335|361->336|361->336|362->337|362->337|362->337|363->338|364->339|364->339|365->340|366->341|366->341|367->342|367->342|371->346|371->346|371->346|372->347|375->350|375->350|376->351|378->353|378->353|379->354|381->356|381->356|382->357|382->357|383->358|386->361|386->361|387->362|387->362|388->363|389->364|389->364|390->365|390->365|391->366|392->367|392->367|393->368|393->368|394->369|396->371|396->371|397->372|397->372|398->373|404->379|404->379|405->380|405->380|406->381|412->387|412->387|413->388|413->388|415->390|415->390|416->391|417->392|417->392|418->393|422->397|422->397|423->398|425->400|425->400|427->402|427->402|428->403|430->405|430->405|431->406|431->406|432->407|433->408|433->408|434->409|437->412|437->412|438->413|438->413|438->413|439->414|440->415|440->415|441->416|441->416|441->416|442->417|443->418|443->418|444->419|445->420|445->420|446->421|446->421|448->423|448->423|448->423|449->424|451->426|451->426|452->427|453->428|453->428|454->429|455->430|455->430|456->431|456->431|457->432|458->433|458->433|459->434|459->434|460->435|462->437|462->437|463->438|464->439|464->439|465->440|465->440|466->441|466->441|467->442|472->447|472->447|473->448|473->448|474->449|474->449|475->450|475->450|475->450|476->451|480->455|480->455|481->456|483->458|483->458|485->460|485->460|486->461|487->462|487->462|488->463|490->465|490->465|491->466|491->466|491->466|492->467|494->469|494->469|495->470|496->471|496->471|497->472|498->473|498->473|499->474|499->474|500->475|501->476|501->476|502->477|502->477|503->478|505->480|505->480|506->481|507->482|507->482|508->483|508->483|509->484|509->484|510->485|515->490|515->490|516->491|516->491|517->492|517->492|518->493|518->493|518->493|519->494|523->498|523->498|524->499|526->501|526->501|528->503|528->503|529->504|530->505|530->505|531->506|533->508|533->508|535->510|535->510|535->510|536->511|540->515|540->515|541->516|543->518|543->518|544->519|544->519|545->520|545->520|545->520|546->521|546->521|546->521|547->522|552->527|552->527|554->529|558->533|558->533|558->533|558->533|558->533|567->542|567->542|568->543|568->543|569->544|569->544|570->545|570->545|572->547|572->547|575->550
                  -- GENERATED --
              */
          