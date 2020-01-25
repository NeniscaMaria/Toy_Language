package Interfaces;

public interface Value {
    Type getType();
    boolean isEqual(Value valueFromUser);
    Value clone();

}
