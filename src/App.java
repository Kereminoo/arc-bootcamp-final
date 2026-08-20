import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();
        while (true) {
            System.out.println("=== ARC#6014 Supply Tracker ===\n" + //
                    "1. View inventory (sorted by quantity)\n" + //
                    "2. View inventory (sorted by value)\n" + //
                    "3. Add new item\n" + //
                    "4. Adjust stock quantity\n" + //
                    "5. Remove item\n" + //
                    "6. View low-stock items\n" + //
                    "7. Import from CSV\n" + //
                    "8. Export to CSV\n" + //
                    "9. Exit");
            System.out.print("Choose an option: ");
            String selection = scanner.nextLine();

            switch (selection) {
                case "1":
                    List<Hardware> inventorySortedByQuantity = inventoryManager.getSortedInventoryByQuantity();
                    System.out.println("\n=== INVENTORY SORTED BY QUANTITY ===");
                    if (inventorySortedByQuantity.isEmpty()) {
                        System.out.println("Inventory is empty. Have you imported the inventory by CSV?");
                    } else {
                        for (Hardware item : inventorySortedByQuantity) {
                            System.out.println(item.getCategoryDetails());
                        }
                    }
                    System.out.println("====================================\n");
                    break;
                case "2":
                    List<Hardware> inventorySortedByTotalValue = inventoryManager.getSortedInventoryByTotalValue();
                    System.out.println("\n=== INVENTORY SORTED BY TOTAL VALUE ===");
                    if (inventorySortedByTotalValue.isEmpty()) {
                        System.out.println("Inventory is empty. Have you imported the inventory by CSV?");
                    } else {
                        for (Hardware item : inventorySortedByTotalValue) {
                            System.out.println(item.getCategoryDetails());
                        }
                    }
                    System.out.println("====================================\n");
                    break;
                case "3":
                    handleAddingNewItem(inventoryManager, scanner);
                    break;
                case "4":
                    int partID = Integer.parseInt(askQuestion(
                        "Which hardware do you want to remove (input its part ID)?", 
                        "Part ID either doesn't exist or isn't an integer.",
                        input -> {
                            try {
                                int id = Integer.parseInt(input);
                                return inventoryManager.existsInInventory(id) && id > 0;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        scanner));
                    int newStock = askInt("How much stock of the object remains?", "Amount in stock must be an integer.", scanner);
                    inventoryManager.adjustQuantity(partID, newStock);
                    break;
                case "5":
                    break;
                case "6":
                    int lowStockThreshold = askInt("What is the high threshold for low stock?", "Enter a positive integer.", scanner);
                    List<Hardware> lowStockInventory = inventoryManager.getLowStockItems(lowStockThreshold);
                    System.out.println("=== LOW STOCK INVENTORY ===");
                    if (lowStockInventory.isEmpty()) {
                        System.out.println("No items to display.");
                    } else {
                        for (Hardware item : lowStockInventory) {
                            System.out.println(item.getCategoryDetails());
                        }
                    }
                    System.out.println("====================================\n");
                    break;
                case "7":
                    System.out.println("\nImporting inventory from CSV...");
                    // lambdas dont work otherwise
                    InventoryManager loaded = CSVIO.loadInventoryFromCSV("src/inventory.csv");
                    inventoryManager.setInventory(loaded.getInventory());
                    System.out.println("Imported " + inventoryManager.getInventory().size() + " items.\n");
                    break;
                case "8":
                    System.out.println("\nExporting inventory to CSV...\n");
                    CSVIO.exportInventoryToCSV(inventoryManager, "src/inventory.csv");
                    break;
                case "9":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.err.println("Please input a number between 1-9 to choose an operation!\n");
            }
        }
    }

    static String askQuestion(String question, String reprompt, Function<String, Boolean> validator, Scanner scanner) {
        while (true) {
            System.out.print(question + ": ");
            String answer = scanner.nextLine();
            boolean validAnswer = validator.apply(answer);

            if (!validAnswer) {
                System.err.println(reprompt);
            } else {
                return answer;
            }
        }
    }

    static double askDouble(String question, String reprompt, Scanner scanner) {
        return Double.parseDouble(askQuestion(
                question,
                reprompt,
                input -> {
                    try {
                        double inputParsed = Double.parseDouble(input);
                        return inputParsed > 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                scanner));
    }

    static int askInt(String question, String reprompt, Scanner scanner) {
        return Integer.parseInt(askQuestion(
                question,
                reprompt,
                input -> {
                    try {
                        int inputParsed = Integer.parseInt(input);
                        return inputParsed > 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                scanner));
    }

    // engaging in some tomfoolery just to keep myself sane
    static <T extends Enum<T>> T askEnum(String prompt, Class<T> enumClass, String reprompt, Scanner scanner) {
        return Enum.valueOf(enumClass,
            askQuestion(
                prompt,
                reprompt,
                input -> {
                    try {
                        Enum.valueOf(enumClass, input);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                },
                scanner
            )
        );
    }

    static void handleAddingNewItem(InventoryManager inventoryManager, Scanner scanner) {
        String hardwareType = askQuestion(
                "What type of hardware will you add?",
                "This type of hardware doesn't exist. Try again.",
                input -> Set.of("bearing", "bolt", "drillbit", "gear", "nut", "screwdriver", "screwdriverbit",
                        "threadedrod", "washer", "wire").contains(input.toLowerCase().replace(" ", "")),
                scanner);
        int partID = Integer.parseInt(askQuestion(
                "What is the part ID of the hardware?",
                "Hardware with the same part ID already exists, or part ID is invalid. Try again.",
                input -> {
                    try {
                        int id = Integer.parseInt(input);
                        return !inventoryManager.existsInInventory(id) && id > 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                scanner));
        String displayName = askQuestion(
                "What is the display name for the " + hardwareType + "?",
                "Display name cannot be empty.",
                input -> !input.isBlank(),
                scanner);
        int amountInStock = Integer.parseInt(askQuestion(
                "How many " + displayName + "(s) is available currently?",
                "Available amount must be a positive number.",
                input -> {
                    try {
                        int availableStock = Integer.parseInt(input);
                        return availableStock >= 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                scanner));
        double unitCost = askDouble(
                "How much does each unit of " + displayName + " cost?",
                "Unit cost must be a positive rational number.",
                scanner);
        
        Hardware newItem = null;
        // hell
        switch (hardwareType) {
            case "bearing":
                double boreDiameterMm = askDouble("What is the bore diameter (mm) of the bearing?", "The bore diameter must be a positive number!", scanner);
                double staticLoadRatingN = askDouble("What is the static load rating (N) of the bearing?", "The static load rating must be a positive number!", scanner);
                newItem = new Bearing(partID, displayName, amountInStock, unitCost, boreDiameterMm, staticLoadRatingN);
                break;
            case "bolt":
                double lengthMm = askDouble("What is the length (mm) of the bolt?", "Length of the bolt must be a positive number!", scanner);
                double diameterMm = askDouble("What is the diameter (mm) of the bolt?", "Diameter of the bolt must be a positive number!", scanner);
                HeadType headType = askEnum("What is the head type of the bolt (HEX, SOCKET, PHILLIPS, FLAT)?", HeadType.class, "The bolt type you inputted is invalid.", scanner);
                newItem = new Bolt(partID, displayName, amountInStock, unitCost, lengthMm, diameterMm, headType);
                break;
            case "drillbit":
                diameterMm = askDouble("What is the diameter (mm) of the drill bit?", "Diameter of the drill bit must be a positive number!", scanner);
                DrillBitType drillBitType = askEnum("What is the type of the drill bit (TWIST, BRAD_POINT, FORSTNER, MASONRY, STEP)?", DrillBitType.class, "The drill bit type you inputted is invalid", scanner);
                newItem = new DrillBit(partID, displayName, amountInStock, unitCost, diameterMm, drillBitType);
                break;
            case "gear":
                int teethCount = askInt("How much teeth does the gear have?", "Gear count must be a positive number!", scanner);
                GearMaterial material = askEnum("What material is the gear made out of (STEEL, CAST_IRON, BRONZE, PLASTIC)?", GearMaterial.class, "The material you inputted is invalid.", scanner);
                double pitchDiameterMm = askDouble("What is the pitch diameter (mm) of the gear?", "The pitch diameter must be a positive number!", scanner);
                newItem = new Gear(partID, displayName, amountInStock, unitCost, teethCount, material, pitchDiameterMm);
                break;
            case "nut":
                diameterMm = askDouble("What is the diameter (mm) of the nut?", "Diameter of the nut must be a positive number!", scanner);
                double threadPitchMm = askDouble("What is the thread pitch (mm) of the nut?", "Thread pitch of the nut must be a positive number!", scanner);
                newItem = new Nut(partID, displayName, amountInStock, unitCost, diameterMm, threadPitchMm);
                break;
            case "screwdriver":
                diameterMm = askDouble("What is the diameter (mm) of the screwdriver?", "Diameter of the screwdriver must be a positive number!", scanner);
                ScrewdriverType screwdriverType = askEnum("What type of screwdriver is it (FLATHEAD, PHILLIPS, TORX, HEX, ROBERTSON)?", ScrewdriverType.class, "Screwdriver type you inputted is invalid.", scanner);
                newItem = new Screwdriver(partID, displayName, amountInStock, unitCost, diameterMm, screwdriverType);
                break;
            case "screwdriverbit":
                diameterMm = askDouble("What is the diameter (mm) of the screwdriver bit?", "Diameter of the screwdriver bit must be a positive number!", scanner);
                ScrewdriverBitType screwdriverBitType = askEnum("What type of screwdriver bit is it (FLATHEAD, PHILLIPS, TORX, HEX, ROBERTSON)?", ScrewdriverBitType.class, "Screwdriver type you inputted is invalid.", scanner);
                newItem = new ScrewdriverBit(partID, displayName, amountInStock, unitCost, diameterMm, screwdriverBitType);
                break;
            case "threadedrod":
                diameterMm = askDouble("What is the diameter (mm) of the threaded rod?", "Diameter of the threaded rod must be a positive number!", scanner);
                double lengthMeters = askDouble("What is the length (m) of the threaded rod?", "The length of the threaded rod must be a positive number!", scanner);
                threadPitchMm = askDouble("What is the thread pitch (mm) of the threaded rod?", "Thread pitch of the threaded rod must be a positive number!", scanner);
                newItem = new ThreadedRod(partID, displayName, amountInStock, unitCost, diameterMm, lengthMeters, threadPitchMm);
                break;
            case "washer":
                double outerDiameterMm = askDouble("What is the outer diameter (mm) of the washer?", "The outer diameter of the washer must be a positive number!", scanner);
                double innerDiameterMm = askDouble("What is the inner diameter (mm) of the washer?", "The inner diameter of the washer must be a positive number!", scanner);
                double thicknessMm = askDouble("What is the thickness (mm) of the washer?", "The thickness of the washer must be a positive number!", scanner);
                newItem = new Washer(partID, displayName, amountInStock, unitCost, outerDiameterMm, innerDiameterMm, thicknessMm);
                break;
            case "wire":
                double gaugeMmSquared = askDouble("What is the gauge (mm²) of the wire?", "The gauge of the wire must be a positive number!", scanner);
                lengthMeters = askDouble("What is the length (m) of the wire?", "The length of the wire must be a positive number!", scanner);
                WireInsulationColor insulationColor = askEnum("What is the insulation color of the wire (BROWN, LIGHT_BLUE, GREEN)?", WireInsulationColor.class, "The insulation color you inputted is invalid.", scanner);
                newItem = new Wire(partID, displayName, amountInStock, unitCost, gaugeMmSquared, lengthMeters, insulationColor);
                break;
        }
        inventoryManager.addNewItem(newItem);
    }
}