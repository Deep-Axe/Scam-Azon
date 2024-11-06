class ElectronicsProduct extends Product {
    private String warranty;

    public ElectronicsProduct(String name, double price) {
        super(name, price);
        this.warranty = "1-year warranty";
    }

    @Override
    public String getProductInfo() {
        return "Electronics Product: " + name + ", Price: $" + price + ", " + warranty;
    }
}
