package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public class ModifyPartController implements Initializable
{
    /**
     * label fot machine ID and company name
     */
    @FXML
    public Label MachineIDAndCompanyName;
    /**
     * toggles between inHouse and Outsourced
     */
    @FXML
    private ToggleGroup toggleGroup = new ToggleGroup();
    /**
     * radio button for inHouse
     */
    @FXML
    public RadioButton inHousePartRadioButton;
    /**
     * radio button for Outsourced
     */
    @FXML
    public RadioButton outsourcedPartRadioButton;
    /**
     * text field for name
     */

    @FXML
    private TextField namePartTextField;
    /**
     * text field for inventory
     */
    @FXML
    private TextField invPartTextField;
    /**
     * text field for price
     */
    @FXML
    private TextField pricePartTextField;
    /**
     * text field for max
     */
    @FXML
    private TextField maxPartTextField;
    /**
     * text field for machine ID or company name
     */
    @FXML
    private TextField companyOrMachineIdPartTextField;
    /**
     * text field for min
     */
    @FXML
    private TextField minPartTextField;
    /**
     * text field for ID
     */
    @FXML
    public TextField idTextField;
    /**
     * Listener for company name and machine ID
     */
    public ChangeListener<String> companyAndMachineTextFieldListener;


    private Stage stage;
    private Parent root;
    private Scene scene;
    public static Part part;

    /**
     * Saves part
     * @param actionEvent
     * @throws IOException
     */
    @FXML

    public void savePart(ActionEvent actionEvent) throws IOException
    {
        /**
         * stores new part
         */

        /**
         * RUNTIME ERROR
         *
         * Logical error I had to create a new part since I was running into an error where whenever I switch between inHouse
         * to outsourced. When changing options the text field would run into a problem where it wouldn't save the outsourced option.
         * InHouse option would save but if I switched to outsourced it would not change to an outsourced part. Thus I had to create a new part
         * so I could properly save the part.
         */
        Part newPart = null;
        String newName = namePartTextField.getText();

        /**
         * stores new price
         */
        Double newPrice;
        try {
            newPrice = Double.parseDouble(pricePartTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("Invalid Price");
            alert.showAndWait();
            return;
        }
        /**
         * stores new Min
         */
        Integer newMin;
        try {
            newMin = Integer.parseInt(minPartTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Min");
            alert.setHeaderText("Invalid Min");
            alert.showAndWait();
            return;
        }
        /**
         * stores new max
         */
        Integer newMax;
        try {
            newMax = Integer.parseInt(maxPartTextField.getText());
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
            minPartTextField.setText("");
            return;
        }
        /**
         * stores new inventory
         */
        Integer newStock;
        try
        {
            newStock = Integer.parseInt(invPartTextField.getText());
        }
        catch (NumberFormatException e)
        {
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
            invPartTextField.setText("");
            return;
        }
        /**
         * stores new machine ID
         */
        int newMachineId;
        /**
         * stores new company name
         */
        String newCompanyName = "";
        if(inHousePartRadioButton.isSelected()) {
            try {
                /**
                 * RUNTIME ERROR
                 *
                 * I tried to force the bottom text field to take the correct inputs but did not work out and ran into an
                 * issue where the bottom text field would always start on machine ID and I would have to switch between buttons
                 * to get it "working". An attempt that prove to not be the solution to the problem.
                 */
                newMachineId = Integer.parseInt(companyOrMachineIdPartTextField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Machine ID");
                alert.setHeaderText("Invalid Machine ID");
                alert.showAndWait();
                return;
            }
            newPart = new InHouse(part.getId(), newName,newPrice, newStock, newMin, newMax, newMachineId);
        } else if(outsourcedPartRadioButton.isSelected()) {
            newCompanyName = companyOrMachineIdPartTextField.getText();
            if(newCompanyName.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: Empty company name");
                alert.setHeaderText("Error: Empty company name");
                alert.showAndWait();
                return;
            }
            newPart = new Outsourced(part.getId(), newName,newPrice, newStock, newMin, newMax, newCompanyName);

        }
        Inventory.deletePart(part);

        Inventory.addPart(newPart);

        toMainForm(actionEvent);
    }

    /**
     * Returns to main form by saving or cancelling
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
     * When Outsourced radio button is selected
     * @param actionEvent
     */
    @FXML
    public void OnOutsourced(ActionEvent actionEvent)
    {
        MachineIDAndCompanyName.setText("Company Name");
        companyOrMachineIdPartTextField.setText("");

    }

    /**
     * When inHouse radio button is selected
     * @param actionEvent
     */
    public void OnInHouse(ActionEvent actionEvent)
    {
        MachineIDAndCompanyName.setText("Machine ID");
        companyOrMachineIdPartTextField.setText("");

    }

    /**
     * Initializes modify part form
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources)
    {

        if(part instanceof InHouse) {
            inHousePartRadioButton.setSelected(true);
            companyOrMachineIdPartTextField.setText(String.valueOf(((InHouse) part).getMachineId()));

        } else if(part instanceof Outsourced) {

            outsourcedPartRadioButton.setSelected(true);
            companyOrMachineIdPartTextField.setText(((Outsourced) part).getCompanyName());
        }

        /**
         * RUNTIME ERROR
         *
         * I attempted to initialize the modify part form the same way as add part but since it takes the input from add part,
         * the modify product would run into an issue where I could not enter string text on the outsourced option.
         * If I attempted to for text there would be an error occurring. I just had to remove the duplicate code that would
         * compromise the initialization.
         */
        inHousePartRadioButton.setToggleGroup(toggleGroup);
        outsourcedPartRadioButton.setToggleGroup(toggleGroup);

        idTextField.setText(Integer.toString(part.getId()));
        idTextField.setEditable(false);
        idTextField.setDisable(true);
        namePartTextField.setText(part.getName());
        pricePartTextField.setText(Double.toString(part.getPrice()));
        maxPartTextField.setText(Integer.toString(part.getMax()));
        minPartTextField.setText(Integer.toString(part.getMin()));
        invPartTextField.setText(Integer.toString(part.getStock()));
        if(part instanceof InHouse) {
            companyOrMachineIdPartTextField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else if(part instanceof Outsourced) {
            MachineIDAndCompanyName.setText("Company Name");
            companyOrMachineIdPartTextField.setText(((Outsourced) part).getCompanyName());
        }
    }

    /**
     * sets part
     * @param _part
     */
    public static void setPart(Part _part) {
        part = _part;
    }
}
