package View;

import Interfaces.Value;

public class TableValue<A,B> {
    private A name;
    private B value;
    public TableValue(A nameFromUser, B valueFromUser){
        name=nameFromUser;
        value=valueFromUser;
    }
    public A getName(){
        return name;
    }
    public B getValue(){
        return value;
    }
}
