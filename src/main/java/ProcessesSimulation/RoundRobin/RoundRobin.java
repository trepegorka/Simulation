package ProcessesSimulation.RoundRobin;

import ProcessesSimulation.Algorithm;
import ProcessesSimulation.Process;
import ProcessesSimulation.Work;
import org.apache.commons.math3.util.Precision;

import java.util.Map;

public class RoundRobin implements Algorithm {
    // max time for process in round
    private final int timeForProcess;
    private int totalTimeForSimulation = 0;
    private final boolean fastMode;


    public RoundRobin(int timeForProcess, boolean fastMode) {
        this.timeForProcess = timeForProcess;
        this.fastMode = fastMode;
    }

    @Override
    public int getTotalTimeForSimulation() {
        return totalTimeForSimulation;
    }

    //RR algorithm
    @Override
    public void run() throws Exception {
        System.out.println("\n================= ROUND ROBIN SIMULATION =================");
        while (!Work.getWork().areCompleted()) {
            for (Map.Entry<Process, String> process : Work.getWork().getProcesses().entrySet()) {
                if (process.getKey().isComplete()) {
                    continue;
                }
                for (int i = 0; i < timeForProcess; i++) {
                    if (!process.getKey().isComplete()) {
                        if (!process.getKey().isCanBeInterrupted()) {
                            i--;
                        }
                        setWaitingAndCompleteTimeForProcesses(process.getKey());
                        process.getKey().setLeftTime(process.getKey().getLeftTime() - 1);
                        double completed = Precision.round(100 - process.getKey().getLeftTime() / ((double) process.getKey().getBurstTime() / 100), 1);
                        String output = "| Waiting for process '" + process.getValue() + "'..." + " Completed: " + completed + "%";
                        switch (output.length()) {
                            case 52:
                                output += "    |";
                                break;
                            case 53:
                                output += "   |";
                                break;
                            case 54:
                                output += "  |";
                                break;
                            case 55:
                                output += " |";
                                break;
                            case 56:
                                output += "|";
                                break;
                        }
                        System.out.println(output);
                        totalTimeForSimulation++;
                        if (!fastMode) {
                            Thread.sleep(100);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    //Round Robin console display
    @Override
    public void displaySimulation(Algorithm roundRobin, Map<Process, String> processes) {
        System.out.println("==========================================================");
        System.out.println("ROUND ROBIN TOTAL TIME: " + roundRobin.getTotalTimeForSimulation());
        double averageWaitingTime = 0.0;
        double averageTurnaroundTime = 0.0;
        for (Map.Entry<Process, String> mapProcess : processes.entrySet()) {
            String waiting = "WAITING TIME FOR PROCESS '" + mapProcess.getValue() + "': " + mapProcess.getKey().getTotalWaitingTime();
            switch (waiting.length()) {
                case 38:
                    waiting += "    ";
                    break;
                case 39:
                    waiting += "   ";
                    break;
                case 40:
                    waiting += "  ";
                    break;
                case 41:
                    waiting += " ";
                    break;
                default:
                    waiting += "";
                    break;
            }
            System.out.print("\n" + waiting);
            System.out.print("\t|\tTURNAROUND TIME: " + mapProcess.getKey().getCompletedIn());
            averageWaitingTime += mapProcess.getKey().getTotalWaitingTime();
            averageTurnaroundTime += mapProcess.getKey().getCompletedIn();
        }
        System.out.println("\n\nAVERAGE WATING TIME FOR PROCESSES: " + Precision.round(averageWaitingTime / processes.size(), 1));
        System.out.println("AVERAGE TURNAROUND TIME FOR PROCESSES: " + Precision.round(averageTurnaroundTime / processes.size(), 1));
    }
}
