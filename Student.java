package model;

public class Student {
    private int id;           // DB primary key
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String course;

    // Constructor without ID (for insert)
    public Student(String firstName, String lastName, String email, String phone, String course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.course = course;
    }

    // Constructor with ID (for update)
    public Student(int id, String firstName, String lastName, String email, String phone, String course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.course = course;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

	public Object getstudentID() {
		// TODO Auto-generated method stub
		return null;
	}
}
