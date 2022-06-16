package org.sebastian.junit5app.ejemplos.exceptions;

/**
 * @author jhonc
 * @version 1.0
 * @since 6/12/2021
 */
public class DineroInsuficienteException extends RuntimeException {

    public DineroInsuficienteException(String message) {
        super(message);
    }
}
