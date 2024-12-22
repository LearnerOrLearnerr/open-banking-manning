package io.betterbanking.tp;

import java.util.List;
import java.util.LinkedList;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    /**
     * Fetch transactions list by account number
     * @param accountNumber
     * @return
     */
    public List<Transaction> findAllByAccountNumber (String accountNumber) {
        List<Transaction> list = new LinkedList<>();
        return list;
    }
}
