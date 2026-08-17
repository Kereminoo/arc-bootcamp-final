public abstract class Hardware {
    private int partID;
    private String displayName;
    private int amountInStock;
    private double unitCost;

    public Hardware(int partID, String displayName, int amountInStock, double unitCost) {
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
}
