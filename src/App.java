import java.util.List;
import java.util.Scanner;

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
                        for (Hardware item: inventorySortedByQuantity) {
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
                        for (Hardware item: inventorySortedByTotalValue) {
                            System.out.println(item.getCategoryDetails());
                        }
                    }
                    System.out.println("====================================\n");
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    System.out.println("\nImporting inventory from CSV...");
                    inventoryManager = CSVIO.loadInventoryFromCSV("src/inventory.csv");
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
}