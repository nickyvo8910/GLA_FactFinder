/**
 * 
 */
package controllers;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * @author nicky
 *
 */
public class DocReader {

	public static SAXParserFactory factory = SAXParserFactory.newInstance();
	public SAXParser saxParser;
	
	public EventHandler eventHandler;

	

	/**
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 */
	public DocReader() throws ParserConfigurationException, SAXException {
		// TODO Auto-generated constructor stub
		saxParser = factory.newSAXParser();
		eventHandler = new EventHandler();
		
	}
	
	public void startParsing(String xmlFile){
		try {
			saxParser.parse(xmlFile, eventHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}