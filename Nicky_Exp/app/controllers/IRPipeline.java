/**
 *
 */
package controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.text.WordUtils;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.terrier.indexing.Document;
import org.terrier.indexing.TRECCollection;
import org.xml.sax.SAXException;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NumericCompositeValueAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import models.Event;
import models.TrecDoc;

/**
 * @author nicky
 *
 */
public class IRPipeline {
	// -Dterrier.home=/media/nicky/STUDY/3_GU/4thYr/PRJ/RES/Nicky_Exp/
	private static DataController datCon = new DataController();
	private static String reportingWords = "said told stated mentioned";
	private static String injuryWords = "injuries injury injured injures wounds wound wounded";
	private static String naturalDamageWords = "affect affects afffecting affected damage damages damaging damaged break broke broken breaking tear tears tearing torn destroy destroying destroyed ";
	private static String casualtyWords = "dead deaths death died casualties casualty kill killing kills killed fatalities fatal fataling";
	private static String naturalEvents = "storm earthquake fire flood";

	/**
	 *
	 */
	public IRPipeline() {
		datCon = new DataController();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();

			IRPipeline irPipeline = new IRPipeline();
			irPipeline.doIngestion();
			System.out.println("DONE Ingestion");
			irPipeline.doPipeline();
			startTime = System.currentTimeMillis() - startTime;
			System.out.println("____________________________________________________________");
			System.out.println(" The test took " + startTime + " milliseconds");

		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doIngestion() throws ParserConfigurationException, SAXException, CodecConfigurationException, InterruptedException {
		String eventXml = "/media/nicky/STUDY/3_GU/4thYr/PRJ/RES/Nicky_Exp/topics-masked.xml";
		/*
		 * Things to do for basic backend Gather data Read in predefined xml file
		 * SAXParser Load info into MongoDB --> Events For each stream Create collspec
		 * file find $PWD/
		 *//*
			 * .gz > trec-kba2014.collspec Run extraction Update Document data -->MongoDB
			 * 
			 */
	
		DocReader docReader = new DocReader();
		// Parsing in the events from xml files
		docReader.startParsing(eventXml);
		// Pasring in documents data from streams
		articleExtracting("/media/nicky/STUDY/3_GU/4thYr/PRJ/RES/nickydata/trec-kba2014.collspec");
		System.out.println("DONE");
	}


	public void doPipeline() throws InterruptedException {
		int docGauge = Integer.MAX_VALUE;
		int doEvent = 1;
		int eventGauge = 10;
		int sentenceLength = 350;
		Properties NERProps = new Properties();
		// set the list of annotators to run
		NERProps.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, regexner ,parse,depparse");
		NERProps.setProperty("regexner.mapping", "stanford_train.txt");
		System.out.println("Init Standford NLP---May take a while");
		StanfordCoreNLP NERPipeline = new StanfordCoreNLP(NERProps);

		// For each Stream
		for (int specFileItterator = doEvent; specFileItterator <= eventGauge; specFileItterator++) {
			List<TrecDoc> crrStreamDocs = datCon.searchTrecDocByStreamId(specFileItterator);
			Event crrEvent = datCon.getEventById(specFileItterator);
			// Statement Retrieval

			StatementTracking eventStatementTracking = new StatementTracking();

			int tmpDocCount = 0;
			for (TrecDoc doc : crrStreamDocs) {
				if (!(doc.getKbaStream().equalsIgnoreCase("MAINSTREAM_NEWS")
						| doc.getKbaStream().equalsIgnoreCase("news")))
					continue;
				if (!(crrEvent.getEventStart() <= Long.parseLong(doc.getEPOCH()))
						&& crrEvent.getEventEnd() >= Long.parseLong(doc.getEPOCH()))
					continue;
				eventStatementTracking.newDocTracking();
				if (tmpDocCount >= docGauge)
					break;
				else
					tmpDocCount++;
				// Retrieval

				System.out.println("[" + tmpDocCount + "]~~~~~~~~~~~~~~~~New Doc~~~~~~~~~~~~~~~~~~~~~~~~~~");
				// Preprocessing
				// RegExp for potential sentences

				Set<String> potentialStatements = new HashSet<>();

				Pattern statementPtn = Pattern
						.compile("((\\w*)[^.;?!]*(said|told|stated|mentioned)[^.;?!#]*(\\w*)[\\.])");
				Matcher statementMatcher = statementPtn.matcher(doc.getDocBody());
				Matcher topicMatcher = statementPtn.matcher(doc.getDocTitle());
				while (topicMatcher.find()) {
					if (topicMatcher.group().length() <= sentenceLength)
						potentialStatements.add(topicMatcher.group());
				}
				while (statementMatcher.find()) {
					if (statementMatcher.group().length() <= sentenceLength)
						potentialStatements.add(statementMatcher.group());
				}

				System.out.println("potentialStatements : " + potentialStatements.size() + " sentences.");

				// Processing

				long time;
				System.out.println("Starting Statement Retrieval");
				System.out.println("____________________________________________________________");
				time = System.currentTimeMillis();
				if (potentialStatements.size() > 0)
					runStatementSentenceNLPPipeline(NERPipeline, potentialStatements, eventStatementTracking);
				time = System.currentTimeMillis() - time;
				System.out.println("____________________________________________________________");
				System.out.println(" The Statement Retrieval test took " + time + " milliseconds");
				StanfordCoreNLP.clearAnnotatorPool();

				if (crrEvent != null) {
					crrEvent.setEventEntities(eventStatementTracking.getImportantFigureStatements());
					datCon.updateEventDetails(crrEvent, specFileItterator);

				} else {
					System.out.println("Cannot retrieve event details to update.");
				}

			}

			// Damage Retrival

			DamageTracking crrEventDamageTracking = new DamageTracking();

			tmpDocCount = 0;
			for (TrecDoc doc : crrStreamDocs) {
				if (!(doc.getKbaStream().equalsIgnoreCase("MAINSTREAM_NEWS")
						| doc.getKbaStream().equalsIgnoreCase("news")))
					continue;
				if (!(crrEvent.getEventStart() <= Long.parseLong(doc.getEPOCH()))
						&& crrEvent.getEventEnd() >= Long.parseLong(doc.getEPOCH()))
					continue;
				if (tmpDocCount >= docGauge)
					break;
				else
					tmpDocCount++;
				// Retrieval

				System.out.println("[" + tmpDocCount + "]~~~~~~~~~~~~~~~~New Doc~~~~~~~~~~~~~~~~~~~~~~~~~~");

				Set<String> potentialDamage = new HashSet<>();

				StringBuilder ptnBuilder = new StringBuilder();
				if (naturalEvents.contains(crrEvent.getEventType().toLowerCase()))
					for (String word : naturalDamageWords.split(" ")) {
						ptnBuilder.append(word);
						ptnBuilder.append("|");
					}
				for (String word : injuryWords.split(" ")) {
					ptnBuilder.append(word);
					ptnBuilder.append("|");
				}
				for (String word : casualtyWords.split(" ")) {
					ptnBuilder.append(word);
					ptnBuilder.append("|");
				}
				ptnBuilder.deleteCharAt(ptnBuilder.length() - 1);
				System.out.println("DAMAGE PATTERN: " + ptnBuilder.toString());
				Pattern damagePtn = Pattern
						.compile("((\\w*)[^.;?!]*" + "(" + ptnBuilder.toString() + ")" + "[^.;?!]*(\\w*)[\\.])");
				Matcher damageMatcher = damagePtn.matcher(doc.getDocBody());
				Matcher topicMatcher = damagePtn.matcher(doc.getDocTitle());
				while (topicMatcher.find()) {
					if (topicMatcher.group().length() <= sentenceLength)
						potentialDamage.add(topicMatcher.group());
				}
				while (damageMatcher.find()) {
					if (damageMatcher.group().length() <= sentenceLength)
						potentialDamage.add(damageMatcher.group());
				}

				System.out.println("potentialDamage : " + potentialDamage.size() + " sentences.");

				long time;
				System.out.println("Starting Damage Retrieval");
				System.out.println("____________________________________________________________");
				time = System.currentTimeMillis();
				if (potentialDamage.size() > 0)
					runDamageSentenceNLPPipeline(NERPipeline, potentialDamage, crrEventDamageTracking, doc);
				time = System.currentTimeMillis() - time;
				System.out.println("____________________________________________________________");
				System.out.println(" The Damage Retrieval test took " + time + " milliseconds");
				StanfordCoreNLP.clearAnnotatorPool();
				crrEvent = datCon.getEventById(specFileItterator);
				if (crrEvent != null) {
					// Typo : Death & Injuries not Death &Casualties
//					crrEvent.setEventCasualties(crrEventDamageTracking.getCrrInjuries());
//					crrEvent.setEventDeaths(crrEventDamageTracking.getCrrCasualties());

					crrEvent.setEventInjuries(crrEventDamageTracking.getCrrInjuriesFromMap());
					crrEvent.setEventDeaths(crrEventDamageTracking.getCrrCasualtiesFromMap());

					crrEvent.setEventInjuryMap(crrEventDamageTracking.getInjuryMap());
					crrEvent.setEventDeathMap(crrEventDamageTracking.getCasualtyMap());

					datCon.updateEventDetails(crrEvent, specFileItterator);

				}
			}
			crrEvent = datCon.getEventById(specFileItterator);
			crrEvent.setEventInjuries(crrEventDamageTracking.getCrrInjuriesFromMap());
			crrEvent.setEventDeaths(crrEventDamageTracking.getCrrCasualtiesFromMap());
			crrEvent.setEventDeathMap(crrEventDamageTracking.filterValues(crrEvent.getEventDeathMap()));
			crrEvent.setEventInjuryMap(crrEventDamageTracking.filterValues(crrEvent.getEventInjuryMap()));
			datCon.updateEventDetails(crrEvent, specFileItterator);

			// Present for STREAM

			crrEventDamageTracking.presentAtEventLevel();
			System.out.println("#############################################################################");
			System.out.println("#############################################################################");
			System.out.println("#############################################################################");

			eventStatementTracking.presentAtEventLevel();

		}
	}


	public void articleExtracting(String specFile) throws CodecConfigurationException, InterruptedException {

		TRECCollection documentCollection = new TRECCollection(specFile);

		int i = 0;
		while (documentCollection.nextDocument()) {
			i++;
			System.out.println("DOC " + i + " :---------------------------------------");
			Document terrierDocument = documentCollection.getDocument();
			while (!terrierDocument.endOfDocument())
				try {
					String t = terrierDocument.getNextTerm();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			TrecDoc newTrecDoc = new TrecDoc();

			if (terrierDocument.getProperty("text") != null)
				newTrecDoc.setDocBody(terrierDocument.getProperty("text"));
			if (terrierDocument.getProperty("filename") != null)
				newTrecDoc.setTrecStreamID(terrierDocument.getProperty("filename").split("/")[10]);
			if (terrierDocument.getProperty("docno") != null)
				newTrecDoc.setTrecDocId(terrierDocument.getProperty("docno"));
			if (terrierDocument.getProperty("YYYYMMDDHH") != null)
				newTrecDoc.setYYYYMMDDHH(terrierDocument.getProperty("YYYYMMDDHH"));
			if (terrierDocument.getProperty("KBASTREAM") != null)
				newTrecDoc.setKbaStream(terrierDocument.getProperty("KBASTREAM"));
			if (terrierDocument.getProperty("EPOCH") != null)
				newTrecDoc.setEPOCH(terrierDocument.getProperty("EPOCH"));
			if (terrierDocument.getProperty("TITLE") != null)
				newTrecDoc.setDocTitle(terrierDocument.getProperty("TITLE"));
			if (terrierDocument.getProperty("URL") != null)
				newTrecDoc.setDocUrl(terrierDocument.getProperty("URL"));

			datCon.insertTrecDoc(newTrecDoc);

		}
		documentCollection.close();
	}

	
	public static void runDamageSentenceNLPPipeline(StanfordCoreNLP NERPipeline, Set<String> inSentences,
			DamageTracking tracker, TrecDoc crrDoc) {

		for (String sentence : inSentences) {
			CoreDocument singleSentenceDoc = new CoreDocument(sentence);
			NERPipeline.annotate(singleSentenceDoc);
			getDamagesinSentence(singleSentenceDoc.sentences().get(0), tracker);
		}
		tracker.presentAtDocLevel();
		tracker.trackNewDoc(crrDoc.getEPOCH().toString());
	}

	public static void getDamagesinSentence(CoreSentence inSentence, DamageTracking tracker) {
		int crrInjuries = 0;
		int crrCasualties = 0;
		List<CoreLabel> tokensList = inSentence.tokens();
		SemanticGraph dependencyParse = inSentence.dependencyParse();

		for (int i = 0; i < tokensList.size(); i++) {
			CoreLabel token = tokensList.get(i);

			if ((token.get(NamedEntityTagAnnotation.class)).equals("NUMBER")) {

				CoreLabel prevToken = null, nextToken = null;
				if (i - 1 > 0)
					prevToken = tokensList.get(i - 1);
				if (i + 1 < tokensList.size())
					nextToken = tokensList.get(i + 1);

				if (prevToken != null) {
					String prevWord = tokensList.get(i - 1).get(TextAnnotation.class);
					if (injuryWords.contains(prevWord))
						if (token.get(NumericCompositeValueAnnotation.class) != null)
							crrInjuries += token.get(NumericCompositeValueAnnotation.class).intValue();
						else if (casualtyWords.contains(prevWord))
							if (token.get(NumericCompositeValueAnnotation.class) != null)
								crrCasualties += token.get(NumericCompositeValueAnnotation.class).intValue();
				}
				if (nextToken != null) {
					String nextWord = tokensList.get(i + 1).get(TextAnnotation.class);
					if (injuryWords.contains(nextWord))
						if (token.get(NumericCompositeValueAnnotation.class) != null)
							crrInjuries += token.get(NumericCompositeValueAnnotation.class).intValue();
						else if (casualtyWords.contains(nextWord))
							if (token.get(NumericCompositeValueAnnotation.class) != null)
								crrCasualties += token.get(NumericCompositeValueAnnotation.class).intValue();
				}
			}

		}
		// damage word --> nsubjpass relation --> dependent is in Fullname

		ArrayList<String> numModTargets = new ArrayList<>();
		ArrayList<Integer> numModValues = new ArrayList<>();
		for (Iterator<SemanticGraphEdge> iterator = dependencyParse.edgeIterable().iterator(); iterator.hasNext();) {
			SemanticGraphEdge crrEdge = (SemanticGraphEdge) iterator.next();
			String crrGovWord = crrEdge.getGovernor().toString().split("/")[0];
			String crrDepWord = crrEdge.getDependent().toString().split("/")[0];

			if (crrEdge.getRelation().toString().equals("nummod")) {
				String tmpGovWord = crrEdge.getGovernor().toString().split("/")[0];
				if (crrEdge.getDependent().get(NumericCompositeValueAnnotation.class) != null) {
					numModTargets.add(tmpGovWord);
					numModValues.add(crrEdge.getDependent().get(NumericCompositeValueAnnotation.class).intValue());
				}
			}
			// BIAS : Injuries or Deaths first
			if (crrEdge.getRelation().toString().equals("nsubjpass"))// passsive subject
			{
				if (injuryWords.contains(crrGovWord))// of injuries words
					for (int i = 0; i < numModTargets.size(); i++) {
						String numModTarget = numModTargets.get(i);
						if (numModTarget.equals(crrDepWord)) {
							crrInjuries += numModValues.get(i);
							numModTargets.remove(i);
							numModValues.remove(i);
						}
					}
				if (casualtyWords.contains(crrGovWord))// of casualties words
					for (int i = 0; i < numModTargets.size(); i++) {
						String numModTarget = numModTargets.get(i);
						if (numModTarget.equals(crrDepWord)) {
							crrCasualties += numModValues.get(i);
							numModTargets.remove(i);
							numModValues.remove(i);
						}
					}

			}
		}
		System.out.println("Sentence: " + inSentence.text());
		tracker.addDamageStats(crrInjuries, crrCasualties);
		tracker.presentAtSentenceLevel();
	}

	public static void runStatementSentenceNLPPipeline(StanfordCoreNLP NERPipeline, Set<String> inSentences,
			StatementTracking statementTracking) {

		
		for (String sentence : inSentences) {

			CoreDocument singleSentenceDoc = new CoreDocument(sentence);

			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Starting test for sentence");
			long time = System.currentTimeMillis();
			NERPipeline.annotate(singleSentenceDoc);

			time = System.currentTimeMillis() - time;
			List<CoreLabel> tokensList = singleSentenceDoc.sentences().get(0).tokens();
			System.out.println(" The test took " + time + " milliseconds or " + time / 1000 + " seconds for "
					+ tokensList.size() + "tokens.");

			List<String> fullNamesInSentence = getFullNameinSentence(singleSentenceDoc.sentences().get(0));

			if (!fullNamesInSentence.isEmpty())
				for (String fullName : fullNamesInSentence) {
					fullName = replaceWithPattern(fullName, " ").trim();
					statementTracking.addStatement(fullName, sentence.toString());
//					System.out.println("Added to list");
				}
		}
		// Present at Doc Level
		statementTracking.presentAtDocLevel();

	}

	public static String replaceWithPattern(String str, String replace) {

		Pattern ptn = Pattern.compile("\\s+");
		Matcher mtch = ptn.matcher(str);
		return mtch.replaceAll(replace);
	}

	public static List<String> getFullNameinSentence(CoreSentence inSentence) {
		List<CoreLabel> tokensList = inSentence.tokens();
		SemanticGraph dependencyParse = inSentence.dependencyParse();

		// report word --> nsubj relation --> dependent is in Fullname
		String mainNNP = "";
		SemanticGraphEdge mainEdge = null;
		for (Iterator<SemanticGraphEdge> iterator = dependencyParse.edgeIterable().iterator(); iterator.hasNext();) {
			// first is always root->left
			// care only to this
			SemanticGraphEdge crrEdge = (SemanticGraphEdge) iterator.next();

			if (crrEdge.getRelation().toString().equals("nsubj"))// subject
			{
				if (reportingWords.contains(crrEdge.getGovernor().toString().split("/")[0]))// of reporting words
				{
					if (dependencyParse.getPathToRoot(crrEdge.getDependent()).size() == 1) // main (1st tier)
					{
						mainEdge = crrEdge;
						break;
					}
				}
			}
			// if done --> break the loop
		}
		if (mainEdge != null)
			mainNNP = mainEdge.getDependent().toString().split("/")[0];

		String tagLabel = "";
		int tagCounter = 0;
		List<String> fullNames = new ArrayList<>();
		for (int i = 0; i < tokensList.size(); i++) {
			CoreLabel token = tokensList.get(i);
			if (tagLabel.equals("")) {
				if ((token.get(NamedEntityTagAnnotation.class)).equals("PERSON")
						|| (token.get(NamedEntityTagAnnotation.class)).equals("ORGANIZATION")) {
					tagCounter++;
					tagLabel = token.get(NamedEntityTagAnnotation.class);
				}
			} else {
				if ((token.get(NamedEntityTagAnnotation.class)).equals(tagLabel)) {
					tagCounter++;
				} else {
					// get $tagCounter items
					if (tagCounter != 0) {
						StringBuilder builder = new StringBuilder();
						while (tagCounter != 0) {
							builder.append(WordUtils.capitalize(
									tokensList.get(i - tagCounter).get(TextAnnotation.class).replace("-", " ")));
							builder.append(" ");
							tagCounter--;
						}
						if (!mainNNP.equals("")) {
							if (builder.toString().contains(mainNNP))
								if (!fullNames.contains(builder.toString()))
									fullNames.add(builder.toString());
						} else {
							if (!fullNames.contains(builder.toString()))
								fullNames.add(builder.toString());
						}
						tagCounter = 0;
						tagLabel = "";
					}
				}
			}
		}
		return fullNames;
	}

}
