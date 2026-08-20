import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVIO {
    public static InventoryManager loadInventoryFromCSV(String filepath) throws FileNotFoundException {
        InventoryManager inventoryManager = new InventoryManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                // format is:
                // TYPE,partID,displayName,amountInStock,unitCost,type-specific fields
                // technically invalid CSV but hey who cares
                lineNumber++;

                // skip header row
                if (lineNumber == 1) {
                    continue;
                }

                try {
                    String[] splitLine = line.split(",");

                    String type = splitLine[0];
                    int partID = Integer.parseInt(splitLine[1]);
                    String displayName = splitLine[2];
                    int amountInStock = Integer.parseInt(splitLine[3]);
                    double unitCost = Double.parseDouble(splitLine[4]);
                    
                    Hardware newItem;

                    switch (type) {
                        case "BEARING":
                            double boreDiameterMm = Double.parseDouble(splitLine[5]);
                            double staticLoadRatingN = Double.parseDouble(splitLine[6]);
                            newItem = new Bearing(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                boreDiameterMm, 
                                staticLoadRatingN
                            );
                            break;
                        case "BOLT":
                            double lengthMm = Double.parseDouble(splitLine[5]);
                            double diameterMm = Double.parseDouble(splitLine[6]);
                            HeadType headType = HeadType.valueOf(splitLine[7]);
                            newItem = new Bolt(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                lengthMm, 
                                diameterMm, 
                                headType);
                            break;
                        case "DRILLBIT":
                            diameterMm = Double.parseDouble(splitLine[5]);  
                            DrillBitType drillBitType = DrillBitType.valueOf(splitLine[6]);
                            newItem = new DrillBit(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                diameterMm, 
                                drillBitType
                            );
                            break;
                        case "GEAR":
                            int teethCount = Integer.parseInt(splitLine[5]);
                            GearMaterial material = GearMaterial.valueOf(splitLine[6]);
                            double pitchDiameterMm = Double.parseDouble(splitLine[7]);
                            newItem = new Gear(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                teethCount, 
                                material, 
                                pitchDiameterMm
                            );
                            break;
                        case "NUT":
                            diameterMm = Double.parseDouble(splitLine[5]);
                            double threadPitchMm = Double.parseDouble(splitLine[6]);
                            newItem = new Nut(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                diameterMm, 
                                threadPitchMm
                            );
                            break;
                        case "SCREWDRIVER":
                            diameterMm = Double.parseDouble(splitLine[5]);
                            ScrewdriverType screwdriverType = ScrewdriverType.valueOf(splitLine[6]);
                            newItem = new Screwdriver(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                diameterMm, 
                                screwdriverType
                            );
                            break;
                        case "SCREWDRIVERBIT":
                            diameterMm = Double.parseDouble(splitLine[5]);
                            ScrewdriverBitType screwdriverBitType = ScrewdriverBitType.valueOf(splitLine[6]);
                            newItem = new ScrewdriverBit(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                diameterMm, 
                                screwdriverBitType
                            );
                            break;
                        case "THREADEDROD":
                            diameterMm = Double.parseDouble(splitLine[5]);
                            double lengthMeters = Double.parseDouble(splitLine[6]);
                            threadPitchMm = Double.parseDouble(splitLine[7]);
                            newItem = new ThreadedRod(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                diameterMm, 
                                lengthMeters, 
                                threadPitchMm, 
                                lengthMeters
                            );
                            break;
                        case "WASHER":
                            double outerDiameterMm = Double.parseDouble(splitLine[5]);
                            double innerDiameterMm = Double.parseDouble(splitLine[6]);
                            double thicknessMm = Double.parseDouble(splitLine[7]);
                            newItem = new Washer(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                outerDiameterMm, 
                                innerDiameterMm, 
                                thicknessMm
                            );
                            break;
                        case "WIRE":
                            double gaugeMmSquared = Double.parseDouble(splitLine[5]);
                            lengthMeters = Double.parseDouble(splitLine[6]);
                            WireInsulationColor insulationColor = WireInsulationColor.valueOf(splitLine[7]);
                            newItem = new Wire(
                                partID, 
                                displayName, 
                                amountInStock, 
                                unitCost, 
                                gaugeMmSquared, 
                                lengthMeters, 
                                insulationColor
                            );
                            break;
                        default:
                            System.err.println("Skipping line " + lineNumber + " as type " + type + " doesn't exist.");
                            continue;
                    }
                    inventoryManager.addNewItem(newItem);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping line " + lineNumber + " due to invalid number formatting.");
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Skipping line " + lineNumber + " as it is missing fields or not properly separated with commas.");
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping line " + lineNumber + " due to wrong sub-types of hardware.");
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read CSV file in " + filepath + ". Returning empty inventory.");
        }
        return inventoryManager;
    }

    public static void exportInventoryToCSV(InventoryManager inventoryManager, String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("Type,partID,displayName,amountInStock,unitCost,ParamA,ParamB,ParamC");
            
            Map<Integer, Hardware> inventory = inventoryManager.getInventory();
            for (Hardware item: inventory.values()) {
                // dumb method
                String itemType = item.getClass().getSimpleName().toUpperCase();
                
                String commonFields = String.join(
                    ",", 
                    itemType, 
                    String.valueOf(item.getPartID()), 
                    item.getDisplayName(), 
                    String.valueOf(item.getAmountInStock()), 
                    String.valueOf(item.getUnitCost()));
                
                String itemSpecificFields = "";

                switch (itemType) {
                    case "BEARING":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Bearing)item).getBoreDiameterMm()),
                            String.valueOf(((Bearing)item).getStaticLoadRatingN())
                        );
                        break;
                    case "BOLT":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Bolt)item).getLengthMm()),
                            String.valueOf(((Bolt)item).getDiameterMm()),
                            String.valueOf(((Bolt)item).getHeadType()));
                        break;
                    case "DRILLBIT":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((DrillBit)item).getDiameterMm()),
                            String.valueOf(((DrillBit)item).getType())
                        );
                        break;
                    case "GEAR":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Gear)item).getTeethCount()),
                            String.valueOf(((Gear)item).getMaterial()),
                            String.valueOf(((Gear)item).getPitchDiameterMm()));
                        break;
                    case "NUT":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Nut)item).getDiameterMm()),
                            String.valueOf(((Nut)item).getThreadPitchMm())
                        );
                        break;
                    case "SCREWDRIVER":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Screwdriver)item).getDiameterMm()),
                            String.valueOf(((Screwdriver)item).getType())
                        );
                        break;
                    case "SCREWDRIVERBIT":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((ScrewdriverBit)item).getDiameterMm()),
                            String.valueOf(((ScrewdriverBit)item).getType())
                        );
                        break;
                    case "THREADEDROD":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((ThreadedRod)item).getDiameterMm()),
                            String.valueOf(((ThreadedRod)item).getLengthMeters()),
                            String.valueOf(((ThreadedRod)item).getThreadPitchMm()));
                        break;
                    case "WASHER":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Washer)item).getOuterDiameterMm()),
                            String.valueOf(((Washer)item).getInnerDiameterMm()),
                            String.valueOf(((Washer)item).getThicknessMm()));
                        break;
                    case "WIRE":
                        itemSpecificFields = String.join(
                            ",", 
                            String.valueOf(((Wire)item).getGaugeMmSquared()),
                            String.valueOf(((Wire)item).getLengthMeters()),
                            String.valueOf(((Wire)item).getInsulationColor()));
                        break;
                }

                String fullLine = commonFields + "," + itemSpecificFields;
                writer.write(fullLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
