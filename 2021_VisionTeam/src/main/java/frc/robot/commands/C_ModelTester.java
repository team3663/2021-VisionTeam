// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.PatternRecognition2;
import frc.robot.drivers.Pixy;

public class C_ModelTester extends CommandBase {

  private NetworkTableEntry currentModel;
  private NetworkTableEntry prediction;
  private NetworkTableEntry save;

  private ArrayList<int[]> savedModels = new ArrayList<int[]>();

  private int[] detectedModel = PatternRecognition2.generateModel();

  public C_ModelTester() {
    ShuffleboardTab tab = Shuffleboard.getTab("Model Builder");

    currentModel = tab.add("Model", "")
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 0)
      .withSize(1, 4)
      .getEntry();

    prediction = tab.add("Prediction", "-1")
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 1)
      .withSize(1, 1)
      .getEntry();

    save = tab.add("save", false)
      .withWidget(BuiltInWidgets.kToggleButton)
      .withPosition(0, 1)
      .withSize(1, 1)
      .getEntry();
  }

  @Override
  public void execute() {

    boolean updateModel = true;

    if(Pixy.getInstance().getNumPowerCells() == 3) {
      for(int i = 0; i < 3; i++) {
        if(Pixy.getInstance().getPowerCells().get(i).getAge() < 255) {
          updateModel = false;
        }
      }
    } else {
      updateModel = false;
    }
    
    if(updateModel) {
      detectedModel = PatternRecognition2.generateModel();
    }

    //show the currently detected model
    String modelString = "";
    for(int i = 0; i < 6; i++) {
      modelString += "  " + detectedModel[i];
    }

    currentModel.setString(modelString);

    //save the model if needed
    if(save.getBoolean(false)) {
      savedModels.add(detectedModel);
      save.setBoolean(false);
    }

    //make a prediction for the model

    int currentPrediction = 0;

    for(int i = 0; i < savedModels.size(); i++) {
      if(PatternRecognition2.compareModel(detectedModel, savedModels.get(i)) < PatternRecognition2.compareModel(detectedModel, savedModels.get(currentPrediction))) {
        currentPrediction = i;
      }
    }

    prediction.setString("" + currentPrediction);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
