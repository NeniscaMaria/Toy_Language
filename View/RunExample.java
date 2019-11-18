package View;

import Controller.Controller;
import Exceptions.MyException;
import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String description,Controller controllerFromUser){
        super(key, description);
        this.controller=controllerFromUser;
    }
    @Override
    public void execute() {
        try{
            controller.allStepExecution();
        }
        catch (MyException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}