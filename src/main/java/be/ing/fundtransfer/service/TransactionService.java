package be.ing.fundtransfer.service;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TransactionService {
    public String createTransaction(TransactionData transactionData) throws DataInsertionException;
    public TransactionData getTransaction(String transactionId) throws DataRetrievalException;
    public String updateTransaction(TransactionData transactionData) throws DataInsertionException;
    public void deleteTransaction(TransactionData transactionData)throws DataRetrievalException;
}




