package Domain.Values;
import Domain.Types.ReferenceType;
import Interfaces.Type;
import Interfaces.Value;

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
    public Type getType() { return new ReferenceType(locationType);}
}
