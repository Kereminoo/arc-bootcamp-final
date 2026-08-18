public class Bolt extends Hardware implements SoldByWeight {
    private double lengthMm;
    private double diameterMm;
    private HeadType headType;
    private double pricePerKg;

    public Bolt(int partID, String displayName, int amountInStock, double unitCost, double lengthMm, double diameterMm, HeadType headType, double pricePerKg) {
        super(partID, displayName, amountInStock, unitCost);

        if (lengthMm <= 0) {
            throw new IllegalArgumentException("Length (mm) cannot be zero or below!");
        }
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter (mm) cannot be zero or below!");
        }
        if (pricePerKg <= 0) {
            throw new IllegalArgumentException("Price per kilogram cannot be zero or negative!");
        }

        this.lengthMm = lengthMm;
        this.diameterMm = diameterMm;
        this.headType = headType;
        this.pricePerKg = pricePerKg;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Bolt, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Length (mm): %f, Diameter (mm): %f, Head Type: %s.",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), lengthMm, diameterMm, headType 
        );
    }

    public double getLengthMm() {
        return lengthMm;
    }

    public double getDiameterMm() {
        return diameterMm;
    }

    public HeadType getHeadType() {
        return headType;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }
}
