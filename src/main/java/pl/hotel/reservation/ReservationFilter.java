package pl.hotel.reservation;

import pl.hotel.room.Room;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationFilter {

    public static int counterId;
    private int id;
    private int customerId;
    private LocalDate startReservationDate;
    private LocalDate endReservationDate;
    private Room room;
    private BigDecimal sum;
    private BigDecimal deposit;
    private boolean isFullPaid;
    private int employerId;

    public ReservationFilter(int id, int customerId, LocalDate startReservationDate, LocalDate endReservationDate, Room room, BigDecimal sum, BigDecimal deposit, boolean isFullPaid, int employerId) {
        this.id = id;
        this.customerId = customerId;
        this.startReservationDate = startReservationDate;
        this.endReservationDate = endReservationDate;
        this.room = room;
        this.sum = sum;
        this.deposit = deposit;
        this.isFullPaid = isFullPaid;
        this.employerId = employerId;
    }




    public static int getCounterId() {
        return counterId;
    }

    public static void setCounterId(int counterId) {
        ReservationFilter.counterId = counterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getStartReservationDate() {
        return startReservationDate;
    }

    public void setStartReservationDate(LocalDate startReservationDate) {
        this.startReservationDate = startReservationDate;
    }

    public LocalDate getEndReservationDate() {
        return endReservationDate;
    }

    public void setEndReservationDate(LocalDate endReservationDate) {
        this.endReservationDate = endReservationDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public boolean isFullPaid() {
        return isFullPaid;
    }

    public void setFullPaid(boolean fullPaid) {
        isFullPaid = fullPaid;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }
}
