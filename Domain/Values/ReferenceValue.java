package Domain.Values;
import Domain.Types.ReferenceType;
import Interfaces.Type;
import Interfaces.Value;

import java.sql.Ref;

public class ReferenceValue implements Value {
    private int address;
    private Type locationType;

    public ReferenceValue(int addressFromUser,Type locationTypeFromUser) {
        address=addressFromUser;
        locationType=locationTypeFromUser;
    }
    public boolean isEqual(Value valueFromUser){
        if (valueFromUser instanceof ReferenceValue) {
            ReferenceValue v = (ReferenceValue) valueFromUser;
            return address == v.getAddress();
        }else
            return false;
    }
    public int getAddress() {
        return address;
    }
    public Type getLocationType(){
        return locationType;
    }
    public Type getType() { return new ReferenceType(locationType);}
    @Override
    public String toString(){
        return "("+Integer.toString(address)+","+locationType.toString()+")";
    }
    public Value clone(){
        return new ReferenceValue(address,locationType);
    }
}
