package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Types.StringType;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.*;

import java.io.BufferedReader;
import java.io.IOException;
/*
class that implements the closing of a file in our toy language
 */
public class closeReadFile implements StatementInterface {
    private ExpressionInterface expression;
    public closeReadFile(ExpressionInterface expressionFromUser){
        expression=expressionFromUser;
    }
    @Override
    public String toString(){
        return "closeReadFile("+expression.toString()+")";
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String, Value> symbolTable=state.getSymbolTable();
        Value val=expression.evaluate(symbolTable,state.getHeap());
        StringValue id = (StringValue)val;
        MyDictionaryInterface<StringValue, BufferedReader> fileTable=state.getFileTable();
        if(fileTable.isDefined(id)) {
            try {
                BufferedReader descriptor = fileTable.getValue(id);
                descriptor.close();
                fileTable.delete(id);
            }catch (IOException e){
                throw new MyException(e.getMessage());
            }
        }else
                throw new MyException(id+" is not defined in the FileTable");
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type typeExpression=expression.typecheck(typeEnvironment);
        if(typeExpression.equals(new StringType()))
            return typeEnvironment;
        else
            throw new MyException(this.toString()+": the argument is not of StringType");
    }
}
