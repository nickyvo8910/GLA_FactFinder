/**
 *
 */
package controllers;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.sun.org.apache.xpath.internal.operations.And;

import models.Event;
import models.TrecDoc;

public class DataController {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private CodecRegistry pojoCodecRegistry;
	private MongoCollection<Document> testCollection;
	private MongoCollection<Event> eventCollection;
	private MongoCollection<TrecDoc> trecDocCollection;

	public DataController() {
		// Constructor
		ConnectionString conString = new ConnectionString("mongodb://nickyvo:admin123@"
				+ "cluster0-shard-00-00-gmg8s.mongodb.net:27017," + "cluster0-shard-00-01-gmg8s.mongodb.net:27017,"
				+ "cluster0-shard-00-02-gmg8s.mongodb.net:27017/" + "test?" + "streamType=netty&ssl=true"
				+ "&replicaSet=Cluster0-shard-0" + "&authSource=admin" + "&retryWrites=true");

		// Make connection

		mongoClient = MongoClients.create("mongodb://localhost");
		// mongoClient = MongoClients.create(conString);

		// create codec registry for POJOs

		pojoCodecRegistry = fromRegistries(MongoClients.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		database = mongoClient.getDatabase("FactExt").withCodecRegistry(pojoCodecRegistry);

		testCollection = database.getCollection("test", Document.class);

		eventCollection = database.getCollection("events", Event.class);

		trecDocCollection = database.getCollection("trecDocs", TrecDoc.class);


	}
	// Helper functions

	public void closeConn() {
		mongoClient.close();
	}

	// DB TEST

	// Query Filters
	// now use a query to get 1 document out
	public List<Document> searchDocument(String query) throws InterruptedException {
		List<Document> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		testCollection.find(eq("eventTitle", query)).into(docs, new SingleResultCallback<List<Document>>() {
			@Override
			public void onResult(final List<Document> result, final Throwable t) {
				System.out.println("Found Documents: #" + result.size());

				queryLatch.countDown();
			}
		});
		queryLatch.await();
		return docs;
	}

	// Event Collection
	// Insert
	public void insertEvent(Event event) throws InterruptedException {

		System.out.println("Original Event Model: " + event);
		CountDownLatch queryLatch = new CountDownLatch(1);
		eventCollection.insertOne(event, new SingleResultCallback<Void>() {
			@Override
			public void onResult(final Void result, final Throwable t) {
				// Person will now have an ObjectId
				System.out.println("Mutated Event Model: " + event);
				System.out.println("Inserted!");
				queryLatch.countDown();
			}
		});
		queryLatch.await();
	}

	// View
	public List<Event> searchEvent(String query) throws InterruptedException {
		List<Event> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		eventCollection.find(eq("eventQuery", java.util.regex.Pattern.compile(query))).into(docs,
				new SingleResultCallback<List<Event>>() {
					@Override
					public void onResult(final List<Event> result, final Throwable t) {
						System.out.println("Found Documents: #" + result.size());

						queryLatch.countDown();
					}
				});
		queryLatch.await();
		return docs;
	}

	public List<Event> searchEventByType(String query) throws InterruptedException {
		List<Event> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		eventCollection.find(eq("eventType", query)).into(docs, new SingleResultCallback<List<Event>>() {
			@Override
			public void onResult(final List<Event> result, final Throwable t) {
				System.out.println("Found Documents: #" + result.size());

				queryLatch.countDown();
			}
		});
		queryLatch.await();
		return docs;
	}

	@SuppressWarnings("deprecation")
	public List<Event> searchEventByEPOCH(Long inEPOCH) throws InterruptedException {
		System.out.println("inEPOCH: " + inEPOCH);
		Date date = new Date(0);
		date.setSeconds(inEPOCH.intValue());
		System.out.println("inDate =" + date.toString() + " Component: " + date.getYear() + "-" + date.getTime());
		Date startDate = new Date(date.getYear(), 1, 1);
		Date endDate = new Date(date.getYear(), 12, 31);

		List<Event> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);

		System.out.println(startDate.getTime());
		System.out.println(endDate.getTime());

		eventCollection
				.find(and(gte("eventStart", startDate.getTime() / 1000), lte("eventStart", endDate.getTime() / 1000)))
				.into(docs, new SingleResultCallback<List<Event>>() {
					@Override
					public void onResult(final List<Event> result, final Throwable t) {
						System.out.println("Found Documents: #" + result.size());

						queryLatch.countDown();
					}
				});
		queryLatch.await();
		return docs;
	}

