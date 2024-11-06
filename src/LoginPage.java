import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.regex.*;

public class LoginPage {
    private Stage stage;

    public LoginPage(Stage stage) {
        this.stage = stage;
        openLoginPage();
    }

    private void openLoginPage() {
        VBox loginLayout = new VBox(10);
        
        Label titleLabel = new Label("Welcome to Scam-Azon");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        passwordField.setPromptText("Enter your password");

        // Password constraints
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPassword(newValue)) {
                passwordField.setStyle("-fx-border-color: red;");
            } else {
                passwordField.setStyle("-fx-border-color: green;");
            }
        });

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        // Event handling for login
        loginButton.setOnAction(e -> {
            if (UserDatabase.validateUser(usernameField.getText(), passwordField.getText())) {
                // Successful login; navigate to dashboard
                DashboardPage dashboard = new DashboardPage(stage);
                Scene dashboardScene = new Scene(dashboard.getLayout(), 800, 600);
                stage.setScene(dashboardScene);
            } else {
                showError("Invalid Username or Password");
            }
        });

        // Event handling for registration button
        registerButton.setOnAction(e -> openRegistrationPage());

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerButton);
        Scene loginScene = new Scene(loginLayout, 400, 300);
        stage.setScene(loginScene);
    }

    private void openRegistrationPage() {
        VBox registerLayout = new VBox(10);

        Label registerLabel = new Label("Register for Scam-Azon");
        TextField newUsernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        newUsernameField.setPromptText("Enter new username");
        newPasswordField.setPromptText("Enter new password");
        confirmPasswordField.setPromptText("Confirm password");

        Button registerConfirmButton = new Button("Register");
        Button backButton = new Button("Back");

        // Registration button event handling
        registerConfirmButton.setOnAction(e -> {
            String username = newUsernameField.getText();
            String password = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (password.equals(confirmPassword) && isValidPassword(password)) {
                if (UserDatabase.registerUser(username, password)) {
                    showInfo("Registration successful! Please log in.");
                    openLoginPage();  // Go back to login page after successful registration
                } else {
                    showError("Username already exists. Please choose another.");
                }
            } else if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
            } else {
                showError("Password does not meet requirements.");
            }
        });

        // Back button to go back to login page
        backButton.setOnAction(e -> openLoginPage());

        registerLayout.getChildren().addAll(registerLabel, newUsernameField, newPasswordField, confirmPasswordField, registerConfirmButton, backButton);

        Scene registerScene = new Scene(registerLayout, 400, 300);
        stage.setScene(registerScene);
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
        return Pattern.matches(pattern, password);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
