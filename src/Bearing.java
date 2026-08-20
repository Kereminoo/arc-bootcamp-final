public class Bearing extends Hardware {
    private double boreDiameterMm;
    private double staticLoadRatingN;

    public Bearing(int partID, String displayName, int amountInStock, double unitCost, double boreDiameterMm,
            double staticLoadRatingN) {
        super(partID, displayName, amountInStock, unitCost);
        if (boreDiameterMm <= 0) {
            throw new IllegalArgumentException("Bore diameter cannot be zero or negative!");
        }
        if (staticLoadRatingN <= 0) {
            throw new IllegalArgumentException("Static load rating cannot be zero or negative newtons!");
        }
        this.boreDiameterMm = boreDiameterMm;
        this.staticLoadRatingN = staticLoadRatingN;
    }

    @Override
    public String getCategoryDetails() {
        return String.format(
                "Category: Bearing, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Bore Diameter (mm): %f, Static Load Rating (N): %f.",
                getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), boreDiameterMm, staticLoadRatingN);
    }

    public double getBoreDiameterMm() {
        return boreDiameterMm;
    }

    public double getStaticLoadRatingN() {
        return staticLoadRatingN;
    }
}
