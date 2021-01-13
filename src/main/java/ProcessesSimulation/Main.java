package ProcessesSimulation;

import ProcessesSimulation.RoundRobin.RoundRobin;
import org.apache.commons.math3.util.Precision;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // Array of processes to complete
        Map<Process, String> processes = new LinkedHashMap<>();

        Process p1 = new Process(24);
        Process p2 = new Process(3);
        Process p3 = new Process(3);

        processes.put(p1, "Process 1");
        processes.put(p2, "Process 2");
        processes.put(p3, "Process 3");

        Algorithm roundRobin = new RoundRobin(4, false);

        Work.getWork(processes).start(roundRobin);
        System.out.println("==========================================================");
        System.out.println("ROUND ROBIN TOTAL TIME: " + roundRobin.getTotalTimeForSimulation());

        double averageWaitingTime = 0.0;
        double averageTurnaroundTime = 0.0;
        for (Map.Entry<Process, String> mapProcess : processes.entrySet()) {
            System.out.print("\nWAITING TIME FOR PROCESS '" + mapProcess.getValue() + "': " + mapProcess.getKey().getTotalWaitingTime());
            System.out.print("\t\tTURNAROUND TIME: " + mapProcess.getKey().getCompletedIn());
            averageWaitingTime += mapProcess.getKey().getTotalWaitingTime();
            averageTurnaroundTime += mapProcess.getKey().getCompletedIn();
        }
        System.out.println("\n\nAVERAGE WATING TIME FOR PROCESSES: " + Precision.round(averageWaitingTime/processes.size(), 1));
        System.out.println("AVERAGE TURNAROUND TIME FOR PROCESSES: " + Precision.round(averageTurnaroundTime/processes.size(), 1));
    }
}
