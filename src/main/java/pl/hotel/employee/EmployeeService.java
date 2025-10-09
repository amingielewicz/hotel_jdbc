package pl.hotel.employee;

import java.sql.Connection;
import java.util.List;


public class EmployeeService implements EmployeeServiceInterface {

    private EmployeeRepository employeeRepository;

    public EmployeeService(Connection connection) {
        employeeRepository = new EmployeeRepository(connection);
    }

    public Employee create(Employee employee) {
        return employeeRepository.create(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(EmployeeFilter employeeFilter) {
        return employeeRepository.get(employeeFilter);
    }

    public Employee update(Employee updateEmployee) {
        return employeeRepository.update(updateEmployee);
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }

}
