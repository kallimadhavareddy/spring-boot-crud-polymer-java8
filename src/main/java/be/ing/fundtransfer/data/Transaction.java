package be.ing.fundtransfer.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

/*import org.apache.commons.lang.builder.ToStringBuilder;*/

@Table(value="user_transactions")
public class Transaction {

	@PrimaryKey
	private UUID id;
	
	@Column(value = "from_user_name")	
	private String fromAccountUser;
	
	@Column(value = "to_user_name")	
	private String toAccountUser;
	
	@Column(value = "transfer_amount")		
	private String trnsfAmount;
	
	@Column(value = "from_otp")		
	private String fromOtp;

	@Column(value = "to_otp")		
	private String toOtp;
	
	@Column(value = "status")		
	private int status;

	@Column(value = "iban_num")
	private String ibanNum;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFromAccountUser() {
		return fromAccountUser;
	}

	public void setFromAccountUser(String fromAccountUser) {
		this.fromAccountUser = fromAccountUser;
	}

	public String getToAccountUser() {
		return toAccountUser;
	}

	public void setToAccountUser(String toAccountUser) {
		this.toAccountUser = toAccountUser;
	}

	public String getTrnsfAmount() {
		return trnsfAmount;
	}

	public void setTrnsfAmount(String trnsfAmount) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIbanNum() {
		return ibanNum;
	}

	public void setIbanNum(String ibanNum) {
		this.ibanNum = ibanNum;
	}

	@Override
	public String toString() 	{
	    return ToStringBuilder.reflectionToString(this);
	}

}
