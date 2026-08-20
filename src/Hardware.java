public abstract class Hardware {
    private int partID;
    private String displayName;
    private int amountInStock;
    private double unitCost;

    public Hardware(int partID, String displayName, int amountInStock, double unitCost) {
        if (partID <= 0) {
            throw new IllegalArgumentException("PartID must be at least 1.");
        }
        if (displayName == null || displayName.equals("")) {
            throw new IllegalArgumentException("Hardware must have a display name.");
        }
        if (amountInStock < 0) {
            throw new IllegalArgumentException("Hardware cannot have negative stock.");
        }
        if (unitCost <= 0) {
            throw new IllegalArgumentException(
                    "Hardware cannot be free or cost negative money (we ain't giving money away bro).");
        }
        this.partID = partID;
        this.displayName = displayName;
        this.amountInStock = amountInStock;
        this.unitCost = unitCost;
    }

    public int getPartID() {
        return partID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setAmountInStock(int amountInStock) {
        if (amountInStock < 0) {
            throw new IllegalArgumentException("Amount in stock cannot be negative!");
        }
        this.amountInStock = amountInStock;
    }

    public abstract String getCategoryDetails();

    @Override
    public String toString() {
        return String.format("Hardware[partID=%d, displayName=%s, amountInStock=%d, unitCost=%f]",
                partID, displayName, amountInStock, unitCost);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Hardware))
            return false;
        if (this == other)
            return true;
        return this.partID == ((Hardware) other).partID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(partID);
    }
}
