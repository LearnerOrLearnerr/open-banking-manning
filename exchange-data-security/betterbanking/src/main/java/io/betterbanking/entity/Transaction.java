package io.betterbanking.entity;

import io.swagger.v3.oas.models.media.XML;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.slf4j.Logger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor // required by @Builder
@Document (collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String type;
    private Date date;
    private Integer accountNumber;
    private String currency;
    private Double amount;
    private String merchantName;
    private String merchantLogo;

    private Logger logger = LoggerFactory.getLogger(Transaction.class);

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return  false;

        Transaction that = (Transaction) o;
        if (accountNumber == that.accountNumber && currency.equals(that.currency)) {
            return true;
        }

        return false;
    }
    
    @Override
    public int hashCode() {
        double code = (amount / accountNumber);
        return (int) code;
    }
}
