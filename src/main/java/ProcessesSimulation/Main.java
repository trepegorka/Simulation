package ProcessesSimulation;

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
        processes = generateProcesses(100);

        //FastMode: sleep 100ml with FALSE. Change to TRUE for fast display.
        //Time for process mean how much time process will be used in round
        Algorithm roundRobin = new RoundRobin(4, true);
        Work.getWork(processes).start(roundRobin);
        displayRoundRobinSimulation(roundRobin, processes);
    }

    //Round Robin console display
    private static void displayRoundRobinSimulation(Algorithm roundRobin, Map<Process, String> processes){
        System.out.println("==========================================================");
        System.out.println("ROUND ROBIN TOTAL TIME: " + roundRobin.getTotalTimeForSimulation());
        double averageWaitingTime = 0.0;
        double averageTurnaroundTime = 0.0;
        for (Map.Entry<Process, String> mapProcess : processes.entrySet()) {
            String waiting = "WAITING TIME FOR PROCESS '" + mapProcess.getValue() + "': " + mapProcess.getKey().getTotalWaitingTime();
            switch (waiting.length()){
                case 38:
                    waiting +="    ";
                    break;
                case 39:
                    waiting +="   ";
                    break;
                case 40:
                    waiting +="  ";
                    break;
                case 41:
                    waiting +=" ";
                    break;
                default:
                    waiting +="";
                    break;
            }
            System.out.print("\n"+waiting);
            System.out.print("\t|\tTURNAROUND TIME: " + mapProcess.getKey().getCompletedIn());
            averageWaitingTime += mapProcess.getKey().getTotalWaitingTime();
            averageTurnaroundTime += mapProcess.getKey().getCompletedIn();
        }
        System.out.println("\n\nAVERAGE WATING TIME FOR PROCESSES: " + Precision.round(averageWaitingTime/processes.size(), 1));
        System.out.println("AVERAGE TURNAROUND TIME FOR PROCESSES: " + Precision.round(averageTurnaroundTime/processes.size(), 1));
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
