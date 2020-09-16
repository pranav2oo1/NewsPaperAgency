package billHistory;

import java.io.Serializable;

public class BillBean implements Serializable{

//	1 	billid Primary 	int(11) 	
//	2 	cust_mobile 	varchar(12)  	
//	3 	noofdays 	varchar(3) 	 	
//	4 	date_of_billing 	date  	
//	5 	amount 	float 	
//	6 	status 	tinyint(1)
	int id;
	String cust_mobile,noofdays,date_of_billing,status;
	float amount;
	public BillBean()
	{
		
	}
	public BillBean(int id, String cust_mobile, String noofdays, String date_of_billing, String status, float amount) {
		super();
		this.id = id;
		this.cust_mobile = cust_mobile;
		this.noofdays = noofdays;
		this.date_of_billing = date_of_billing;
		this.status = status;
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BillBean [id=" + id + ", cust_mobile=" + cust_mobile + ", noofdays=" + noofdays + ", date_of_billing="
				+ date_of_billing + ", status=" + status + ", amount=" + amount + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCust_mobile() {
		return cust_mobile;
	}
	public void setCust_mobile(String cust_mobile) {
		this.cust_mobile = cust_mobile;
	}
	public String getNoofdays() {
		return noofdays;
	}
	public void setNoofdays(String noofdays) {
		this.noofdays = noofdays;
	}
	public String getDate_of_billing() {
		return date_of_billing;
	}
	public void setDate_of_billing(String date_of_billing) {
		this.date_of_billing = date_of_billing;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
