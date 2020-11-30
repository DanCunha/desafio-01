package br.com.desafio.exception;

public class CpfInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    public CpfInvalidException(String message) {
        super(message);
    }

    public CpfInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
