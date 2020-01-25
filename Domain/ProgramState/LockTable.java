package Domain.ProgramState;


import Exceptions.MyException;
import Interfaces.LockInterface;
import View.TableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTable implements LockInterface<Integer,Integer> {
    private Map<Integer, Integer> lock;
    private AtomicInteger latestAddress;
    public LockTable(){
        lock = new ConcurrentHashMap<Integer, Integer>();
        latestAddress=new AtomicInteger(0);
    }
    private boolean isDefined(Integer id){
        return lock.containsKey(id);
    }
    public Integer lookUp(Integer index){
        if(isDefined(index))
            return lock.get(index);
        else
            throw new MyException("Nothing at "+index+" !");
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("LockTable:" + System.lineSeparator());
        for(int key:lock.keySet()) {
            result.append(Integer.toString(key)).append("-->").append(lock.get(key).toString()).append(System.lineSeparator());
        }
        return result.toString();
    }
    private AtomicInteger generateNewAddress(){
        latestAddress.getAndIncrement();
        return latestAddress;
    }
    public int add(Integer valueFromUser){
        AtomicInteger newAddress=generateNewAddress();
        lock.put(newAddress.get(),valueFromUser);
        return newAddress.get();
    }
    public Integer getValue(Integer addressFromUser){
        if(lock.containsKey(addressFromUser)){
            return lock.get(addressFromUser);
        }else
            throw new MyException(addressFromUser+" is not a valid address in the lockTable");
    }
    public boolean isAddress(Integer address){
        return lock.containsKey(address);
    }
    public void update(Integer address, Integer valueOfExpression){
        if(isAddress(address))
            lock.put(address,valueOfExpression);
        else
            throw new MyException("You tried to access a non-existing address!!");
    }
    public ObservableList<TableValue<Integer,Integer>> getLockValues(){
        ObservableList<TableValue<Integer,Integer>> list = FXCollections.observableArrayList();
        for(Integer key: lock.keySet())
            list.add(new TableValue<Integer, Integer>(key,lock.get(key)));
        return list;
    }
}
