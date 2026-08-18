import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryManager {
    private Map<Integer, Hardware> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public void addNewItem(Hardware item) {
        inventory.put(item.getPartID(), item);
    }

    public void removeItem(int partID) {
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
}
