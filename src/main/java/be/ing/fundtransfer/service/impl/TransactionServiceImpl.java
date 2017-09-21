package be.ing.fundtransfer.service.impl;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.dao.FundsTransferDAO;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.service.TransactionService;
import be.ing.fundtransfer.util.Constants;
import be.ing.fundtransfer.util.UserDaoWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    FundsTransferDAO fundsTransferDAO;

    @Override
    public String createTransaction(TransactionData transactionData) throws DataInsertionException {
        try {
            Transaction transaction= fundsTransferDAO.createFundsTransfer(transactionData);

            if(transaction!=null){
                return Constants.SUCCESS;
            }else{
                return Constants.FAILED;
            }
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }

    @Override
    public TransactionData getTransaction(String transactionUid) throws DataRetrievalException {
        try {
           return fundsTransferDAO.getTransaction(UUID.fromString(transactionUid));
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public String updateTransaction(TransactionData transactionData)throws DataInsertionException {
        try {
            Transaction transaction= fundsTransferDAO.createFundsTransfer(transactionData);
            if(transaction!=null){
                return Constants.SUCCESS;
            }else{
                return Constants.FAILED;
            }
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }
    @Override
    public void deleteTransaction(TransactionData transactionData) throws DataRetrievalException {
        fundsTransferDAO.deleteTransaction(UUID.fromString(transactionData.getId()));
    }


}
