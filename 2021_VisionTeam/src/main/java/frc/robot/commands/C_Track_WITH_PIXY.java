// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.drivers.Pixycam;
import frc.robot.drivers.Pixycam.Priority;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Track_WITH_PIXY extends CommandBase {
  
  private SS_DriveTrain drive = SS_DriveTrain.getinstance();
  private Pixycam pixycam = Pixycam.getInstance();
  
  public C_Track_WITH_PIXY() {
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
    if(pixycam.getXOffset(Priority.BIGGEST) > 0) {
      drive.setPower(-Constants.leftPower, Constants.rightPower);
    }
    else if(pixycam.getXOffset(Priority.BIGGEST) < 0) {
      drive.setPower(Constants.leftPower, -Constants.rightPower);
    }
    else {
      drive.setPower(Constants.noPower, Constants.noPower);
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
