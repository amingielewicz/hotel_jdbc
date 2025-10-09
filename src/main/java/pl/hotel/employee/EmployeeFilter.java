package pl.hotel.employee;

public class EmployeeFilter {

    private int id;
    private String name;
    private String surname;
    private String role;
    private String personalSkill;

    public EmployeeFilter(String name, String surname, String role, String personalSkill) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.personalSkill = personalSkill;
    }

    public EmployeeFilter(int id, String name, String surname, String role, String personalSkill) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.personalSkill = personalSkill;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public String getPersonalSkill() { return personalSkill; }
}

