package be.ing.fundtransfer.dao.impl;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.dao.UserDAO;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.repository.UserDetailsRepository;
import be.ing.fundtransfer.repository.UserRepository;
import be.ing.fundtransfer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
    UserRepository userRepository;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Override
	public User createUser(User user) throws DataInsertionException {

		try {
			return userRepository.save(user);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new DataInsertionException("Failed to Create User : " + ex.getLocalizedMessage());
		}
		//return null;
	}


	
	@Override
	public User getUserByEmail(String email) throws DataRetrievalException {

		try {
			User userObj = userRepository.findByEmail(email);
			if (userObj != null) {
				return userObj;
			}else{
				throw new DataRetrievalException("User Not found BY Email : " );
			}
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " + exp.getLocalizedMessage());
		}
	}
	
	@Override
	public User getUserByUserName(String userName) throws DataRetrievalException {

		try {
			User userObj = userRepository.findByUsername(userName);
			if (userObj != null) {
				return userObj;
			}else{
				throw new DataRetrievalException("User Not found BY  " + userName);
			}
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " + exp.getLocalizedMessage());
		}
	}	

	@Override
	public boolean updateUser(UserData userData) throws DataInsertionException  {
		UserDetails userDetails = userDetailsRepository.findByUserName(userData.getUsername());
		userDetails.setCurAmount(userData.getCurAmount());
		userDetails.setAvalAmount(userData.getAvalAmount());
		userDetailsRepository.save(userDetails);
		return true;
	}
	
	private User buildUser( User userObj){
		User user = new User();
		UserDetails userDetails  = null;

		userDetails = userDetailsRepository.findByUserName(userObj.getUserName());
		System.out.println("able to fetch userDetails ");
		user.setUserName(userObj.getUserName());
		user.setPassword(userObj.getPassword());
		user.setFirstName(userObj.getFirstName());
		user.setLastName(userObj.getLastName());
		user.setEmail(userObj.getEmail());
		user.setMobileNumber(userObj.getMobileNumber());

		return user;
	}

	@Override
	public List<User> findAllUsers() {
		return null;
	}

	@Override
	public User getEmailAndMobile(String userEmail, String userPhone) throws DataRetrievalException {

		try {
			User userObj = userRepository.findByEmailAndMobile(userEmail,userPhone);
			if (userObj != null) {
				return userObj;
			}else{
				throw new DataRetrievalException("User Not found BY Email : " );
			}
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " + exp.getLocalizedMessage());
		}
	}

	@Override
	public void deleteUser(User user) throws DataRetrievalException {
		userRepository.delete(user.getId());
	}
}
