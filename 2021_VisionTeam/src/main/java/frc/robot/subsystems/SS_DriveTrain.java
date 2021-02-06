// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util.TalonSRXController;

public class SS_DriveTrain extends SubsystemBase {
  
  private static SS_DriveTrain instance;

  public static SS_DriveTrain getinstance() {
    if(instance == null) {
      instance = new SS_DriveTrain();
    }

    return instance;
  }

  private TalonSRX leftMotor, rightMotor;

  public SS_DriveTrain() {
    rightMotor = new TalonSRXController(Constants.LEFT_DRIVE);  
    leftMotor = new TalonSRXController(Constants.RIGTH_DRIVE);
    leftMotor.setInverted(true);
    rightMotor.setInverted(false);
  }

  public void arcadeDrive(double power, double rotation) {
    if(rotation > 0) {
      setPower(power, (-2 * rotation + 1) * power);
    } else if (rotation < 0) {
      setPower((2 * rotation + 1) * power, power);
    } else {
      setPower(power, power);
    }
  }

  public void setPower(double leftPower, double rightPower) {
    leftMotor.set(ControlMode.PercentOutput, leftPower);
    rightMotor.set(ControlMode.PercentOutput, rightPower);
  }
}
