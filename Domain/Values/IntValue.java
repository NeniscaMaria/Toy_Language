package Domain.Values;
import Interfaces.Value;
import Interfaces.Type;
import Domain.Types.IntType;

public class IntValue implements Value{
    private int value;
    public IntValue(int valueFromUser){
        value=valueFromUser;
    }
    public int getValue(){
        return value;
    }
    @Override
    public String toString(){
        return Integer.toString(value);
    }
    public Type getType(){
        return new IntType();
    }
    public boolean isEqual(Value valueFromUser){
        IntValue v = (IntValue)valueFromUser;
        return value==v.getValue();
    }
}
