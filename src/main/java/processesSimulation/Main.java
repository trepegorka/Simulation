package processesSimulation;

import processesSimulation.algorithms.Algorithm;
import processesSimulation.algorithms.FCFS.FCFS;
import processesSimulation.algorithms.RoundRobin.RoundRobin;
import processesSimulation.processes.Process;
import processesSimulation.processes.Processes;
import processesSimulation.processes.Work;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Processes simulations
 *
 * @author Yahor Patapka
 **/
public class Main {
    public static void main(String[] args) throws Exception {
//        Map of processes to complete and their names
        Map<Process, Integer> processes;
        Map<Process, Integer> processesForRR = new LinkedHashMap<>();
        Map<Process, Integer> processesForFCFS = new LinkedHashMap<>();


//        Create processes and add by hand
//        burstTime is a time for process to be completed
//        Process p1 = new Process(24,0, false);
//        Process p2 = new Process(3, 2,true);
//        Process p3 = new Process(3, 3, true);
//        processes.put(p1, "Process1");
//        processes.put(p2, "Process2");
//        processes.put(p3, "Process3");

//        Or generate them: (writing into file while generating)
        processes = Processes.generateProcesses(10);
        processes = Processes.getSortedProcessesByStartTime(processes);

//        Or read from file
//        processes = Processes.readProcessesFromFile();
//        processes = Processes.getSortedProcessesByStartTime(processes);


//        make maps equal for future compare
        for (Map.Entry<Process, Integer> entry : processes.entrySet()) {
            processesForFCFS.put(new Process(entry.getKey().getBurstTime(), entry.getKey().getStartTime(), entry.getKey().isCanBeInterrupted()), entry.getValue());
            processesForRR.put(new Process(entry.getKey().getBurstTime(), entry.getKey().getStartTime(), entry.getKey().isCanBeInterrupted()), entry.getValue());
        }

//        FastMode: sleep 100 ml with FALSE.Change to TRUE for fast display.
//        Time for process mean how much time process will be used in round
        Algorithm roundRobin = new RoundRobin(3, true, true);
        Work.getWork(processesForRR).start(roundRobin);
        roundRobin.displaySimulation(roundRobin, processesForRR);

        System.out.println("\n\n");

        Algorithm FCFS = new FCFS(true, true);
        Work.getWork(processesForFCFS).start(FCFS);
        FCFS.displaySimulation(FCFS, processesForFCFS);
    }
}
