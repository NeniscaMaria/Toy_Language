package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Exceptions.MyException;
import Interfaces.ExpressionInterface;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;

public class ForStatement implements StatementInterface {
    private String variableName;
    private ExpressionInterface expression1;
    private ExpressionInterface expression2;
    private ExpressionInterface expression3;
    private StatementInterface statement;
    public ForStatement(String variableFromUser,ExpressionInterface expr1fromUser,
                        ExpressionInterface expr2FromUser, ExpressionInterface expr3FromUser,
                        StatementInterface statementFromUSer){
        variableName=variableFromUser;
        expression1=expr1fromUser;
        expression2=expr2FromUser;
        expression3=expr3FromUser;
        statement=statementFromUSer;
    }
    public ProgramState execute(ProgramState state){
        CompoundStatement statementToBePushed = new CompoundStatement(new VariableDeclarationStatement(variableName,new IntType()),
                new CompoundStatement(new AssignmentStatement(variableName,expression1),
                        new While(expression2, new CompoundStatement(statement,new AssignmentStatement(variableName,expression3)))));
        state.getStack().push(statementToBePushed);
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        //we don't have to check the variable because it will be declared as int when the for is executed
        typeEnvironment.update("v",new IntType());//need to add this to the type environment because it i part of the scope of for
        Type typeExpression1=expression1.typecheck(typeEnvironment);
        Type typeExpression2=expression2.typecheck(typeEnvironment);
        Type typeExpression3=expression3.typecheck(typeEnvironment);
        if(typeExpression1.equals(new IntType())) {
            if(typeExpression2.equals(new BoolType())) {
                if(typeExpression3.equals(new IntType()))
                    return typeEnvironment;
                else
                    throw new MyException(expression3+": is not of IntType.");
            }
            else
                throw new MyException(expression2+": is not of IntType.");
        }
        else
            throw new MyException(expression1+": is not of IntType.");
    }
    @Override
    public String toString(){
        return "for( "+variableName+" = "+expression1+";"+variableName+" < "+expression2+";"+variableName+" = "+expression3+")"+System.lineSeparator()+statement;
    }
    public String getText(){
        return System.lineSeparator()+"for( "+variableName+" = "+expression1+";"+expression2+";"+expression3+")"+System.lineSeparator()+"\t"+statement;
    }
}
