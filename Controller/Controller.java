package Controller;

import Domain.ProgramState.ProgramState;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface {
    private RepositoryInterface repository;
    private boolean display;
    private ExecutorService executor;

    public Controller(RepositoryInterface repositoryFromUser,boolean displayFromUSer){
        repository=repositoryFromUser;
        display=displayFromUSer;
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programListFromUser){
        return programListFromUser.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> conservativeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream().filter(e->symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symTableValues){
         return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toList());

    }
    private void oneStepForAllPrograms(List<ProgramState> programList) {
        programList.forEach(program-> {
            repository.logProgramStateExecution(program);
        });
        //prepare the list of callables fot the concurrent one step execution of the program states
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState p) -> {
                    try {
                        return (Callable<ProgramState>)(p.oneStepExecution());
                    } catch (IOException e) {
                        throw new MyException(e.getMessage());
                    }
                }).collect(Collectors.toList());
        //starting the execution
        try{
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            if(future==null)
                                return null;
                            else
                                return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new MyException(e.getMessage());
                        }
                    }).filter(p -> p!=null).collect(Collectors.toList());
            programList.addAll(newProgramList);
            //collecting the garbage for each program state
            programList.forEach(program -> {
                List<Integer> addresses=getAddressesFromSymbolTable(program.getSymbolTable().getContent().values());
                addresses.addAll(program.getHeap().getInAddresses());
                HeapInterface heap = program.getHeap();
                program.getHeap().setContent(conservativeGarbageCollector(
                        addresses, program.getHeap().getContent()));
            });
            repository.setProgramsList(programList);
        }catch(InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }
    public void allStepExecution() throws IOException {
        executor= Executors.newFixedThreadPool(2);
        List<ProgramState> programList=removeCompletedPrograms(repository.getProgramsList());
        while(programList.size()>0){
            oneStepForAllPrograms(programList);
            programList=removeCompletedPrograms(repository.getProgramsList());

        }
        executor.shutdownNow();
            //HERE the repository still contains at least one Completed Prg
            // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
            //setPrgList of repository in order to change the repository
            // update the repository state
        repository.setProgramsList(programList);
    }
    public void displayCurrentState(){
            System.out.println(repository.getCurrentProgramState());
    }
}
