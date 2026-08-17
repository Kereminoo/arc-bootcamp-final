public class Nut extends Hardware{
    private double diameterMm;
    private double threadPitchMm; // if i understood it correctly

    public Nut(int partID, String displayName, int amountInStock, double unitCost, double diameterMm, double threadPitchMm) {
        super(partID, displayName, amountInStock, unitCost);
        
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter (mm) cannot be zero or below!");
        }
        if (threadPitchMm <= 0) {
            throw new IllegalArgumentException("Thread pitch (mm) cannot be zero or below!");
        }

        this.diameterMm = diameterMm;
        this.threadPitchMm = threadPitchMm;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Nut, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Diameter (mm): %f, Thread Pitch (mm): %f.",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), diameterMm, threadPitchMm 
        );
    }

    public double getDiameterMm() {
        return diameterMm;
    }
    
    public double getThreadPitchMm() {
        return threadPitchMm;
    }
}
