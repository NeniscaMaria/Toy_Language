package Domain.Values;

import Domain.Types.StringType;
import Interfaces.Type;
import Interfaces.Value;

import javax.print.DocFlavor;

public class StringValue implements Value {
    private String value;
    public StringValue(String valueFromUser){
        value=valueFromUser;
    }
    public String getValue(){
        return value;
    }
    @Override
    public String toString(){
        return value;
    }
    public Type getType(){
        return new StringType();
    }
    public boolean isEqual(Value valueFromUser){
        StringValue v = (StringValue)valueFromUser;
        return value.equals(v.getValue());
    }
    public Value clone(){
        return new StringValue(value);
    }
}
