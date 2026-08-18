public class Bearing extends Hardware {
    public double boreDiameterMm;
    public double staticLoadRatingN;

    public Bearing(int partID, String displayName, int amountInStock, double unitCost, double boreDiameterMm, double staticLoadRatingG) {
        super(partID, displayName, amountInStock, unitCost);
        if (boreDiameterMm <= 0) {
            throw new IllegalArgumentException("Bore diameter cannot be zero or negative!");
        }
        if (staticLoadRatingG <= 0) {
            throw new IllegalArgumentException("Static load rating cannot be zero or negative newtons!");
        }
        this.boreDiameterMm = boreDiameterMm;
        this.staticLoadRatingN = staticLoadRatingG;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Bolt, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Bore Diameter (mm): %f, Static Load Rating (N): %f.",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), boreDiameterMm, staticLoadRatingN
        );
    }
    
    public double getBoreDiameterMm() {
        return boreDiameterMm;
    }

    public double getStaticLoadRatingN() {
        return staticLoadRatingN;
    }
}
