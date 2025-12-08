package com.chronodharma.systems;

/**
 * Handles the logic for combining raw materials into the Final Cure.
 * Acts as the "AND Gate" for the synthesis protocol.
 */
public class CraftingStation {

    // Required Item IDs
    private static final int ID_SANJEEVANI_ROOT = 101;
    private static final int ID_CELESTIAL_AMRIT = 201;
    private static final int ID_QUANTUM_STABILIZER = 301;

    // Result Item ID
    private static final int ID_FINAL_CURE = 999;

    /**
     * Attempts to synthesize the Final Cure using items from the player's
     * inventory.
     * 
     * @param playerInventory The player's inventory manager.
     * @return A message indicating success or failure.
     */
    public String attemptSynthesis(InventoryManager playerInventory) {
        // Logic: Check if all 3 specific items exist simultaneously (AND Gate)
        boolean hasRoot = playerInventory.hasItem(ID_SANJEEVANI_ROOT);
        boolean hasAmrit = playerInventory.hasItem(ID_CELESTIAL_AMRIT);
        boolean hasStabilizer = playerInventory.hasItem(ID_QUANTUM_STABILIZER);

        if (hasRoot && hasAmrit && hasStabilizer) {
            // Remove ingredients
            playerInventory.removeItem(ID_SANJEEVANI_ROOT);
            playerInventory.removeItem(ID_CELESTIAL_AMRIT);
            playerInventory.removeItem(ID_QUANTUM_STABILIZER);

            // Create and add the Final Cure
            FinalCure cure = new FinalCure(
                    ID_FINAL_CURE,
                    "Stabilized Panacea Injector",
                    "A stabilized serum capable of healing temporal injuries.",
                    true);
            playerInventory.addItem(cure);

            return "SUCCESS: Synthesis Complete! 'Stabilized Panacea Injector' added to inventory.";
        } else {
            // Construct failure message
            StringBuilder missing = new StringBuilder();
            if (!hasRoot)
                missing.append(" [Sanjeevani Root]");
            if (!hasAmrit)
                missing.append(" [Celestial Amrit]");
            if (!hasStabilizer)
                missing.append(" [Quantum Stabilizer]");

            return "FAILURE: Missing components:" + missing.toString();
        }
    }
}
