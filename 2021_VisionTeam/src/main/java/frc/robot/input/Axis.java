package frc.robot.input;



public abstract class Axis {
    public final double DEADBAND = 0.05;

    private boolean inverted = false;
    private double scale = 1;

    public boolean inInverted(){
        return inverted;
    }
    public void setInverted(boolean inverted){
        this.inverted = inverted;
    }
    public double getScale(){
        return scale;
    }
    public void setScale(double scale){
        this.scale = scale;
    }
    public double get(){
        return get(false,false);
    }
    public double get(boolean squared){
        return get(true, false);
        
    }
    
    public double get(boolean squared, boolean ignoreScale){
        double value = getRaw();

        if(inverted) value = -value;
        if(squared) value = Math.copySign(value*value, value);
        if(!ignoreScale) value *= scale;

        return value;
    }
    public abstract double getRaw();
    
}
