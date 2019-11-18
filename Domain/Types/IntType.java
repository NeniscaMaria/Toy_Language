package Domain.Types;
import Domain.Values.IntValue;
import Interfaces.Type;
import Interfaces.Value;

public class IntType implements Type{
    public boolean equals(Object another) {
        return another instanceof IntType;
    }
    @Override
    public String toString() {
        return "int";
    }
    public Value getDefaultValue(){
        return new IntValue(0);
    }
}
