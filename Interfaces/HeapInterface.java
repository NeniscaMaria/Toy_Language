package Interfaces;

import java.util.HashMap;
import java.util.Map;

public interface HeapInterface<A,B> {
    int add(B value);
    B getValue(A address);
    boolean isAddress(A address);
    void update(A address, B valueOfExpression);
    void setContent(Map<A,B> newContent);
    Map<A,B> getContent();
}
