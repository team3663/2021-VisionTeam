package frc.robot.input;



import edu.wpi.first.wpilibj2.command.button.Button;

public abstract class Controller {

    //Triggers
    public abstract Axis getLeftTriggerAxis();
    public abstract Axis getRightTriggerAxis();
    //Right Joystick
    public abstract Axis getRightJoystickYAxis();
    public abstract Axis getRightJoystickXAxis();
    //Left Joystick
    public abstract Axis getLeftJoystickYAxis();
    public abstract Axis getLeftJoystickXAxis();
    //Buttons
    public abstract Button getAButton(); 
    public abstract Button getBButton();
    public abstract Button getXButton();
    public abstract Button getYButton();
    public abstract Button getLeftBumperButton();
    public abstract Button getRightBumperButton();
    public abstract Button getBackButton();
    public abstract Button getStartButton();
    public abstract Button getLeftJoystickButton();
    public abstract Button getRightJoystickButton();
}
