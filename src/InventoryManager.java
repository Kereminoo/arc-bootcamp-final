import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InventoryManager {
    private Map<Integer, Hardware> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public Map<Integer, Hardware> getInventory() {
        return inventory;
    }

    public void addNewItem(Hardware item) {
        if (inventory.containsKey(item.getPartID())) {
            throw new IllegalArgumentException("Object with part ID " + item.getPartID() + "is already present in the inventory!");
        }
        inventory.put(item.getPartID(), item);
    }

    public void removeItem(int partID) {
        if (!inventory.containsKey(partID)) {
            throw new IllegalArgumentException("There is no object in the inventory with part ID " + partID + "!");
        }
        inventory.remove(partID);
    }

    public void adjustQuantity(int partID, int newQuantity) {
        inventory.get(partID).setAmountInStock(newQuantity);
    }

    public Optional<Hardware> findItemByID(int partID) {
        if (inventory.containsKey(partID)) {
            return Optional.of(inventory.get(partID));
        }
        return Optional.empty();
    }

    public List<Hardware> getSortedInventoryByQuantity() {
        List<Hardware> inventoryList = new ArrayList<>(inventory.values());
        inventoryList.sort(Comparator.comparingInt(Hardware::getAmountInStock));
        return inventoryList;
    }

    public List<Hardware> getSortedInventoryByTotalValue() {
        List<Hardware> inventoryList = new ArrayList<>(inventory.values());
        inventoryList.sort(Comparator.comparingDouble(
            hardware -> hardware.getUnitCost() * hardware.getAmountInStock()
        ));
        return inventoryList;
    }

    public List<Hardware> getLowStockItems(int lowStockThreshold) {
        if (lowStockThreshold <= 0) {
            throw new IllegalArgumentException("Threshold for low stock cannot be zero or negative!");
        }
        List<Hardware> inventoryList = new ArrayList<>(inventory.values());
        // only keeps the items with low stock
        inventoryList.removeIf(hardware -> hardware.getAmountInStock() > lowStockThreshold);
        return inventoryList;
    }
}
