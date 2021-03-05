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

    public double distanceTo(int x, int y) {
        return Math.sqrt(Math.pow(getX() - x, 2) + Math.pow(getY() - y, 2));
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
            confidence += compareCurve(getX() - other.getX());
            divide++;
        }
        if(y) {
            confidence += compareCurve(getY() - other.getY());
            divide++;
        }
        if(size) {
            confidence += compareCurve(getSize() - other.getSize());
            divide++;
        }

        if(divide == 0) {
            return confidence;
        }
        
        return confidence / divide;
    }

    public double compareCurve(double error) {
        return 1 - Math.pow(Math.tanh(error * 0.07), 2);
    }

    @Override
    public String toString() {
        String result = "";

        result += "X: " + getX() + " ";
        result += "Y: " + getY() + " ";
        result += "Size: " + getSize() + " ";
        result += "Angle: " + getAngle() + " ";
        result += "Age: " + getAge();

        return result;
    }

    /*
    public int getDistance() {
        return -1; //TODO
    }
    */
}
