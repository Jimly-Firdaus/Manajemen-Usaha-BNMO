package logic.feature;
// import logic.feature.Product;
import java.util.*;

import lombok.NoArgsConstructor;

class ListOfProduct {
    /*Attribute */
    private Map<String, Product> productList;

    /*Constructor */
    public ListOfProduct(){
        this.productList = new HashMap<String, Product>();
    }

    /*Method */
    public int getProductCount(){
        return this.productList.size();
    }

    public Product searchProduct(String productName){
        if(this.productList.get(productName) != null){
            return this.productList.get(productName);
        }
        else{
            return null;
        }
    }

    public void addProduct(Product product){
        this.productList.put(product.getName(), product);
    }

    public void removeProduct(String productName){
        this.productList.remove(productName);
    }

    public void printAllProduct(){
        if(getProductCount() == 0){
            System.out.println("No product");
        }else{
            for (String key : this.productList.keySet()) {
                System.out.println(key);
            }
        }
    }

    public static void main(String[] args) {
        Product product1 = new Product(2, "product-1", 100, 100, "desc-1");
        Product product2 = new Product(3, "product-2", 100, 100, "desc-2");
        Product product4 = new Product(4, "product-3", 100, 100, "desc-3");
        ListOfProduct productList = new ListOfProduct();
        productList.printAllProduct();
        productList.addProduct(product1);
        productList.printAllProduct();

    }
}