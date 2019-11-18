package Domain.Types;
import Domain.Values.BoolValue;
import Interfaces.Type;
import Interfaces.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        return another instanceof BoolType;
    }
    @Override
    public String toString(){
        return "boolean";
    }
    public Value getDefaultValue(){
        return new BoolValue(false);
    }
}
