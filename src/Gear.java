public class Gear extends Hardware {
    public int teethCount;
    public GearMaterial material;
    public double pitchDiameterMm;

    public Gear(int partID, String displayName, int amountInStock, double unitCost, int teethCount, GearMaterial material, double pitchDiameterMm) {
        super(partID, displayName, amountInStock, unitCost);

        if (teethCount <= 0) {
            throw new IllegalArgumentException("Teeth count cannot be zero or negative!");
        }
        if (pitchDiameterMm <= 0) {
            throw new IllegalArgumentException("Pitch diameter cannot be zero or negative!");
        }
        this.teethCount = teethCount;
        this.material = material;
        this.pitchDiameterMm = pitchDiameterMm;
    }

    @Override
    public String getCategoryDetails() {
        return String.format("Category: Bolt, PartID: %d, Display Name: %s, Amount Left In Stock: %d, Unit Cost: %f, Teeth Count: %d, Material: %s, Pitch Diameter (mm): %f",
                                                  getPartID(), getDisplayName(), getAmountInStock(), getUnitCost(), teethCount, material, pitchDiameterMm 
        );
    }
    
    public int getTeethCount() {
        return teethCount;
    }

    public GearMaterial getMaterial() {
        return material;
    }

    public double getPitchDiameterMm() {
        return pitchDiameterMm;
    }
}
