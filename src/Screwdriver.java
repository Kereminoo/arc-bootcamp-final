public class Screwdriver extends Hardware {
    private double diameterMm;
    private ScrewdriverType type;

    public Screwdriver(int partID, String displayName, int amountInStock, double unitCost, double diameterMm, ScrewdriverType type) {
        super(partID, displayName, amountInStock, unitCost);
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter cannot be zero or negative!");
        }
        this.diameterMm = diameterMm;
        this.type = type;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Screwdriver, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Diameter (mm): %f, Type: %s,",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), diameterMm, type
        );
    }

    public double getDiameterMm() {
        return diameterMm;
    }

    public ScrewdriverType getType() {
        return type;
    }
}
