// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Track_WITH_PIXY extends CommandBase {
  
  private SS_DriveTrain drive = SS_DriveTrain.getinstance();
  private Pixy pixy;
  
  public C_Track_WITH_PIXY() {
    pixy = Pixy.getInstance();
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setPower(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    PowerCell biggest = pixy.getBiggestPowerCell();
    try {
      SmartDashboard.putNumber("X Offset", biggest.getX());
      SmartDashboard.putNumber("Size", biggest.getSize());
      if(biggest == null) {
        drive.setPower(Constants.noPower, Constants.noPower);
        return;
      }

      if(biggest.getX() > 10 && biggest.getSize() < 5000) {
        drive.setPower(-Constants.leftPower, Constants.rightPower);
      }
      else if(biggest.getX() < -10 && biggest.getSize() < 5000) {
        drive.setPower(Constants.leftPower, -Constants.rightPower);
      }
      else if(biggest.getX() < 10 && biggest.getX() > -10 && biggest.getSize() > 5000) {
        drive.setPower(Constants.leftPower, Constants.rightPower);
      }
      else if(biggest.getX() < 10 && biggest.getX() > -10 && biggest.getSize() < 5000) {
        drive.setPower(-Constants.leftPower, -Constants.rightPower);
      }
    }
    catch(Exception e) {
      //Nothing lol
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //drive.setPower(Constants.noPower, Constants.noPower);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
