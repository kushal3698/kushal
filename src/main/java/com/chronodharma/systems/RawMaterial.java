package com.chronodharma.systems;

/**
 * Represents a raw, unstable material collected from a specific time period.
 */
public class RawMaterial extends GameItem {

    public enum OriginEra {
        RAMAYANA,
        MAHABHARATA,
        PRESENT_DAY
    }

    private OriginEra originEra;

    public RawMaterial(int itemID, String name, String description, OriginEra originEra) {
        super(itemID, name, description);
        this.originEra = originEra;
    }

    public OriginEra getOriginEra() {
        return originEra;
    }

    public void setOriginEra(OriginEra originEra) {
        this.originEra = originEra;
    }
}
