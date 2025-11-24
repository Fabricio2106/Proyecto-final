package com.techsolution.gestion_app.common.exception;
public class InsufficientStockException  extends RuntimeException{
    public InsufficientStockException (String productName){
    super("stock insuficiente para el producto"+productName);
    }
}
