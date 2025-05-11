package ksr.knn;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ksr.knn.Classifier.ClassificationResult;
import ksr.knn.Classifier.DistanceMetrics.ChebyshevDistance;
import ksr.knn.Classifier.DistanceMetrics.Distances;
import ksr.knn.Classifier.DistanceMetrics.EuclideanDistance;
import ksr.knn.Classifier.DistanceMetrics.ManhattanDistance;
import ksr.knn.Classifier.KnnClassifier;
import ksr.knn.Classifier.Measures.JaccardMeasure;
import ksr.knn.Classifier.Measures.NgramMethod;
import ksr.knn.Classifier.Measures.WordsSimilarityMeasure;
import ksr.knn.DataExtraction.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class HelloController implements Initializable {
    @FXML
    private TextField k_value;
    @FXML
    private TextField percent_value;
    @FXML
    private TextField numOfArticles;
    @FXML
    private CheckBox c1;
    @FXML
    private CheckBox c2;
    @FXML
    private CheckBox c3;
    @FXML
    private CheckBox c4;
    @FXML
    private CheckBox c5;
    @FXML
    private CheckBox c6;
    @FXML
    private CheckBox c7;
    @FXML
    private CheckBox c8;
    @FXML
    private CheckBox c9;
    @FXML
    private CheckBox c10;
    @FXML
    private CheckBox c11;
    @FXML
    private CheckBox c12;
    @FXML
    private ChoiceBox distanceMetric;
    @FXML
    private ChoiceBox wordsSimilarityMeasure;
    @FXML
    private Button run_knn;
    @FXML
    private Label executionTime;
    @FXML
    private Label trainSet;
    @FXML
    private Label testSet;
    @FXML
    private Label correctPredictions;
    @FXML
    private Label incorrectPredictions;
    @FXML
    private Label accuracy;
    @FXML
    private TableView<ConfusionMatrixRow> confusionMatrixTable;
    @FXML
    private TableColumn<ConfusionMatrixRow, String> trueClassColumn;
    @FXML
    private TableView<ScoreRow> scoresTable;

    @FXML
    private TableColumn<ScoreRow, String> classColumn;

    @FXML
    private TableColumn<ScoreRow, String> precisionColumn;

    @FXML
    private TableColumn<ScoreRow, String> recallColumn;

    @FXML
    private TableColumn<ScoreRow, String> f1Column;

    private ObservableList<ScoreRow> scoreRows = FXCollections.observableArrayList();

    private ObservableList<ConfusionMatrixRow> matrixRows = FXCollections.observableArrayList();

    private List<ReutersElement> articles;
    private List<String> surnameDict;
    private List<String> countryDict;
    private List<String> keywordDict;
    private List<String> stopWords;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        this.articles = ReutersXmlData.classificationArticles;

        ReutersInfoData.readData();
        this.surnameDict = ReutersInfoData.allPeopleCodes;
        this.countryDict = ReutersInfoData.allPlacesCodes;
        try {
            this.keywordDict = KeywordsExtraction.extractKeywords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            this.stopWords = StopWords.getStopWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.distanceMetric.getItems().addAll("Euklides", "Manhattan", "Chebysheva");
        this.distanceMetric.setValue("Euklides");
        this.wordsSimilarityMeasure.getItems().addAll("Ngram");
        this.wordsSimilarityMeasure.setValue("Ngram");
        restrict(k_value, value -> value > 0 && value < 999999);
        restrict(percent_value, value -> value > 0 && value < 100);
        restrict(numOfArticles, value -> value >= 0 && value < 999999);

        trueClassColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrueClass()));

        classColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassName()));
        precisionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecision()));
        recallColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecall()));
        f1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getF1()));
        scoresTable.setItems(scoreRows);
    }

    public static void restrict(TextField textField, Predicate<Integer> condition) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }
            try {
                int value = Integer.parseInt(newText);
                if (condition.test(value)) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // ignorujemy błędne dane
            }
            return null;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    @FXML
    protected void onRunKnnClick() throws IOException {
        System.out.println("KNN button clicked");
        int k = Integer.parseInt(k_value.getText());
        int percent = Integer.parseInt(percent_value.getText());
        int numOfArticles = Integer.parseInt(this.numOfArticles.getText());
        List<CheckBox> checkBoxes = List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);
        List<String> features = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                features.add("c" + (i + 1));
            }
        }
        Distances distanceMetric = null;
        switch (this.distanceMetric.getValue().toString()) {
            case "Euklides":
                distanceMetric = new EuclideanDistance();
                break;
            case "Manhattan":
                distanceMetric = new ManhattanDistance();
                break;
            case "Chebysheva":
                distanceMetric = new ChebyshevDistance();
                break;
        }
        WordsSimilarityMeasure wordsSimMeasure = null;
        switch (this.wordsSimilarityMeasure.getValue().toString()) {
            case "Ngram":
                wordsSimMeasure = new NgramMethod();
                break;
            case "Jaccard":
                wordsSimMeasure = new JaccardMeasure();
                break;
        }

        KnnClassifier knnClassifier = new KnnClassifier(this.articles, this.surnameDict, this.countryDict, this.keywordDict, this.stopWords);
        ClassificationResult cr = knnClassifier.classify(k, percent, numOfArticles, features, distanceMetric, wordsSimMeasure);
        cr.printClassificationResults();
        this.executionTime.setText("Czas działania [s]: " + cr.duration / 1000.0);
        this.trainSet.setText("Zbiór treningowy: " + cr.trainSet.size() + " | " + cr.trainSetDistribution);
        this.testSet.setText("Zbiór testowy: " + cr.testSet.size() + " | " + cr.testSetDistribution);
        this.correctPredictions.setText("Poprawne klasyfikacje: " + cr.correctPredictions);
        this.incorrectPredictions.setText("Błędne klasyfikacje: " + cr.incorrectPredictions);
        this.accuracy.setText("Accuracy: " + String.format("%.3f", cr.accuracy));
        loadConfusionMatrix(cr.confusionMatrix);
        loadScoresMatrix(cr.scoresMatrix);

    }

    private void loadConfusionMatrix(Map<String, Map<String, Integer>> confusionMatrix) {
        matrixRows.clear();
        confusionMatrixTable.getColumns().retainAll(trueClassColumn);

        Set<String> predictedClasses = confusionMatrix.values().stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());

        // Tworzenie dynamicznych kolumn
        for (String predictedClass : predictedClasses) {
            TableColumn<ConfusionMatrixRow, Integer> col = new TableColumn<>(predictedClass);
            col.setCellValueFactory(cellData -> {
                Integer value = cellData.getValue().getPrediction(predictedClass);
                return new SimpleIntegerProperty(value).asObject();
            });
            confusionMatrixTable.getColumns().add(col);
        }

        // Wiersze
        for (String trueClass : confusionMatrix.keySet()) {
            Map<String, Integer> row = confusionMatrix.get(trueClass);
            matrixRows.add(new ConfusionMatrixRow(trueClass, row));
        }

        confusionMatrixTable.setItems(matrixRows);
    }

    private void loadScoresMatrix(Map<String, Map<String, Float>> scoresMatrix) {
        scoreRows.clear();

        for (String className : scoresMatrix.keySet()) {
            Map<String, Float> metrics = scoresMatrix.get(className);
            String precision = String.format("%.3f", metrics.getOrDefault("precision", 0f));
            String recall = String.format("%.3f",metrics.getOrDefault("recall", 0f));
            String f1 = String.format("%.3f" ,metrics.getOrDefault("f1", 0f));

            scoreRows.add(new ScoreRow(className, precision, recall, f1));
        }
    }

    public static class ConfusionMatrixRow {
        private final String trueClass;
        private final Map<String, Integer> predictedCounts;

        public ConfusionMatrixRow(String trueClass, Map<String, Integer> predictedCounts) {
            this.trueClass = trueClass;
            this.predictedCounts = predictedCounts;
        }

        public String getTrueClass() {
            return trueClass;
        }

        public Integer getPrediction(String predictedClass) {
            return predictedCounts.getOrDefault(predictedClass, 0);
        }
    }

    public static class ScoreRow {
        private final SimpleStringProperty className;
        private final SimpleStringProperty precision;
        private final SimpleStringProperty recall;
        private final SimpleStringProperty f1;

        public ScoreRow(String className, String precision, String recall, String f1) {
            this.className = new SimpleStringProperty(className);
            this.precision = new SimpleStringProperty(precision);
            this.recall = new SimpleStringProperty(recall);
            this.f1 = new SimpleStringProperty(f1);
        }

        public String getClassName() {
            return className.get();
        }

        public String getPrecision() {
            return precision.get();
        }

        public String getRecall() {
            return recall.get();
        }

        public String getF1() {
            return f1.get();
        }
    }
}