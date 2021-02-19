// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;

public class C_CheckPattern extends CommandBase {

  NetworkTableEntry pattern;

  public C_CheckPattern() {
    ShuffleboardTab tab = Shuffleboard.getTab("Pattern Training");

    pattern = tab.add("Pattern Code", "none")
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 0)
      .withSize(4, 1)
      .getEntry();

  }

  @Override
  public void execute() {
    String result = "{";

    ArrayList<PowerCell> cells = Pixy.getInstance().getPowerCells();

    if(cells == null) {
      return;
    }

    boolean first = true;

    for(PowerCell cell : cells) {
      if(!first) {
        result += ", ";
      }

      result += "PowerCell.getMarkerCell(";
      result += "" + cell.getX() + ", ";
      result += "" + cell.getY() + ", ";
      result += "" + cell.getSize() + ")";
    }

    result += "}";

    pattern.setString(result);

    first = false;
  }
}
