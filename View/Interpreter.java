package View;
import Domain.Expressions.*;
import Domain.Statements.*;
import Domain.ProgramState.*;
import Domain.Types.*;
import Domain.Values.*;
import Controller.Controller;
import Interfaces.RepositoryInterface;
import Interfaces.StatementInterface;
import Repository.Repository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import static javafx.application.Application.launch;

import javafx.application.Application;

public class Interpreter extends Application {
    private static TextMenu menu;
    //private ControllerGUI controller;
    public static void main(String[] args) {
        launch(args);
    }
    public static TextMenu getMenu(){
        return menu;
    }
    public static void setMenu(TextMenu menuFromUser){
        menu=menuFromUser;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        //initialize class members
        menu=new TextMenu();
        populateExamplesMenu();
        FXMLLoader mainLoader=new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("gui.fxml"));
        Parent root = mainLoader.load();
        primaryStage.setTitle("Toy Language Interpreter");
        Scene scene = new Scene(root, 1000, 970);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        ControllerGUI mainController = mainLoader.getController();

        FXMLLoader secondLoader=new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("examplesWindow.fxml"));
        Parent root2 = secondLoader.load();
        primaryStage.setTitle("Examples");
        Scene scene2 = new Scene(root2, 1000, 970);

        primaryStage.setScene(scene);
        populateExamplesMenu();
        primaryStage.show();

        ControllerExamplesWindow exController = secondLoader.getController();
        exController.setMainController(mainController);

        Stage secondStage = new Stage();
        secondStage.setScene(scene2);
        secondStage.show();


    }
    public void populateExamplesMenu(){
        //example 1 int v; v=2;Print(v)
        StatementInterface printExample1 = new PrintStatement(new VariableExpression("v"));
        StatementInterface assignmentExample1 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface declarationExample1 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface compoundExample1 = new CompoundStatement(printExample1, assignmentExample1);
        StatementInterface example1 = new CompoundStatement(declarationExample1, compoundExample1);
        ProgramState prg1 = new ProgramState(example1);
        RepositoryInterface repo1 = new Repository(prg1, "log1.txt");
        Controller controller1 = new Controller(repo1);

        //example 2 s
        //int a;int b; a=2+3*5;b=a+1;Print(b)
        StatementInterface example2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement(
                                "a", new ArithmeticExpression("+", new ValueExpression(new IntValue(2)), new
                                ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        ProgramState program2 = new ProgramState(example2);
        RepositoryInterface repo2 = new Repository(program2, "log2.txt");
        Controller controller2 = new Controller(repo2);

        //example 3
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        StatementInterface example3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        ProgramState program3 = new ProgramState(example3);
        RepositoryInterface repo3 = new Repository(program3, "log3.txt");
        Controller controller3 = new Controller(repo3);

        //example4
        //string varf;
        //varf="test.in";
        //openRFile(varf);
        //int varc;
        //readFile(varf,varc);print(varc);
        //readFile(varf,varc);print(varc)
        //closeRFile(varf)
        StatementInterface example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("testin.txt"))),
                        new CompoundStatement(new openReadFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new closeReadFile(new VariableExpression("varf"))))))))));
        ProgramState program4 = new ProgramState(example4);
        RepositoryInterface repo4 = new Repository(program4, "log4.txt");
        Controller controller4 = new Controller(repo4);

        //example 5
        //bool a;
        // a= 2<3;
        //bool b;
        // b= 5<1;
        //if(2!=3) then print(5) else print(6)
        StatementInterface example5 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new AssignmentStatement("a",new RelationalExpression(new ValueExpression(new IntValue(2)),new ValueExpression(new IntValue(3)),"<")),
                        new CompoundStatement(new VariableDeclarationStatement("b",new BoolType()),
                                new CompoundStatement(new AssignmentStatement("b", new RelationalExpression(new ValueExpression(new IntValue(5)),new ValueExpression(new IntValue(1)),"<")),
                                        new IfStatement(new RelationalExpression(new ValueExpression(new IntValue(2)),new ValueExpression(new IntValue(3)),"!="),
                                                new PrintStatement(new ValueExpression(new IntValue(5))),new PrintStatement(new ValueExpression(new IntValue(6))))))));
        ProgramState program5 = new ProgramState(example5);
        RepositoryInterface repo5 = new Repository(program5, "log4.txt");
        Controller controller5 = new Controller(repo5);

        //example6
        //Ref int v;
        // new(v,20);
        // Ref Ref int a;
        // new(a,v);
        // print(v);
        // print(a)
        StatementInterface example6=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
        ProgramState program6 = new ProgramState(example6);
        RepositoryInterface repo6 = new Repository(program6, "log6.txt");
        Controller controller6 = new Controller(repo6);

        //example7
        //Ref int v;
        // new(v,20);
        // Ref Ref int a;
        // new(a,v);
        // print(rH(v));
        // print(rH(rH(a))+5)
        StatementInterface example7=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression("+",new ReadHeap(new ReadHeap(new VariableExpression("a"))),new ValueExpression(new IntValue(5)))))))));
        ProgramState program7 = new ProgramState(example7);
        RepositoryInterface repo7 = new Repository(program7, "log7.txt");
        Controller controller7 = new Controller(repo7);

        //example8
        //Ref int v;
        // new(v,20);
        // print(rH(v));
        // wH(v,30);
        // print(rH(v)+5);
        StatementInterface example8=new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeap("v",new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression("+",new ReadHeap(new VariableExpression("v")),new ValueExpression(new IntValue(5))))))));
        ProgramState program8 = new ProgramState(example8);
        RepositoryInterface repo8 = new Repository(program8, "log8.txt");
        Controller controller8 = new Controller(repo8);
        //example9
        //Ref int v;
        //new(v,20);
        // Ref Ref int a;
        // new(a,v);
        // new(v,30);
        // print(rH(rH(a)))
        StatementInterface example9 = new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocation("v",new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        ProgramState program9 = new ProgramState(example9);
        RepositoryInterface repo9 = new Repository(program9, "log9.txt");
        Controller controller9 = new Controller(repo9);
        //example10
        // int v;
        // v=4;
        // (while (v>0) print(v);v=v-1);
        // print(v)
        StatementInterface example10=new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new While(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignmentStatement("v",
                                        new ArithmeticExpression("-",new VariableExpression("v"),new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
        ProgramState program10 = new ProgramState(example10);
        RepositoryInterface repo10 = new Repository(program10, "log10.txt");
        Controller controller10 = new Controller(repo10);
        //example 11
        //int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        StatementInterface example11=new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(
                                                new CompoundStatement(new WriteHeap("a",new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeap(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));
        ProgramState program11 = new ProgramState(example11);
        RepositoryInterface repo11 = new Repository(program11, "log11.txt");
        Controller controller11 = new Controller(repo11);
        //example to check the typechecker
        //bool a;
        // a= 2<3;
        //bool b;
        // b= 5<hello;
        //if(hello) then print(5) else print(6)
        CompoundStatement example12=new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new AssignmentStatement("a",new RelationalExpression(new ValueExpression(new IntValue(2)),new ValueExpression(new IntValue(3)),"<")),
                        new CompoundStatement(new VariableDeclarationStatement("b",new BoolType()),
                                new CompoundStatement(new AssignmentStatement("b", new RelationalExpression(new ValueExpression(new IntValue(5)),new ValueExpression(new StringValue("hello")),"<")),
                                        new IfStatement(new ValueExpression(new StringValue("hello")),
                                                new PrintStatement(new ValueExpression(new IntValue(5))),new PrintStatement(new ValueExpression(new IntValue(6))))))));
        ProgramState program12 = new ProgramState(example12);
        RepositoryInterface repo12 = new Repository(program12, "log12.txt");
        Controller controller12 = new Controller(repo12);
        menu.addCommand(new RunExample(1, example1.getText(), controller1));
        menu.addCommand(new RunExample(2, example2.getText(), controller2));
        menu.addCommand(new RunExample(3, example3.getText(), controller3));
        menu.addCommand(new RunExample(4, example4.getText(), controller4));
        menu.addCommand(new RunExample(5, example5.getText(), controller5));
        menu.addCommand(new RunExample(6, example6.getText(), controller6));
        menu.addCommand(new RunExample(7, example7.getText(), controller7));
        menu.addCommand(new RunExample(8, example8.getText(), controller8));
        menu.addCommand(new RunExample(9, example9.getText(), controller9));
        menu.addCommand(new RunExample(10, example10.getText(), controller10));
        menu.addCommand(new RunExample(11, example11.getText(), controller11));
        menu.addCommand(new RunExample(12, example12.getText(), controller12));
        //menu.show();
    }

}

