package Domain.ProgramState;
import Domain.TypeChecker;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.*;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgramState {
    private MyStackInterface<StatementInterface> executionStack;
    private MyDictionaryInterface<String,Value> symbolTable;
    private MyListInterface<Value> output;
    private StatementInterface originalProgram;
    private MyDictionaryInterface<StringValue,BufferedReader> fileTable;
    private HeapInterface heap;
    private TypeChecker typeEnvironment;
    private LockInterface<Integer,Integer> lockTable;
    private static AtomicInteger nextID;
    private AtomicInteger ID;
    @Override
    public String toString(){
        return "Thread ID: "+Integer.toString(ID.intValue())+System.lineSeparator()+executionStack.toString()+symbolTable.toString()
                +output.toString()+fileTable.toString()+heap.toString()+System.lineSeparator()+"LockTable: "+lockTable.toString();
    }
    public AtomicInteger getID(){
        return ID;
    }
    public synchronized AtomicInteger generateNewID(){//synchronized uses a lock on the entire execution of the method
        int res = nextID.getAndIncrement();
        return new AtomicInteger(res);
    }
    public void typecheck(){
        originalProgram.typecheck(this.typeEnvironment);
        System.out.println(typeEnvironment);
    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,
                        MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser,
                        HeapInterface heapFromUser){
        //constructor that does not receive a thread id from the user
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser;
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
        nextID=new AtomicInteger(0);
        ID=generateNewID();
        lockTable=new LockTable();
        typeEnvironment=new TypeChecker();

    }
    public ProgramState(MyStackInterface<StatementInterface> executionStackFromUser, MyDictionaryInterface<String,Value> symbolTableFromUser,
                        MyListInterface<Value> outputFromUser,StatementInterface programFromUser,MyDictionaryInterface<StringValue,BufferedReader> fileTableFromUser,
                        HeapInterface heapFromUser,int idFromUser,LockInterface<Integer,Integer> lockTableFromUser){
        //constructor that receives thread id from user
        executionStack=executionStackFromUser;
        symbolTable=symbolTableFromUser;
        output=outputFromUser;
        originalProgram=programFromUser;
        fileTable=fileTableFromUser;
        executionStack.push(programFromUser);
        heap=heapFromUser;
        ID=new AtomicInteger(idFromUser);
        nextID=new AtomicInteger(idFromUser+1);
        lockTable=lockTableFromUser;
        typeEnvironment=new TypeChecker();
    }
    public ProgramState(StatementInterface programFromUser){
        originalProgram=programFromUser;
        executionStack=new ExecutionStack();
        symbolTable=new SymbolTable();
        output=new Output();
        fileTable=new FileTable();
        executionStack.push(originalProgram);
        heap=new Heap();
        ID=new AtomicInteger(1);
        nextID=new AtomicInteger(2);
        typeEnvironment=new TypeChecker();
        lockTable=new LockTable();
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
    public TypeChecker getTypeChecker(){return typeEnvironment;}
    public LockInterface<Integer,Integer> getLockTable(){return lockTable;}
}

