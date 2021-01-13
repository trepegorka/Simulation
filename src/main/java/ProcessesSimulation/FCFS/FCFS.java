package ProcessesSimulation.FCFS;

import ProcessesSimulation.Algorithm;
import ProcessesSimulation.Process;
import ProcessesSimulation.Work;
import org.apache.commons.math3.util.Precision;

import java.util.Map;

public class FCFS implements Algorithm {
    private int totalTimeForSimulation = 0;
    private final boolean fastMode;
    private final boolean displayMode;

    public FCFS(boolean fastMode, boolean displayMode) {
        this.fastMode = fastMode;
        this.displayMode = displayMode;
    }

    @Override
    public int getTotalTimeForSimulation() {
        return totalTimeForSimulation;
    }

    @Override
    public void run() throws InterruptedException {
        System.out.println("\n================= FCFS SIMULATION ======================");
        while (!Work.getWork().areCompleted()) {
            for (Map.Entry<Process, String> process : Work.getWork().getProcesses().entrySet()) {
                if (process.getKey().isComplete()) {
                    continue;
                }
                for (int i = 0; i < process.getKey().getBurstTime(); i++) {
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
                        if (displayMode){
                            System.out.println(output);
                        }
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

    @Override
    public void displaySimulation(Algorithm FCFS, Map<Process, String> processes) {
        System.out.println("========================================================");
        System.out.println("FCFS TOTAL TIME: " + FCFS.getTotalTimeForSimulation());
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

