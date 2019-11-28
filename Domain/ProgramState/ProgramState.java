package Domain.ProgramState;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.*;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    private MyStackInterface<StatementInterface> executionStack;
    private MyDictionaryInterface<String,Value> symbolTable;
    private MyListInterface<Value> output;
    private StatementInterface originalProgram;
    private MyDictionaryInterface<StringValue,BufferedReader> fileTable;
    private HeapInterface heap;
    private static int ID;
    @Override
    public String toString(){
        return "Thread ID: "+Integer.toString(ID)+System.lineSeparator()+executionStack.toString()+symbolTable.toString()+output.toString()+fileTable.toString()+heap.toString();
    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser, HeapInterface heapFromUser){
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser; //deepCopy(programFromUser)
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
        //TODO: generate unique id
    }
    public ProgramState(StatementInterface programFromUser){
        originalProgram=programFromUser;
        executionStack=new ExecutionStack();
        symbolTable=new SymbolTable();
        output=new Output();
        fileTable=new FileTable();
        executionStack.push(originalProgram);
        heap=new Heap();
        //TODO: generate unique id
    }
    public ProgramState oneStepExecution() throws MyException, IOException {
        if(executionStack.isEmpty())
            throw new MyException("Execution stack is empty!");
        StatementInterface currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
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

