package com.chronodharma.systems;

/**
 * Represents the final stabilized cure created via synthesis.
 */
public class FinalCure extends GameItem {
    private boolean isStabilized;

    public FinalCure(int itemID, String name, String description, boolean isStabilized) {
        super(itemID, name, description);
        this.isStabilized = isStabilized;
    }

    public boolean isStabilized() {
        return isStabilized;
    }

    public void setStabilized(boolean stabilized) {
        isStabilized = stabilized;
    }
}
