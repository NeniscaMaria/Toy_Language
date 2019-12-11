package Domain.ProgramState;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.*;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgramState {
    private MyStackInterface<StatementInterface> executionStack;
    private MyDictionaryInterface<String,Value> symbolTable;
    private MyListInterface<Value> output;
    private StatementInterface originalProgram;
    private MyDictionaryInterface<StringValue,BufferedReader> fileTable;
    private HeapInterface heap;
    private static int nextID;
    private int ID;
    private Lock lock;
    @Override
    public String toString(){
        return "Thread ID: "+Integer.toString(ID)+System.lineSeparator()+executionStack.toString()+symbolTable.toString()
                +output.toString()+fileTable.toString()+heap.toString();
    }
    public int getID(){
        return ID;
    }
    public int generateNewID(){
        //TODO: synchronized
        lock.lock();
        int copy=nextID;
        nextID+=1;
        lock.unlock();
        return copy;
    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,
                        MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser,
                        HeapInterface heapFromUser){
        //constructor that does not receive a thread id from the user
        lock = new ReentrantLock();
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser;
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
        ID=1;
        nextID=2;
        Lock lock = new ReentrantLock();
        generateNewID();
    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,
                        MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser,
                        HeapInterface heapFromUser,int idFromUser){
        //constructor that receives thread id from user
        lock = new ReentrantLock();
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser;
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
        ID=idFromUser;
        nextID=idFromUser+1;
        Lock lock = new ReentrantLock();
    }
    public ProgramState(StatementInterface programFromUser){
        lock = new ReentrantLock();
        originalProgram=programFromUser;
        executionStack=new ExecutionStack();
        symbolTable=new SymbolTable();
        output=new Output();
        fileTable=new FileTable();
        executionStack.push(originalProgram);
        heap=new Heap();
        nextID=2;
        ID=1;
        Lock lock = new ReentrantLock();

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

