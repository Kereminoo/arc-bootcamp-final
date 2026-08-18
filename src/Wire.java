public class Wire extends Hardware {
    private double gaugeMmSquared; // gauge is measured with milimeters squared
    private double lengthMeters; // inconsistent naming but screw it
    private WireInsulationColor insulationColor;
    
    public Wire(int partID, String displayName, int amountInStock, double unitCost, double gaugeMmSquared, double lengthMeters, WireInsulationColor insulationColor) {
        super(partID, displayName, amountInStock, unitCost);
        if (gaugeMmSquared <= 0) {
            throw new IllegalArgumentException("Gauge cannot be zero or negative!");
        }
         if (lengthMeters <= 0) {
            throw new IllegalArgumentException("Length of the wire cannot be zero or negative!");
        }
        this.gaugeMmSquared = gaugeMmSquared;
        this.lengthMeters = lengthMeters;
        this.insulationColor = insulationColor;
    }
    
    @Override
    public String getCategoryDetails() {
        return String.format("Category: Wire, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Gauge (mm²): %f, Length (m): %f, Insulation Color: %s",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), gaugeMmSquared, lengthMeters, insulationColor
        );
    }

    public double getGaugeMmSquared() {
        return gaugeMmSquared;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public WireInsulationColor getInsulationColor() {
        return insulationColor;
    }
}
