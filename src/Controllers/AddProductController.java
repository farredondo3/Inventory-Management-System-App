package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Adds product to main form
 */
public class AddProductController implements Initializable
{
    /**
     * parts associated with products to enter modification of parts
     */
    //public ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();
    /**
     * text field for ID of product
     */
    public TextField idTextField;
    /**
     * text field for name of product
     */
    public TextField nameTextField;
    /**
     * text field for inventory of product
     */
    public TextField invTextField;
    /**
     * text field for price of product
     */
    @FXML
    public TextField priceTextField;
    /**
     *  text field for min of product
     */
    public TextField minTextField;
    /**
     * text field for max of product
     */
    public TextField maxTextField;
    /**
     * table view for every part made
     */
    public TableView allPartsTable;
    /**
     * table view for every part associated with a product
     */
    public TableView productPartsTable;
    /**
     * list of associated parts to a product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    /**
     * Search field for add product in order to find and make an associated part link to a product
     */
    public TextField partsSearchField;

    private Stage stage;
    private Scene scene;


    /**
     * Initializes the add product form with all the necessary information in order for the fxml to work as intended
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setText(Integer.toString(Inventory.newProductId()));
        idTextField.setEditable(false);
        idTextField.setDisable(true);

        idTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    idTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        invTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    invTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        minTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        maxTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    maxTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        this.allPartsTable.setEditable(true);
        this.productPartsTable.setEditable(true);

        /**
         * initializes columns of all parts within add product form
         */
        TableColumn productIDCol = new TableColumn("Part ID");
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn productNameCol = new TableColumn("Part Name");
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn inventoryLevelCol = new TableColumn("Inventory Level");
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn priceCostPerUnitCol = new TableColumn("Price/Cost per Unit");
        priceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTable.getColumns().addAll(productIDCol, productNameCol, inventoryLevelCol, priceCostPerUnitCol);


        allPartsTable.setItems(Inventory.getAllParts());

        /**
         * initializes columns of associated parts within add product form
         */
        TableColumn productIDCol2 = new TableColumn("Part ID");
        productIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn productNameCol2 = new TableColumn("Part Name");
        productNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn inventoryLevelCol2 = new TableColumn("Inventory Level");
        inventoryLevelCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn priceCostPerUnitCol2 = new TableColumn("Price/Cost per Unit");
        priceCostPerUnitCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartsTable.getColumns().addAll(productIDCol2, productNameCol2, inventoryLevelCol2, priceCostPerUnitCol2);
        productPartsTable.setItems(associatedParts);
    }

    /**
     * Search bar is used whenever the user needs to search for a certain part to add.
     * @param actionEvent
     */
    public void getPartSearchResultsHandler(ActionEvent actionEvent) {

        /**
         * stores user input and goes through a checklist to ensure accuracy of the part being searched for
         */
        String queryText = this.partsSearchField.getText();

        if(queryText.isEmpty()) {
            this.allPartsTable.setItems(Inventory.getAllParts());
        }

        /**
         * compares the search that the user entered to the parts that have been made.
         */
        ObservableList<Part> parts = Inventory.lookUpPart(queryText);
        try {
            int idNum = Integer.parseInt(queryText);
            Part part = Inventory.lookUpPart(idNum);
            parts.add(part);
        } catch(NumberFormatException exception) {
            System.out.println("Non Fatal Error: " + queryText + " cannot be converted to Integer.");
        }

        if(parts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: no results for " + queryText);
            alert.setHeaderText("Error: no results for " + queryText);
            alert.showAndWait();
            return;
        }
        this.allPartsTable.setItems(parts);
        this.partsSearchField.setText("");
    }

    /**
     * Saves the product
     * @param actionEvent
     * @throws IOException
     */

    public void saveProduct(ActionEvent actionEvent) throws IOException {

        /**
         * stores name
         */
        String newName = nameTextField.getText();
        if(newName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Invalid Name");
            alert.showAndWait();
            return;
        }
        /**
         * stores price
         */
        Double newPrice;
        try {
            newPrice = Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("Invalid Price");
            alert.showAndWait();
            return;
        }
        /**
         * stores min
         */
        Integer newMin;
        try {
            newMin = Integer.parseInt(minTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Min");
            alert.setHeaderText("Invalid Min");
            alert.showAndWait();
            return;
        }
        /**
         * stores max
         */
        Integer newMax;
        try {
            newMax = Integer.parseInt(maxTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Max");
            alert.setHeaderText("Invalid Max");
            alert.showAndWait();
            return;
        }

        if(newMax < newMin) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Max must be greater than min");
            alert.setHeaderText("Error: Max must be greater than min");
            alert.showAndWait();
            return;
        }
        /**
         * stores inventory
         */
        Integer newStock;
        try {
            newStock = Integer.parseInt(invTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Inv");
            alert.setHeaderText("Invalid Inv");
            alert.showAndWait();
            return;
        }
        if(newStock < newMin || newStock > newMax) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Inventory needs to be corrected");
            alert.setHeaderText("Error: Inventory needs to be corrected");
            alert.showAndWait();
            invTextField.setText("");
            return;
        }

        /**
         * Creates a new product based on the information the user gave
         */
        Product product = new Product(Integer.parseInt(idTextField.getText()), newName, newPrice, newStock, newMin, newMax);
        product.getAllAssociatedParts().addAll(associatedParts);
        Inventory.addProduct(product);
        toMainForm(actionEvent);
    }

    /**
     * tied to the save button, sends us back to Main Form
     * @param actionEvent
     * @throws IOException
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 400);
        stage.setTitle("MainForm");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * RUNTIME ERROR Logical error I had was with the cancel button where I used the toMain() to return to main form which was
     * assigned to both save and cancel. Thus whenever I cancelled it would not properly warn me if I want cancel for sure.
    /**
     * goes to main form from cancel button
     * @param event
     * @throws IOException
     */
    public void cancelToMainForm(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you want sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Adds parts to associated parts window
     * @param actionEvent
     */
    public void addToProductPartsTable(ActionEvent actionEvent) {
        Part part = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        } else {
            associatedParts.add(part);
            productPartsTable.setEditable(true);
            productPartsTable.setItems(associatedParts);
        }
    }

    /**
     * Removes associated part from associated parts table
     * @param actionEvent
     */
    public void onRemovePartBtn(ActionEvent actionEvent) {
        Part part = (Part) productPartsTable.getSelectionModel().getSelectedItem();
        int index = productPartsTable.getSelectionModel().getSelectedIndex();
        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Really delete part: " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(index);
                productPartsTable.setItems(associatedParts);
            }
        }
    }
}