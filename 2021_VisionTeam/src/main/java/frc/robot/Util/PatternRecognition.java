// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Util;

import java.util.ArrayList;
import java.util.Arrays;

import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;

public class PatternRecognition {

    public enum Pattern {
        RED_A(0),
        RED_B(1),
        BLUE_A(2),
        BLUE_B(3);

        private PowerCell[][] PatternArray = {
            {PowerCell.getMarkerCell(1, 1, 1), PowerCell.getMarkerCell(1, 1, 1)},
            {PowerCell.getMarkerCell(1, 1, 1), PowerCell.getMarkerCell(1, 1, 1)},
            {PowerCell.getMarkerCell(1, 1, 1), PowerCell.getMarkerCell(1, 1, 1)},
            {PowerCell.getMarkerCell(1, 1, 1), PowerCell.getMarkerCell(1, 1, 1)}
        };

        Pattern(int newNumber) {
            number = newNumber;
        }

        private int number;

        public PowerCell[] getPattern() {
            return PatternArray[number];
        }

    }

    public static Pattern getPattern() {
        checkPattern(Pattern.RED_A, Pixy.getInstance().getPowerCells());

        


        return Pattern.RED_A;
    }

    private static double checkPattern(Pattern pattern, ArrayList<PowerCell> actual) {
        ArrayList<PowerCell> patternArr = new ArrayList<PowerCell>(Arrays.asList(pattern.getPattern()));
        ArrayList<PowerCell> actualArr = (ArrayList) actual.clone();

        if(patternArr.size() != actualArr.size()) {
            return 0.0;
        }

        double finalConfidence = 0;
        int avarageDivide = patternArr.size();

        for(int i = 0; i < patternArr.size(); i++) {

            double closestConfidence = 0;
            int closestIndex = 0;
            for(int j = 0; j < actualArr.size(); j++) {

                double currentConfidence = actualArr.get(0).compare(patternArr.get(j));

                if(currentConfidence > closestConfidence) {
                    closestConfidence = currentConfidence;
                    closestIndex = j;
                }
            }

            finalConfidence += closestConfidence;
            patternArr.remove(closestIndex);
            actualArr.remove(0);
        }

        return finalConfidence / avarageDivide;
    }
}