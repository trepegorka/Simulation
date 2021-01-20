package ProcessesSimulation.RoundRobin;

import ProcessesSimulation.Algorithm;
import ProcessesSimulation.Process;
import ProcessesSimulation.Work;
import org.apache.commons.math3.util.Precision;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoundRobin implements Algorithm {
    private static int time = 0;
    // max time for process in round
    private final int timeForProcess; //quantum
    private int totalTimeForSimulation = 0;
    private final boolean fastMode;
    private final boolean displayMode;

    public RoundRobin(int timeForProcess, boolean fastMode, boolean displayMode) {
        this.timeForProcess = timeForProcess;
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
    public boolean isFastMode() {
        return fastMode;
    }

    @Override
    public boolean isDisplayMode() {
        return fastMode;
    }

    //RR algorithm
    @Override
    public void run(Algorithm algorithm) throws Exception {
        System.out.println("\n================= ROUND ROBIN SIMULATION =================");
        Work.getWork().updateReadyProcesses(algorithm);
        Work.getWork().updateNewProcesses(algorithm);
        while (!Work.getWork().areCompleted()) {
            if (Work.getWork().getReadyProcesses().size() == 0 && Work.getWork().getNewProcesses().size() == 0) {
                if (displayMode) {
                    System.out.println("| No one process in turn, wait...                       |");
                }
                time++;
                totalTimeForSimulation++;
                Work.getWork().updateNewProcesses(algorithm);
                continue;
            }
            loadNewProcesses(algorithm);
            for (Map.Entry<Process, Integer> process : Work.getWork().getReadyProcesses().entrySet()) {
                if (Work.getWork().getReadyProcesses().containsValue(process.getValue())) {
                    makeRound(process, algorithm);
                }
            }
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
        Work.getWork().updateReadyProcesses(algorithm);
    }

    private void makeRound(Map.Entry<Process, Integer> process, Algorithm algorithm) throws InterruptedException {
        if (!process.getKey().isCanBeInterrupted()) {
            while (!process.getKey().isComplete()) {
                makeSteps(process, algorithm);
                time++;
                totalTimeForSimulation++;
                Work.getWork().updateNewProcesses(algorithm);
            }
        }
        if (process.getKey().isCanBeInterrupted()) {
            for (int i = 0; i < timeForProcess; i++) {
                if (process.getKey().isComplete()) {
                    break;
                }
                makeSteps(process, algorithm);
                time++;
                totalTimeForSimulation++;
                Work.getWork().updateNewProcesses(algorithm);
            }
        }
    }


    //Round Robin console display
    @Override
    public void displaySimulation(Algorithm roundRobin, Map<Process, Integer> processes) {
        System.out.println("==========================================================");
        System.out.println("ROUND ROBIN TOTAL TIME: " + roundRobin.getTotalTimeForSimulation());
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
            System.out.print("\tTURNAROUND TIME: " + mapProcess.getKey().getCompletedIn());
            averageWaitingTime += mapProcess.getKey().getTotalWaitingTime();
            averageTurnaroundTime += mapProcess.getKey().getCompletedIn();
        }
        System.out.println("\n\nAVERAGE WATING TIME FOR PROCESSES: " + Precision.round(averageWaitingTime / processes.size(), 1));
        System.out.println("AVERAGE TURNAROUND TIME FOR PROCESSES: " + Precision.round(averageTurnaroundTime / processes.size(), 1));
    }
}

// сделать 1 (4 из 5) / (5 из 5) -> добавили 2 и 3 в new.
// 1 кинуть в лист ready / не кидать
// сделать 2(4 из 5) и убрать из new
// 2 кинуть в лист ready
// сделать 3(4 из 6) и убрать из new -> добавили 4 в new
// 3 кинуть в лист ready
// 4 сделать (2 из 2)
// 1 -> 2 -> 3

