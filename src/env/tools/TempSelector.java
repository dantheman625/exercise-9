package tools;

import java.util.Arrays;
import java.util.ArrayList;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class TempSelector extends Artifact{

    @OPERATION
    public void selectTempOfAgent(String agent, Object[] temperatureList, OpFeedbackParam<Double> temp) {
        
        ArrayList<Object> tempList = new ArrayList<Object>(Arrays.asList(temperatureList));
        for (Object obj : tempList) {
            Object[] objArr = (Object[]) obj;
            if ((String) objArr[1] == agent) {
                temp.set((Double) objArr[1]);
            }
        }
    }   
}
