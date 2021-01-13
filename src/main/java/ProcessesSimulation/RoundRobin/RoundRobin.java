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

    public int getTotalTimeForSimulation() {
        return totalTimeForSimulation;
    }

    public RoundRobin(int timeForProcess, boolean fastMode) {
        this.timeForProcess = timeForProcess;
        this.fastMode = fastMode;
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

    private void setWaitingAndCompleteTimeForProcesses(Process process) {
        for (Map.Entry<Process, String> mapProcess : Work.getWork().getProcesses().entrySet()) {
            if (!mapProcess.getKey().equals(process) && !mapProcess.getKey().isComplete()) {
                mapProcess.getKey().setTotalWaitingTime(mapProcess.getKey().getTotalWaitingTime() + 1);
            }
            if (!mapProcess.getKey().isComplete()) {
                mapProcess.getKey().setCompletedIn(mapProcess.getKey().getCompletedIn() + 1);
            }
        }
    }
}
