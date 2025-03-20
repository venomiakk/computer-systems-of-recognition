package ksr.proj1.DataExtraction;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReutersInfoData {
    private static final Path infoDataPath = Paths.get("src", "main", "resources", "data", "info");

    // Separate category files
    public static List<String> allPlacesCodes = new ArrayList<>();
    public static List<String> allPeopleCodes = new ArrayList<>();


    public static void readData(){
        if (!infoDataPath.toFile().exists()) {
            System.out.println("Directory does not exist: " + infoDataPath);
            return;
        }
        allPlacesCodes.clear();
        allPeopleCodes.clear();
        try (var paths = java.nio.file.Files.list(infoDataPath)) {
            paths.forEach(path -> {
                try {
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(path.toFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        switch (path.getFileName().toString()) {
                            case "all-places-strings.lc.txt":
                                allPlacesCodes.add(line.strip());
                                break;
                            case "all-people-strings.lc.txt":
                                allPeopleCodes.add(line.strip());
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
    }
}
