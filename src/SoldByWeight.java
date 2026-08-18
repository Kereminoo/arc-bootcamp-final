public interface SoldByWeight {
    double getPricePerKg();

    default double calculateBulkPrice(double amountKg) {
        return amountKg * getPricePerKg();
    }
}
