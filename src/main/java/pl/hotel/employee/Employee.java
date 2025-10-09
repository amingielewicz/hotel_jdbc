package pl.hotel.employee;

import pl.hotel.utils.Common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee extends Common {

    public static int counterId = 1;
    private int id = counterId;
    private String role;
    private String personalSkill;
    private LocalDate createDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;

    public Employee(String name, String surname, String role, String personalSkill, LocalDate createDate, LocalDateTime updateDate, LocalDateTime deleteDate) {
        super(name, surname);
        this.role = role;
        this.personalSkill = personalSkill;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
    }

    public Employee(int id,  String name, String surname, String role, String personalSkill, LocalDate createDate, LocalDateTime updateDate, LocalDateTime deleteDate) {
        super(name, surname);
        this.id = id;
        this.role = role;
        this.personalSkill = personalSkill;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
    }

    public Employee(String name, String surname, String role, String personalSkill, LocalDate createDate) {
        super(name, surname);
        this.role = role;
        this.personalSkill = personalSkill;
        this.createDate = createDate;
    }

    public Employee() {
    }

    public Employee(int id, String name, String surname, String role, String personalSkill, LocalDate createDate) {
        super(name, surname);
        this.id = id;
        this.role = role;
        this.personalSkill = personalSkill;
        this.createDate = createDate;
    }

    public Employee(int id, String name, String surname, String role, String personalSkill) {
        super(name, surname);
        this.id = id;
        this.role = role;
        this.personalSkill = personalSkill;
    }

    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public String getPersonalSkill() {
        return personalSkill;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setPersonalSkill(String personalSkill) {
        this.personalSkill = personalSkill;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " " +
                "Imię: " + super.getName() + " " +
                "Nazwisko: " + super.getSurname() + " " +
                "stanowisko: " + getRole() + " " +
                "umiejętności: " + getPersonalSkill();
    }


}