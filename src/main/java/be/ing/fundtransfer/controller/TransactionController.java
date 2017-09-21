package be.ing.fundtransfer.controller;

import be.ing.fundtransfer.bean.EmailMessage;
import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.service.MailService;
import be.ing.fundtransfer.service.SendMessageService;
import be.ing.fundtransfer.service.TransactionService;
import be.ing.fundtransfer.service.UserService;
import be.ing.fundtransfer.util.Constants;
import com.datastax.driver.core.utils.UUIDs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    MailService mailService;
    @Autowired
    SendMessageService sendMessageService;
    @Autowired
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createTransaction(TransactionData transactionData) throws DataInsertionException {
        UserData userDataTo =null;
        UserData userDataFrom = null;
        try {
            userDataTo= userService.getEmailAndMobile(transactionData.getToAccountEmail(),transactionData.getToAccountPhone());//status 1
            System.out.println("Log::Transaction Controller::1");

            System.out.println(transactionData);

            transactionData.setStatus(1);
            System.out.println("::::user in Transaction Controller"+transactionData.getFromAccount());
            userDataFrom = userService.getByUserName(transactionData.getFromAccount());//status 2
            System.out.println(userDataFrom);
            transactionData.setStatus(2);
            System.out.println("Log::Transaction Controller::2");


            if(userDataTo!=null && userDataFrom!=null){
                String message="OTP Password Generated ";

                String toOtp = sendMessageService.generateOTP();
                String fromOtp = sendMessageService.generateOTP();
                String toOtpStatus = sendMessageService.sendOTP(message,userDataTo.getMobileNumber(),toOtp);
                String fromOtpStatus = sendMessageService.sendOTP(message,userDataFrom.getMobileNumber(),fromOtp);
                if(toOtpStatus.equals(Constants.SUCCESS) && fromOtpStatus.equals(Constants.SUCCESS)){
                    transactionData.setIbanNum(userDataTo.getIbanNum());
                    transactionData.setStatus(3);//status 3;
                    System.out.println("Log::Transaction Controller::OTP Sent Successfully::3");
                    transactionData.setToOtp(toOtp);
                    transactionData.setFromOtp(fromOtp);
                    transactionData.setToAccount(userDataTo.getUsername());
                    EmailMessage emailMesage= new EmailMessage();
                    emailMesage.setEmail(userDataTo.getEmail());
                    emailMesage.setUserName(userDataFrom.getUsername());
                    emailMesage.setTransactionId(transactionData.getId());
                    String status = mailService.sendEmail(emailMesage);
                    if(status.contains("OK")){
                        transactionData.setStatus(4);//status 4;
                    }
                }else{
                    return Response.status(400).build();
                }
                if(transactionData.getStatus()==4) {
                    System.out.println("Log::Transaction Control::4");
                    String status = transactionService.createTransaction(transactionData);

                    if (StringUtils.equals(status, Constants.SUCCESS)) {
                        return Response.status(200).entity(transactionData).build();
                    } else {
                        System.out.println("Log::Transaction Control::Transaction Failed Mail Not sent");
                        return Response.status(400).build();
                    }
                }else{
                    return Response.status(400).build();
                }
            }
           }catch(Exception ex){
            System.out.println("Log::Transaction Control::Transaction Failed::Data Not Fetched");
            ex.printStackTrace();
            return Response.status(400).build();
        }
        return Response.status(200).entity(transactionData).build();


    }
    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/retrieve/{id}")
    public Response getTransaction(@PathParam("id") String  transactionId)throws DataRetrievalException {
        TransactionData transactionDataFetched = transactionService.getTransaction(transactionId);
        if(transactionDataFetched !=null && transactionDataFetched.getFromAccount()!=null){
            return Response.status(200).entity(transactionDataFetched).build();
        }else{
            return Response.status(400).build();
        }
    }*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate")
    public Response validateTransaction(TransactionData transactionData)throws DataInsertionException {
       try {
           System.out.println("Log::::::::::::::::Validate Transaction::::::::::::::::");
           System.out.println(transactionData);

           System.out.println("Log::::::::::::::::Validate Transaction::::::::::::::::");
           if (transactionData.getStatus() == 4) {
               TransactionData fetchedTransactionData = transactionService.getTransaction(transactionData.getId());
               if (fetchedTransactionData!=null && StringUtils.equals(transactionData.getToOtp(), fetchedTransactionData.getToOtp())
                       && StringUtils.equals(transactionData.getFromOtp(), fetchedTransactionData.getFromOtp())
                       ) {
                   //Update data in the transaction
                   //get the user details
                   System.out.println("Log::::::::::::::::Validate Transaction::::::::::::::::Transferring funds");
                   UserData userDataFrom = userService.getByUserName(transactionData.getFromAccount());
                   if (userDataFrom != null) {
                       double currentAmount = userDataFrom.getCurAmount();
                       double availableAmount = userDataFrom.getAvalAmount();
                       availableAmount = availableAmount - transactionData.getTrnsfAmount();
                       userDataFrom.setAvalAmount(availableAmount);
                       userService.deleteUser(userDataFrom);
                       userDataFrom.setCurAmount(availableAmount);

                       String fromUserStatus = userService.createUser(userDataFrom);

                       if (StringUtils.equals(fromUserStatus, Constants.SUCCESS)) {
                           UserData fromDataTo = userService.getByUserName(transactionData.getToAccount());
                           if (fromDataTo != null && StringUtils.equals(fromDataTo.getIbanNum(),transactionData.getIbanNum())) {
                               double toCurrentAmount = fromDataTo.getCurAmount();
                               double toAvailableAmount = fromDataTo.getAvalAmount();
                               toAvailableAmount = toAvailableAmount + transactionData.getTrnsfAmount();
                               fromDataTo.setAvalAmount(toAvailableAmount);
                               fromDataTo.setCurAmount(toAvailableAmount);
                               userService.deleteUser(fromDataTo);
                               String toUserStatus = userService.createUser(fromDataTo);
                               if (StringUtils.equals(toUserStatus, Constants.SUCCESS)) {
                                   transactionData.setStatus(5);
                                   transactionService.deleteTransaction(transactionData);
                                   String status = transactionService.createTransaction(transactionData);
                                   if(StringUtils.equals(status, Constants.SUCCESS)){
                                       return Response.status(200).entity(transactionData).build();
                                   }else{
                                       return Response.status(400).build();
                                   }
                               }
                           }
                       }
                   }

               }
               System.out.println("Log::::::::::::::::Validate Transaction::::::::::::::::Transferring funds successful");
           }
       }catch(Exception ex){
           return Response.status(400).build();
       }
        return Response.status(400).build();
     }

    @GET
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction(@PathParam("uid") String uid) throws DataInsertionException {
        System.out.println("UID::"+uid);
        try {
            TransactionData transactionData = transactionService.getTransaction(uid);
            System.out.println("::::::::::::::::transactionData::::::::::::::");
            System.out.println(transactionData);
            System.out.println("::::::::::::::::transactionData::::::::::::::");

            if (transactionData.getStatus() == 4) {
                    return Response.status(200).entity(transactionData).build();
                } else {
                    return Response.status(400).build();
                }

        }catch(Exception ex){
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/tranJson")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionData getTransactionJson()throws DataInsertionException {
        TransactionData trn = new TransactionData();
        return trn;
    }
}