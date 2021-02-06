// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivers;

import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

//This is a helper class that centers the pixy coordinates on zero when getting the x and y values
public class PowerCell extends Block {

    public static PowerCell getMarkerCell(int x, int y, int size) {
        return new PowerCell(0, x, y, size, 1, 0, 0, 0);
    }

    public PowerCell(int signature, int x, int y, int width, int height, int angle, int index, int age) {
        super(signature, x, y, width, height, angle, index, age);
    }

    public PowerCell(Block b) {
        super(b.getSignature(), b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getAngle(), b.getIndex(), b.getAge());
    }

    @Override
    public int getX() {
        return super.getX() - (Pixy.getInstance().getCamWidth() / 2);
    }

    @Override
    public int getY() {
        return (Pixy.getInstance().getCamHeight() / 2) - super.getY();
    }

    public int getSize() {
        return super.getWidth() * super.getHeight();
    }

    public double getErrorX() {
        return getX() / Pixy.getInstance().getCamWidth();
    }

    public double compare(PowerCell other) {
        return compare(other, true, true, true);
    }

    public double compare(PowerCell other, boolean x, boolean y, boolean size) {
        double confidence = 0;
        int divide = 0;

        if(x) {
            confidence += Math.abs(getX() - other.getX()) / ((getX() + other.getX()) / 2);
            divide++;
        }
        if(y) {
            confidence += Math.abs(getY() - other.getY()) / ((getY() + other.getY()) / 2);
            divide++;
        }
        if(size) {
            confidence += Math.abs(getSize() - other.getSize()) / ((getSize() + other.getSize()) / 2);
            divide++;
        }
        
        return confidence / divide;
    }

    /*
    public int getDistance() {
        return -1; //TODO
    }
    */
}
