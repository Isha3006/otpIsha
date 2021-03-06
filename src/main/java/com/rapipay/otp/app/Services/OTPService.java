package com.rapipay.otp.app.Services;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rapipay.otp.app.OTP;
import com.rapipay.otp.app.OTPRepository;
import com.rapipay.otp.app.OTPResponse;
import com.rapipay.otp.controller.OTPController;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidAttemptsException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.exception.InvalidOTPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OTPService.class);
	
	@Value("${NoOfAttempts}")
	private int count;
	@Value("${Timelimit}")
	private int time;

    @Autowired
    public OTPRepository dao;

//    @Autowired
//    public EmailService emailService;

    public Integer generateOTP() {
    	LOGGER.info("Inside generate otp");
        return (int) ((Math.random() * 900000) + 100000);
    }
    public boolean checkEmail(String email) {
    	LOGGER.info("Inside check email otp method");
        String regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w{2,}([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(regex);
            
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
       
        }
    
    
    public String sendOTP(OTP inputotp) {
    	LOGGER.info("Inside send otp method");
    	List<OTP>checkEmail=dao.findByEmailAndVerified(inputotp.getEmail(), false);
    	
    	int i=1;
        for(OTP b:checkEmail) {
        	if (b.getCreated_at() > (new Date().getTime() / 1000) - time) {
        		i++;
        		if(i>count) {
        			throw new InvalidAttemptsException("Try After sometime!!");
        		}
        		
        	}
        }
    	if(checkEmail(inputotp.getEmail())) {
    		OTP otp = new OTP();
    		
            otp.setEmail(inputotp.getEmail());
            otp.setOrderId();
            otp.setChannelName(inputotp.getChannelName());
            otp.setPhoneNo(inputotp.getPhoneNo());
            otp.setOtp(generateOTP());
            otp.setVerified(false);
            otp.setCreated_at(new Date().getTime() /1000);
//            sendOTPMail(otp);
            dao.saveAndFlush(otp);
            return "successfully";
    	}
        throw new InvalidEmailException("Entered Email is Wrong!!");
    }
    
   
    public String verifyOTP(OTP otp){
    	LOGGER.info("Inside verify otp");
        OTPResponse response = new OTPResponse();
        List<OTP>checkEmail=dao.findByEmailAndVerified(otp.getEmail(), false);
        
        if(checkEmail==null || checkEmail.size()==0) {
        	
        	response.setStatus(false);
        	
        	throw new EmailNotFoundException("Email Not Exixts");
       
        }
       
        List<OTP> otps;
        otps = (dao.findByEmailAndOtpAndVerified(otp.getEmail(),otp.getOtp(),false));
        
       
        if (otps == null || otps.size() == 0) {
            response.setMessage("OTP verification failed");
                response.setStatus(false);
                response.toString();
                throw new InvalidOTPException("Entered OTP is Wrong!");
        }

        for(OTP o : otps) {
                if (o.getCreated_at() > (new Date().getTime() / 1000) - 300) {
                    o.setVerified(true);
                    dao.save(o);
                    response.setMessage("OTP verified successfully");
                    response.setStatus(true);
                    return "OTP verified successfully";
                }
            }
            response.setMessage("This OTP has expired. Please request a new one");
            response.setStatus(false);
   
            return response.toString();
        }

		public String sendOTPSMS(OTP inputotp) {
			LOGGER.info("Inside send otp to sms method");
			// TODO Auto-generated method stub
			OTP otp = new OTP();
            otp.setEmail(inputotp.getEmail());
            otp.setOrderId();
            otp.setChannelName(inputotp.getChannelName());
            otp.setPhoneNo(inputotp.getPhoneNo());
            otp.setOtp(generateOTP());
            System.out.println("OTP-> "+otp.getOtp());
            otp.setVerified(false);
            otp.setCreated_at(new Date().getTime() /1000);
            dao.saveAndFlush(otp);
            return "successfully";
			
		}
    }
