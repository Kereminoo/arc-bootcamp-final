public class DrillBit extends Hardware {
    private double diameterMm;
    private DrillBitType type;

    public DrillBit(int partID, String displayName, int amountInStock, double unitCost, double diameterMm, DrillBitType type) {
        super(partID, displayName, amountInStock, unitCost);
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter cannot be zero or negative!");
        }
        this.diameterMm = diameterMm;
        this.type = type;
    }

    @Override
    public String getCategoryDetails() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategoryDetails'");
    }
}
