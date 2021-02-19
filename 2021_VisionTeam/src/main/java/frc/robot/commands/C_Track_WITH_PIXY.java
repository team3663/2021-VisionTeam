// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Track_WITH_PIXY extends CommandBase {
  
  NetworkTableEntry kPr;
  NetworkTableEntry kIr;
  NetworkTableEntry kDr;

  private SS_DriveTrain drive = SS_DriveTrain.getinstance();
  private Pixy pixy;
  private PIDController pidDrive = new PIDController(0.0001, 0, 0);
  private PIDController pidRotation = new PIDController(0.01, 0, 0.00055);
  
  public C_Track_WITH_PIXY() {
    ShuffleboardTab pidTab = Shuffleboard.getTab("PID Tab");
    kPr = pidTab.add("kP", 0.0)
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 0)
      .withSize(2, 1)
      .getEntry();
    
    kIr = pidTab.add("kI", 0.0)
    .withWidget(BuiltInWidgets.kTextView)
    .withPosition(0, 1)
    .withSize(2, 1)
    .getEntry();

    kDr = pidTab.add("kD", 0.0)
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 2)
      .withSize(2, 1)
      .getEntry();

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
    pidRotation.setP(kPr.getDouble(0));
    pidRotation.setI(kIr.getDouble(0));
    pidRotation.setD(kDr.getDouble(0));

    PowerCell biggest = pixy.getBiggestPowerCell();
    try {
      SmartDashboard.putNumber("X Offset", biggest.getX());
      SmartDashboard.putNumber("Size", biggest.getSize());

      double power = pidDrive.calculate(5000 - biggest.getSize());
      double rotation = pidRotation.calculate(biggest.getX());

      if(biggest.getX() > 25) {
        drive.setPower(Constants.leftPower, -Constants.rightPower);
      }
      else if(biggest.getX() < -25) {
        drive.setPower(-Constants.leftPower, Constants.rightPower);
      }
      else {
        drive.arcadeDrive(-power, 0);
      }

      /*
      if(biggest.getX() < 0 && biggest.getSize() < 3000) {
        drive.setPower(-rotation, rotation);
      }
      else if(biggest.getX() > 0 && biggest.getSize() < 3000) {
        drive.setPower(rotation, -rotation);
      }
      else if(biggest.getX() == 0) {
        drive.arcadeDrive(power, 0);
      }
      */

      //drive.setPower(power, -power);

      /*if(biggest.getX() > 10 && biggest.getSize() < 5000) {
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
      }*/
    }
    catch(Exception e) {
      //Nothing lol
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setPower(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
