package be.ing.fundtransfer.dao.impl;

import be.ing.fundtransfer.dao.UserDetailsDAO;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.repository.UserDetailsRepository;
import be.ing.fundtransfer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsDAOImpl implements UserDetailsDAO {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public String createUserdDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
        return Constants.SUCCESS;
    }

    @Override
    public UserDetails getUserByUserDetailsName(String userName) {
        UserDetails fetchedUserDetails = userDetailsRepository.findByUserName(userName);
        return fetchedUserDetails;
    }

    @Override
    public void deleteUserDetails(UserDetails userDetails) throws DataRetrievalException {
        userDetailsRepository.delete(userDetails.getId());
    }
}
