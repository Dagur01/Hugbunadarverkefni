package is.hi.hbv501g.verkefni.Persistence.Entities;

public class Payment {
    private Booking booking;
    private Long amount;
    private PaymentStatus status;
    private Long paymentId;
    private PaymentMethod method;

    public Payment() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void authorize() {
        this.status = PaymentStatus.AUTHORIZED;
    }

    public void refund(Long amount) {
        this.status = PaymentStatus.REFUNDED;
    }

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, CASH, ONLINE
    }

    public enum PaymentStatus {
        PENDING, AUTHORIZED, REFUNDED, FAILED
    }
}

    
    