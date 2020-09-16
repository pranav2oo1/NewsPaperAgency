package hawkerDisplayBoard;

public class HawkerBean {
//	name	mobile	address	areas	aadharpic	salary	doj
	String name,mobile,address,areas,aadharpic,doj;
	int salary;
	public HawkerBean(String name, String mobile, String address, String areas, String aadharpic, String doj,
			int salary) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.areas = areas;
		this.aadharpic = aadharpic;
		this.doj = doj;
		this.salary = salary;
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
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getAadharpic() {
		return aadharpic;
	}
	public void setAadharpic(String aadharpic) {
		this.aadharpic = aadharpic;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "HawkerBean [name=" + name + ", mobile=" + mobile + ", address=" + address + ", areas=" + areas
				+ ", aadharpic=" + aadharpic + ", doj=" + doj + ", salary=" + salary + "]";
	}
	
	
}
