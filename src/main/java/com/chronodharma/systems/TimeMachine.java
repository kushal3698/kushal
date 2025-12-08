package com.chronodharma.systems;

/**
 * Manages the logic for time travel, including energy requirements.
 */
public class TimeMachine {
    private static final int JUMP_COST = 50;
    private int fluxEnergy;

    public TimeMachine(int initialEnergy) {
        this.fluxEnergy = initialEnergy;
    }

    /**
     * Attempts to jump to a specific era.
     * 
     * @return true if jump is successful, false if insufficient energy.
     */
    public boolean attemptJump() {
        if (fluxEnergy >= JUMP_COST) {
            fluxEnergy -= JUMP_COST;
            System.out.println("Time Jump Successful! Remaining Energy: " + fluxEnergy);
            return true;
        } else {
            System.out.println("CRITICAL FAILURE: Insufficient Flux Energy. Jump Aborted.");
            return false;
        }
    }

    public void addEnergy(int amount) {
        this.fluxEnergy += amount;
        if (this.fluxEnergy > 100)
            this.fluxEnergy = 100; // Cap at 100
    }

    public int getFluxEnergy() {
        return fluxEnergy;
    }
}
