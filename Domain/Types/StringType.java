package Domain.Types;

import Domain.Values.StringValue;
import Interfaces.Type;
import Interfaces.Value;

public class StringType implements Type {
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
    @Override
    public String toString() {
        return "string";
    }
    public Value getDefaultValue(){
        return new StringValue("");
    }
}
