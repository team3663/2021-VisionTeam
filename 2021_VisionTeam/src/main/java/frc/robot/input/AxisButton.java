package frc.robot.input;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class AxisButton extends Trigger {
    private double acuationForce;
    private Axis axis;

    public AxisButton(Axis axis){
        this.axis = axis;
        acuationForce = .5;
        
    }
    public AxisButton(Axis axis, BooleanSupplier isPressed){
        super(isPressed);
        this.axis = axis;
        acuationForce = .5;
        
    }

    public AxisButton(Axis axis,double acuationForce){
        this.axis = axis;
        this.acuationForce = acuationForce;
    }
    
    public AxisButton(Axis axis,double acuationForce, BooleanSupplier isPressed){
        super(isPressed);
        this.axis = axis;
        this.acuationForce = acuationForce;
    }



    





    public AxisButton whenPressed(final Command command){
        whenActive(command);
        return this;
    }
    public AxisButton whenPressed(final Command command, boolean interruptible){
        whenActive(command,interruptible);
        return this;
    }

    public AxisButton whenHeld(final Command command){
        whileActiveContinuous(command);
        return this;
    }

    public AxisButton whenHeld(final Command command,boolean interruptible){
        whileActiveContinuous(command,interruptible);
        return this;
    }

    public AxisButton whenReleased(final Command command){
        whenInactive(command);
        return this;
    }

    public boolean get(){
        return (axis.get() > acuationForce);
    }
    public double getAxis(){
        return axis.get();
    }
}
