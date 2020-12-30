/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

/**
 *
 * @author Kanfitine
 */
public class Variables {
    private int variableId;
    private String variableName;
    private String variableValue;

    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public Variables(int variableId, String variableName, String variableValue) {
        this.variableId = variableId;
        this.variableName = variableName;
        this.variableValue = variableValue;
    }
    
}
