
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import play.mvc.Http.Context.Implicit._


Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>

"""),format.raw/*4.1*/("""
"""),format.raw/*5.1*/("""<html>
<head>
<title>Fact Extraction Web App</title>
<link rel="stylesheet" href=""""),_display_(/*8.31*/routes/*8.37*/.Assets.versioned("stylesheets/bootstrap.min.css")),format.raw/*8.87*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*9.47*/routes/*9.53*/.Assets.versioned("stylesheets/all.min.css")),format.raw/*9.97*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*10.47*/routes/*10.53*/.Assets.versioned("stylesheets/open-iconic-bootstrap.min.css")),format.raw/*10.115*/("""">
<link rel="stylesheet" type="text/css" href=""""),_display_(/*11.47*/routes/*11.53*/.Assets.versioned("stylesheets/DivTable.css")),format.raw/*11.98*/("""">


<link href="https://fonts.googleapis.com/css?family=Varela+Round"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<script src=""""),_display_(/*20.15*/routes/*20.21*/.Assets.versioned("javascripts/jquery.min.js")),format.raw/*20.67*/(""""></script>
<script src=""""),_display_(/*21.15*/routes/*21.21*/.Assets.versioned("javascripts/jquery.easing.min.js")),format.raw/*21.74*/(""""></script>
<script src=""""),_display_(/*22.15*/routes/*22.21*/.Assets.versioned("javascripts/bootstrap.min.js")),format.raw/*22.70*/(""""></script>
<script src=""""),_display_(/*23.15*/routes/*23.21*/.Assets.versioned("javascripts/all.min.js")),format.raw/*23.64*/(""""></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body id="myBody"
	wsdata=""""),_display_(/*30.11*/routes/*30.17*/.HomeController.socket.webSocketURL(request)),format.raw/*30.61*/(""""
	onload="initalize()">

	<div class="container">

		<div class="row">
			<div class="col-md-12">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<h4 style="font-family: 'Varela Round'">Welcome to Fact
							Extraction Web App</h4>
					</div>
				</nav>
			</div>
		</div>

		<div class="row">
			<div id="searchbar" class="col-md-12">
				<div class="input-group mb-3">
					<input id="searchText" type="text" class="form-control"
						placeholder="Event Search" aria-label="Event Search"
						aria-describedby="basic-addon2">
					<div class="input-group-append">
						<button type="button" class="btn btn-primary"
							onclick="searchButtonPressed()">Look Up!</button>
					</div>
				</div>
			</div>

		</div>

		<br />


		<div id="eventTypeOptionWrapper">
			<form class="input-group mb-3">
				<div class="form-control">
					<input type="radio" name="eventTypeOption" id="filterNat"
						value="natural" />Natural
				</div>
				<div class="form-control">
					<input type="radio" name="eventTypeOption" id="filterArt"
						value="artificial" />Artificial
				</div>
				<div class="form-control">
					<input type="radio" name="eventTypeOption" id="filterAll"
						value="all" checked />All
				</div>
			</form>
		</div>

		<br />


		<div id="sortingOptionWrapper">
			<form class="input-group mb-3">
				<div class="form-control">
					<input type="radio" name="sortingOption" id="srtName" value="Name"
						checked />Event Title
				</div>
				<div class="form-control">
					<input type="radio" name="sortingOption" id="srtStart"
						value="Start" />Start Time
				</div>
				<div class="form-control">
					<input type="radio" name="sortingOption" id="srtEnd" value="End" />End
					Time
				</div>
				<div class="input-group-append">
					<button type="button" class="btn btn-primary"
						onclick="eventSorting()">Sort Results</button>
				</div>
			</form>
		</div>


		<div id="resultsDivScroll" style="display: inline-table; width: 100%;">
			<div id="content">
				<table class="table table-striped" id="dvTable"
					style="display: none; width: 100%;">
					<thead>
						<tr>
							<th>Event Results</th>
						</tr>
						<tr id="resultTableRow" style="display: none; width: 100%;">
							<td>
								<div class="divTable">
									<div class="divTableBody">
										<div class="divTableRow">
											<div class="divTableCell">Event Title:</div>
											<div class="divTableCell">message.eventTitle</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">Event Type:</div>
											<div class="divTableCell">message.eventType</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">Event Query Keywords:</div>
											<div class="divTableCell">message.eventQuery</div>
										</div>

										<div class="divTableRow">
											<div class="divTableCell">Event Occurred on:</div>
											<div class="divTableCell">message.eventStart</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">Event Finished on:</div>
											<div class="divTableCell">message.eventEnd</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">Important Figures Commented:</div>
											<div class="divTableCell">message.eventFigs</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">More at:</div>
											<div class="divTableCell">
												<a>message.eventDes</a>
											</div>
										</div>
										<div class="divTableRow">
											<div class="divTableCell">Event Summary:</div>
											<div class="divTableCell">
												<a>message.eventDes</a>
											</div>
										</div>
									</div>
								</div> <!-- DivTable.com -->
							</td>
						</tr>
					</thead>
					<tbody id="resultTableBody">

					</tbody>
				</table>

			</div>
		</div>

	</div>

	<script type="text/javascript">

		function eventSorting()"""),format.raw/*173.26*/("""{"""),format.raw/*173.27*/("""
			"""),format.raw/*174.4*/("""var sortBy="End";
			if (document.getElementById('srtName').checked) """),format.raw/*175.52*/("""{"""),format.raw/*175.53*/("""
				"""),format.raw/*176.5*/("""sortBy = document.getElementById('srtName').value;
				"""),format.raw/*177.5*/("""}"""),format.raw/*177.6*/("""
			"""),format.raw/*178.4*/("""else if (document.getElementById('srtStart').checked) """),format.raw/*178.58*/("""{"""),format.raw/*178.59*/("""
				"""),format.raw/*179.5*/("""sortBy = document.getElementById('srtStart').value;
				"""),format.raw/*180.5*/("""}"""),format.raw/*180.6*/("""
			"""),format.raw/*181.4*/("""else if (document.getElementById('srtEnd').checked) """),format.raw/*181.56*/("""{"""),format.raw/*181.57*/("""
				"""),format.raw/*182.5*/("""sortBy = document.getElementById('srtEnd').value;
				"""),format.raw/*183.5*/("""}"""),format.raw/*183.6*/("""

			"""),format.raw/*185.4*/("""var toSort = document.getElementById("resultTableBody").children;
			toSort = Array.prototype.slice.call(toSort, 0);
			toSort.sort(function(a, b) """),format.raw/*187.31*/("""{"""),format.raw/*187.32*/("""
				"""),format.raw/*188.5*/("""var compareResult;
				if(sortBy.localeCompare("Name")==0)"""),format.raw/*189.40*/("""{"""),format.raw/*189.41*/("""
					"""),format.raw/*190.6*/("""var aord = a.children[0].children[0].children[0].children[0].children[1].innerHTML;
					var bord = b.children[0].children[0].children[0].children[0].children[1].innerHTML;

					compareResult = aord.localeCompare(bord);

				"""),format.raw/*195.5*/("""}"""),format.raw/*195.6*/("""else if(sortBy.localeCompare("Start")==0)"""),format.raw/*195.47*/("""{"""),format.raw/*195.48*/("""

					"""),format.raw/*197.6*/("""var aDate = a.children[0].children[0].children[0].children[3].children[1].innerHTML.split("/");
					var aEPOCH = new Date(aDate[2], aDate[1], aDate[0]).getTime() / 1000;
				    var aord = +aEPOCH;

				    var bDate = b.children[0].children[0].children[0].children[3].children[1].innerHTML.split("/");
					var bEPOCH = new Date(bDate[2], bDate[1], bDate[0]).getTime() / 1000;

				    var bord = +bEPOCH;
				    compareResult = aord > bord;
				"""),format.raw/*206.5*/("""}"""),format.raw/*206.6*/("""else if(sortBy.localeCompare("End")==0)"""),format.raw/*206.45*/("""{"""),format.raw/*206.46*/("""
					"""),format.raw/*207.6*/("""var aDate = a.children[0].children[0].children[0].children[4].children[1].innerHTML.split("/");
					var aEPOCH = new Date(aDate[2], aDate[1], aDate[0]).getTime() / 1000;
				    var aord = +aEPOCH;

				    var bDate = b.children[0].children[0].children[0].children[4].children[1].innerHTML.split("/");
					var bEPOCH = new Date(bDate[2], bDate[1], bDate[0]).getTime() / 1000;

				    var bord = +bEPOCH;
				    compareResult = aord > bord;
				"""),format.raw/*216.5*/("""}"""),format.raw/*216.6*/("""
			    """),format.raw/*217.8*/("""return compareResult;
			"""),format.raw/*218.4*/("""}"""),format.raw/*218.5*/(""");
			var tableBody = document.getElementById("resultTableBody");
			while (tableBody.firstChild) """),format.raw/*220.33*/("""{"""),format.raw/*220.34*/("""
				"""),format.raw/*221.5*/("""tableBody.removeChild(tableBody.firstChild);
			"""),format.raw/*222.4*/("""}"""),format.raw/*222.5*/("""
			"""),format.raw/*223.4*/("""for(var i = 0, l = toSort.length; i < l; i++) """),format.raw/*223.50*/("""{"""),format.raw/*223.51*/("""
				"""),format.raw/*224.5*/("""tableBody.appendChild(toSort[i]);
			"""),format.raw/*225.4*/("""}"""),format.raw/*225.5*/("""
		"""),format.raw/*226.3*/("""}"""),format.raw/*226.4*/("""


		"""),format.raw/*229.3*/("""function addToResult(message,entities) """),format.raw/*229.42*/("""{"""),format.raw/*229.43*/("""
			"""),format.raw/*230.4*/("""var filterBy="all";
			if (document.getElementById('filterNat').checked) """),format.raw/*231.54*/("""{"""),format.raw/*231.55*/("""
				"""),format.raw/*232.5*/("""filterBy = document.getElementById('filterNat').value;
				"""),format.raw/*233.5*/("""}"""),format.raw/*233.6*/("""
			"""),format.raw/*234.4*/("""else if (document.getElementById('filterArt').checked) """),format.raw/*234.59*/("""{"""),format.raw/*234.60*/("""
				"""),format.raw/*235.5*/("""filterBy = document.getElementById('filterArt').value;
				"""),format.raw/*236.5*/("""}"""),format.raw/*236.6*/("""
			"""),format.raw/*237.4*/("""else if (document.getElementById('filterAll').checked) """),format.raw/*237.59*/("""{"""),format.raw/*237.60*/("""
				"""),format.raw/*238.5*/("""filterBy = document.getElementById('filterAll').value;
				"""),format.raw/*239.5*/("""}"""),format.raw/*239.6*/("""

			"""),format.raw/*241.4*/("""var dvTable = document.getElementById("dvTable");
			dvTable.style.display = "inline";

			//Create a HTML Table element.
			var tableBody = document.getElementById("resultTableBody");
			var tableRow = document.getElementById("resultTableRow");
			var newRow = tableRow.cloneNode(true);

			var utcStart = message.eventStart;
			var utcEnd = message.eventEnd;
			var dStart = new Date(0); // The 0 there is the key, which sets the date to the epoch
			var dEnd = new Date(0); // The 0 there is the key, which sets the date to the epoch
			dStart.setUTCSeconds(utcStart);
			dEnd.setUTCSeconds(utcEnd);

			newRow.style.display = "inline-table";
			newRow.children[0].children[0].children[0].children[0].children[1].innerHTML = message.eventTitle.replace(/\b\w/g, l => l.toUpperCase());
			newRow.children[0].children[0].children[0].children[1].children[1].innerHTML = message.eventType.replace(/\b\w/g, l => l.toUpperCase());
			newRow.children[0].children[0].children[0].children[2].children[1].innerHTML = message.eventQuery.replace(/\b\w/g, l => l.toUpperCase());

			newRow.children[0].children[0].children[0].children[3].children[1].innerHTML = dStart.toLocaleDateString();
			newRow.children[0].children[0].children[0].children[4].children[1].innerHTML = dEnd.toLocaleDateString();
			if(entities != null)newRow.children[0].children[0].children[0].children[5].children[1].innerHTML = entities;
			newRow.children[0].children[0].children[0].children[6].children[1].children[0].setAttribute("href", message.eventDes);
			newRow.children[0].children[0].children[0].children[6].children[1].children[0].innerHTML = message.eventDes;
			newRow.children[0].children[0].children[0].children[7].children[1].children[0].setAttribute("href", "/eventDetails?eventId="+message.eventId+"");
			newRow.children[0].children[0].children[0].children[7].children[1].children[0].innerHTML = "Go to Page";

			//check filter
			if(filterBy.localeCompare("natural")==0)"""),format.raw/*270.44*/("""{"""),format.raw/*270.45*/("""
				"""),format.raw/*271.5*/("""var acceptedTypes = "storm earthquake fire flood";
				if(acceptedTypes.includes(message.eventType))"""),format.raw/*272.50*/("""{"""),format.raw/*272.51*/("""
					"""),format.raw/*273.6*/("""tableBody.appendChild(newRow);
				"""),format.raw/*274.5*/("""}"""),format.raw/*274.6*/("""

			"""),format.raw/*276.4*/("""}"""),format.raw/*276.5*/("""else if(filterBy.localeCompare("artificial")==0)"""),format.raw/*276.53*/("""{"""),format.raw/*276.54*/("""
				"""),format.raw/*277.5*/("""var acceptedTypes = "accident shooting bombing";
				if(acceptedTypes.includes(message.eventType))"""),format.raw/*278.50*/("""{"""),format.raw/*278.51*/("""
					"""),format.raw/*279.6*/("""tableBody.appendChild(newRow);
				"""),format.raw/*280.5*/("""}"""),format.raw/*280.6*/("""
			"""),format.raw/*281.4*/("""}"""),format.raw/*281.5*/("""else if(filterBy.localeCompare("all")==0)"""),format.raw/*281.46*/("""{"""),format.raw/*281.47*/("""
				"""),format.raw/*282.5*/("""tableBody.appendChild(newRow);
			"""),format.raw/*283.4*/("""}"""),format.raw/*283.5*/("""

		"""),format.raw/*285.3*/("""}"""),format.raw/*285.4*/("""

		"""),format.raw/*287.3*/("""function initalize() """),format.raw/*287.24*/("""{"""),format.raw/*287.25*/("""

			"""),format.raw/*289.4*/("""openWebSocketConnection();
		"""),format.raw/*290.3*/("""}"""),format.raw/*290.4*/("""

		"""),format.raw/*292.3*/("""var ws; // websocket to the backend

		// ######################################################################################
		// Top level control logic
		// ######################################################################################

		function openWebSocketConnection() """),format.raw/*298.38*/("""{"""),format.raw/*298.39*/("""

			"""),format.raw/*300.4*/("""var wsURL = document.getElementById("myBody")
					.getAttribute("wsdata");

			ws = new WebSocket(wsURL);
			ws.onmessage = function(event) """),format.raw/*304.35*/("""{"""),format.raw/*304.36*/("""
				"""),format.raw/*305.5*/("""var message;
				message = JSON.parse(event.data);
				switch (message.messageType) """),format.raw/*307.34*/("""{"""),format.raw/*307.35*/("""
				case "init":
					break;
				case "statusMessage":
					alert(message.status);
					break;
				case "queryResult": """),format.raw/*313.25*/("""{"""),format.raw/*313.26*/("""
					"""),format.raw/*314.6*/("""var val = JSON.parse(message["nodeData"]);
					var entities = null;
					if(message["entities"]!=null)
						entities = JSON.parse(message["entities"]);

					console.log(val);
					console.log(entities);
					addToResult(val,entities);
				"""),format.raw/*322.5*/("""}"""),format.raw/*322.6*/("""
					"""),format.raw/*323.6*/("""break;
				default:
					return console.log(message);
				"""),format.raw/*326.5*/("""}"""),format.raw/*326.6*/("""
			"""),format.raw/*327.4*/("""}"""),format.raw/*327.5*/(""";
			ws.onclose = function(event) """),format.raw/*328.33*/("""{"""),format.raw/*328.34*/("""
				"""),format.raw/*329.5*/("""// do nothing
			"""),format.raw/*330.4*/("""}"""),format.raw/*330.5*/(""";
		"""),format.raw/*331.3*/("""}"""),format.raw/*331.4*/("""

		"""),format.raw/*333.3*/("""function searchButtonPressed() """),format.raw/*333.34*/("""{"""),format.raw/*333.35*/("""
			"""),format.raw/*334.4*/("""var searchText = document.getElementById("searchText").value.toLowerCase();
			var tableBody = document.getElementById("resultTableBody");
			while (tableBody.firstChild) """),format.raw/*336.33*/("""{"""),format.raw/*336.34*/("""
				"""),format.raw/*337.5*/("""tableBody.removeChild(tableBody.firstChild);
			"""),format.raw/*338.4*/("""}"""),format.raw/*338.5*/("""
			"""),format.raw/*339.4*/("""ws.send(JSON.stringify("""),format.raw/*339.27*/("""{"""),format.raw/*339.28*/("""
				"""),format.raw/*340.5*/("""messageType : "doSearch",
				query : searchText
			"""),format.raw/*342.4*/("""}"""),format.raw/*342.5*/("""));
		"""),format.raw/*343.3*/("""}"""),format.raw/*343.4*/("""


	"""),format.raw/*346.2*/("""</script>



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
                  SOURCE: /media/nicky/STUDY/3_GU/4thYr/PRJ/factfinderbackend/Nicky_Exp/app/views/index.scala.html
                  HASH: 6d74e4a4a61ba74f6344ec6ea6ed596c3d82b8bf
                  MATRIX: 790->20|858->0|903->61|931->63|1043->149|1057->155|1127->205|1203->255|1217->261|1281->305|1358->355|1373->361|1457->423|1534->473|1549->479|1615->524|1899->781|1914->787|1981->833|2035->860|2050->866|2124->919|2178->946|2193->952|2263->1001|2317->1028|2332->1034|2396->1077|2595->1249|2610->1255|2675->1299|6957->5552|6987->5553|7020->5558|7119->5628|7149->5629|7183->5635|7267->5691|7296->5692|7329->5697|7412->5751|7442->5752|7476->5758|7561->5815|7590->5816|7623->5821|7704->5873|7734->5874|7768->5880|7851->5935|7880->5936|7915->5943|8093->6092|8123->6093|8157->6099|8245->6158|8275->6159|8310->6166|8569->6397|8598->6398|8668->6439|8698->6440|8735->6449|9221->6907|9250->6908|9318->6947|9348->6948|9383->6955|9869->7413|9898->7414|9935->7423|9989->7449|10018->7450|10147->7550|10177->7551|10211->7557|10288->7606|10317->7607|10350->7612|10425->7658|10455->7659|10489->7665|10555->7703|10584->7704|10616->7708|10645->7709|10681->7717|10749->7756|10779->7757|10812->7762|10915->7836|10945->7837|10979->7843|11067->7903|11096->7904|11129->7909|11213->7964|11243->7965|11277->7971|11365->8031|11394->8032|11427->8037|11511->8092|11541->8093|11575->8099|11663->8159|11692->8160|11727->8167|13739->10150|13769->10151|13803->10157|13933->10258|13963->10259|13998->10266|14062->10302|14091->10303|14126->10310|14155->10311|14232->10359|14262->10360|14296->10366|14424->10465|14454->10466|14489->10473|14553->10509|14582->10510|14615->10515|14644->10516|14714->10557|14744->10558|14778->10564|14841->10599|14870->10600|14904->10606|14933->10607|14967->10613|15017->10634|15047->10635|15082->10642|15140->10672|15169->10673|15203->10679|15526->10973|15556->10974|15591->10981|15765->11126|15795->11127|15829->11133|15944->11219|15974->11220|16129->11346|16159->11347|16194->11354|16472->11604|16501->11605|16536->11612|16625->11673|16654->11674|16687->11679|16716->11680|16780->11715|16810->11716|16844->11722|16890->11740|16919->11741|16952->11746|16981->11747|17015->11753|17075->11784|17105->11785|17138->11790|17340->11963|17370->11964|17404->11970|17481->12019|17510->12020|17543->12025|17595->12048|17625->12049|17659->12055|17741->12109|17770->12110|17805->12117|17834->12118|17869->12125
                  LINES: 24->3|27->1|29->4|30->5|33->8|33->8|33->8|34->9|34->9|34->9|35->10|35->10|35->10|36->11|36->11|36->11|45->20|45->20|45->20|46->21|46->21|46->21|47->22|47->22|47->22|48->23|48->23|48->23|55->30|55->30|55->30|198->173|198->173|199->174|200->175|200->175|201->176|202->177|202->177|203->178|203->178|203->178|204->179|205->180|205->180|206->181|206->181|206->181|207->182|208->183|208->183|210->185|212->187|212->187|213->188|214->189|214->189|215->190|220->195|220->195|220->195|220->195|222->197|231->206|231->206|231->206|231->206|232->207|241->216|241->216|242->217|243->218|243->218|245->220|245->220|246->221|247->222|247->222|248->223|248->223|248->223|249->224|250->225|250->225|251->226|251->226|254->229|254->229|254->229|255->230|256->231|256->231|257->232|258->233|258->233|259->234|259->234|259->234|260->235|261->236|261->236|262->237|262->237|262->237|263->238|264->239|264->239|266->241|295->270|295->270|296->271|297->272|297->272|298->273|299->274|299->274|301->276|301->276|301->276|301->276|302->277|303->278|303->278|304->279|305->280|305->280|306->281|306->281|306->281|306->281|307->282|308->283|308->283|310->285|310->285|312->287|312->287|312->287|314->289|315->290|315->290|317->292|323->298|323->298|325->300|329->304|329->304|330->305|332->307|332->307|338->313|338->313|339->314|347->322|347->322|348->323|351->326|351->326|352->327|352->327|353->328|353->328|354->329|355->330|355->330|356->331|356->331|358->333|358->333|358->333|359->334|361->336|361->336|362->337|363->338|363->338|364->339|364->339|364->339|365->340|367->342|367->342|368->343|368->343|371->346
                  -- GENERATED --
              */
          