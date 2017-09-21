package be.ing.fundtransfer.controller;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.util.Constants;
import be.ing.fundtransfer.service.UserService;
import com.datastax.driver.core.utils.UUIDs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.glassfish.jersey.message.internal.HttpHeaderReader.Event.Control;

@Component
@Path("/login")
public class LoginController {
    @Autowired
    UserService userService;


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetails(@PathParam("id") String id) throws DataRetrievalException {
        System.out.println("ID::"+id+":");
        UserData userData =userService.getByUserName(id);
        return Response.status(200).entity(userData).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response registerUser(UserData userData) {
        try{
       String Status = userService.createUser(userData);
                if(StringUtils.equals(Status,Constants.SUCCESS)){
                return Response.status(200).entity(userData).build();
            }else{
                return Response.status(400).build();
            }
        }catch (DataInsertionException exp){
            return Response.status(400).build();
        }
}
   @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate")
    public Response validateLogin(UserData userData) {
        try{
            UserData userDataFetched = userService.getByUserName(userData.getUsername());
            if(userDataFetched.getPassword().equals(userData.getPassword())) {
                //return Response.status(200).entity(userDataFetched).build();
                return Response.status(200).entity(userData).header("Access-Control-Allow-Origin","*").build();//to work two different ports
            }else{
                return Response.status(400).build();
            }
        }catch (DataRetrievalException exp){
            return Response.status(400).build();
        }
    }
}