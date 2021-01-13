package ProcessesSimulation;

import ProcessesSimulation.FCFS.FCFS;
import ProcessesSimulation.RoundRobin.RoundRobin;
import org.apache.commons.math3.util.Precision;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        // Map of processes to complete and their names
        Map<Process, String> processesForRR = new LinkedHashMap<>();
        Map<Process, String> processesForFCFS = new LinkedHashMap<>();

        //burstTime is a time for process to be completed
        Process p1 = new Process(24, false);
        Process p2 = new Process(3, true);
        Process p3 = new Process(3, true);

        // Add processes here:
//        processes.put(p1, "Process 1");
//        processes.put(p2, "Process 2");
//        processes.put(p3, "Process 3");
        // or generate them:
        processesForRR = generateProcesses(3);

        // make maps equal for future compare
        for (Map.Entry<Process, String> entry : processesForRR.entrySet()) {
            processesForFCFS.put(new Process(entry.getKey().getBurstTime(), entry.getKey().isCanBeInterrupted()), entry.getValue());
        }


        //FastMode: sleep 100ml with FALSE. Change to TRUE for fast display.
        //Time for process mean how much time process will be used in round
        Algorithm roundRobin = new RoundRobin(4, true, false);
        Work.getWork(processesForRR).start(roundRobin);
        roundRobin.displaySimulation(roundRobin, processesForRR);

        System.out.println("\n\n");
        Algorithm FCFS = new FCFS(true, false);
        Work.getWork(processesForFCFS).start(FCFS);
        FCFS.displaySimulation(FCFS, processesForFCFS);
    }

    public static Map<Process, String> generateProcesses(int numberOfProcesses) {
        Map<Process, String> processes = new LinkedHashMap<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            Random rand = new Random();
            //30 max burstTime.
            int burstTime = rand.nextInt((30 - 1) + 1) + 1;
            int trueFalse = rand.nextInt((2 - 1) + 1) + 1;
            boolean isInterrupted;
            isInterrupted = trueFalse == 1;
            String processName = "Process " + (i + 1);
            processes.put(new Process(burstTime, isInterrupted), processName);
        }
        return processes;
    }
}
