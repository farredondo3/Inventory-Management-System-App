package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * Adds part to main form fxml
 */
public class AddPartController implements Initializable
{

    /**
     * label fot machine ID and company name
     */
    @FXML
    public Label MachineIDAndCompanyName;

    /**
     * radio button for inHouse
     */
    @FXML
    private RadioButton inHousePartRadioButton;

    /**
     * radio button for outsourced
     */
    @FXML
    private RadioButton outsourcedPartRadioButton;
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
     * text field for company name or machine ID
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
     * Listener for machine ID and company name
     */
    public ChangeListener<String> companyAndMachineTextFieldListener;

    private Stage stage;
    private Scene scene;

    /**
     * goes to main form from save
     * @param actionEvent
     * @throws IOException
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException
    {
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
     * when outsourced radio button is chosen
     * @param actionEvent
     */
    @FXML
    public void OnOutsourced(ActionEvent actionEvent)
    {
        MachineIDAndCompanyName.setText("Company Name");
        companyOrMachineIdPartTextField.setText("");
        companyOrMachineIdPartTextField.textProperty().removeListener(this.companyAndMachineTextFieldListener);
        this.companyAndMachineTextFieldListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                companyOrMachineIdPartTextField.setText(newValue);
            }
        };
        companyOrMachineIdPartTextField.textProperty().addListener(this.companyAndMachineTextFieldListener);
    }

    /**
     * when inHouse radio button is chosen
     * @param actionEvent
     */
    public void OnInHouse(ActionEvent actionEvent)
    {
        MachineIDAndCompanyName.setText("Machine ID");
        companyOrMachineIdPartTextField.setText("");
        companyOrMachineIdPartTextField.textProperty().removeListener(this.companyAndMachineTextFieldListener);
        this.companyAndMachineTextFieldListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    companyOrMachineIdPartTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        };
        companyOrMachineIdPartTextField.textProperty().addListener(this.companyAndMachineTextFieldListener);
    }

    /**
     * Saves part
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException
    {
        /**
         * stores new name
         */
        String newName = namePartTextField.getText();

        if(newName.length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Invalid Name");
            alert.showAndWait();
        }
        /**
         * stores new price
         */
        Double newPrice = 0.0;
        try {
            newPrice = Double.parseDouble(pricePartTextField.getText());
        } catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("Invalid Price");
            alert.showAndWait();
        }
        /**
         * stores new min
         */
        Integer newMin = 0;
        try
        {
            newMin = Integer.parseInt(minPartTextField.getText());
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid min");
            alert.setHeaderText("Invalid min");
            alert.showAndWait();
        }
        /**
         * stores new max
         */

        Integer newMax = 0;
        try
        {
            newMax = Integer.parseInt(maxPartTextField.getText());
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid max");
            alert.setHeaderText("Invalid max");
            alert.showAndWait();
        }

        if(newMax < newMin)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Max must be greater than min");
            alert.setHeaderText("Max must be greater than min");
            alert.showAndWait();
            minPartTextField.setText("");
            return;
        }

        /**
         * stores new inventory
         */

        Integer newStock = 0;
        try
        {
            newStock = Integer.parseInt(invPartTextField.getText());
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid inv");
            alert.setHeaderText("Invalid inv");
            alert.showAndWait();
        }
        /**
         * RUNTIME ERROR
         * A logical error I ran to was that into was when inventory was not being corrected if it was outside the bounds.
         * I would enhance this buy creating a method that ensures the inventory is compared to the min/max
         */
        if(newStock < newMin || newStock > newMax) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Inventory needs to be corrected");
            alert.setHeaderText("Error: Inventory needs to be corrected");
            alert.showAndWait();
            invPartTextField.setText("");
            return;
        }
        /**
         * stores new machineID
         */
        int newMachineId = -1;
        /**
         * stores new companyName
         */
        String newCompanyName;
        if(inHousePartRadioButton.isSelected())
        {
            try
            {
                newMachineId = Integer.parseInt(companyOrMachineIdPartTextField.getText());
            }
            catch(NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Machine ID");
                alert.setHeaderText("Invalid Machine ID");
                alert.showAndWait();
            }
            InHouse part = new InHouse(Integer.parseInt(idTextField.getText()), newName, newPrice, newStock, newMin, newMax, newMachineId);
            Inventory.addPart(part);
        } else if(outsourcedPartRadioButton.isSelected()) {
            newCompanyName = companyOrMachineIdPartTextField.getText();
            if(newCompanyName.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: Empty company name");
                alert.setHeaderText("Error: Empty company name");
                alert.showAndWait();
                return;
            }
            Outsourced part = new Outsourced(Integer.parseInt(idTextField.getText()), newName, newPrice, newStock, newMin, newMax, newCompanyName);
            Inventory.addPart(part);
        }
        toMainForm(event);

    }


    /**
     * Initializes add product form fxml
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources)
    {
        idTextField.setText(Integer.toString(Inventory.newPartId()));
        idTextField.setEditable(false);
        idTextField.setDisable(true);

        inHousePartRadioButton.setSelected(true);

        invPartTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    invPartTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        minPartTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    minPartTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        maxPartTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    maxPartTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        this.companyAndMachineTextFieldListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    companyOrMachineIdPartTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        };
        companyOrMachineIdPartTextField.textProperty().addListener(companyAndMachineTextFieldListener);

    }
}
