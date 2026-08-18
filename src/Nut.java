public class Nut extends Hardware implements SoldByWeight{
    private double diameterMm;
    private double threadPitchMm; // if i understood it correctly
    private double pricePerKg;

    public Nut(int partID, String displayName, int amountInStock, double unitCost, double diameterMm, double threadPitchMm, double pricePerKg) {
        super(partID, displayName, amountInStock, unitCost);
        
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter (mm) cannot be zero or below!");
        }
        if (threadPitchMm <= 0) {
            throw new IllegalArgumentException("Thread pitch (mm) cannot be zero or below!");
        }
        if (pricePerKg <= 0) {
            throw new IllegalArgumentException("Price per kilogram cannot be zero or negative!");
        }

        this.diameterMm = diameterMm;
        this.threadPitchMm = threadPitchMm;
        this.pricePerKg = pricePerKg;
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

    @Override
    public double getPricePerKg() {
        return pricePerKg;
    }
}
