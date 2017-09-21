package be.ing.fundtransfer.dao;


import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {

	public User createUser(User user) throws DataInsertionException;
	public User getUserByEmail(String userEamil) throws DataRetrievalException;
	public User getUserByUserName(String userName)throws DataRetrievalException;
	public User getEmailAndMobile(String userEmail,String userPhone) throws DataRetrievalException;
	public boolean updateUser(UserData userData)throws DataInsertionException;
	public List<User> findAllUsers()throws DataRetrievalException;
	public void deleteUser(User user) throws DataRetrievalException;
}
