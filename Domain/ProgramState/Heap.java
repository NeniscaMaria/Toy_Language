package Domain.ProgramState;

import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.HeapInterface;
import Interfaces.Value;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap implements HeapInterface<Integer,Value> {
    private Map<Integer, Value> heap;
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
    public boolean isAddress(Integer address){
        return heap.containsKey(address);
    }
    public void update(Integer address, Value valueOfExpression){
        if(isAddress(address))
            heap.put(address,valueOfExpression);
        else
            throw new MyException("You tried to access a non-existing address!!");
    }
    public void setContent(Map<Integer,Value> newContent){
        heap=newContent;
    }
    public Map<Integer,Value> getContent(){
        return heap;
    }
    public List<Integer> getInAddresses(){
        List<Integer> addresses=new ArrayList<>();
        for(Integer key:heap.keySet()){
            if(heap.get(key) instanceof ReferenceValue){
                addresses.add(((ReferenceValue) heap.get(key)).getAddress());
            }
        }
        return addresses;
    }
}
