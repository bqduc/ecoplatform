package net.brilliance.service.api;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3891534644498426670L;

    public ObjectNotFoundException(String accountId) {
        super("No such account with id: " + accountId);
    }
}

