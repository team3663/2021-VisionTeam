// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Util.XboxGamepad;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Drive extends CommandBase {
  /** Creates a new C_Drive. */
private SS_DriveTrain drive;
private XboxGamepad controller;
  public C_Drive() {
    drive = SS_DriveTrain.getinstance();
    controller = RobotContainer.getXboxController();
   addRequirements(SS_DriveTrain.getinstance());

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setPower(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.setPower(controller.getRawAxis(Axis.kLeftY.value), controller.getRawAxis(Axis.kRightY.value));  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setPower(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
