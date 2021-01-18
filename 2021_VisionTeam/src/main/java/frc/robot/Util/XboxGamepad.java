// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Util;


import edu.wpi.first.wpilibj.XboxController;

/** Add your docs here. */
public class XboxGamepad extends XboxController{
    private double deadzone;
  






    public XboxGamepad(int port, double deadzone) {
        super(port);
        
        this.deadzone = deadzone;
    }

    @Override
    public double getRawAxis(int axis) {

        double reading = super.getRawAxis(axis);

        if(Math.abs(reading)<= deadzone){
            return 0;
        }
        return reading;
        
    }
    
    
}
