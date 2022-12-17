package net.ausiamarch.digimondecksSB.exception;

public class JWTException extends RuntimeException {

    public JWTException(String msg) {
        super("ERROR: JWTException: " + msg);
    }

}
