package Controller;

import Domain.ProgramState.ProgramState;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface {
    private RepositoryInterface repository;
    private boolean display;
    public Controller(RepositoryInterface repoFromUser){
        repository=repoFromUser;
        display=false;
    }
    public Controller(RepositoryInterface repositoryFromUser,boolean displayFromUSer){
        repository=repositoryFromUser;
        display=displayFromUSer;
    }
    public void oneStepExecution(ProgramState state) throws MyException, IOException {
        MyStackInterface<StatementInterface> stack = state.getStack();
        if(stack.isEmpty())
            throw new MyException("Execution stack is empty!");
        StatementInterface currentStatement=stack.pop();
        currentStatement.execute(state);
        repository.logProgramStateExecution();
    }
    private Map<Integer, Value> garbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap){
        final Map<Integer, Value> collect = heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return collect;
    }
    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symTableValues){
         return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toList());

    }

    public void allStepExecution() throws IOException {
        ProgramState program = repository.getCurrentProgramState();
        repository.logProgramStateExecution();
        if(display)
            displayCurrentState();
        while(!program.getStack().isEmpty()){
            oneStepExecution(program);
            List<Integer> addresses=getAddressesFromSymbolTable(program.getSymbolTable().getContent().values());
            addresses.addAll(program.getHeap().getInAddresses());
            repository.logProgramStateExecution();
            HeapInterface heap = program.getHeap();
            heap.setContent(garbageCollector(
                    addresses,
                    heap.getContent()));

            repository.logProgramStateExecution();
            if(display)
                displayCurrentState();
        }
    }
    public void displayCurrentState(){
            System.out.println(repository.getCurrentProgramState());
    }
}
