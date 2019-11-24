package Domain.ProgramState;
import Domain.Values.StringValue;
import Interfaces.*;
import java.io.BufferedReader;

public class ProgramState {
    private MyStackInterface<StatementInterface> executionStack;
    private MyDictionaryInterface<String,Value> symbolTable;
    private MyListInterface<Value> output;
    private StatementInterface originalProgram;
    private MyDictionaryInterface<StringValue,BufferedReader> fileTable;
    private HeapInterface heap;
    @Override
    public String toString(){
        return executionStack.toString()+symbolTable.toString()+output.toString()+fileTable.toString()+heap.toString();
    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser, HeapInterface heapFromUser){
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser; //deepCopy(programFromUser)
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
    }
    public ProgramState(StatementInterface programFromUser){
        originalProgram=programFromUser;
        executionStack=new ExecutionStack();
        symbolTable=new SymbolTable();
        output=new Output();
        fileTable=new FileTable();
        executionStack.push(originalProgram);
        heap=new Heap();
    }
    public MyStackInterface<StatementInterface> getStack(){
        return executionStack;
    }
    public MyDictionaryInterface<String,Value> getSymbolTable(){
        return symbolTable;
    }
    public MyListInterface<Value> getOutput(){
        return output;
    }
    public MyDictionaryInterface<StringValue, BufferedReader> getFileTable(){
        return fileTable;
    }
    public HeapInterface getHeap(){return heap;}
}

