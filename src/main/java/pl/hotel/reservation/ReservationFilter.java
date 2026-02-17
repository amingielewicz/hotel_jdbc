package pl.hotel.reservation;

import pl.hotel.room.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationFilter {

    public static int counterId;
    private int id;
    private int customerId;
    private LocalDate startReservationDate;
    private LocalDate endReservationDate;
    private int roomNumber;
    private BigDecimal totalAmount;
    private BigDecimal deposit;
    private boolean isFullPaid;
    private int employerId;
    private LocalDateTime createdReservationDate;

    public ReservationFilter(int id, int customerId, LocalDate startReservationDate, LocalDate endReservationDate, int roomNumber, BigDecimal totalAmount, BigDecimal deposit, boolean isFullPaid, int employerId, LocalDateTime createdReservationDate) {
        this.id = id;
        this.customerId = customerId;
        this.startReservationDate = startReservationDate;
        this.endReservationDate = endReservationDate;
        this.roomNumber = roomNumber;
        this.totalAmount = totalAmount;
        this.deposit = deposit;
        this.isFullPaid = isFullPaid;
        this.employerId = employerId;
        this.createdReservationDate = createdReservationDate;
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

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public LocalDateTime getCreatedDate() {
        return createdReservationDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdReservationDate = createdDate;
    }
}
