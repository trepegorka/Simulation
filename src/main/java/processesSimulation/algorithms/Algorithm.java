package processesSimulation.algorithms;

import processesSimulation.processes.Process;
import processesSimulation.processes.Work;
import org.apache.commons.math3.util.Precision;

import java.util.Map;

public interface Algorithm {
    void run(Algorithm algorithm) throws Exception;

    void displaySimulation(Algorithm algorithm, Map<Process, Integer> processes);

    int getTotalTimeForSimulation();

    int getTime();

    boolean isFastMode();

    boolean isDisplayMode();

    default void setWaitingAndCompleteTimeForProcesses(Process process, Algorithm algorithm) {
        for (Map.Entry<Process, Integer> mapProcess : Work.getWork().getProcesses().entrySet()) {
            if (!mapProcess.getKey().equals(process) && !mapProcess.getKey().isComplete()) {
                if (mapProcess.getKey().isStarted(algorithm)) {
                    mapProcess.getKey().setTotalWaitingTime(mapProcess.getKey().getTotalWaitingTime() + 1);
                }
            }
            if (!mapProcess.getKey().isComplete()) {
                if (mapProcess.getKey().isStarted(algorithm)) {
                    mapProcess.getKey().setCompletedIn(mapProcess.getKey().getCompletedIn() + 1);
                }
            }
        }
    }

    default void makeSteps(Map.Entry<Process, Integer> process, Algorithm algorithm) throws InterruptedException {
        setWaitingAndCompleteTimeForProcesses(process.getKey(), algorithm);
        process.getKey().setLeftTime(process.getKey().getLeftTime() - 1);
        double completed = Precision.round(100 - process.getKey().getLeftTime() / ((double) process.getKey().getBurstTime() / 100), 1);
        String output = "| Waiting for process '" + process.getValue() + "'..." + " Completed: " + completed + "%";
        switch (output.length()) {
            case 44:
                output += "            |";
                break;
            case 45:
                output += "           |";
                break;
            case 46:
                output += "          |";
                break;
            case 47:
                output += "         |";
                break;
            case 48:
                output += "        |";
                break;
        }
        if (isDisplayMode()) {
            System.out.println(output);
        }
        if (!isFastMode()) {
            Thread.sleep(100);
        }
    }
}
