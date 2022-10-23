package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
 *
 */
public class ModifyProductController implements Initializable {
    /**
     * stores product
     */
    public static Product product;
    /**
     * stores original product
     */
    public static Product productOriginal;
    /**
     * stores users input when searching for parts
     */
    @FXML
    public TextField partsSearchField;
    /**
     * add button
     */
    public Button addButton;
    /**
     * remove button
     */
    public Button removePartBtn;
    /**
     * save button
     */
    public Button saveBtn;
    /**
     * cancel button
     */
    public Button cancelBtn;
    /**
     * stores ID from text field
     */
    @FXML
    public TextField idTextField;
    /**
     * stores name from text field
     */
    @FXML
    public TextField nameTextField;
    /**
     * stores inventory from text field
     */
    @FXML
    public TextField invTextField;
    /**
     * stores price from text field
     */
    @FXML
    public TextField priceTextField;
    /**
     * stores min from text field
     */
    @FXML
    public TextField minTextField;
    /**
     * stores max from text field
     */
    @FXML
    public TextField maxTextField;
    /**
     * Table view of all parts created
     */
    public TableView allPartsTable;
    /**
     * Table for parts associated with a product
     */
    public TableView productPartsTable;
    /**
     * List of associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private Stage stage;
    private Scene scene;


    /**
     * Initializes Modify product
     * @param url
     * @param resourceBundle
     */

    /** RUNTIME ERROR
     * Similar to what happened to modifyPart form I didn't need to initialize this method the same as addProduct
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTextField.setText(Integer.toString(product.getId()));
        idTextField.setEditable(false);
        idTextField.setDisable(true);
        nameTextField.setText(product.getName());
        priceTextField.setText(Double.toString(product.getPrice()));
        maxTextField.setText(Integer.toString(product.getMax()));
        minTextField.setText(Integer.toString(product.getMin()));
        invTextField.setText(Integer.toString(product.getStock()));

        if(product.getAllAssociatedParts() != null) {
            for(Part part : product.getAllAssociatedParts()) {
                associatedParts.add(part);
            }
        }

        this.allPartsTable.setEditable(true);
        this.productPartsTable.setEditable(true);

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
     * Searches for parts to be added to be associated to products
     * @param actionEvent
     */
    public void getPartSearchResultsHandler(ActionEvent actionEvent) {
        String queryText = this.partsSearchField.getText();

        if(queryText.isEmpty()) {
            this.allPartsTable.setItems(Inventory.getAllParts());
        }

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
     * sets product
     * @param _product
     */


    public static void setProduct(Product _product) {
        product = _product;
    }

    /**
     * saves product
     * @param actionEvent
     * @throws IOException
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        /**
         * stores name
         */
        String newName = nameTextField.getText();

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
        Product key = new Product(product.getId(), newName, newPrice, newStock, newMin, newMax);
        key.getAllAssociatedParts().addAll(associatedParts);
        Inventory.deleteProduct(product);
        Inventory.addProduct(key);

        toMainForm(actionEvent);
    }

    /**
     * goes to main form after saving and/ or cancelling
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
     * adds product to associated part table
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
            associatedParts.add(part);;
        }
    }

    /**
     * removes part from associated table
     * @param actionEvent
     */
    public void onRemovePartBtn(ActionEvent actionEvent) {
        Part part = (Part) productPartsTable.getSelectionModel().getSelectedItem();
        int index = productPartsTable.getSelectionModel().getSelectedIndex();
        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Really delete part: " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(index);

            }
        }
    }
}