	public Event getEventById(int eventId) throws InterruptedException {
		List<Event> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		eventCollection.find(eq("eventId", eventId)).into(docs, new SingleResultCallback<List<Event>>() {
			@Override
			public void onResult(final List<Event> result, final Throwable t) {
				System.out.println(result);
				System.out.println("Found Documents: #" + result.size());

				queryLatch.countDown();
			}
		});
		queryLatch.await();
		if (docs.size() > 0)
			return docs.get(0);
		else
			return null;
	}

	// Update
	public void updateEventDetails(Event newEvent, int oldEventId) throws InterruptedException {
		CountDownLatch queryLatch = new CountDownLatch(1);
		eventCollection.updateOne(eq("eventId", oldEventId), combine(set("eventDeaths", newEvent.getEventDeaths()),
				set("eventEntities", newEvent.getEventEntities()), set("eventInjuryMap", newEvent.getEventInjuryMap()),
				set("eventDeathMap", newEvent.getEventDeathMap()), set("eventInjuries", newEvent.getEventInjuries())),
				new SingleResultCallback<UpdateResult>() {
					@Override
					public void onResult(final UpdateResult result, final Throwable t) {
						if(result!=null) System.out.println("Updated [" + result.getModifiedCount() + "] event.");
						queryLatch.countDown();
					}
				});
//		queryLatch.await();
	}

	// TrecDoc Collection
	// Insert
	public void insertTrecDoc(TrecDoc newTrecDoc) throws InterruptedException {

		CountDownLatch queryLatch = new CountDownLatch(1);
		trecDocCollection.insertOne(newTrecDoc, new SingleResultCallback<Void>() {
			@Override
			public void onResult(final Void result, final Throwable t) {
				System.out.println("Inserted!");
				queryLatch.countDown();
			}
		});
		queryLatch.await();
	}

	// View
	public List<TrecDoc> searchTrecDoc(String query) throws InterruptedException {
		List<TrecDoc> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		trecDocCollection.find(eq("docTitle", java.util.regex.Pattern.compile(query))).into(docs,
				new SingleResultCallback<List<TrecDoc>>() {
					@Override
					public void onResult(final List<TrecDoc> result, final Throwable t) {
						System.out.println("Found Documents: #" + result.size());

						queryLatch.countDown();
					}
				});
		queryLatch.await();
		return docs;
	}

	public List<TrecDoc> searchTrecDocByStreamId(int streamId) throws InterruptedException {
		List<TrecDoc> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		trecDocCollection.find(eq("trecStreamID", "" + streamId)).into(docs, new SingleResultCallback<List<TrecDoc>>() {
			@Override
			public void onResult(final List<TrecDoc> result, final Throwable t) {
				System.out.println("Found Documents: #" + result.size());

				queryLatch.countDown();
			}
		});
		queryLatch.await();
		return docs;
	}

	public TrecDoc searchTrecDocByDocId(String docId) throws InterruptedException {
		List<TrecDoc> docs = new ArrayList<>();
		CountDownLatch queryLatch = new CountDownLatch(1);
		trecDocCollection.find(eq("_id", new ObjectId(docId))).into(docs, new SingleResultCallback<List<TrecDoc>>() {
			@Override
			public void onResult(final List<TrecDoc> result, final Throwable t) {
				System.out.println(result);
				System.out.println("Found Documents: #" + result.size());

				queryLatch.countDown();
			}
		});
		queryLatch.await();
		if (docs.size() > 0)
			return docs.get(0);
		else
			return null;
	}


	public static void main(String[] args) {
		try {
			DataController controller = new DataController();
			controller.getEventById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
