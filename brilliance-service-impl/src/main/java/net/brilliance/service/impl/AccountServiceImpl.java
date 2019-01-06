package net.brilliance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.sales.Account;
import net.brilliance.repository.sales.AccountRepository;
import net.brilliance.service.api.AccountService;
import net.brilliance.exceptions.ObjectNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${dummy.type}")
    private String dummyType;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     * <p/>
     * Dummy method for testing purposes.
     * 
     * @param number The account number. Set 0000 to get an {@link ObjectNotFoundException} 
     */
    @Override
    public Account findOne(String number) throws ObjectNotFoundException {
        if(number.equals("0000")) {
            throw new ObjectNotFoundException("0000");
        }
        
        Account account = accountRepository.findByNumber(number);
        if(account == null){
          account = createAccountByNumber(number);
        }

        return account;
    }

    @Override
    public Account createAccountByNumber(String number) {
        Account account = new Account();
        account.setNumber(number);
        return accountRepository.save(account);
    }

    public String getDummyType() {
        return dummyType;
    }

    public void setDummyType(String dummyType) {
        this.dummyType = dummyType;
    }

}
