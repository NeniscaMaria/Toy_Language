package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Domain.Types.StringType;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openReadFile implements StatementInterface {
    private ExpressionInterface expression;
    public openReadFile(ExpressionInterface expressionFromUser){
        expression=expressionFromUser;
    }
    @Override
    public String toString(){
        return "openReadFile("+expression.toString()+")";
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String,Value> table = state.getSymbolTable();
        MyDictionaryInterface<StringValue,BufferedReader> fileTable = state.getFileTable();
        Value val= expression.evaluate(table,state.getHeap());
        if(val.getType().equals(new StringType())){
            StringValue id=(StringValue) val;
            if(fileTable.isDefined(id)){
                throw new MyException(val+" already exists in the file table");
            }else{
                try{
                    BufferedReader newFile = new BufferedReader(new FileReader(id.getValue()));
                    fileTable.update(id,newFile);
                } catch (IOException e){
                    throw new MyException(e.getMessage());
                }

            }
        }
        else
            throw new MyException("This is not a StringType (in openReadFile("+expression+"))");
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type typeExpression=expression.typecheck(typeEnvironment);
        if(typeExpression.equals(new StringType()))
            return typeEnvironment;
        else
            throw new MyException(this.toString()+": the argument specified is not of StringType");
    }
}
