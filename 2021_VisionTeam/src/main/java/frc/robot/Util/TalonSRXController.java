// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

//Helper class for the Talon SRX to implement the speed controller
public class TalonSRXController extends TalonSRX implements SpeedController {

    public TalonSRXController(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public void set(double speed) {
        super.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return super.getMotorOutputPercent();
    }

    @Override
    public void disable() {
        stopMotor();
    }

    @Override
    public void stopMotor() {
        super.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void pidWrite(double output) {
        set(output);
    }

}
