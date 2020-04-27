
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

object nlpinit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

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


<link href="https://fonts.googleapis.com/css?family=Varela+Round"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<script src=""""),_display_(/*19.15*/routes/*19.21*/.Assets.versioned("javascripts/jquery.min.js")),format.raw/*19.67*/(""""></script>
<script src=""""),_display_(/*20.15*/routes/*20.21*/.Assets.versioned("javascripts/jquery.easing.min.js")),format.raw/*20.74*/(""""></script>
<script src=""""),_display_(/*21.15*/routes/*21.21*/.Assets.versioned("javascripts/bootstrap.min.js")),format.raw/*21.70*/(""""></script>
<script src=""""),_display_(/*22.15*/routes/*22.21*/.Assets.versioned("javascripts/all.min.js")),format.raw/*22.64*/(""""></script>

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
							Extraction Web App / Stanford NLP Pipeline</h4>
					</div>
				</nav>
			</div>
		</div>





		<div id="resultsDiv"></div>

		<div class="row">
			<div id="inputForm" class="col-md-12">
				<div class="input-group mb-3">
					<!-- <input id="inputFile" type="text" class="form-control" placeholder="Link to news acticle" aria-label="Article link" aria-describedby="basic-addon2"> -->
					<div class="input-group-append">
						<button type="button" class="btn btn-primary"
							onclick="processButtonPressed()">Proceed NPL pipeline!</button>
					</div>
				</div>
			</div>

		</div>



	</div>

	<script type="text/javascript">
			function initalize() """),format.raw/*71.25*/("""{"""),format.raw/*71.26*/("""

				"""),format.raw/*73.5*/("""openWebSocketConnection();


			"""),format.raw/*76.4*/("""}"""),format.raw/*76.5*/("""

			"""),format.raw/*78.4*/("""var ws; // websocket to the backend

			// ######################################################################################
			// Top level control logic
			// ######################################################################################

		    function openWebSocketConnection() """),format.raw/*84.42*/("""{"""),format.raw/*84.43*/("""

		    	   """),format.raw/*86.11*/("""var wsURL = document.getElementById("myBody").getAttribute("wsdata");

		           ws = new WebSocket(wsURL);
		           ws.onmessage = function (event) """),format.raw/*89.46*/("""{"""),format.raw/*89.47*/("""
		               """),format.raw/*90.18*/("""var message;
		               message = JSON.parse(event.data);
		               switch (message.messageType) """),format.raw/*92.47*/("""{"""),format.raw/*92.48*/("""
		                   case "init":
		               		   break;
		                   case "statusMessage":
		                	   alert(message.status);
		                	   break;
		                   default:
		                       return console.log(message);
		               """),format.raw/*100.18*/("""}"""),format.raw/*100.19*/("""
		           """),format.raw/*101.14*/("""}"""),format.raw/*101.15*/(""";
		           ws.onclose = function (event) """),format.raw/*102.44*/("""{"""),format.raw/*102.45*/("""
		        	   """),format.raw/*103.15*/("""// do nothing
		           """),format.raw/*104.14*/("""}"""),format.raw/*104.15*/(""";
		    """),format.raw/*105.7*/("""}"""),format.raw/*105.8*/("""


			"""),format.raw/*108.4*/("""function processButtonPressed() """),format.raw/*108.36*/("""{"""),format.raw/*108.37*/("""

				"""),format.raw/*110.5*/("""ws.send(JSON.stringify("""),format.raw/*110.28*/("""{"""),format.raw/*110.29*/("""
                    """),format.raw/*111.21*/("""messageType: "doNLP",
                    query: "nothing"
                """),format.raw/*113.17*/("""}"""),format.raw/*113.18*/("""));
			"""),format.raw/*114.4*/("""}"""),format.raw/*114.5*/("""


		    """),format.raw/*117.7*/("""</script>



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
                  SOURCE: /media/nicky/STUDY/3_GU/4thYr/PRJ/factfinderbackend/Nicky_Exp/app/views/nlpinit.scala.html
                  HASH: 555aa8142582883ee8e0a31fc5d20464e4f20724
                  MATRIX: 792->20|860->0|905->61|933->63|1045->149|1059->155|1129->205|1205->255|1219->261|1283->305|1360->355|1375->361|1459->423|1743->680|1758->686|1825->732|1879->759|1894->765|1968->818|2022->845|2037->851|2107->900|2161->927|2176->933|2240->976|2441->1150|2456->1156|2521->1200|3562->2213|3591->2214|3626->2222|3688->2257|3716->2258|3750->2265|4079->2566|4108->2567|4150->2581|4337->2740|4366->2741|4413->2760|4553->2872|4582->2873|4901->3163|4931->3164|4975->3179|5005->3180|5080->3226|5110->3227|5155->3243|5212->3271|5242->3272|5279->3281|5308->3282|5345->3291|5406->3323|5436->3324|5472->3332|5524->3355|5554->3356|5605->3378|5711->3455|5741->3456|5777->3464|5806->3465|5846->3477
                  LINES: 24->3|27->1|29->4|30->5|33->8|33->8|33->8|34->9|34->9|34->9|35->10|35->10|35->10|44->19|44->19|44->19|45->20|45->20|45->20|46->21|46->21|46->21|47->22|47->22|47->22|55->30|55->30|55->30|96->71|96->71|98->73|101->76|101->76|103->78|109->84|109->84|111->86|114->89|114->89|115->90|117->92|117->92|125->100|125->100|126->101|126->101|127->102|127->102|128->103|129->104|129->104|130->105|130->105|133->108|133->108|133->108|135->110|135->110|135->110|136->111|138->113|138->113|139->114|139->114|142->117
                  -- GENERATED --
              */
          