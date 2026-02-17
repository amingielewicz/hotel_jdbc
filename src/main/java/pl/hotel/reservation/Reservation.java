package pl.hotel.reservation;

import pl.hotel.utils.Common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation extends Common {

    private static int counterId = 1;
    private int id;
    private int customerId;
    private LocalDate startReservationDate;
    private LocalDate endReservationDate;
    private int roomNumber;
    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private boolean isFullPaid;
    private int employerId;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;
    private LocalDateTime createReservationDate;


    public Reservation(int id, int customerId, LocalDate startReservationDate,
                       LocalDate endReservationDate, int roomNumber,
                       BigDecimal totalAmount, BigDecimal depositAmount,
                       boolean isFullPaid, int employerId, LocalDateTime createDate) {
        this.id = id;
        this.customerId = customerId;
        this.startReservationDate = startReservationDate;
        this.endReservationDate = endReservationDate;
        this.roomNumber = roomNumber;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.isFullPaid = isFullPaid;
        this.employerId = employerId;
        this.createReservationDate = LocalDateTime.now();
    }

    public Reservation(int customerId, LocalDate startReservationDate, LocalDate endReservationDate, int roomNumber, BigDecimal totalAmount, BigDecimal depositAmount,
                       boolean isFullPaid, int employerId) {
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.startReservationDate = startReservationDate;
        this.endReservationDate = endReservationDate;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.isFullPaid = isFullPaid;
        this.employerId = employerId;
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
        return depositAmount;
    }

    public void setDeposit(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
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

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public LocalDateTime getCreateReservationDate() {
        return createReservationDate;
    }

    public void setCreateReservationDate(LocalDateTime createReservationDate) {
        this.createReservationDate = createReservationDate;
    }
}
