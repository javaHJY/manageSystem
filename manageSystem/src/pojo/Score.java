package pojo;

public class Score {
	private Integer id;
	private Employee emp;
	private Project pro;
	private Double value;
	private String grade;

	public Score() {
		super();
	}

	public Score(Integer id, Employee emp, Project pro, Double value, String grade) {
		super();
		this.id = id;
		this.emp = emp;
		this.pro = pro;
		this.value = value;
		this.grade = grade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Project getPro() {
		return pro;
	}

	public void setPro(Project pro) {
		this.pro = pro;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", emp=" + emp + ", pro=" + pro + ", value=" + value + ", grade=" + grade + "]";
	}

}
