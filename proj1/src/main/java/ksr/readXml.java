package ksr;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class readXml {
    public void readXmlT() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Reading xml file");
        File inputFile = new File("src/main/resources/xml/reut2-021.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Element główny: " + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("REUTERS");
        System.out.println("Ilość elementów: " + nList.getLength());
    }
}
