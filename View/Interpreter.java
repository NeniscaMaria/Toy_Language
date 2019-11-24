package View;
import Domain.Expressions.*;
import Domain.Statements.*;
import Domain.ProgramState.*;
import Domain.Types.*;
import Domain.Values.*;
import Controller.Controller;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;
import Interfaces.StatementInterface;
import Repository.Repository;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.util.Scanner;

class Interpreter {

    public static void main(String[] args) {
        System.out.println("Input the log file for example1: ");
        Scanner scanner=new Scanner(System.in);
        String logfile1=scanner.nextLine();
        //example 1 int v; v=2;Print(v)
        StatementInterface printExample1 = new PrintStatement(new VariableExpression("v"));
        StatementInterface assignmentExample1 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface declarationExample1 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface compoundExample1 = new CompoundStatement(printExample1, assignmentExample1);
        StatementInterface example1 = new CompoundStatement(declarationExample1, compoundExample1);
        ProgramState prg1 = new ProgramState(example1);
        RepositoryInterface repo1 = new Repository(prg1, logfile1);
        Controller controller1 = new Controller(repo1, true);

        System.out.println("Input the log file for example2: ");
        String logfile2=scanner.nextLine();
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
        RepositoryInterface repo2 = new Repository(program2, logfile2);
        Controller controller2 = new Controller(repo2, true);

        System.out.println("Input the log file for example3: ");
        String logfile3=scanner.nextLine();
        //example 3
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        StatementInterface example3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        ProgramState program3 = new ProgramState(example3);
        RepositoryInterface repo3 = new Repository(program3, logfile3);
        Controller controller3 = new Controller(repo3, true);

        System.out.println("Input the log file for example4: ");
        String logfile4=scanner.nextLine();
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
        RepositoryInterface repo4 = new Repository(program4, logfile4);
        Controller controller4 = new Controller(repo4, true);

        System.out.println("Input the log file for example5: ");
        String logfile5=scanner.nextLine();
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
        RepositoryInterface repo5 = new Repository(program5, logfile5);
        Controller controller5 = new Controller(repo5, true);

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
        RepositoryInterface repo6 = new Repository(program6, "l6.txt");
        Controller controller6 = new Controller(repo6, true);

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
        RepositoryInterface repo7 = new Repository(program7, "l7.txt");
        Controller controller7 = new Controller(repo7, true);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", example1.toString(), controller1));
        menu.addCommand(new RunExample("2", example2.toString(), controller2));
        menu.addCommand(new RunExample("3", example3.toString(), controller3));
        menu.addCommand(new RunExample("4", example4.toString(), controller4));
        menu.addCommand(new RunExample("5", example5.toString(), controller5));
        menu.addCommand(new RunExample("6", example6.toString(), controller6));
        menu.addCommand(new RunExample("7", example7.toString(), controller7));
        menu.show();
    }
}

