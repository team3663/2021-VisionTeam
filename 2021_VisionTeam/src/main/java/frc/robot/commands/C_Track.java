// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Limelight;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Track extends CommandBase {
    private Limelight limelight = Limelight.getinstance();
    private SS_DriveTrain driveTrain = SS_DriveTrain.getinstance();
    public C_Track() {
      addRequirements(driveTrain);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLEDMode(Limelight.LED_ON);
    driveTrain.setPower(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelight.updateTelemetry();
      if(limelight.getXOffset() > -3) {
        driveTrain.setPower(-0.2, 0.2);
      }
      else if(limelight.getXOffset() < -3) {
        driveTrain.setPower(0.2, -0.2);
      }
      else {
        driveTrain.setPower(0, 0);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //driveTrain.setPower(0, 0);
    limelight.setLEDMode(Limelight.LED_OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
