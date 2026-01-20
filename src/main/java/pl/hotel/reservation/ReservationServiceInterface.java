package pl.hotel.reservation;

import pl.hotel.employee.Employee;
import pl.hotel.employee.EmployeeFilter;

import java.util.List;

public interface ReservationServiceInterface {

    Reservation create(Reservation reservation);
    List<Reservation> findAll();
    Reservation getReservation(ReservationFilter reservationFilter);
    Reservation update(Reservation reservation);
    void delete(int id);
}
