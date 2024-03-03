package br.com.bitzen.desafio.exception;

public class BitzenServiceException extends RuntimeException {

	private static final long serialVersionUID = -7983976798079243488L;

	public BitzenServiceException(String msg) {
        super(msg);
    }

    public BitzenServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
