package pl.hotel.customer;

public class CustomerFilter {

    private int id;
    private String name;
    private String surname;
    private String pesel;

    public CustomerFilter(String name, String surname, String pesel) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

    public CustomerFilter(int id, String name, String surname, String pesel) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
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

    public String getPesel() {
        return pesel;
    }
}