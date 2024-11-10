
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.*;
import java.util.regex.Pattern;

public class App extends Application {
    private Stage primaryStage;
    private Map<String, String> userCredentials = new HashMap<>();
    private Map<String, List<Product>> productsByCategory = new HashMap<>();
    private List<Product> cart = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeProducts();
        showLoginPage();
        primaryStage.setTitle("E-Commerce Application");
        primaryStage.show();
    }

    private void initializeProducts() {
        // Initialize more sample products across 5 categories
        List<Product> electronics = Arrays.asList(
            new Product("Laptop", 999.99, "Electronics"),
            new Product("Smartphone", 599.99, "Electronics"),
            new Product("Desktop PC", 899.99, "Electronics"),
            new Product("Wireless Headphones", 149.99, "Electronics"),
            new Product("Smart TV", 799.99, "Electronics"),
            new Product("Drone", 399.99, "Electronics"),
            new Product("Gaming Console", 499.99, "Electronics"),
            new Product("Fitness Tracker", 99.99, "Electronics")
        );
        List<Product> clothing = Arrays.asList(
            new Product("T-Shirt", 19.99, "Clothing"),
            new Product("Jeans", 49.99, "Clothing"),
            new Product("Dress", 59.99, "Clothing"),
            new Product("Jacket", 79.99, "Clothing"),
            new Product("Polo Shirt", 29.99, "Clothing"),
            new Product("Sweatpants", 39.99, "Clothing"),
            new Product("Sneakers", 69.99, "Clothing"),
            new Product("Sunglasses", 49.99, "Clothing")
        );
        List<Product> homeGoods = Arrays.asList(
            new Product("Sofa", 799.99, "Home Goods"),
            new Product("Bed", 599.99, "Home Goods"),
            new Product("Dining Table", 499.99, "Home Goods"),
            new Product("Lamp", 49.99, "Home Goods"),
            new Product("Area Rug", 199.99, "Home Goods"),
            new Product("Coffee Table", 299.99, "Home Goods"),
            new Product("Bookshelf", 159.99, "Home Goods"),
            new Product("Curtains", 89.99, "Home Goods")
        );
        List<Product> sports = Arrays.asList(
            new Product("Basketball", 29.99, "Sports"),
            new Product("Yoga Mat", 39.99, "Sports"),
            new Product("Treadmill", 999.99, "Sports"),
            new Product("Bicycle", 399.99, "Sports"),
            new Product("Soccer Ball", 24.99, "Sports"),
            new Product("Dumbbell Set", 149.99, "Sports"),
            new Product("Kayak", 799.99, "Sports"),
            new Product("Ski Goggles", 89.99, "Sports")
        );
        List<Product> toys = Arrays.asList(
            new Product("Lego Set", 59.99, "Toys"),
            new Product("Plush Toy", 19.99, "Toys"),
            new Product("Remote Control Car", 49.99, "Toys"),
            new Product("Board Game", 29.99, "Toys"),
            new Product("Puzzle", 14.99, "Toys"),
            new Product("Building Blocks", 24.99, "Toys"),
            new Product("Stuffed Animal", 15.99, "Toys"),
            new Product("Art Supplies", 39.99, "Toys")
        );

        productsByCategory.put("Electronics", electronics);
        productsByCategory.put("Clothing", clothing);
        productsByCategory.put("Home Goods", homeGoods);
        productsByCategory.put("Sports", sports);
        productsByCategory.put("Toys", toys);
    }
    private void showLoginPage() {
        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Label messageLabel = new Label();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (userCredentials.containsKey(username) && 
                userCredentials.get(username).equals(password)) {
                showMainPage();
            } else {
                messageLabel.setText("Invalid credentials!");
            }
        });

        registerButton.setOnAction(e -> showRegistrationPage());

        loginBox.getChildren().addAll(
            new Label("Login"),
            usernameField,
            passwordField,
            loginButton,
            registerButton,
            messageLabel
        );

        Scene scene = new Scene(loginBox, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showRegistrationPage() {
        VBox registerBox = new VBox(10);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setPadding(new Insets(20));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Register");
        Label messageLabel = new Label();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password (8+ chars, 1 uppercase, 1 lowercase, 1 special)");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (validatePassword(password)) {
                userCredentials.put(username, password);
                showLoginPage();
            } else {
                messageLabel.setText("Invalid password format!");
            }
        });

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> showLoginPage());

        registerBox.getChildren().addAll(
            new Label("Register"),
            usernameField,
            passwordField,
            registerButton,
            backButton,
            messageLabel
        );

        Scene scene = new Scene(registerBox, 800, 600);
        primaryStage.setScene(scene);
    }

    private boolean validatePassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+])(?=\\S+$).{8,12}$";
        return Pattern.matches(pattern, password);
    }

    private void showMainPage() {
        BorderPane mainLayout = new BorderPane();
        
        // Top section with search
        TextField searchField = new TextField();
        Button cartButton = new Button("Cart");
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.getChildren().addAll(searchField, cartButton);
        mainLayout.setTop(topBar);

        // Left section with categories
        VBox categoryButtons = new VBox(10);
        categoryButtons.setPadding(new Insets(10));
        for (String category : productsByCategory.keySet()) {
            Button catButton = new Button(category);
            catButton.setOnAction(e -> displayProducts(category));
            categoryButtons.getChildren().add(catButton);
        }
        mainLayout.setLeft(categoryButtons);

        // Center section for products
        GridPane productsGrid = new GridPane();
        productsGrid.setHgap(10);
        productsGrid.setVgap(10);
        productsGrid.setPadding(new Insets(10));
        displayAllProducts(productsGrid);
        mainLayout.setCenter(productsGrid);

        // Search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                displayAllProducts(productsGrid);
            } else {
                displaySearchResults(newValue, productsGrid);
            }
        });

        // Cart button functionality
        cartButton.setOnAction(e -> showCartPage());

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
    }

    private void displayProducts(String category) {
        GridPane productsGrid = (GridPane) ((BorderPane) primaryStage.getScene().getRoot()).getCenter();
        productsGrid.getChildren().clear();
        List<Product> products = productsByCategory.get(category);
        displayProductList(products, productsGrid);
    }

    private void displayAllProducts(GridPane productsGrid) {
        productsGrid.getChildren().clear();
        List<Product> allProducts = new ArrayList<>();
        productsByCategory.values().forEach(allProducts::addAll);
        displayProductList(allProducts, productsGrid);
    }

    private void displaySearchResults(String searchText, GridPane productsGrid) {
        productsGrid.getChildren().clear();
        List<Product> searchResults = new ArrayList<>();
        productsByCategory.values().forEach(products -> {
            products.stream()
                   .filter(p -> p.name.toLowerCase().contains(searchText.toLowerCase()))
                   .forEach(searchResults::add);
        });
        displayProductList(searchResults, productsGrid);
    }

    private void displayProductList(List<Product> products, GridPane grid) {
        int row = 0;
        int col = 0;
        for (Product product : products) {
            VBox productBox = createProductBox(product);
            grid.add(productBox, col, row);
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createProductBox(Product product) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");

        Label nameLabel = new Label(product.name);
        Label priceLabel = new Label("$" + product.price);
        Button addToCartButton = new Button("Add to Cart");

        addToCartButton.setOnAction(e -> {
            cart.add(product);
            showAlert("Added to Cart", product.name + " added to cart successfully!");
        });

        box.getChildren().addAll(nameLabel, priceLabel, addToCartButton);
        return box;
    }

    private void showCartPage() {
        VBox cartBox = new VBox(10);
        cartBox.setPadding(new Insets(20));
        cartBox.setAlignment(Pos.CENTER);

        double total = 0;
        for (Product product : cart) {
            HBox productLine = new HBox(10);
            productLine.setAlignment(Pos.CENTER);
            Label productLabel = new Label(product.name + " - $" + product.price);
            total += product.price;
            productLine.getChildren().add(productLabel);
            cartBox.getChildren().add(productLine);
        }

        Label totalLabel = new Label("Total: $" + String.format("%.2f", total));
        Button placeOrderButton = new Button("Place Order");
        Button backButton = new Button("Back to Shopping");

        placeOrderButton.setOnAction(e -> {
            String orderId = generateOrderId();
            showAlert("Order Placed", "Your order ID is: " + orderId);
            cart.clear();
            showMainPage();
        });

        backButton.setOnAction(e -> showMainPage());

        cartBox.getChildren().addAll(totalLabel, placeOrderButton, backButton);

        Scene scene = new Scene(cartBox, 800, 600);
        primaryStage.setScene(scene);
    }

    private String generateOrderId() {
        return "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class Product {
        String name;
        double price;
        String category;

        Product(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }
}