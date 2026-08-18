public class ThreadedRod extends Hardware implements SoldByWeight {
    private double diameterMm;
    private double lengthMeters;
    private double threadPitchMm;
    private double pricePerKg;

    public ThreadedRod(int partID, String displayName, int amountInStock, double unitCost, double diameterMm, double lengthMeters, double threadPitchMm, double pricePerKg) {
        super(partID, displayName, amountInStock, unitCost);
        if (diameterMm <= 0) {
            throw new IllegalArgumentException("Diameter cannot be zero or negative!");
        }
        if (lengthMeters <= 0) {
            throw new IllegalArgumentException("Length cannot be zero or negative!");
        }
        if (threadPitchMm <= 0) {
            throw new IllegalArgumentException("Thread pitch cannot be zero or negative!");
        }
        if (pricePerKg <= 0) {
            throw new IllegalArgumentException("Price per kilogram cannot be zero or negative!");
        }

        this.diameterMm = diameterMm;
        this.lengthMeters = lengthMeters;
        this.threadPitchMm = threadPitchMm;
        this.pricePerKg = pricePerKg;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Threaded Rod, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Diameter (mm): %f, Length (m): %f, Thread Pitch (mm): %f.",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), diameterMm, lengthMeters, threadPitchMm
        );
    }
    
    public double getDiameterMm() {
        return diameterMm;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public double getThreadPitchMm() {
        return threadPitchMm;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }
}
