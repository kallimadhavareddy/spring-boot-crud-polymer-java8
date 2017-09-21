package be.ing.fundtransfer.service.impl;



import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.dao.UserDAO;

import be.ing.fundtransfer.dao.UserDetailsDAO;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.util.Constants;
import be.ing.fundtransfer.service.UserService;
import be.ing.fundtransfer.util.UserDaoWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserDetailsDAO userDetailsDAO;


    @Override
    public String createUser(UserData userData) throws DataInsertionException{
        try {
            userDAO.createUser(UserDaoWraper.convertUserDataToUser(userData));
            userDetailsDAO.createUserdDetails(UserDaoWraper.convertUserDataToUserDetails(userData));
            return Constants.SUCCESS;
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }
    @Override
    public UserData getByUserName(String userName)throws DataRetrievalException {
        try {
            System.out.println(":userName::"+userName);
             User user = userDAO.getUserByUserName(userName);
             UserDetails userDetails =userDetailsDAO.getUserByUserDetailsName(user.getUserName());
              return UserDaoWraper.convertUserAndUserDetailsToUserData(user, userDetails);
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public UserData getByUserEmail(String email) throws DataRetrievalException{
        try {
            User user = userDAO.getUserByEmail(email);
            UserDetails userDetails =userDetailsDAO.getUserByUserDetailsName(user.getUserName());


            return UserDaoWraper.convertUserAndUserDetailsToUserData(user, userDetails);
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public String updateUser(UserData user) {
        return null;
    }

    @Override
    public String deleteUser(UserData user) throws DataRetrievalException {
        User deleteUser = userDAO.getUserByUserName(user.getUsername());
        UserDetails deleteUserDetails =userDetailsDAO.getUserByUserDetailsName(deleteUser.getUserName());
        userDAO.deleteUser(deleteUser);
        userDetailsDAO.deleteUserDetails(deleteUserDetails);

        return Constants.SUCCESS;

    }

    @Override
    public List<UserData> findAllUsers() throws DataRetrievalException{
        List<User> users = userDAO.findAllUsers();
        return null;
    }

    @Override
    public UserData getEmailAndMobile(String email, String mobile) throws DataRetrievalException {
        User user = userDAO.getEmailAndMobile(email,mobile);
        System.out.println(":::::::::::inside user");
        System.out.println(user);
        System.out.println(":::::::::::inside user and");
        UserDetails userDetails =userDetailsDAO.getUserByUserDetailsName(user.getUserName());
        System.out.println(":::::::::::inside userData");
        System.out.println(userDetails);
        System.out.println(":::::::::::inside userData");
        return UserDaoWraper.convertUserAndUserDetailsToUserData(user, userDetails);
    }
}
