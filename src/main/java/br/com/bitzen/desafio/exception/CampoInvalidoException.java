package br.com.bitzen.desafio.exception;

public class CampoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = -7983976798079243488L;

	public CampoInvalidoException(String msg) {
        super(msg);
    }

    public CampoInvalidoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
