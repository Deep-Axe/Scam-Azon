public abstract class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Overloaded method to create different product types
    public static Product createProduct(String name, double price) {
        return new GeneralProduct(name, price);
    }

    public static Product createProduct(String name, double price, String category) {
        switch (category.toLowerCase()) {
            case "electronics":
                return new ElectronicsProduct(name, price);
            case "clothing":
                return new ClothingProduct(name, price);
            default:
                return new GeneralProduct(name, price);
        }
    }

    public abstract String getProductInfo();
}

class GeneralProduct extends Product {
    public GeneralProduct(String name, double price) {
        super(name, price);
    }

    @Override
    public String getProductInfo() {
        return "General Product: " + name + ", Price: $" + price;
    }
}
