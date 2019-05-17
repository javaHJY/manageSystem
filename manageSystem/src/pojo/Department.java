package pojo;

public class Department {
	private Integer id;
	private String name;
	private Integer empNums;
	public Department() {
		super();
	}
	public Department(Integer id, String name, Integer empNums) {
		super();
		this.id = id;
		this.name = name;
		this.empNums = empNums;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEmpNums() {
		return empNums;
	}
	public void setEmpNums(Integer empNums) {
		this.empNums = empNums;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", empNums=" + empNums + "]";
	}
	
}
