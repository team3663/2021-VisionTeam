package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XboxController extends Controller {

    private final Joystick joystick;

    private final Button aButton;
    private final Button bButton;
    private final Button xButton;
    private final Button yButton;
    private final Button leftBumperButton;
    private final Button rightBumperButton;
    private final Button backButton;
    private final Button startButton;
    private final Button leftStickButton;
    private final Button rightStickButton;

    private final Axis leftTriggerAxis;
    private final Axis rightTriggerAxis;
    private final Axis leftJoyStickYAxis;
    private final Axis leftJoystickXAxis;
    private final Axis rightJoystickYAxis;
    private final Axis rightJoystickXAxis;

    private final JoystickButtonAxis leftTriggerButton;
    private final JoystickButtonAxis rightTriggerButton;

    public XboxController(int port){
        joystick = new Joystick(port);

        aButton = new JoystickButton(joystick, 1);
        bButton = new JoystickButton(joystick, 2);
        xButton = new JoystickButton(joystick, 3);
        yButton = new JoystickButton(joystick, 4);

        leftBumperButton = new JoystickButton(joystick, 5);
        rightBumperButton = new JoystickButton(joystick, 6);

        backButton = new JoystickButton(joystick, 7);
        startButton = new JoystickButton(joystick, 8);

        leftStickButton = new JoystickButton(joystick, 9);
        rightStickButton = new JoystickButton(joystick, 10);

        leftJoystickXAxis = new JoystickAxis(joystick, 0);
        leftJoyStickYAxis = new JoystickAxis(joystick, 1);

        leftTriggerAxis = new JoystickAxis(joystick, 2);
        rightTriggerAxis = new JoystickAxis(joystick, 3);

        rightJoystickXAxis = new JoystickAxis(joystick, 4);
        rightJoystickYAxis = new JoystickAxis(joystick, 5);

        leftTriggerButton = new JoystickButtonAxis(joystick, 2, .25);
        rightTriggerButton = new JoystickButtonAxis(joystick, 3, .25);        
    }

    @Override
    public Axis getLeftTriggerAxis() {
        return leftTriggerAxis;
    }

    @Override
    public Axis getRightTriggerAxis() {
        return rightTriggerAxis;
    }

    @Override
    public Axis getRightJoystickYAxis() {
        return rightJoystickYAxis;
    }

    @Override
    public Axis getRightJoystickXAxis() {
        return rightJoystickXAxis;
    }

    @Override
    public Axis getLeftJoystickYAxis() {
        return leftJoyStickYAxis;
    }

    @Override
    public Axis getLeftJoystickXAxis() {
        return leftJoystickXAxis;
    }

    @Override
    public Button getAButton() {
        return aButton;
    }

    @Override
    public Button getBButton() {
        return bButton;
    }

    @Override
    public Button getXButton() {
        return xButton;
    }

    @Override
    public Button getYButton() {
        return yButton;
    }

    @Override
    public Button getLeftBumperButton() {
        return leftBumperButton;
    }

    @Override
    public Button getRightBumperButton() {
        return rightBumperButton;
    }

    @Override
    public Button getBackButton() {
        return backButton;
    }

    @Override
    public Button getStartButton() {
        return startButton;
    }

    @Override
    public Button getLeftJoystickButton() {
        return rightStickButton;
    }

    @Override
    public Button getRightJoystickButton() {
        return leftStickButton;
    }
    public JoystickButtonAxis getLeftTriggerButton(){
        return leftTriggerButton;
    }
    public JoystickButtonAxis getRightTriggerButton(){
        return rightTriggerButton;
    }
    
}
