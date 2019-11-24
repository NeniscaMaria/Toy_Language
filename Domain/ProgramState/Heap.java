package Domain.ProgramState;

import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.HeapInterface;
import Interfaces.Value;

import java.util.HashMap;

public class Heap implements HeapInterface<Integer,Value> {
    private HashMap<Integer, Value> heap;
    private int latestAddress;
    public Heap(){
        heap = new HashMap<Integer, Value>();
        latestAddress=0;
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("Heap:" + System.lineSeparator());
        for(int key:heap.keySet()) {
            result.append(Integer.toString(key)).append("-->").append(heap.get(key).toString()).append(System.lineSeparator());
        }
        return result.toString();
    }
    private int generateNewAddress(){
        latestAddress+=1;
        return latestAddress;
    }
    public int add(Value valueFromUser){
        int newAddress=generateNewAddress();
        heap.put(newAddress,valueFromUser);
        return newAddress;
    }
    public  Value getValue(Integer addressFromUser){
        if(heap.containsKey(addressFromUser)){
            Value value = heap.get(addressFromUser);
            return value;
        }else
            throw new MyException(addressFromUser+" is not a valid address in the heap");
    }
}
