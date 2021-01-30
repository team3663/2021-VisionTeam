// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivers;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

/**
 * @see https://github.com/PseudoResonance/Pixy2JavaAPI/wiki/Using-the-API
 */
public class Pixycam {

    private static Pixycam instance;

    public static Pixycam getInstance() {
        if(instance == null) {
            instance = new Pixycam();
        }

        return instance;
    }

    private final int MAX_POWER_CELLS = 10;
    private final int CAM_WIDTH = 316; //TODO confirm this number (https://forum.pixycam.com/t/resolution-tracking-pixy2/5646)
    private final int CAM_HWIDTH = CAM_WIDTH / 2;
    private final int CAM_HEIGHT = 208; //TODO
    private final int CAM_HHEIGHT = CAM_HEIGHT / 2;

    private Pixy2 pixy;
    
    public Pixycam() {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.init();
    }

    //Priority for where to look for power cells
    public enum Priority {
        BIGGEST, //biggest block
        SMALLEST, //smallest block
        OLDEST, //oldest block
        NEWEST, //newest block
        TOP, //block closest to top
        BOTTOM, //block closest to bottom
        LEFT, //block closest to left
        RIGHT, //block closest to right
        TOP_LEFT, //block closest to top left corner
        TOP_RIGHT, //block closest to top right corner
        BOTTOM_LEFT, //block closest to bottom left corner
        BOTTOM_RIGHT, //block closest to bottom right corner
        COORDINATES, //block closest to given set of coordinates
        V_LINE, //block closest to given vertical line
        H_LINE //block closest tp given horizontal line
    }

    //post new telemetry data to Smart Dashboard
    public void updateTelemetry() {
        SmartDashboard.putNumber("Pixy X", getXOffset(Priority.BIGGEST));
        SmartDashboard.putNumber("Pixy Y", getYOffset(Priority.BIGGEST));
        SmartDashboard.putNumber("Pixy num blocks", pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS));
    }

    //get the x offset of the power cell with the highest priority
    public double getXOffset(Priority priority, int x, int y) {
        try {
            return findMatchingBlock(priority, x, y).getX();
        } catch(Exception e) {
            return 0;
        }
    }

    public double getXOffset(Priority priority) {
        return getXOffset(priority, 0, 0);
    }

    //get the y offset of the power cell with the highest priority
    public double getYOffset(Priority priority, int x, int y) {
        try {
            return findMatchingBlock(priority, x, y).getY();
        } catch(Exception e) {
            return 0;
        }
    }

    public double getYOffset(Priority priority) {
        return getYOffset(priority, 0, 0);
    }
    
    private Block findMatchingBlock(Priority priority, int x, int y) {
        //ensure that there are objects detected. This also must be called to collect data with the Pixy cam
        if(pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS) <= 0) return null;

        ArrayList<Block> blocks = pixy.getCCC().getBlockCache();

        switch(priority) {
            case BIGGEST: return searchForBlock(blocks, (Block block) -> Integer.MAX_VALUE - block.getWidth() * block.getHeight());
            case SMALLEST: return searchForBlock(blocks, (Block block) -> block.getWidth() * block.getHeight());

            case OLDEST: return searchForBlock(blocks, (Block block) -> Integer.MAX_VALUE - block.getAge());
            case NEWEST: return searchForBlock(blocks, (Block block) -> block.getAge());

            case TOP: return searchForBlock(blocks, (Block block) -> getDistance(0, block.getY(), 0, CAM_HHEIGHT));
            case BOTTOM: return searchForBlock(blocks, (Block block) -> getDistance(0, block.getY(), 0, -CAM_HHEIGHT));
            case LEFT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), 0, -CAM_HWIDTH, 0));
            case RIGHT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), 0, CAM_HWIDTH, 0));

            case TOP_LEFT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), block.getY(), -CAM_HWIDTH, CAM_HHEIGHT));
            case TOP_RIGHT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), block.getY(), CAM_HWIDTH, CAM_HHEIGHT));
            case BOTTOM_LEFT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), block.getY(), -CAM_HWIDTH, -CAM_HHEIGHT));
            case BOTTOM_RIGHT: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), block.getY(), CAM_HWIDTH, -CAM_HHEIGHT));

            case COORDINATES: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), block.getY(), x, y));
            case H_LINE: return searchForBlock(blocks, (Block block) -> getDistance(block.getX(), 0, x, 0));
            case V_LINE: return searchForBlock(blocks, (Block block) -> getDistance(0, block.getY(), 0, y));
        }

        //no block was found
        return null;
    }

    private int normalizeX(Block block) {
        return block.getX() - CAM_HWIDTH;
    }

    private int normalizeY(Block block) {
        return block.getY() - CAM_HHEIGHT;
    }

    private interface BlockEvaluation {
        //The closer the block's evaluation is to zero, the higher its priority
        int evaluate(Block block);
    }

    private Block searchForBlock(ArrayList<Block> blocks, BlockEvaluation evaluator) {
        Block result = blocks.get(0);
        int resultMetric = 0;

        for(Block currentBlock : blocks) {
            int currentMetric = Math.abs(evaluator.evaluate(currentBlock));
            if(currentMetric < resultMetric) {
                result = currentBlock;
                resultMetric = currentMetric;
            }
        }

        return result;
    }

    private int getDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
