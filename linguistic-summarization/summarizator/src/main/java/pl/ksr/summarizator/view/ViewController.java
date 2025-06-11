package pl.ksr.summarizator.view;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.ksr.summarizator.model.DataLoader;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.LinguisticVariable;
import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectTerm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController {

    public void initialize() {
        // Initialize the TreeView or any other components if needed
        System.out.println("ViewController initialized");
        // *: Single Subject Terms TableView Initialization
        // In your initialize() method
        sstTermColumn.setCellFactory(tc -> new TableCell<>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);
                label.prefWidthProperty().bind(sstTermColumn.widthProperty().subtract(10));
                // Listen for label height changes and update cell height
                label.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    setPrefHeight(newHeight.doubleValue() + 10); // add some padding
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                } else {
                    label.setText(item);
                    setGraphic(label);
                    // Force layout to update height
                    label.applyCss();
                    label.layout();
                    setPrefHeight(label.getHeight() + 10);
                }
            }
        });
        sstTermColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        sstT0Column.setCellValueFactory(new PropertyValueFactory<>("t0"));
        sstT1Column.setCellValueFactory(new PropertyValueFactory<>("t1"));
        sstT2Column.setCellValueFactory(new PropertyValueFactory<>("t2"));
        sstT3Column.setCellValueFactory(new PropertyValueFactory<>("t3"));
        sstT4Column.setCellValueFactory(new PropertyValueFactory<>("t4"));
        sstT5Column.setCellValueFactory(new PropertyValueFactory<>("t5"));
        sstT6Column.setCellValueFactory(new PropertyValueFactory<>("t6"));
        sstT7Column.setCellValueFactory(new PropertyValueFactory<>("t7"));
        sstT8Column.setCellValueFactory(new PropertyValueFactory<>("t8"));
        sstT9Column.setCellValueFactory(new PropertyValueFactory<>("t9"));
        sstT10Column.setCellValueFactory(new PropertyValueFactory<>("t10"));
        sstT11Column.setCellValueFactory(new PropertyValueFactory<>("t11"));
        //TODO: zmienić model i tam dodać atrybut z lp
        sstIndex.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        singleSubjectTermsTableView.setItems(singleSubjectTermsList);
        singleSubjectTermsTableView.setPlaceholder(new Label("Brak wygenerowanych podsumowań."));
        // *: Fuzzy Sets TreeView Initialization
        fuzzySetsTreeView.setCellFactory(tv -> new TreeCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setDisable(false);
                checkBox.setOnAction(e -> {
                    TreeItem<Object> item = getTreeItem();
                    if (item instanceof CheckBoxTreeItem<?> cbItem) {
                        cbItem.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getLabel(item));
                    if (getTreeItem() instanceof CheckBoxTreeItem<?>) {
                        checkBox.setSelected(((CheckBoxTreeItem<?>) getTreeItem()).isSelected());
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private String getLabel(Object item) {
                if (item instanceof LinguisticVariable lv) return lv.getName();
                if (item instanceof FuzzySet fs) return fs.getName().replaceFirst(("^[^:]*:"), (""));
                return item.toString();
            }
        });
        List<LinguisticVariable> variables = SummarizationService.getLinguisticVariables();

        TreeItem<Object> rootItem = new TreeItem<>("Fuzzy Sets");
        rootItem.setExpanded(true);

        for (LinguisticVariable variable : variables) {
            TreeItem<Object> variableItem = new TreeItem<>(variable);

            for (FuzzySet set : variable.getFuzzySets()) {
                CheckBoxTreeItem<Object> setItem = new CheckBoxTreeItem<>(set);
                variableItem.getChildren().add(setItem);
            }

            rootItem.getChildren().add(variableItem);
        }

        fuzzySetsTreeView.setShowRoot(false);
        fuzzySetsTreeView.setRoot(rootItem);

        // *: Quantifiers TreeView Initialization
        quantifiersTreeView.setCellFactory(tv -> new TreeCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setDisable(false);
                checkBox.setOnAction(e -> {
                    TreeItem<Object> item = getTreeItem();
                    if (item instanceof CheckBoxTreeItem<?> cbItem) {
                        cbItem.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getLabel(item));
                    if (getTreeItem() instanceof CheckBoxTreeItem<?>) {
                        checkBox.setSelected(((CheckBoxTreeItem<?>) getTreeItem()).isSelected());
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private String getLabel(Object item) {
                if (item instanceof Quantifier q) return q.getName();
                return item.toString();
            }
        });

        List<Quantifier> quantifiers = SummarizationService.getQuantifiers();
        TreeItem<Object> quantifiersRootItem = new TreeItem<>("Quantifiers");
        quantifiersRootItem.setExpanded(true);
        for (Quantifier quantifier : quantifiers) {
            CheckBoxTreeItem<Object> quantifierItem = new CheckBoxTreeItem<>(quantifier);
            quantifiersRootItem.getChildren().add(quantifierItem);
            quantifierItem.setSelected(true);
        }
        quantifiersTreeView.setShowRoot(false);
        quantifiersTreeView.setRoot(quantifiersRootItem);

        // *: Double Subject Terms
        // *: Fuzzy sets TreeView Initialization
        doubleSubjectFuzzySetsTreeView.setCellFactory(tv -> new TreeCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setDisable(false);
                checkBox.setOnAction(e -> {
                    TreeItem<Object> item = getTreeItem();
                    if (item instanceof CheckBoxTreeItem<?> cbItem) {
                        cbItem.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getLabel(item));
                    if (getTreeItem() instanceof CheckBoxTreeItem<?>) {
                        checkBox.setSelected(((CheckBoxTreeItem<?>) getTreeItem()).isSelected());
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private String getLabel(Object item) {
                if (item instanceof LinguisticVariable lv) return lv.getName();
                if (item instanceof FuzzySet fs) return fs.getName().replaceFirst(("^[^:]*:"), (""));
                return item.toString();
            }
        });

        TreeItem<Object> rootItem2 = new TreeItem<>("Fuzzy Sets");
        rootItem2.setExpanded(true);

        for (LinguisticVariable variable : variables) {
            TreeItem<Object> variableItem = new TreeItem<>(variable);

            for (FuzzySet set : variable.getFuzzySets()) {
                CheckBoxTreeItem<Object> setItem = new CheckBoxTreeItem<>(set);
                variableItem.getChildren().add(setItem);
            }

            rootItem2.getChildren().add(variableItem);
        }

        doubleSubjectFuzzySetsTreeView.setShowRoot(false);
        doubleSubjectFuzzySetsTreeView.setRoot(rootItem2);

        // *: Quantifiers TreeView Initialization
        doubleSubjectQuantifiersTreeView.setCellFactory(tv -> new TreeCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setDisable(false);
                checkBox.setOnAction(e -> {
                    TreeItem<Object> item = getTreeItem();
                    if (item instanceof CheckBoxTreeItem<?> cbItem) {
                        cbItem.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getLabel(item));
                    if (getTreeItem() instanceof CheckBoxTreeItem<?>) {
                        checkBox.setSelected(((CheckBoxTreeItem<?>) getTreeItem()).isSelected());
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private String getLabel(Object item) {
                if (item instanceof Quantifier q) return q.getName();
                return item.toString();
            }
        });

        List<Quantifier> quantifiers2 = SummarizationService.getQuantifiers();
        TreeItem<Object> quantifiersRootItem2 = new TreeItem<>("Quantifiers");
        quantifiersRootItem2.setExpanded(true);
        for (Quantifier quantifier : quantifiers2) {
            CheckBoxTreeItem<Object> quantifierItem = new CheckBoxTreeItem<>(quantifier);
            quantifiersRootItem2.getChildren().add(quantifierItem);
            quantifierItem.setSelected(true);
        }
        doubleSubjectQuantifiersTreeView.setShowRoot(false);
        doubleSubjectQuantifiersTreeView.setRoot(quantifiersRootItem2);

        // *: Subjects ComboBoxes Initialization
        Map<String, List<SubjectViewModel>> firstSubjectOptions = new HashMap<>();
        firstSubjectOptions.put("Typ paliwa", List.of(
                new SubjectViewModel("Typ paliwa", "fuel_type", "Diesel"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Gasoline"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Gas"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Hybrid")
        ));
        firstSubjectOptions.put("Rodzaj skrzyni biegów", List.of(
                new SubjectViewModel("Rodzaj skrzyni biegów", "transmission", "Manual"),
                new SubjectViewModel("Rodzaj skrzyni biegów", "transmission", "Automatic")
        ));
        firstSubjectOptions.put("Nadwozie", List.of(
                new SubjectViewModel("Nadwozie", "body_type", "Sedan"),
                new SubjectViewModel("Nadwozie", "body_type", "Crossover"),
                new SubjectViewModel("Nadwozie", "body_type", "Coupe"),
                new SubjectViewModel("Nadwozie", "body_type", "Hatchback"),
                new SubjectViewModel("Nadwozie", "body_type", "Wagon"),
                new SubjectViewModel("Nadwozie", "body_type", "Cabriolet"),
                new SubjectViewModel("Nadwozie", "body_type", "Roadster"),
                new SubjectViewModel("Nadwozie", "body_type", "Minivan"),
                new SubjectViewModel("Nadwozie", "body_type", "Pickup"),
                new SubjectViewModel("Nadwozie", "body_type", "Fastback"),
                new SubjectViewModel("Nadwozie", "body_type", "Targa")
        ));
        firstSubjectOptions.put("Napęd", List.of(
                new SubjectViewModel("Napęd", "drive_wheels", "fwd"),
                new SubjectViewModel("Napęd", "drive_wheels", "rwd"),
                new SubjectViewModel("Napęd", "drive_wheels", "awd"),
                new SubjectViewModel("Napęd", "drive_wheels", "4wd")
        ));
        firstSubjectOptions.put("Układ cylindrów", List.of(
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Inline"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "V-type"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Opposed"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "W-type"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Rotary-piston")
        ));
        firstSubjectOptions.put("Liczba cylindrów", List.of(
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "2"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "3"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "4"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "5"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "6"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "8"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "10"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "12")
        ));

        firstSubjectMainComboBox.getItems().addAll(firstSubjectOptions.keySet());

        firstSubjectMainComboBox.setOnAction(e -> {
            String selectedCategory = firstSubjectMainComboBox.getValue();
            List<SubjectViewModel> subOptions = firstSubjectOptions.getOrDefault(selectedCategory, List.of());
            firstSubjectSubComboBox.getItems().setAll(subOptions);
            firstSubjectSubComboBox.getSelectionModel().clearSelection();
        });
        firstSubjectSubComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(SubjectViewModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.typeName());
                }
            }
        });
        firstSubjectSubComboBox.setButtonCell(firstSubjectSubComboBox.getCellFactory().call(null));

        Map<String, List<SubjectViewModel>> secondSubjectOptions = new HashMap<>();
        secondSubjectOptions.put("Typ paliwa", List.of(
                new SubjectViewModel("Typ paliwa", "fuel_type", "Diesel"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Gasoline"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Gas"),
                new SubjectViewModel("Typ paliwa", "fuel_type", "Hybrid")
        ));
        secondSubjectOptions.put("Rodzaj skrzyni biegów", List.of(
                new SubjectViewModel("Rodzaj skrzyni biegów", "transmission", "Manual"),
                new SubjectViewModel("Rodzaj skrzyni biegów", "transmission", "Automatic")
        ));
        secondSubjectOptions.put("Nadwozie", List.of(
                new SubjectViewModel("Nadwozie", "body_type", "Sedan"),
                new SubjectViewModel("Nadwozie", "body_type", "Crossover"),
                new SubjectViewModel("Nadwozie", "body_type", "Coupe"),
                new SubjectViewModel("Nadwozie", "body_type", "Hatchback"),
                new SubjectViewModel("Nadwozie", "body_type", "Wagon"),
                new SubjectViewModel("Nadwozie", "body_type", "Cabriolet"),
                new SubjectViewModel("Nadwozie", "body_type", "Roadster"),
                new SubjectViewModel("Nadwozie", "body_type", "Minivan"),
                new SubjectViewModel("Nadwozie", "body_type", "Pickup"),
                new SubjectViewModel("Nadwozie", "body_type", "Fastback"),
                new SubjectViewModel("Nadwozie", "body_type", "Targa")
        ));
        secondSubjectOptions.put("Napęd", List.of(
                new SubjectViewModel("Napęd", "drive_wheels", "fwd"),
                new SubjectViewModel("Napęd", "drive_wheels", "rwd"),
                new SubjectViewModel("Napęd", "drive_wheels", "awd"),
                new SubjectViewModel("Napęd", "drive_wheels", "4wd")
        ));
        secondSubjectOptions.put("Układ cylindrów", List.of(
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Inline"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "V-type"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Opposed"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "W-type"),
                new SubjectViewModel("Układ cylindrów", "cylinder_layout", "Rotary-piston")
        ));
        secondSubjectOptions.put("Liczba cylindrów", List.of(
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "2"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "3"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "4"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "5"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "6"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "8"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "10"),
                new SubjectViewModel("Liczba cylindrów", "number_of_cylinders", "12")
        ));

        secondSubjectMainComboBox.getItems().addAll(secondSubjectOptions.keySet());

        secondSubjectMainComboBox.setOnAction(e -> {
            String selectedCategory = secondSubjectMainComboBox.getValue();
            List<SubjectViewModel> subOptions = secondSubjectOptions.getOrDefault(selectedCategory, List.of());
            secondSubjectSubComboBox.getItems().setAll(subOptions);
            secondSubjectSubComboBox.getSelectionModel().clearSelection();
        });
        secondSubjectSubComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(SubjectViewModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.typeName());
                }
            }
        });
        secondSubjectSubComboBox.setButtonCell(secondSubjectSubComboBox.getCellFactory().call(null));

        // *: Double Subject Terms TableView Initialization
        dstTermColumn.setCellFactory(tc -> new TableCell<>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);
                label.prefWidthProperty().bind(dstTermColumn.widthProperty().subtract(10));
                // Listen for label height changes and update cell height
                label.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    setPrefHeight(newHeight.doubleValue() + 10); // add some padding
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                } else {
                    label.setText(item);
                    setGraphic(label);
                    // Force layout to update height
                    label.applyCss();
                    label.layout();
                    setPrefHeight(label.getHeight() + 10);
                }
            }
        });
        dstFormColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        dstTermColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        dstT1Column.setCellValueFactory(new PropertyValueFactory<>("t1"));
        //TODO: zmienić model i tam dodać atrybut z lp
        dstIndex.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(doubleSubjectTermsTreeView.getItems().indexOf(cellData.getValue()) + 1)
        );
        doubleSubjectTermsTreeView.setItems(doubleSubjectTermsList);
        doubleSubjectTermsTreeView.setPlaceholder(new Label("Brak wygenerowanych podsumowań."));


    }

    //* Single Subject Terms

    @FXML
    private TreeView<Object> fuzzySetsTreeView;
    @FXML
    private TreeView<Object> quantifiersTreeView;
    @FXML
    private Button generateSingleSubjectButton;
    @FXML
    private Button exportSingleSubjectButton;
    @FXML
    private TableView<SstViewModel> singleSubjectTermsTableView;
    @FXML
    private TableColumn<SstViewModel, Number> sstIndex;
    @FXML
    private TableColumn<SstViewModel, String> sstTermColumn;
    @FXML
    private TableColumn<SstViewModel, Double> sstT0Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT1Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT2Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT3Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT4Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT5Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT6Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT7Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT8Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT9Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT10Column;
    @FXML
    private TableColumn<SstViewModel, Double> sstT11Column;

    @FXML
    private ObservableList<SstViewModel> singleSubjectTermsList = FXCollections.observableArrayList();

    private List<SingleSubjectTerm> terms = new ArrayList<>();

    @FXML
    protected void onGenerateSingleSubjectTermButtonClick() {
        List<FuzzySet> selectedFuzzySets = new ArrayList<>();
        collectSelectedFuzzySets(fuzzySetsTreeView.getRoot(), selectedFuzzySets);
        List<Quantifier> selectedQuantifiers = new ArrayList<>();
        collectSelectedQuantifiers(quantifiersTreeView.getRoot(), selectedQuantifiers);
        if (selectedFuzzySets.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranych etykiet");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przynajmniej jedną etykietę z listy.");
            alert.show();
            return;
        }
        if (selectedQuantifiers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranych kwantyfikatorów");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przynajmniej jeden kwantyfikator z listy.");
            alert.show();
            return;
        }

        terms.clear();
        terms = SummarizationService.getSingleSubjectTerms(selectedQuantifiers, selectedFuzzySets);
        singleSubjectTermsList.clear();
        singleSubjectTermsList.setAll(
                terms.stream()
                        .map(SstViewModel::new).toList()
        );
        singleSubjectTermsTableView.refresh();
        Platform.runLater(() -> singleSubjectTermsTableView.requestLayout());
        exportSingleSubjectButton.setDisable(false);
    }

    @FXML
    protected void onExportSingleSubjectButtonClick() {
        System.out.println("Export button clicked!");
        DataLoader.saveResults(terms);
    }

    // *: TreeView Methods

    private void collectSelectedFuzzySets(TreeItem<Object> current, List<FuzzySet> result) {
        if (current instanceof CheckBoxTreeItem<?>) {
            CheckBoxTreeItem<?> cbItem = (CheckBoxTreeItem<?>) current;
            if (cbItem.isSelected() && cbItem.getValue() instanceof FuzzySet fs) {
                result.add(fs);
            }
        }

        for (TreeItem<Object> child : current.getChildren()) {
            collectSelectedFuzzySets(child, result);
        }
    }

    private void collectSelectedQuantifiers(TreeItem<Object> current, List<Quantifier> result) {
        if (current instanceof CheckBoxTreeItem<?>) {
            CheckBoxTreeItem<?> cbItem = (CheckBoxTreeItem<?>) current;
            if (cbItem.isSelected() && cbItem.getValue() instanceof Quantifier q) {
                result.add(q);
            }
        }

        for (TreeItem<Object> child : current.getChildren()) {
            collectSelectedQuantifiers(child, result);
        }
    }

    // *: Double Subject Terms

    @FXML
    private Button generateDoubleSubjectTermsButton;
    @FXML
    private Button exportDoubleSubjectTermsButton;
    @FXML
    private TreeView<Object> doubleSubjectFuzzySetsTreeView;
    @FXML
    private TreeView<Object> doubleSubjectQuantifiersTreeView;
    @FXML
    private TreeView<Object> subjectsTreeView;
    @FXML
    private ComboBox<String> firstSubjectMainComboBox;
    @FXML
    private ComboBox<SubjectViewModel> firstSubjectSubComboBox;
    @FXML
    private ComboBox<String> secondSubjectMainComboBox;
    @FXML
    private ComboBox<SubjectViewModel> secondSubjectSubComboBox;
    @FXML
    private TableView<DstViewModel> doubleSubjectTermsTreeView;
    @FXML
    private TableColumn<DstViewModel, Number> dstIndex;
    @FXML
    private TableColumn<DstViewModel, String> dstFormColumn;
    @FXML
    private TableColumn<DstViewModel, String> dstTermColumn;
    @FXML
    private TableColumn<DstViewModel, String> dstT1Column;
    @FXML
    private ObservableList<DstViewModel> doubleSubjectTermsList = FXCollections.observableArrayList();

    @FXML
    protected void onGenerateDoubleSubjectTermsButtonClick() {
        List<FuzzySet> selectedFuzzySets = new ArrayList<>();
        collectSelectedFuzzySets(doubleSubjectFuzzySetsTreeView.getRoot(), selectedFuzzySets);
        List<Quantifier> selectedQuantifiers = new ArrayList<>();
        collectSelectedQuantifiers(doubleSubjectQuantifiersTreeView.getRoot(), selectedQuantifiers);
        SubjectViewModel firstSubject = firstSubjectSubComboBox.getValue();
        SubjectViewModel secondSubject = secondSubjectSubComboBox.getValue();
        if (selectedFuzzySets.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranych etykiet");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przynajmniej jedną etykietę z listy.");
            alert.show();
            return;
        }
        if (selectedQuantifiers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranych kwantyfikatorów");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przynajmniej jeden kwantyfikator z listy.");
            alert.show();
            return;
        }
        if (firstSubject == null || secondSubject == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranych podmiotów");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz podmioty z listy.");
            alert.show();
            return;
        }
        doubleSubjectTermsList.clear();
        doubleSubjectTermsList.setAll(
        SummarizationService.getDoubleSubjectTerms(
                selectedQuantifiers,
                selectedFuzzySets,
                firstSubject,
                secondSubject
        ));
        //doubleSubjectTermsList.add(new DstViewModel("1", "Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1Term 1", 0.54));
        //doubleSubjectTermsList.add(new DstViewModel("2", "Term 2", 0.75));
        doubleSubjectTermsTreeView.refresh();
        Platform.runLater(() -> doubleSubjectTermsTreeView.requestLayout());
        exportDoubleSubjectTermsButton.setDisable(true);
    }
}