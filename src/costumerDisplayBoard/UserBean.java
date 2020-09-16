package costumerDisplayBoard;
public class UserBean {
	public UserBean() {}
	public UserBean(String name, String mobile, String address, String papers, String hawker, String curdate) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.papers = papers;
		this.hawker = hawker;
		this.curdate = curdate;
	}
	//name	mobile	address	papers	hawker	curdate
	String name,mobile,address,papers,hawker,curdate;

	@Override
	public String toString() {
		return "UserBean [name=" + name + ", mobile=" + mobile + ", address=" + address + ", papers=" + papers
				+ ", hawker=" + hawker + ", curdate=" + curdate + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getHawker() {
		return hawker;
	}

	public void setHawker(String hawker) {
		this.hawker = hawker;
	}

	public String getCurdate() {
		return curdate;
	}

	public void setCurdate(String curdate) {
		this.curdate = curdate;
	}
	
	

}
