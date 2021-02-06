// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;
import frc.robot.subsystems.SS_DriveTrain;

public class C_TrackPowerCell extends CommandBase {

  NetworkTableEntry movementDelayEntry;

  private SS_DriveTrain driveTrain;
  private Pixy pixy;

  public C_TrackPowerCell() {
    driveTrain = SS_DriveTrain.getinstance();
    addRequirements(driveTrain);

    pixy = Pixy.getInstance();
  }

  @Override
  public void initialize() {
    driveTrain.setPower(0, 0);
  }

  @Override
  public void execute() {

    PowerCell cell = pixy.getBiggestPowerCell();

    if(cell == null) {
      driveTrain.setPower(0, 0);
    } else {
      driveTrain.arcadeDrive(0.5, cell.getErrorX());
    }

    driveTrain.arcadeDrive(0.5, cell.getErrorX());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
