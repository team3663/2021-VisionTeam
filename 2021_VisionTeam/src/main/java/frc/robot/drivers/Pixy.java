// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivers;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

import frc.robot.drivers.PowerCell;

public class Pixy extends SubsystemBase {

    private static Pixy instance;

    public static Pixy getInstance() {
        if(instance == null) {
            instance = new Pixy();
        }

        return instance;
    }

    private final int MAX_POWER_CELLS = 15; //maximum number of power cells to check for
    private final Pixy2 pixy;

    private boolean updated = false;
    private ArrayList<PowerCell> cells;

    public Pixy() {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.init();
        
        //turn on the LEDs
        pixy.setLamp((byte) 1, (byte) 1);
    }

    @Override
    public void periodic() {
        updated = false;
    }

    private void update() {
        if(!updated) {
            // update the blocks that the pixycam sees and ensure that there are power cells detected
            if(pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS) <= 0) {
                cells = new ArrayList<PowerCell>();
            }

            //get the blocks
            ArrayList<Block> blocks = pixy.getCCC().getBlockCache();

            //convert the blocks to power cells
            ArrayList<PowerCell> powerCells = new ArrayList<PowerCell>();

            for(Block currentBlock : blocks) {
                powerCells.add(new PowerCell(currentBlock));
            }

            cells = powerCells;

            updated = true;
        }
    }

    public int getNumPowerCells() {
        update();
        return cells.size();
    }

    public int getCamWidth() {
        return pixy.getFrameWidth();
    }

    public int getCamHeight() {
        return pixy.getFrameHeight();
    }

    public ArrayList<PowerCell> getPowerCells() {
        update();
        return (ArrayList<PowerCell>) cells.clone();
    }

    //get the largest power cell that the camera detects
    public PowerCell getBiggestPowerCell() {

        if(cells.size() <= 0) return null;

        PowerCell largestCell = cells.get(0);

        for(PowerCell cell : cells) {
            if(cell.getSize() > largestCell.getSize()) {
                largestCell = cell;
            }
        }

        return largestCell;
    }
}
