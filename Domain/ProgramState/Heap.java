package Domain.ProgramState;

import Domain.Values.IntValue;
import Interfaces.HeapInterface;
import Interfaces.Value;

import java.util.HashMap;

public class Heap implements HeapInterface {
    private HashMap<Integer, Value> heap;
    public Heap(){
        heap = new HashMap<Integer, Value>();
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("Heap:" + System.lineSeparator());
        for(int key:heap.keySet()) {
            result.append(Integer.toString(key)).append("-->").append(heap.get(key).toString()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
