package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectMain extends Application {

    // JAVADOC LOCATION
    // The JavaDoc is in its own folder between the .idea and the out folder

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * RUNTIME ERROR Logical error I had was with the cancel button where I used the toMain() to return to main form which was
     * assigned to both save and cancel. Thus whenever I cancelled it would not properly warn me if I want cancel for sure.
     *
     * FUTURE ENHANCEMENT would be to have the Main Form have columns have a column for the machine ID or company name since the parts can
     * have similar features and the name and ID can distinguish each part more.
     *
     * FUTURE ENHANCEMENT another enhancement I would want is a small search for ID specifically. When using the same search bar
     * the numbers, it can get confusing because some products have numbers and t gets mixed with the ID. For both parts and products.
     */


}
