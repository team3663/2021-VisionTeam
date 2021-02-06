// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_DriveTrain;

public class C_ArcadeDrive extends CommandBase {
  /** Creates a new C_ArcadeDrive. */

  private XboxController controller = new XboxController(0);

  private SS_DriveTrain drive = SS_DriveTrain.getinstance();

  public C_ArcadeDrive() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double power = controller.getTriggerAxis(Hand.kRight) - controller.getTriggerAxis(Hand.kLeft);
    double rotation = controller.getRawAxis(Axis.kLeftX.value);

    drive.arcadeDrive(deadzone(power), deadzone(rotation));

  }

  private double deadzone(double num) {
    if(Math.abs(num) > 0.2) {
      return num;
    }
    return 0;
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
