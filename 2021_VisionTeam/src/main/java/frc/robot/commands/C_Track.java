// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Util.XboxGamepad;
import frc.robot.drivers.LimeLight;
import frc.robot.subsystems.SS_DriveTrain;

public class C_Track extends CommandBase {
  /** Creates a new C_Track. */

  //Constants
  //PID Tolerance
  private final double ROTATION_TOLERANCE = 1;
  private final double SETPOINT_POSITION = 0;

  private final double kP = .0005;
  private final double kI = 0.0;
  private final double kD = 0.0;

  //Controllers
  private XboxGamepad driveController = RobotContainer.getXboxController();

  //Robot Componets
  private LimeLight limeLight = RobotContainer.getLimeLight();
  private SS_DriveTrain driveTrain = SS_DriveTrain.getinstance();

  private PIDController pid = new PIDController(kP,kI,kD);


  public C_Track() {

    //init PID
    pid.setSetpoint(SETPOINT_POSITION);
    pid.setTolerance(ROTATION_TOLERANCE);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    //LimeLight Init
    //when you are initing the Lime Light you need to turn on the light
    //  and just in case i set the mode to default.
    limeLight.setCameraMode(LimeLight.CAMERA_DEFAULT_MODE);
    limeLight.setLEDMode(LimeLight.LED_ON);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //turns tward the target when there is a target.
    if(limeLight.getValidTarget() && !pid.atSetpoint()){
      //get a value between 0 and 1 with a speed offset.
      double targetOffset = pid.calculate(limeLight.getXOffset(),0);
      //replaces the power inputs as the targetOffset value.
      driveTrain.setPower(targetOffset, targetOffset * -1);
    }
    
  }

  // Called once the command ends or is interrupted.

  //All WAYS TURN OFF THE LIME_LIGHT WHEN THE COMMAND ENDS!!!
  @Override
  public void end(boolean interrupted) {
    limeLight.setLEDMode(LimeLight.LED_OFF);
  }

  // Returns true when the command should end.

  @Override
  public boolean isFinished() {
    return false;
  }
}
