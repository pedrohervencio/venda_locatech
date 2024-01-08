package br.com.locacar.api.venda_locatech.controller.exception;

public class ControllerNotFoundException extends RuntimeException{
    public ControllerNotFoundException(String message) {
        super(message);
    }
}
