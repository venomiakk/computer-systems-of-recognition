package ksr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

    private ArrayList<String> exchanges = new ArrayList<>();
    private ArrayList<String> orgs = new ArrayList<>();
    private ArrayList<String> people = new ArrayList<>();
    private ArrayList<String> places = new ArrayList<>();
    private ArrayList<String> subjectCodes = new ArrayList<>();
    private ArrayList<String> economicIndicatorCodes = new ArrayList<>();
    private ArrayList<String> currencyCodes = new ArrayList<>();
    private ArrayList<String> corporateCodes = new ArrayList<>();
    private ArrayList<String> commodityCodes = new ArrayList<>();
    private ArrayList<String> energyCodes = new ArrayList<>();

    public ArrayList<String> getExchanges() {
        return exchanges;
    }

    public ArrayList<String> getOrgs() {
        return orgs;
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public ArrayList<String> getSubjectCodes() {
        return subjectCodes;
    }

    public ArrayList<String> getEconomicIndicatorCodes() {
        return economicIndicatorCodes;
    }

    public ArrayList<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public ArrayList<String> getCorporateCodes() {
        return corporateCodes;
    }

    public ArrayList<String> getCommodityCodes() {
        return commodityCodes;
    }

    public ArrayList<String> getEnergyCodes() {
        return energyCodes;
    }

    public Data() {
        loadDataFromFile("proj1/src/main/resources/info/all-exchanges-strings.lc.txt", exchanges);
        loadDataFromFile("proj1/src/main/resources/info/all-orgs-strings.lc.txt", orgs);
        loadDataFromFile("proj1/src/main/resources/info/all-people-strings.lc.txt", people);
        loadDataFromFile("proj1/src/main/resources/info/all-places-strings.lc.txt", places);
        loadCategoriesFromFile("proj1/src/main/resources/info/cat-descriptions_120396.txt");
        removeWordsStartingWith(exchanges);
        removeWordsStartingWith(orgs);
        removeWordsStartingWith(people);
        removeWordsStartingWith(places);
        removeWordsStartingWith(subjectCodes);
        removeWordsStartingWith(economicIndicatorCodes);
        removeWordsStartingWith(currencyCodes);
        removeWordsStartingWith(corporateCodes);
        removeWordsStartingWith(commodityCodes);
        removeWordsStartingWith(energyCodes);
    }

    private void loadDataFromFile(String filePath, ArrayList<String> list) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine().trim();
                if (!data.isEmpty()) {
                    list.add(data);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading data from file: " + filePath);
            e.printStackTrace();
        }
    }

    public void loadCategoriesFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<String> currentList = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith("**")) {
                    if (line.contains("Subject Codes")) {
                        currentList = subjectCodes;
                    } else if (line.contains("Economic Indicator Codes")) {
                        currentList = economicIndicatorCodes;
                    } else if (line.contains("Currency Codes")) {
                        currentList = currencyCodes;
                    } else if (line.contains("Corporate Codes")) {
                        currentList = corporateCodes;
                    } else if (line.contains("Commodity Codes")) {
                        currentList = commodityCodes;
                    } else if (line.contains("Energy Codes")) {
                        currentList = energyCodes;
                    } else if (line.contains("Exchange Codes")) {
                        currentList = exchanges;
                    }
                } else if (line.startsWith("@heading")) {
                    if (line.contains("Organization Codes")) {
                        currentList = orgs;
                    } else if (line.contains("Exchange Codes")) {
                        currentList = exchanges;
                    } else if (line.contains("Country Codes")) {
                        currentList = places;
                    } else if (line.contains("People Codes")) {
                        currentList = people;
                    }
                } else if (currentList != null && !line.isEmpty() && !line.startsWith("@") && !line.startsWith("_")) {
                    currentList.add(line.split("\\(")[0].trim());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading categories from file: " + filePath);
            e.printStackTrace();
        }
    }

    private void removeWordsStartingWith(ArrayList<String> list) {
        list.removeIf(word -> word.startsWith("@") || word.startsWith("_") || word.trim().isEmpty());
    }
}