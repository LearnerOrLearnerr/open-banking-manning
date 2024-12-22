package io.betterbanking.tp;

import java.util.List;
import java.util.Date;
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

        for (int i=0; i<5; i++) {
            Transaction txn = new Transaction();
            txn.setAccountNumber("123");
            txn.setCurrency("GBP");
            txn.setMerchantName(i%2 == 0 ? "Asda" : "Tesco");

            txn.setAmount(10*i);
            txn.setDate(new Date());

            list.add (txn);
        }

        return list;
    }
}
