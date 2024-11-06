import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button signInButton = new Button("Sign In");
        signInButton.setOnAction(e -> handleSignIn(usernameField.getText()));

        grid.add(usernameField, 0, 0);
        grid.add(passwordField, 0, 1);
        grid.add(signInButton, 0, 2);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSignIn(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText(null);
        alert.setContentText("Welcome, " + username + "!");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
