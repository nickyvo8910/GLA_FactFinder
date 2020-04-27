
package controllers;

import java.util.Arrays;
import java.util.List;

public class SimpleTFIDF {
	public SimpleTFIDF() {
		// TODO Auto-generated constructor stub
	}

	public double tfCount(List<String> doc, String term) {
		return java.util.Collections.frequency(doc, term);
	}

	public double dfCount(List<List<String>> docs, String term) {
		double n = 0;
		for (List<String> doc : docs) {
			if(doc.contains(term)) n++;
		}
		return n;
	}

	public double tfDF(List<String> crrDoc, List<List<String>> docCollection, String term) {

		double tfScore = 1 + Math.log10(tfCount(crrDoc, term) / crrDoc.size());
		double newTf = tfCount(crrDoc, term)/crrDoc.size();
		double dfScore = Math.log10(
				(dfCount(docCollection, term) + 0.5) / ((docCollection.size()) + 0.5));
		double newDf = dfCount(docCollection, term)/docCollection.size();
//		System.out.println("tfScore :" + tfScore);
//		System.out.println("dfScore :" + dfScore);
//		System.out.println("NEWtfScore :" + newTf);
//		System.out.println("NEWdfScore :" + newDf);
//		System.out.println("Term: '"+term+"' Score :" + tfScore * dfScore);
//		System.out.println("Term: '"+term+"' NEWScore :" + newTf * newDf);
		return newTf * newDf;
	}

}