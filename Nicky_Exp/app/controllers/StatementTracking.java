package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatementTracking {
	private HashMap<String, String> eventEntityStatementFinal;
	private List<HashMap<String, String>> entityStatementsAsAppeared;
	private HashMap<String, String> entityStatements;

	private List<List<String>> eventEntityAsAppeared;
	private List<String> entityAsAppeared;

	public StatementTracking() {
		eventEntityStatementFinal = new HashMap<>();
		entityStatementsAsAppeared = new ArrayList<>();
		entityStatements = new HashMap<>();

		eventEntityAsAppeared = new ArrayList<>();
		entityAsAppeared = new ArrayList<>();
	}

	public void newDocTracking() {
		if (entityStatements.keySet().size() > 0)
			entityStatementsAsAppeared.add(entityStatements);
		addToEventData(entityAsAppeared);

		entityAsAppeared = new ArrayList<>();
		entityStatements = new HashMap<>();
	}

	public void addStatement(String entity, String statement) {

		entityAsAppeared.add(entity);

		if (entityStatements.containsKey(entity)) {
			entityStatements.put(entity, entityStatements.get(entity) + "\n" + statement);
		} else
			entityStatements.put(entity, statement);
	}

	public void presentAtDocLevel() {
		System.out.println("-----Statement Report For Document-----");
		for (String key : entityStatements.keySet()) {
			System.out.println("---New Entity---");
			System.out.println("Key :" + key);
			System.out.println("Statements :" + entityStatements.get(key));
		}
	}

	public void presentAtEventLevel() {
		addToFinalEventData(entityStatementsAsAppeared);
		System.out.println("-----Statement Report For Event-----");
		for (String key : eventEntityStatementFinal.keySet()) {
			System.out.println("---New Entity---");
			System.out.println("Key :" + key);
			System.out.println("Statements :" + eventEntityStatementFinal.get(key));
		}

		System.out.println("-----Important Figure Report For Event-----");
		System.out.println(testStreamMap(calculateTfDf(eventEntityAsAppeared)));
		System.out.println(eventEntityStatementFinal.keySet());
	}

	private List<String> testStreamMap(List<LinkedHashMap<String, Double>> mapList) {

		LinkedHashMap<String, Double> sortedHashMap = new LinkedHashMap<>();
		for (LinkedHashMap<String, Double> linkedHashMap : mapList) {
			for (String entity : linkedHashMap.keySet()) {
				if (sortedHashMap.containsKey(entity))
					sortedHashMap.put(entity, (sortedHashMap.get(entity) + linkedHashMap.get(entity)) / 2.0);
				else
					sortedHashMap.put(entity, linkedHashMap.get(entity));
			}
		}
		sortedHashMap = sortHashMapByValues(sortedHashMap);

		if (sortedHashMap.isEmpty())
			return Collections.emptyList();
		List<Double> tfdfValues = sortedHashMap.values().stream().sorted().collect(Collectors.toList());
		List<Double> tfdfValuesTOP;
		if (tfdfValues.size() > 5)
			tfdfValuesTOP = tfdfValues.subList(tfdfValues.size() - 5, tfdfValues.size());
		else
			tfdfValuesTOP = tfdfValues;
		return sortedHashMap.entrySet().stream().filter(e -> tfdfValuesTOP.contains(e.getValue()))
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}

	public HashMap<String, String> getImportantFigureStatements() {
		presentAtEventLevel();
		return this.eventEntityStatementFinal;
	}

	private void addToFinalEventData(List<HashMap<String, String>> entityStatementsAsAppeared) {
		List<String> importantFigs = testStreamMap(calculateTfDf(eventEntityAsAppeared));
		for (HashMap<String, String> entityStatements : entityStatementsAsAppeared) {
			for (String entity : entityStatements.keySet()) {
				if (importantFigs.contains(entity)) {
					if (eventEntityStatementFinal.containsKey(entity)) {
						StringBuilder builder = new StringBuilder();
						for (String statement : entityStatements.get(entity).split("\n")) {
							if (!eventEntityStatementFinal.get(entity).contains(statement)) {
								builder.append(statement);
								builder.append("\n");
							}
						}
						eventEntityStatementFinal.put(entity,
								(eventEntityStatementFinal.get(entity) + "\n" + builder.toString()).trim());

					} else
						eventEntityStatementFinal.put(entity, entityStatements.get(entity));
				}
			}
		}
	}

	private void addToEventData(List<String> entityAsAppeared) {
		eventEntityAsAppeared.add(entityAsAppeared);
	}

	private List<LinkedHashMap<String, Double>> calculateTfDf(List<List<String>> entityPerDocList) {
		SimpleTFIDF calculator = new SimpleTFIDF();

		// This is for evaluation

		List<LinkedHashMap<String, Double>> entityTFDFPerDocList = new ArrayList<>();

		for (int i = 0; i < entityPerDocList.size(); i++) {
			List<String> crrDoc = entityPerDocList.get(i);
			HashMap<String, Double> termsWithScore = new HashMap<>();
			for (int j = 0; j < entityPerDocList.get(i).size(); j++) {
				String crrTerm = crrDoc.get(j);
				double crrTfDf = calculator.tfDF(crrDoc, entityPerDocList, crrTerm);
//				System.out.println("Term: "+ crrTerm+" | Score: "+crrTfDf);
				if (termsWithScore.containsKey(crrTerm)) {
//					System.out.println("Similar Term : Prv Score: "+termsWithScore.get(crrTerm)+" | New Score: "+crrTfDf);
					termsWithScore.put(crrTerm, crrTfDf);
				} else
					termsWithScore.put(crrTerm, crrTfDf);
			}
			entityTFDFPerDocList.add(sortHashMapByValues(termsWithScore));
		}


		return entityTFDFPerDocList;
	}

	private LinkedHashMap<String, Double> sortHashMapByValues(HashMap<String, Double> passedMap) {
		List<String> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Double> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();

		Iterator<Double> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Double val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next();
				Double comp1 = passedMap.get(key);
				Double comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}
}
