// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SS_DriveTrain extends SubsystemBase {
  /** Creates a new SS_DriveTrain. */
  
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

   
  }
  private TalonSRX rightMotor, leftMotor;
  
  private static SS_DriveTrain instance;
  public static SS_DriveTrain getinstance() {
    if(instance==null){
      instance = new SS_DriveTrain();
    }
    return instance;
  }


  public SS_DriveTrain() {
    rightMotor = new TalonSRX(Constants.LEFT_DRIVE);
    leftMotor = new TalonSRX(Constants.RIGTH_DRIVE);
    rightMotor.setInverted(true);
  }

  public void setPower(double leftPower, double rightPower) {
    leftMotor.set(ControlMode.PercentOutput, leftPower);
    rightMotor.set(ControlMode.PercentOutput, rightPower);
  }
}
