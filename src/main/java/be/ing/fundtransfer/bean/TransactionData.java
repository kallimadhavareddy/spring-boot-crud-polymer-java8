package be.ing.fundtransfer.bean;

import org.springframework.data.cassandra.mapping.Column;

import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TransactionData {

	private String id;

	private String fromAccount;

	private String toAccount;

	private double trnsfAmount;

	private String fromOtp;

	private String toOtp;

	private String ibanNum;
	private String toAccountEmail;
	private String toAccountPhone;
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public double getTrnsfAmount() {
		return trnsfAmount;
	}

	public void setTrnsfAmount(double trnsfAmount) {
		this.trnsfAmount = trnsfAmount;
	}

	public String getFromOtp() {
		return fromOtp;
	}

	public void setFromOtp(String fromOtp) {
		this.fromOtp = fromOtp;
	}

	public String getToOtp() {
		return toOtp;
	}

	public void setToOtp(String toOtp) {
		this.toOtp = toOtp;
	}

	public String getIbanNum() {
		return ibanNum;
	}

	public void setIbanNum(String ibanNum) {
		this.ibanNum = ibanNum;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getToAccountEmail() {
		return toAccountEmail;
	}

	public void setToAccountEmail(String toAccountEmail) {
		this.toAccountEmail = toAccountEmail;
	}

	public String getToAccountPhone() {
		return toAccountPhone;
	}

	public void setToAccountPhone(String toAccountPhone) {
		this.toAccountPhone = toAccountPhone;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
