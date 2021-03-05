// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Util;

import java.util.ArrayList;

import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;

public class PatternRecognition2 {
    
    //returns an error between the models. The lower the error, the closer the models
    public static int compareModel(int[] a, int[] b) {
        
        int error = 0;

        for(int i = 0; i < 6; i++) {
            error += Math.abs(a[i] - b[i]);
        }

        return error;
    }

    public static int[] generateModel() {
        return generateModel(Pixy.getInstance().getPowerCells());
    }

    //models are an array of six integers
    public static int[] generateModel(ArrayList<PowerCell> cells) {

        cells = (ArrayList<PowerCell>) cells.clone();
        ArrayList<PowerCell> orderedCells = new ArrayList<PowerCell>();

        for(int i = 0; i < 3; i++) {
            orderedCells.add(PowerCell.getMarkerCell(0, 0, 0));
        }

        //Sort the power cells by x
        int intitialSize = cells.size();
        for(int i = 0; i < intitialSize; i++) {

            int smallestIndex = 0;

            for(int j = 0; j < cells.size(); j++) {
                if(cells.get(j).getX() < cells.get(smallestIndex).getX()) {
                    smallestIndex = j;
                }

                orderedCells.set(i, cells.remove(smallestIndex));
            }
        }

        //create the model
        int[] model = new int[6];

        //add the distances to the model
        for(int i = 0; i < 6; i += 2) {
            model[i] = orderedCells.get(1).getX() - orderedCells.get(0).getX();
            model[i + 1] = orderedCells.get(1).getY() - orderedCells.get(0).getY();

            //shift everything over in the array
            orderedCells.add(orderedCells.remove(0));
        }

        return model;
    }
}
