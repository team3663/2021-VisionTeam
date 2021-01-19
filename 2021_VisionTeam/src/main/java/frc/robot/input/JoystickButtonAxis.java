package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;


public class JoystickButtonAxis extends Button{

    private final GenericHID m_joystick;
        private final int m_axisNumber;
        private final double m_acuationForce;
        
    public JoystickButtonAxis(GenericHID joystick, int axisNumber, double acuationForce){
        
        m_joystick = joystick;
        m_axisNumber = axisNumber;
        m_acuationForce = acuationForce;

    }

    @Override
    public boolean get(){
        return m_joystick.getRawAxis(m_axisNumber) > m_acuationForce;
    }
}
