class ClothingProduct extends Product {
    private String size;

    public ClothingProduct(String name, double price) {
        super(name, price);
        this.size = "M";
    }

    @Override
    public String getProductInfo() {
        return "Clothing Product: " + name + ", Price: $" + price + ", Size: " + size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
