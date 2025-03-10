package ksr;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Algorithm {

    String filesLocation = "proj1/src/main/resources/xml/";
    String[] filesNames;
    Data data;
    List<CountryVector> dataset = new ArrayList<>();
    Set<String> allowedCountries = new HashSet<>(Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan"));

    public Algorithm() {
        data = new Data();
        filesNames = getFilesNames();
        buildCountryVector("proj1/src/main/resources/xml/reut2-001.xml");
        System.out.println(data.getPeople());
    }

    private String[] getFilesNames() {
        File f = new File(filesLocation);
        String[] files = f.list();
        return files != null ? files : new String[0];
    }

    public void buildCountryVector(String fileName) {
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList reutersList = doc.getElementsByTagName("REUTERS");

            for (int i = 0; i < reutersList.getLength(); i++) {
                Element reuters = (Element) reutersList.item(i);
                NodeList placesList = reuters.getElementsByTagName("PLACES");
                String place = "";
                if (placesList.getLength() == 1) {
                    NodeList countryNodes = ((Element) placesList.item(0)).getElementsByTagName("D");
                    if (countryNodes.getLength() == 1) {
                        place = countryNodes.item(0).getTextContent();
                    }
                }

                if (allowedCountries.contains(place.toLowerCase())) {
                    NodeList textList = reuters.getElementsByTagName("BODY");
                    String bodyText = textList.getLength() > 0 ? textList.item(0).getTextContent().trim() : "";
                    Vector vector = createVector(bodyText);
                    dataset.add(new CountryVector(place, vector));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector createVector(String text) {
        return new Vector(
                findFirstOccurrence(text, data.getExchanges()), countUniqueOccurrences(text, data.getExchanges()),
                findFirstOccurrence(text, data.getPeople()), countUniqueOccurrences(text, data.getPeople()),
                findFirstOccurrence(text, data.getPlaces()), countUniqueOccurrences(text, data.getPlaces()),
                findFirstOccurrence(text, data.getSubjectCodes()), countUniqueOccurrences(text, data.getSubjectCodes()),
                findFirstOccurrence(text, data.getEconomicIndicatorCodes()), countUniqueOccurrences(text, data.getEconomicIndicatorCodes()),
                findFirstOccurrence(text, data.getCurrencyCodes()), countUniqueOccurrences(text, data.getCurrencyCodes()),
                findFirstOccurrence(text, data.getCorporateCodes()), countUniqueOccurrences(text, data.getCorporateCodes()),
                findFirstOccurrence(text, data.getCommodityCodes()), countUniqueOccurrences(text, data.getCommodityCodes()),
                findFirstOccurrence(text, data.getEnergyCodes()), countUniqueOccurrences(text, data.getEnergyCodes())
        );
    }

    private int countUniqueOccurrences(String text, ArrayList<String> list) {
        Set<String> uniqueItems = new HashSet<>();
        for (String item : list) {
            String regex = "\\b" + Pattern.quote(item.toLowerCase()) + "\\b";
            Matcher matcher = Pattern.compile(regex).matcher(text.toLowerCase());
            if (matcher.find()) {
                uniqueItems.add(item);
            }
        }
        return uniqueItems.size();
    }

    private String findFirstOccurrence(String text, ArrayList<String> list) {
        List<Integer> indices = new ArrayList<>();
        Map<Integer, String> indexToItem = new HashMap<>();

        for (String item : list) {
            String regex = "\\b" + Pattern.quote(item.toLowerCase()) + "\\b";
            Matcher matcher = Pattern.compile(regex).matcher(text.toLowerCase());
            while (matcher.find()) {
                indices.add(matcher.start());
                indexToItem.put(matcher.start(), item);
            }
        }

        if (indices.isEmpty()) {
            return null;
        }

        int minIndex = Collections.min(indices);
        return indexToItem.get(minIndex);
    }
}