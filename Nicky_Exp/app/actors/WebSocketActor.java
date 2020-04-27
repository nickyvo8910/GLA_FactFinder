package actors;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import controllers.DataController;
import controllers.IRPipeline;
import models.Event;
import play.libs.Json;
import scala.Array;

public class WebSocketActor extends AbstractActor {

	public static Props props(ActorRef out) {
		return Props.create(WebSocketActor.class, out);
	}

	private final ActorRef out;
	ObjectMapper mapper = new ObjectMapper();

	// user-specific data
	List<String> cdps = null;
	ObjectNode preferences = null;
	List<String> scoredCDPs = null;

//Default constructor for init pageLoad
	public WebSocketActor(ActorRef out) {
		this.out = out;

		ObjectNode msg = Json.newObject();
		msg.put("messageType", "init");
		out.tell(msg, self());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(JsonNode.class, message -> {

			String messageType = message.get("messageType").asText();
			System.out.println(messageType);
			switch (messageType) {
			case "doSearch": {

				try {
					String query = message.get("query").asText();
					DataController datCon = new DataController();
					System.out.println("Query:" + query);
					// do something here

					int i = 0;
					List<Event> queryRS;
					queryRS = datCon.searchEvent(query);
					for (Event event : queryRS) {
						ObjectNode msg1 = Json.newObject();
						msg1.put("messageType", "queryResult");
						System.out.println("Doc: " + i + event);
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
						Gson gson = new Gson();
						String json = gson.toJson(event);
						msg1.put("nodeData", json);
						if (event.getEventEntities() != null)
							msg1.put("entities", gson.toJson(event.getEventEntities().keySet()));
						i++;
						out.tell(msg1, self());
					}
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
					e.printStackTrace();
				}

			}
				break;
			case "doNLP": {

				try {
					IRPipeline irPipeline = new IRPipeline();
					irPipeline.doPipeline();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
				break;
			case "getEventDetails": {
				// do something here

				// Get entities and mentions
				// Get Deaths/Casulties
				// Get DOT/COT
				// Get event Level

				try {
					System.out.println("Start getting event details");
					String query = message.get("query").asText();
					DataController datCon = new DataController();
					System.out.println("Event Query:" + query);
					// do something here

					int i = 0;
					Event queryRS = null;
					queryRS = datCon.getEventById(Integer.parseInt(query));
					if (queryRS != null) {
						ObjectNode msg1 = Json.newObject();
						msg1.put("messageType", "queryResult");
						System.out.println("Event : " + i + queryRS);
						System.out.println(queryRS.getEventInjuryMap());
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
						Gson gson = new Gson();
						String json = gson.toJson(queryRS);
						msg1.put("nodeData", json);
						i++;
						out.tell(msg1, self());

						List<Event> sameCategoryEvents = getSameCategoryEvents(queryRS);
						List<Event> sameYearEvents = getSameYearEvents(queryRS);

						ObjectNode sameCatMsg = Json.newObject();
						sameCatMsg.put("messageType", "sameCatMsg");
						sameCatMsg.put("sameCatEvents", gson.toJson(sameCategoryEvents));
						out.tell(sameCatMsg, self());

						ObjectNode sameYrMsg = Json.newObject();
						sameYrMsg.put("messageType", "sameYrMsg");
						sameYrMsg.put("sameYrEvents", gson.toJson(sameYearEvents));
						out.tell(sameYrMsg, self());

					}
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
					e.printStackTrace();
				} finally {
					System.out.println("DOING GET");
				}
			}
				break;
			}

		}).build();
	}

	private List<Event> getSameCategoryEvents(Event inEvent) throws InterruptedException {
		DataController datCon = new DataController();
		return datCon.searchEventByType(inEvent.getEventType());
	}

	private List<Event> getSameYearEvents(Event inEvent) throws InterruptedException {
		DataController datCon = new DataController();
		return datCon.searchEventByEPOCH(inEvent.getEventStart());
	}

	@Override
	public void postStop() throws Exception {
		// do shutdown
		out.tell(PoisonPill.getInstance(), self());
	}
}
