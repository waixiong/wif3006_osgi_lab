/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.impl.calcalc;

/**
 *
 * @author thechee
 */

import caloriescalculator.ifce.calcalc.ICalCalc;
        
public class CalCalcImpl implements ICalCalc {

    @Override
    public int calculateCalories(boolean isMale, int age, int weight, int height, int exerciseLevel, float weightGain) {
        float kcalPerDay = 0;
        if(isMale) {
            kcalPerDay = (float) (88.362+(13.397*weight)+(4.799*height)-(5.677*age));
        } else {
            kcalPerDay = (float) (447.593+(9.247*weight)+(3.098*height)-(4.330*age));
        }
        switch(exerciseLevel) {
            case 1:
                kcalPerDay *= 1.2;
                break;
            case 2:
                kcalPerDay *= 1.375;
                break;
            case 3:
                kcalPerDay *= 1.55;
                break;
            case 4:
                kcalPerDay *= 1.725;
                break;
            case 5:
                kcalPerDay *= 1.9;
                break;
        }
        kcalPerDay += 1100*weightGain;
        
        return Math.round(kcalPerDay);
    }
    
}
