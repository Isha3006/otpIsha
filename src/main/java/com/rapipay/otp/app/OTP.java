package com.rapipay.otp.app;

import java.sql.Date;
import java.util.Random;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

@Entity
@Table(name="otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer userId;
    private String channelName;
    private Integer orderId;
    @Nullable
    private String email;
    @Nullable
    private String phoneNo;
    private Integer otp;
    private boolean verified;
    private long created_at;

    public OTP() {
    	
    }

   
    
    public OTP(String string, String string2, int i, boolean b, int j) {
		// TODO Auto-generated constructor stub
	}

	public OTP(int i, String string, String string2) {
		// TODO Auto-generated constructor stub
		this.userId=i;
		this.channelName=string;
		this.phoneNo=string2;
	}

	public OTP(int i, String string, String string2, boolean b) {
		// TODO Auto-generated constructor stub
		this.userId=i;
		this.channelName=string;
		this.email=string2;
		this.verified=b;
	}

	

	public OTP(String string, int i) {
		// TODO Auto-generated constructor stub
		this.email=string;
		this.otp=i;
	}



	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
    public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	

	

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId() {
		this.orderId =1000+new Random().nextInt(9000);
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

	@Override
	public String toString() {
		return "OTP [userId=" + userId + ", channelName=" + channelName + ", orderId=" + orderId + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", otp=" + otp + ", verified=" + verified + ", created_at=" + created_at
				+ "]";
	}
	
}
