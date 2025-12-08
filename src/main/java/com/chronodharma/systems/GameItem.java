package com.chronodharma.systems;

/**
 * Abstract base class representing an item in the game.
 * All collectible objects must extend this class.
 */
public abstract class GameItem {
    private int itemID;
    private String name;
    private String description;

    public GameItem(int itemID, String name, String description) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "itemID=" + itemID +
                ", name='" + name + '\'' +
                '}';
    }
}
