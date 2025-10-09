package pl.hotel.employee;

import java.util.List;

public interface EmployeeServiceInterface {
    Employee create(Employee employee);
    List<Employee> findAll();
    Employee getEmployee(EmployeeFilter employeeFilter);
    Employee update(Employee updateEmployee);
    void delete(int id);

}
