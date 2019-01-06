package net.brilliance.service.api;

import net.brilliance.domain.entity.sales.Account;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface AccountService {

    /**
     * Finds the account with the provided account number.
     * 
     * @param number The account number
     * @return The account
     * @throws ObjectNotFoundException If no such account exists.
     */
    Account findOne(String number) throws ObjectNotFoundException;

    /**
     * Creates a new account.
     * 
     * @param number
     * @return created account
     */
    Account createAccountByNumber(String number);
}
