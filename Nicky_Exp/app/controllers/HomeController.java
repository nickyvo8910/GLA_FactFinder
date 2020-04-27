package controllers;

import actors.WebSocketActor;
import views.*;
import play.*;
import play.mvc.*;
import akka.actor.ActorSystem;
import akka.stream.*;
import play.libs.streams.ActorFlow;

import javax.inject.Inject;


public class HomeController extends Controller {

	private final ActorSystem actorSystem;
    private final Materializer materializer;

	@Inject
	public HomeController(ActorSystem actorSystem, Materializer materializer) {
		this.actorSystem = actorSystem;
        this.materializer = materializer;
	}

	public Result index() {
		return ok(views.html.index.render());
    }
	
	public Result nlpinit() {
		return ok(views.html.nlpinit.render());
	}
	
	public Result eventDetails() {
		return ok(views.html.eventSummary.render());
    }

	public WebSocket socket() {
        return WebSocket.Json.accept(request ->
                ActorFlow.actorRef(WebSocketActor::props,
                    actorSystem, materializer
                )
        );
    }
}
