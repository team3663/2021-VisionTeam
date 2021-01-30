// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;

public class PixyClassTest extends CommandBase {

  private Pixy pixy;

  public PixyClassTest() {
    pixy = Pixy.getInstance();
  }

  @Override
  public void execute() {
    PowerCell cell = pixy.getBiggestPowerCell();
    SmartDashboard.putNumber("pixy x offsest", cell.getX());
    SmartDashboard.putNumber("pixy y offsest", cell.getY());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
