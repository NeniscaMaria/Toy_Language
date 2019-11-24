package Interfaces;


public interface HeapInterface<A,B> {
    int add(B value);
    B getValue(A address);
    boolean isAddress(A address);
    void update(A address, B valueOfExpression);
}
