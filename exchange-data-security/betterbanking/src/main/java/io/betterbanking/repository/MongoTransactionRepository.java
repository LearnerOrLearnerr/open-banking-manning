package io.betterbanking.repository;

import com.mongodb.client.MongoCollection;
import io.betterbanking.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoTransactionRepository {

    private Logger logger = LoggerFactory.getLogger(MongoTransactionRepository.class);

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Transaction> findAllByAccountNumber (final Integer acctNr) {
        return repo.findAllByAccountNumber(acctNr);
    }

    public List findDistinctAccountNumbers () {
        return mongoTemplate.findDistinct("accountNumber", Transaction.class, Integer.class);
    }

    public void saveTransaction(Transaction t) {
        repo.save(t);
    }
}
