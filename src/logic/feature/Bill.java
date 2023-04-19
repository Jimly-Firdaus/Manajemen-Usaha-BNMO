class Bill {
    /*Attribute */
    private ListOfProduct basket;
    private int idCustomer;
    private boolean isFixed;

    /*Method */
    public Product searchProduct(string productName){
        return this.basket.searchProduct(productName);
    }

    public void addProduct(Product product){
        this.basket.addProduct(product);
    }

    public void removeProduct(string productName){
        this.basket.removeProduct(productName);
    }

    public void printAllBillProduct(){
        this.basket.printAllProduct();
    }

    public void BillProductCount(){
        this.basket.getProductCount();
    }
}
