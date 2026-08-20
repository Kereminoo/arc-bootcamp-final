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
                    break;
                case "2":
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
                    inventoryManager = CSVIO.loadInventoryFromCSV("src/inventory.csv");
                case "8":
                    break;
                case "9":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.err.println("Please input a number between 1-9 to choose an operation!");
            }
        }
    }
}