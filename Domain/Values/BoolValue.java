package Domain.Values;
import Interfaces.Value;
import Interfaces.Type;
import Domain.Types.BoolType;

public class BoolValue implements Value{
    private boolean value;
    public BoolValue(boolean valueFromUser){
        value=valueFromUser;
    }
    public boolean getValue(){
        return value;
    }
    @Override
    public boolean isEqual(Value valueFromUser){
        BoolValue v= (BoolValue)valueFromUser;
        return value==v.getValue();
    }

    public String toString(){
        return Boolean.toString(value);
    }
    public Type getType(){
        return new BoolType();
    }
}
