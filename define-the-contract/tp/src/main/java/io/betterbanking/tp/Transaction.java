package io.betterbanking.tp;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
 public class Transaction {
    int type;
    Date date;
    String accountNumber;
    String currency;
    double amount;
    String merchantName;
    String merchantLogo;
}
