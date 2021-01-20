package ProcessesSimulation.FCFS;

import ProcessesSimulation.Algorithm;
import ProcessesSimulation.Process;
import ProcessesSimulation.Work;
import org.apache.commons.math3.util.Precision;

import java.util.Map;

public class FCFS implements Algorithm {
    private static int time = 0;
    private int totalTimeForSimulation = 0;

    @Override
    public boolean isFastMode() {
        return fastMode;
    }

    @Override
    public boolean isDisplayMode() {
        return displayMode;
    }

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
    public int getTime() {
        return time;
    }

    @Override
    public void run(Algorithm algorithm) throws InterruptedException {
        System.out.println("\n================= FCFS SIMULATION ======================");
        Work.getWork().updateNewProcesses(algorithm);
        while (!Work.getWork().areCompleted()) {
            if (Work.getWork().getNewProcesses().size() == 0) {
                if (displayMode) {
                    System.out.println("| No one process in turn, wait...                       |");
                }
                time++;
                totalTimeForSimulation++;
                Work.getWork().updateNewProcesses(algorithm);
                continue;
            }
            loadNewProcesses(algorithm);
        }
    }

    private void loadNewProcesses(Algorithm algorithm) throws InterruptedException {
        if (Work.getWork().getNewProcesses().size() > 0) {
            for (Map.Entry<Process, Integer> process : Work.getWork().getNewProcesses().entrySet()) {
                if (Work.getWork().getNewProcesses().containsValue(process.getValue())) {
                    makeRound(process, algorithm);
                }
            }
        }
        Work.getWork().updateNewProcesses(algorithm);
        if (Work.getWork().getNewProcesses().size() > 0) {
            loadNewProcesses(algorithm);
        }
    }

    private void makeRound(Map.Entry<Process, Integer> process, Algorithm algorithm) throws InterruptedException {
        while (!process.getKey().isComplete()) {
            makeSteps(process, algorithm);
            time++;
            totalTimeForSimulation++;
            Work.getWork().updateNewProcesses(algorithm);
        }
    }

    @Override
    public void displaySimulation(Algorithm FCFS, Map<Process, Integer> processes) {
        System.out.println("========================================================");
        System.out.println("FCFS TOTAL TIME: " + FCFS.getTotalTimeForSimulation());
        double averageWaitingTime = 0.0;
        double averageTurnaroundTime = 0.0;
        for (Map.Entry<Process, Integer> mapProcess : processes.entrySet()) {
            String waiting = "WAITING TIME FOR PROCESS '" + mapProcess.getValue() + "': " + mapProcess.getKey().getTotalWaitingTime();
            switch (waiting.length()) {
                case 31:
                    waiting += "    |";
                    break;
                case 32:
                    waiting += "   |";
                    break;
                case 33:
                    waiting += "  |";
                    break;
                case 34:
                    waiting += " |";
                    break;
                default:
                    waiting += "|";
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

