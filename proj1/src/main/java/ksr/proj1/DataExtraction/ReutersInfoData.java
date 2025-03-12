package ksr.proj1.DataExtraction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReutersInfoData {
    private static final Path infoDataPath = Paths.get("src", "main", "resources", "data", "info");
    private static final String catDescriptionFilename = "cat-descriptions_120396.txt";

    // Separate category files
    public static List<String> allExchangesCodes = new ArrayList<>();
    public static List<String> allPlacesCodes = new ArrayList<>();
    public static List<String> allTopicsCodes = new ArrayList<>();
    public static List<String> allOrgsCodes = new ArrayList<>();
    public static List<String> allPeopleCodes = new ArrayList<>();
    // cat-description file
    public static List<String> commodityCodes = new ArrayList<>();
    public static List<String> orgsNames = new ArrayList<>();
    public static List<String> exchangeNames = new ArrayList<>();
    public static List<String> energyCodes = new ArrayList<>();
    public static List<String> energyNames = new ArrayList<>();

    public static void readData(){
        if (!infoDataPath.toFile().exists()) {
            System.out.println("Directory does not exist: " + infoDataPath);
            return;
        }
        allExchangesCodes.clear();
        allPlacesCodes.clear();
        allTopicsCodes.clear();
        allOrgsCodes.clear();
        allPeopleCodes.clear();
        try (var paths = java.nio.file.Files.list(infoDataPath)) {
            paths.forEach(path -> {
                try {
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(path.toFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        switch (path.getFileName().toString()) {
                            case "all-exchanges-strings.lc.txt":
                                allExchangesCodes.add(line);
                                break;
                            case "all-places-strings.lc.txt":
                                allPlacesCodes.add(line);
                                break;
                            case "all-topics-strings.lc.txt":
                                allTopicsCodes.add(line);
                                break;
                            case "all-orgs-strings.lc.txt":
                                allOrgsCodes.add(line);
                                break;
                            case "all-people-strings.lc.txt":
                                allPeopleCodes.add(line);
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error while reading file " + path + " : " + e.getMessage());
                }
            });
        } catch (java.io.IOException e) {
            System.err.println("Error while reading files: " + e.getMessage());
        }

        readCommodityCodes();
        readOrganizationNames();
        readExchangeNames();
        readEnergyData();
    }

    private static void readCommodityCodes() {
        Path catDescriptionPath = infoDataPath.resolve(catDescriptionFilename);
        commodityCodes.clear();
        if (Files.exists(catDescriptionPath)){
            try (BufferedReader reader = new BufferedReader(new FileReader(catDescriptionPath.toFile()))){
                String line;
                boolean readingCommodityCodes = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("**Commodity Codes")) {
                        readingCommodityCodes = true;
                        continue;
                    }
                    else if (readingCommodityCodes && line.contains("**Energy Codes")) {
                        readingCommodityCodes = false;
                    }
                    else if (readingCommodityCodes && !line.isEmpty()) {
                        commodityCodes.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading commodity codes: " + e.getMessage());
            }
        }
        else {
            System.err.println("Commodity codes description file not found: " + catDescriptionPath);
        }
    }

    private static void readOrganizationNames() {
        Path catDescriptionPath = infoDataPath.resolve(catDescriptionFilename);
        orgsNames.clear();
        if (Files.exists(catDescriptionPath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(catDescriptionPath.toFile()))) {
                String line;
                boolean readingOrgs = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("@heading[Organization Codes")) {
                        readingOrgs = true;
                        continue;
                    }
                    else if (readingOrgs && line.equals("@begin[format]")) {
                        continue;
                    }
                    else if (readingOrgs && line.equals("@end[format]")) {
                        break;
                    }
                    else if (readingOrgs && line.contains("(")) {
                        String orgName = line.substring(0, line.indexOf("(")).trim();
                        orgsNames.add(orgName);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading organization names: " + e.getMessage());
            }
        } else {
            System.err.println("Description file not found: " + catDescriptionPath);
        }
    }

    private static void readExchangeNames() {
        Path catDescriptionPath = infoDataPath.resolve(catDescriptionFilename);
        exchangeNames.clear();
        if (Files.exists(catDescriptionPath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(catDescriptionPath.toFile()))) {
                String line;
                boolean readingExchanges = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("@heading[Exchange Codes")) {
                        readingExchanges = true;
                        continue;
                    }
                    else if (readingExchanges && line.equals("@begin[format]")) {
                        continue;
                    }
                    else if (readingExchanges && line.equals("@end[format]")) {
                        break;
                    }
                    else if (readingExchanges && line.contains("(")) {
                        String fullName = line.substring(0, line.indexOf("(")).trim();

                        if (fullName.contains("/")) {
                            int slashPos = fullName.indexOf("/");
                            String prefix = "";
                            int lastSpaceBeforeSlash = fullName.substring(0, slashPos).lastIndexOf(" ");

                            if (lastSpaceBeforeSlash > 0) {
                                prefix = fullName.substring(0, lastSpaceBeforeSlash + 1);
                            }

                            String firstPart = fullName.substring(0, slashPos).trim();
                            exchangeNames.add(firstPart);

                            String secondPart = prefix + fullName.substring(slashPos + 1).trim();
                            exchangeNames.add(secondPart);
                        } else {
                            exchangeNames.add(fullName);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading exchange names: " + e.getMessage());
            }
        } else {
            System.err.println("Description file not found: " + catDescriptionPath);
        }
    }

    private static void readEnergyData() {
        Path catDescriptionPath = infoDataPath.resolve(catDescriptionFilename);
        energyCodes.clear();
        energyNames.clear();
        if (Files.exists(catDescriptionPath)){
            try (BufferedReader reader = new BufferedReader(new FileReader(catDescriptionPath.toFile()))){
                String line;
                boolean readingEnergy = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("**Energy Codes")) {
                        readingEnergy = true;
                        continue;
                    }
                    else if (readingEnergy && line.contains("_________")) {
                        readingEnergy = false;
                    }
                    else if (readingEnergy && !line.isEmpty()) {
                        String fullName = line.substring(0, line.indexOf("(")).trim();
                        String code = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                        energyCodes.add(code);

                        if (fullName.contains("/")){
                            String firstPart = fullName.substring(0, fullName.indexOf("/")).trim();
                            energyNames.add(firstPart);
                            String secondPart = fullName.substring(fullName.indexOf("/") + 1).trim();
                            energyNames.add(secondPart);
                        } else {
                            energyNames.add(fullName);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading energy codes: " + e.getMessage());
            }
        }
        else {
            System.err.println("Commodity codes description file not found: " + catDescriptionPath);
        }
    }
}
