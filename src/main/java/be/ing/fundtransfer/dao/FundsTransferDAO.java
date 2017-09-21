package be.ing.fundtransfer.dao;


import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FundsTransferDAO{
	public Transaction createFundsTransfer(TransactionData transactionData)throws DataInsertionException;
	public TransactionData getTransaction(UUID transId) throws DataRetrievalException;
	public boolean updateTransaction(TransactionData transactionData)throws DataInsertionException;
	public void deleteTransaction(UUID transId)throws DataRetrievalException;
}
