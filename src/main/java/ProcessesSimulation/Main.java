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
        Map<Process, String> processes = new LinkedHashMap<>();

        //burstTime is a time for process to be completed
        Process p1 = new Process(24, false);
        Process p2 = new Process(3, true);
        Process p3 = new Process(3, true);

        // Add processes here:
//        processes.put(p1, "Process 1");
//        processes.put(p2, "Process 2");
//        processes.put(p3, "Process 3");
        // or generate them:
        processes = generateProcesses(10);

        //FastMode: sleep 100ml with FALSE. Change to TRUE for fast display.
        //Time for process mean how much time process will be used in round
        Algorithm roundRobin = new RoundRobin(4, true);
        Work.getWork(processes).start(roundRobin);
        roundRobin.displaySimulation(roundRobin, processes);

        System.out.println("\n\n\n********************************************************\n\n\n");

        processes = generateProcesses(10);
        Algorithm FCFS = new FCFS( true);
        Work.getWork(processes).start(FCFS);
        FCFS.displaySimulation(FCFS, processes);
    }

    public static Map<Process, String> generateProcesses(int numberOfProcesses) {
        Map<Process, String> processes = new LinkedHashMap<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            Random rand = new Random();
            int burstTime = rand.nextInt((100 - 1) + 1) + 1;
            int trueFalse = rand.nextInt((2 - 1) + 1) + 1;
            boolean isInterrupted;
            isInterrupted = trueFalse == 1;
            String processName = "Process " + (i + 1);
            processes.put(new Process(burstTime, isInterrupted), processName);
        }
        return processes;
    }
}
