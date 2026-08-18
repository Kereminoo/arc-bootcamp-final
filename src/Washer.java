public class Washer extends Hardware {
    private double outerDiameterMm;
    private double innerDiameterMm;
    private double thicknessMm;

    public Washer(int partID, String displayName, int amountInStock, double unitCost, double outerDiameterMm, double innerDiameterMm, double thicknessMm) {
        super(partID, displayName, amountInStock, unitCost);
        if (outerDiameterMm <= 0) {
            throw new IllegalArgumentException("Outer diameter cannot be zero or negative!");
        }
        if (innerDiameterMm <= 0) {
            throw new IllegalArgumentException("Inner diameter cannot be zero or negative!");
        }
        if (thicknessMm <= 0) {
            throw new IllegalArgumentException("Thickness cannot be zero or negative!");
        }
        this.outerDiameterMm = outerDiameterMm;
        this.innerDiameterMm = innerDiameterMm;
        this.thicknessMm = thicknessMm;
    }
    
    @Override
    public String getCategoryDetails() {
        return String.format("Category: Washer, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Outer Diameter (mm): %f, Inner Diameter (mm): %f, Thickness (mm): %f",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), outerDiameterMm, innerDiameterMm,  thicknessMm
        );
    }

    public double getOuterDiameterMm() {
        return outerDiameterMm;
    }

    public double getInnerDiameterMm() {
        return innerDiameterMm;
    }

    public double getThicknessMm() {
        return thicknessMm;
    }
}
