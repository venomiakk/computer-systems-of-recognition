package ksr.proj1.DataExtraction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReutersXmlData {
    private static final Path xmlFolderPath = Paths.get("src", "main", "resources", "data", "xml");
    public static List<ReutersElement> allArticles = new ArrayList<>();
    public static List<ReutersElement> classificationArticles = new ArrayList<>();

    public static void readXmlFiles() {
        if (!Files.exists(xmlFolderPath)) {
            System.out.println("Directory does not exist: " + xmlFolderPath);
            return;
        }
        allArticles.clear();
        try (var paths = Files.list(xmlFolderPath)) {
            paths.forEach(path -> {
                try {
                    File xmlFile = path.toFile();
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(xmlFile);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("REUTERS");
                    addNodesToList(nList);
                } catch (Exception e) {
                    System.err.println("Error while reading file " + path + " : " + e.getMessage());
                }

            });
        } catch (IOException e) {
            System.err.println("Error while reading files: " + e.getMessage());
        }
    }

    public static void selectArticlesForClassification() {
        if (allArticles.isEmpty()) {
            System.out.println("No articles to classify");
            return;
        }
        classificationArticles.clear();
        int index = 1;
        for (ReutersElement element : allArticles) {
            if (element.places.size() == 1 && List.of("usa", "france", "japan", "west-germany", "uk", "canada").
                    contains(element.places.getFirst())) {
                element.lp = index;
                element.label = element.places.getFirst();
                classificationArticles.add(element);
                index++;
            }
        }
    }

    private static void addNodesToList(NodeList nList) {
        for (int i = 0; i < nList.getLength(); i++) {
            Element element = (Element) nList.item(i);

            int id = Integer.parseInt(element.getAttribute("NEWID"));
            String cgiSplit = element.getAttribute("CGISPLIT");
            String lewisSplit = element.getAttribute("LEWISSPLIT");
            String date = getTagValue(element, "DATE");
            String title = getTagValue(element, "TITLE");
            String author = getTagValue(element, "AUTHOR");
            String dateline = getTagValue(element, "DATELINE");
            // TODO: maybe handle cases like '&amp;lt;BP&gt;' in body
            String body = getTagValue(element, "BODY");
            List<String> topics = getSubTagsValues(element, "TOPICS", "D");
            List<String> places = getSubTagsValues(element, "PLACES", "D");
            List<String> people = getSubTagsValues(element, "PEOPLE", "D");
            List<String> orgs = getSubTagsValues(element, "ORGS", "D");
            List<String> exchanges = getSubTagsValues(element, "EXCHANGES", "D");
            List<String> companies = getSubTagsValues(element, "COMPANIES", "D");

            allArticles.add(new ReutersElement(id, cgiSplit, lewisSplit, date, topics, places, people, orgs, exchanges,
                    companies, title, author, dateline, body));

        }
    }

    private static String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }

    private static List<String> getSubTagsValues(Element parent, String parentTag, String subTag) {
        List<String> values = new ArrayList<>();
        NodeList parentNodeList = parent.getElementsByTagName(parentTag);
        if (parentNodeList.getLength() > 0) {
            Element parentElement = (Element) parentNodeList.item(0);
            NodeList subNodes = parentElement.getElementsByTagName(subTag);
            for (int i = 0; i < subNodes.getLength(); i++) {
                values.add(subNodes.item(i).getTextContent().trim());
            }
        }
        return values;
    }
}

