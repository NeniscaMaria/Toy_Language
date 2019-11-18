package Domain.ProgramState;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;

import java.io.BufferedReader;
import java.util.HashMap;

public class FileTable implements MyDictionaryInterface<StringValue,BufferedReader> {
    HashMap<StringValue, BufferedReader> fileTable;
    public FileTable(){
        fileTable=new HashMap<StringValue, BufferedReader>();
    }
    public void update(StringValue id, BufferedReader val){
        fileTable.put(id,val);
    }
    public boolean isDefined(StringValue id){
        return fileTable.containsKey(id);
    }
    public BufferedReader getValue(StringValue id){
        return fileTable.get(id);
    }
    public BufferedReader lookup(StringValue id){
        if(isDefined(id))
            return fileTable.get(id);
        else
            throw new MyException("File "+id+" is not opened!");
    }
    public void delete(StringValue id){
        fileTable.remove(id);
    }
    @Override
    public String toString(){
        String result = "FileTable:"+System.lineSeparator();
        for(StringValue key:fileTable.keySet()){
            result+=key.getValue()+System.lineSeparator();
        }
        return result;
    }
}
