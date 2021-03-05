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

public class PixyData extends CommandBase {

  private Pixy pixy = Pixy.getInstance();
  private NetworkTableEntry data;

  @Override
  public void initialize() {
    ShuffleboardTab tab = Shuffleboard.getTab("Pixy Telemetry");

    data = tab.add("PixyData", "")
      .withPosition(0, 0)
      .withWidget(BuiltInWidgets.kTextView)
      .getEntry();
  }

  @Override
  public void execute() {
    String dataReading = "";

    ArrayList<PowerCell> powerCells = pixy.getPowerCells();

    for(PowerCell i : powerCells) {
      dataReading += i.toString() + " | ";
    }

    data.setString(dataReading);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
