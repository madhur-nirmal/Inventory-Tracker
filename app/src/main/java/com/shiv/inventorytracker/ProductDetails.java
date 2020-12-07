package com.shiv.inventorytracker;

public class ProductDetails {
  public String productID;
  public String productName;
  public String productPrice;
  public String productQuantity;

  public ProductDetails(String productID, String productName, String productPrice, String productQuantity) {
    this.productID = productID;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productQuantity = productQuantity;
  }
}
