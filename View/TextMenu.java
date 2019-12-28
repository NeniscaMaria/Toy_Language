package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){
        commands=new HashMap<>();
    }
    public void addCommand(Command commandFromUser){
        commands.put(commandFromUser.getKey(),commandFromUser);
    }
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public Map<String,Command> getMenu(){
        return commands;
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Please select a valid option!");
                continue; }
            com.execute();
        }
    }

}
