package com.company.Application;

import com.company.Application.ProductClasses.Product;

import java.io.Serializable;
import java.util.Arrays;

public class Data implements Serializable {
    private int minLength;
    private String[] message;
    private Product product;
    private int productId;



    public Data(String[] message) {
        this.message = message;
    }
    public Data(String message) {
        this.message = new String[1];
        this.message[0] = message;
    }

    public Data(String[] message, Product product, int minLength) {
        this.message = message;
        this.product = product;
        this.minLength = minLength;
    }
    public Data(String[] message, int minLength) {
        this.message = message;
        this.minLength = minLength;
    }

    public Data(int productId, Product product) {
        this.product = product;
        this.productId = productId;

    }

    public Data(String[] message, int productId,Product product, int minLength) {
        this.message = message;
        this.product = product;
        this.productId = productId;
        this.minLength = minLength;
    }


    public int getMinLength() {
        return minLength;
    }

    public Product getProduct() {
        return product;
    }

    public String[] getMessage() {
        return message;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (message != null)
            Arrays.stream(message).forEach(s -> stringBuilder.append(s).append('\n'));


        if (product!= null){
            stringBuilder.append(productId).append(" ").append(product.toString());
        }

        return stringBuilder.toString();
    }
}
