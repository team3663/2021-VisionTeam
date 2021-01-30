// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivers;

import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

//This is a helper class that centers the pixy coordinates on zero when getting the x and y values
public class PowerCell extends Block {

    public PowerCell(int signature, int x, int y, int width, int height, int angle, int index, int age) {
        super(signature, x, y, width, height, angle, index, age);
    }

    @Override
    public int getX() {
        return super.getX() - (Pixy.getInstance().getCamWidth() / 2);
    }

    @Override
    public int getY() {
        return (Pixy.getInstance().getCamHeight() / 2) - super.getY();
    }
}
