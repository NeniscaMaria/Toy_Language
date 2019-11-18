package Interfaces;

public interface MyDictionaryInterface<A,B> {
    boolean isDefined(A id);
    B getValue(A id);
    void update(A id,B val);
    B lookup(A id);
    void delete(A id);
}
