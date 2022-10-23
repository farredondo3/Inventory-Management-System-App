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
 * Main form that displays parts and product as well as being able to delete them if necessary.
 */
public class MainController implements Initializable
{

    //private Stage stage;  // Stage required to display application.
    //private Parent scene;
    /**
     * Initializes IDs for both parts and products which cannot be edited.
     */
    public static int makePartId;
    public static int makeProductId;
    /**
     * Search text field for parts
     */
    @FXML
    private TextField searchPartTextField;
    /**
     * table view for parts
     */
    @FXML
    private TableView<Part> partTableView;
    /**
     * column for the part's ID
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    /**
     * column for the part's name
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;
    /**
     * column for the part's inventory

     */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    /**
     * column for the part's price

     */
    @FXML
    private TableColumn<Part, Double> partPricePerUnitColumn;
    /**
     * text field for products search
     */
    @FXML
    private TextField searchProductTextField;

    /**
     * table view of products
     */
    @FXML
    private TableView<Product> productTableView;
    /**
     * column for product's ID
     */
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    /**
    * column for product's name
     */
    @FXML
    private TableColumn<Product, String> productNameColumn;
    /**
     * column for product's inventory
     * */
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    /**
     column for product's price
     */
    @FXML
    private TableColumn<Product, Double> productPricePerUnitColumn;
    /**
     * new inventory
     */
    //private static Inventory inventory = new Inventory();


    /**
     * Initializes the Main form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * switches to add part form
     * @param event
     * @throws IOException
     */
    public void switchToAddPartForm(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/AddPart.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switches to add product form
     * @param event
     * @throws IOException
     */
    public void switchToAddProductForm(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/AddProduct.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switches to modify product form
     * @param event
     * @throws IOException
     */
    public void switchToModifyProductForm(ActionEvent event) throws IOException
    {
        Product productToModify = (Product)productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null)
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Product not selected");
            alertError.showAndWait();
        }
        else
        {
            ModifyProductController.setProduct(productToModify);
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * switches to modify part form
     * @param event
     * @throws IOException
     */
    public void switchToModifyPartForm(ActionEvent event) throws IOException
    {
        Part part = (Part)partTableView.getSelectionModel().getSelectedItem();
        if(part == null)
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Part not selected");
            alertError.showAndWait();
        }
        else
        {
            ModifyPartController.setPart(part);
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Exits application
     * @param event
     */
    public void onActionExit(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit application?");
        Optional<ButtonType> answer = alert.showAndWait();

        if(answer.isPresent() && answer.get() == ButtonType.OK)
            System.exit(0);
    }

    /**
     * Deletes part
     * @param event
     */
    @FXML
    void onActionPartDelete(ActionEvent event)
    {
        try
        {
            Inventory.lookUpPart(partTableView.getSelectionModel().getSelectedItem().getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
                partTableView.setItems(Inventory.lookUpPart(searchPartTextField.getText()));
            }
        } catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part.");
            alert.setTitle("Part error");
            alert.showAndWait();
        }

    }

    /**
     * deletes product
     * @param actionEvent
     */
    @FXML
    public void OnDeleteProductBtnClicked(ActionEvent actionEvent) {
        Product product = (Product)productTableView.getSelectionModel().getSelectedItem();

        if(product == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setHeaderText("No product selected");
            alert.showAndWait();
            return;
        } else {
            System.out.println("product: " + product.getName() + " selected.");
            if(product.getAllAssociatedParts().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: Cannot delete a product");
                alert.setHeaderText("Error: Cannot delete a product unless it has zero associated parts. Please remove all parts before deleting.");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Really delete product: " + product.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Inventory.deleteProduct(product);
                    productTableView.setItems(Inventory.getAllProducts());
                }
            }
        }
    }

    /**
     * method of text field search for part
     */
    @FXML
    void textFieldPartSearch()
    {
        /**
         * Searches and finds parts when searching for a particular part
         * Search String stores users's part search
         */
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = searchPartTextField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Error");
            alert.setHeaderText("Part not found");
            alert.showAndWait();
        }
    }

    /**
     * method of text field search for product
     */
    @FXML
    void textFieldProductSearch()
    {
        /**
         * Searches and finds products when searching for a particular product
         * Search String stores users's products search
         */
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = searchProductTextField.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productTableView.setItems(productsFound);

        if (productsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Error");
            alert.setHeaderText("Product not found");
            alert.showAndWait();
        }
    }
}



