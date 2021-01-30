// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivers;

import java.util.ArrayList;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

import frc.robot.drivers.PowerCell;

public class Pixy {

    private static Pixy instance;

    public static Pixy getInstance() {
        if(instance == null) {
            instance = new Pixy();
        }

        return instance;
    }

    private final int MAX_POWER_CELLS = 15; //maximum number of power cells to check for

    private final Pixy2 pixy;

    public Pixy() {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.init();
    }

    public int getNumPowerCells() {
        return pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS);
    }

    public int getCamWidth() {
        return pixy.getFrameWidth();
    }

    public int getCamHeight() {
        return pixy.getFrameHeight();
    }

    public ArrayList<PowerCell> getPowerCells() {
        //ensure that there are power cells detected
        if(pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS) <= 0) return null;

        ArrayList<Block> blocks = pixy.getCCC().getBlockCache();

        ArrayList<PowerCell> powerCells = new ArrayList<PowerCell>();

        //cast the blocks to power cells
        for(Block b : blocks) {
            powerCells.add(new PowerCell(b.getSignature(), b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getAngle(), b.getIndex(), b.getAge()));
        }

        return powerCells;
    }

    //get the largest power cell that the camera detects
    public PowerCell getBiggestPowerCell() {
        ArrayList<PowerCell> powerCells = getPowerCells();

        if(powerCells == null) return null;

        PowerCell largestCell = powerCells.get(0);

        for(PowerCell cell : powerCells) {
            if(cell.getWidth() * cell.getHeight() > largestCell.getWidth() * largestCell.getHeight()) {
                largestCell = cell;
            }
        }

        return largestCell;
    }
}
