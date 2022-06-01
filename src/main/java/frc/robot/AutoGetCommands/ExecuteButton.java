package frc.robot.AutoGetCommands;

import java.util.*;
public abstract class ExecuteButton {
    
    private int buttonNum;
    private String cmdName;
    private boolean finished;
    private ArrayList<Boolean> inputs;

    public ExecuteButton (int buttonNum, String cmdName, ArrayList<Boolean> inputs)
    {
        this.buttonNum = buttonNum;
        this.cmdName = cmdName;
        this.inputs = inputs;
    }


    public void run(){  }
    public boolean isFinished(){return finished;}
    public String getCmdName(){return cmdName;}
    public int getButtonNum(){return buttonNum;}
    public ArrayList<Boolean> getInputs(){return inputs;}

}
