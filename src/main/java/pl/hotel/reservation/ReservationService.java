package pl.hotel.reservation;

import java.sql.Connection;
import java.util.List;

public class ReservationService implements ReservationServiceInterface {

    private ReservationRepository reservationRepository;

    public ReservationService(Connection connection) {
        reservationRepository = new ReservationRepository(connection);
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.create(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation update(Reservation updateReservation) {
        return reservationRepository.update(updateReservation);
    }

    public void delete(int id) {
        reservationRepository.delete(id);
    }

    public Reservation getReservation(ReservationFilter reservationFilter) {
        return reservationRepository.get(reservationFilter);
    }
}
