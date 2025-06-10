package pl.ksr.summarizator.view;

import javafx.application.Platform;
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
import java.util.List;

public class ViewController {

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
    protected void onGenerateSingleSubjectButtonSingleSubjectClick() {
        System.out.println("Test button clicked!");
        List<FuzzySet> selectedFuzzySets = new ArrayList<>();
        collectSelectedFuzzySets(fuzzySetsTreeView.getRoot(), selectedFuzzySets);
        selectedFuzzySets.forEach(fs -> System.out.println("Selected FuzzySet: " + fs.getName()));
        List<Quantifier> selectedQuantifiers = new ArrayList<>();
        collectSelectedQuantifiers(quantifiersTreeView.getRoot(), selectedQuantifiers);
        selectedQuantifiers.forEach(q -> System.out.println("Selected Quantifier: " + q.getName()));
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
        terms = SingleSubjectService.getExampleSingleSubjectTerms();
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
        List<LinguisticVariable> variables = SingleSubjectService.getLinguisticVariables();

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

        List<Quantifier> quantifiers = SingleSubjectService.getQuantifiers();
        TreeItem<Object> quantifiersRootItem = new TreeItem<>("Quantifiers");
        quantifiersRootItem.setExpanded(true);
        for (Quantifier quantifier : quantifiers) {
            CheckBoxTreeItem<Object> quantifierItem = new CheckBoxTreeItem<>(quantifier);
            quantifiersRootItem.getChildren().add(quantifierItem);
            quantifierItem.setSelected(true);
        }
        quantifiersTreeView.setShowRoot(false);
        quantifiersTreeView.setRoot(quantifiersRootItem);

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
}