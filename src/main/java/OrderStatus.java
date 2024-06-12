public enum OrderStatus {
    PROCESSING("processing"),
    IN_DELIVERY("in delivery"),
    COMPLETED("completed");

    private final String statusString;

    OrderStatus(String statusString) {
        this.statusString = statusString;
    }

    public String getStatusString() {
        return statusString;
    }
}
