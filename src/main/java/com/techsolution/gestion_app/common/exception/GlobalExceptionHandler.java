package com.techsolution.gestion_app.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Maneja todas las excepciones de la aplicación de forma centralizada.Permite que los controladores no tengan que repetir código de manejo de errores.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Se invoca cuando un producto no existe.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> onProductNotFound(ProductNotFoundException ex) {
        logger.warn("Producto no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Producto no encontrado: " + ex.getMessage());
    }

    /**
     * Se invoca cuando una orden no existe.
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> onOrderNotFound(OrderNotFoundException ex) {
        logger.warn("Orden no encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Orden no encontrada: " + ex.getMessage());
    }

    //Se invoca cuando no hay suficiente stock para completar la operación.
    
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> onInsufficientStock(InsufficientStockException ex) {
        logger.warn("Stock insuficiente: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Stock insuficiente: " + ex.getMessage());
    }

     // Captura cualquier excepción no prevista.
   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> onGenericException(Exception ex) {
        logger.error("Error inesperado en la aplicación", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Ocurrió un error inesperado en el servidor");
    }
}
