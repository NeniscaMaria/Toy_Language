package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.ExpressionInterface;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Value;
import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements StatementInterface {
    private ExpressionInterface expression;
    private String variableName;
    public readFile(ExpressionInterface expressionFromUser,String variableNameFromUser){
        expression=expressionFromUser;
        variableName=variableNameFromUser;
    }
    @Override
    public String toString(){
        return "readFile("+expression.toString()+", "+variableName+")";
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String, Value> symbolTable=state.getSymbolTable();
        if(symbolTable.isDefined(variableName)){
            if(symbolTable.lookup(variableName).getType().equals(new IntType())) {
                Value val = expression.evaluate(symbolTable,state.getHeap());
                if (val.getType().equals(new StringType())) {
                    StringValue id = (StringValue) val;
                    MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
                    if (fileTable.isDefined(id)) {
                        BufferedReader descriptor = fileTable.lookup(id);
                        try {
                            String line = descriptor.readLine();
                            IntValue value;
                            if (line == null) {
                                value = new IntValue(0);
                            } else {
                                value = new IntValue(Integer.parseInt(line));
                            }
                            symbolTable.update(variableName, value);
                        } catch (IOException e) {
                            throw new MyException(e.getMessage());
                        }
                    } else
                        throw new MyException(id + " is not defined in readFile(" + expression + "," + variableName + ")");

                } else
                    throw new MyException(expression + " does not evaluate as a StringValue");
            }else
                throw new MyException(variableName+" is not an int");
        }else{
            throw new MyException(variableName+" is not defined");
        }
        return state;
    }
}
