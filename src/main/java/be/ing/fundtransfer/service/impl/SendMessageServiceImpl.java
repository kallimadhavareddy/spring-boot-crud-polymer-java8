package be.ing.fundtransfer.service.impl;

import be.ing.fundtransfer.service.SendMessageService;
import be.ing.fundtransfer.util.Constants;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Override
    public String generateOTP() {
        int randomPin = (int)(Math.random()*9000)+1000;
        return String.valueOf(randomPin);
    }

    @Override
    public String sendOTP(String message, String mobile,String otp) {
        System.out.println("Testing OTP START");
        try {
            List<NameValuePair> senderDetailsList = new ArrayList<>();
            String toOtpMobileNumber = "+91" + mobile;
            TwilioRestClient client = new TwilioRestClient(Constants.TWILIO_ACCNT_NUMBER, Constants.AUTH_TOKEN);
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            senderDetailsList.add(new BasicNameValuePair("Body", message + Constants.BLANK + otp));
            senderDetailsList.add(new BasicNameValuePair("To", toOtpMobileNumber));
            senderDetailsList.add(new BasicNameValuePair("From", Constants.TWILIO_MOB_NUMBER));
            messageFactory.create(senderDetailsList);
            return Constants.SUCCESS;
        }catch(TwilioRestException ex){
            ex.printStackTrace();
            return "OTP_SEND_FAILED";
        }
    }
}
