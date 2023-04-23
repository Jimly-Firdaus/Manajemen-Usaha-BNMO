package logic.feature;

class Product {
    /* Attribute */
    private int count;
    private String productName;
    private float basePrice;
    private float boughtPrice;
    private String category;

    /* Constructor */
    public Product(int count, String productName, float basePrice, float boughtPrice, String category) {
        this.count = count;
        this.productName = productName;
        this.basePrice = basePrice;
        this.boughtPrice = boughtPrice;
        this.category = category;
    }

    /* Method */
    public String getName() {
        return this.productName;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int newCount) {
        this.count = newCount;
    }

    public boolean isEquals(Product productCompare) {
        return this.productName.equals(productCompare.productName) && this.basePrice == productCompare.basePrice
                && this.boughtPrice == productCompare.boughtPrice && this.category.equals(productCompare.category);
    }
}
