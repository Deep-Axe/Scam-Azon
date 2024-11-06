import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardPage implements Searchable {
    private BorderPane layout;

    public DashboardPage(Stage stage) {
        layout = new BorderPane();
        createDashboard();
    }

    public BorderPane getLayout() {
        return layout;
    }

    private void createDashboard() {
        // Top panel with logo and search bar
        HBox topPanel = new HBox();
        Label logo = new Label("Scam-Azon");
        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        topPanel.getChildren().addAll(logo, searchField);

        // Left panel with categories
        VBox categoryPanel = new VBox(10, new Label("Category 1"), new Label("Category 2"), new Label("Category 3"));

        // Center grid for displaying products
        GridPane productGrid = new GridPane();

        // Multithreading with exception handling for loading products
        Thread productLoader = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Product product = Product.createProduct("Product " + (i + 1), (i + 1) * 10.0, "electronics");
                    Label productLabel = new Label(product.getProductInfo());

                    int index = i;
                    Platform.runLater(() -> productGrid.add(productLabel, index % 3, index / 3));
                    Thread.sleep(100); // Simulate loading time
                }
            } catch (InterruptedException ex) {
                Platform.runLater(() -> showError("Error loading products"));
                ex.printStackTrace();
            }
        });
        productLoader.start();

        layout.setTop(topPanel);
        layout.setLeft(categoryPanel);
        layout.setCenter(productGrid);
    }

    @Override
    public void search(String query) {
        System.out.println("Searching for: " + query);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
