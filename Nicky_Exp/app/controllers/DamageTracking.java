package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DamageTracking {
	private List<Integer> injuryList = new ArrayList<>();
	private List<Integer> casualtyList = new ArrayList<>();

	private List<Integer> docInjury = new ArrayList<>();
	private List<Integer> docCasualty = new ArrayList<>();

	private Map<String, Integer> injuryMap = new HashMap<>();
	private Map<String, Integer> casualtyMap = new HashMap<>();

	String docEPOCH = "0";

	public DamageTracking() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, Integer> getInjuryMap() {
		return injuryMap;
	}

	public Map<String, Integer> getCasualtyMap() {
		return casualtyMap;
	}

	public void trackNewDoc(String inEPOCH) {
		docEPOCH = inEPOCH;
		injuryList.add(docInjury.stream().mapToInt(Integer::intValue).sum());
		casualtyList.add(docCasualty.stream().mapToInt(Integer::intValue).sum());
		addDamageToMap();
		docInjury = new ArrayList<>();
		docCasualty = new ArrayList<>();
	}

	private void addDamageToMap() {
		if (injuryMap.containsKey(docEPOCH))
			injuryMap.put(docEPOCH, (int) Math
					.round((injuryMap.get(docEPOCH) + docInjury.stream().mapToInt(Integer::intValue).sum()) / 2.0));
		else
			injuryMap.put(docEPOCH, docInjury.stream().mapToInt(Integer::intValue).sum());

		if (casualtyMap.containsKey(docEPOCH))
			casualtyMap.put(docEPOCH, (int) Math
					.round((casualtyMap.get(docEPOCH) + docCasualty.stream().mapToInt(Integer::intValue).sum()) / 2.0));
		else
			casualtyMap.put(docEPOCH, docCasualty.stream().mapToInt(Integer::intValue).sum());
	}
	
	public Map<String, Integer> filterValues(Map<String, Integer> inMap){
		if(!inMap.isEmpty()) {
			double sd = getSD(inMap);
			double mean = (inMap.values().stream().mapToInt(Integer::intValue).sum()) / (inMap.size());
			for (String key : inMap.keySet()) {
				if(inMap.get(key)<mean-sd || inMap.get(key)>mean+sd)
					inMap.replace(key, (int) Math.round(mean));
			}
			return inMap;
		}else return inMap;
	}

	public double getSD(Map<String, Integer> inMap) {

		double sum = 0.0, standardDeviation = 0.0;
		int length = inMap.size();

		for (double num : inMap.values()) {
			sum += num;
		}

		double mean = sum / length;

		for (double num : inMap.values()) {
			standardDeviation += Math.pow(num - mean, 2);
		}

		return Math.sqrt(standardDeviation / (length-1));

	}

	public double getMaxDiffForNewValue(Map<String, Integer> inMap) {
		if (inMap.isEmpty())
			return Double.MAX_VALUE;
		else
			return ((getSD(inMap) + (inMap.values().stream().mapToInt(Integer::intValue).sum()) / (inMap.size()))==0?Double.MAX_VALUE:(getSD(inMap) + (inMap.values().stream().mapToInt(Integer::intValue).sum()) / (inMap.size())));
	}

	public double getMinDiffForNewValue(Map<String, Integer> inMap) {
		if (inMap.isEmpty())
			return 0;
		else
			return ((-getSD(inMap) + (inMap.values().stream().mapToInt(Integer::intValue).sum()) / (inMap.size())) > 0
					? (-getSD(inMap) + (inMap.values().stream().mapToInt(Integer::intValue).sum()) / (inMap.size()))
					: 0);
	}

	public void addDamageStats(int injuryCount, int casualtyCount) {
			docInjury.add(injuryCount);
	
			docCasualty.add(casualtyCount);

	}

	public int getLastInjuries() {
		if (docInjury.size() > 0)
			return docInjury.get(docInjury.size() - 1);
		else
			return 0;

	}

	public int getLastCasualties() {
		if (docCasualty.size() > 0)
			return docCasualty.get(docCasualty.size() - 1);
		else
			return 0;

	}

	private double calculateAverage(List<Integer> nums) {
		Integer sum = 0;
		int numCount = 0;
		if (!nums.isEmpty()) {
			for (Integer num : nums) {
				sum += num;
				if (num != 0)
					numCount++;
			}
			return sum.doubleValue() / numCount;
		}
		return sum;
	}

	private double calculateAverage(Map<String, Integer> map) {
		List<Integer> nums = map.values().stream().collect(Collectors.toList());
		Integer sum = 0;
		int numCount = 0;
		if (!nums.isEmpty()) {
			for (Integer num : nums) {
				sum += num;
				if (num != 0)
					numCount++;
			}
			return sum.doubleValue() / numCount;
		}
		return sum;
	}

	public int getCrrInjuries() {
		return (int) Math.round(calculateAverage(injuryList));
	}

	public int getCrrCasualties() {
		return (int) Math.round(calculateAverage(casualtyList));
	}

	private Map<String, Integer> sortHashMapByKey(HashMap<String, Integer> hmap) {
		return new TreeMap<String, Integer>(hmap);
	}

	public int getCrrInjuriesFromMap() {
		return (int) Math.round(calculateAverage((injuryMap)));
	}

	public int getCrrCasualtiesFromMap() {
		return (int) Math.round(calculateAverage((casualtyMap)));
	}

	public void presentAtSentenceLevel() {
		System.out.println("-----Damage Report For Sentence-----");
		System.out
				.println("Sentence Damage:" + getLastInjuries() + " injuries " + getLastCasualties() + " casualties.");
	}

	public void presentAtDocLevel() {
		System.out.println("-----Damage Report For Doc-----");
		System.out.println("Doc Damage:" + docInjury.stream().mapToInt(Integer::intValue).sum() + " injuries "
				+ docCasualty.stream().mapToInt(Integer::intValue).sum() + " casualties.");
	}

	public void presentAtEventLevel() {
		System.out.println("-----Damage Report For Event-----");
		System.out.println("Total Damage:" + getCrrInjuries() + " injuries " + getCrrCasualties() + " casualties.");
	}

}
