package ksr;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import org.w3c.dom.*;
import java.util.ArrayList;


public class Algorithm {

    String filesLocation = "proj1/src/main/resources/xml/";
    String[] filesNames;

    ArrayList<String> places = new ArrayList<String>();
    ArrayList<String> exchanges = new ArrayList<String>();
    ArrayList<String> people = new ArrayList<String>();
    ArrayList<String> orgs = new ArrayList<String>();

    public Algorithm() {
        Data data = new Data();
        places = data.getPlaces();
        exchanges = data.getExchanges();
        people = data.getPeople();
        orgs = data.getOrgs();
        filesNames = getFilesNames();
        for (String filesName : filesNames) {
            lern(filesLocation+"/"+filesName);
        }
    }

    public String[] getFilesNames() {
        try {

            // Create a file object
            File f = new File(filesLocation);

            // Get all the names of the files present
            // in the given directory
            String[] files = f.list();

            System.out.println("Files are:");

            // Display the names of the files
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i]);
            }
            return files;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new String[0];
    }

    public void lern(String fileName){
        try {
            File inputFile = new File(fileName); // Zamień na rzeczywistą ścieżkę pliku
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList reutersList = doc.getElementsByTagName("REUTERS");

            for (int i = 0; i < reutersList.getLength(); i++) {
                Element reuters = (Element) reutersList.item(i);
                NodeList placesList = reuters.getElementsByTagName("PLACES");
                List<String> countries = new ArrayList<>();
                if (placesList.getLength() == 1) {
                    NodeList countryNodes = ((Element) placesList.item(0)).getElementsByTagName("D");
                    for (int j = 0; j < countryNodes.getLength(); j++) {
                        countries.add(countryNodes.item(j).getTextContent());
                    }
                }
                // Pobranie treści <BODY>
                NodeList textList = reuters.getElementsByTagName("BODY");
                String bodyText = textList.getLength() > 0 ? textList.item(0).getTextContent().trim() : "";
                // Dodanie do listy wynikowej
                System.out.println(countries + bodyText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
