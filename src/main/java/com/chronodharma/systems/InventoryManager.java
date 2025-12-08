package com.chronodharma.systems;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player's collected items.
 */
public class InventoryManager {
    private List<GameItem> inventory;

    public InventoryManager() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     * 
     * @param item The item to add.
     */
    public void addItem(GameItem item) {
        inventory.add(item);
        System.out.println("Added item: " + item.getName());
    }

    /**
     * Removes an item from the inventory by its ID.
     * 
     * @param itemID The ID of the item to remove.
     * @return true if the item was found and removed, false otherwise.
     */
    public boolean removeItem(int itemID) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItemID() == itemID) {
                System.out.println("Removed item: " + inventory.get(i).getName());
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains an item with the specific ID.
     * 
     * @param itemID The ID to check for.
     * @return true if found, false otherwise.
     */
    public boolean hasItem(int itemID) {
        for (GameItem item : inventory) {
            if (item.getItemID() == itemID) {
                return true;
            }
        }
        return false;
    }

    public List<GameItem> getInventory() {
        return inventory;
    }
}
