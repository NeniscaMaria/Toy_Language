package Interfaces;

public interface ExpressionInterface {
    Value evaluate(MyDictionaryInterface<String,Value> table,HeapInterface<Integer,Value> heap);
    Type typecheck(MyDictionaryInterface<String,Type> typeEnvironment);
}

