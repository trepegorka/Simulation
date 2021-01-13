package ProcessesSimulation;

import java.util.Map;

public interface Algorithm {
    void run() throws Exception;

    void displaySimulation(Algorithm algorithm, Map<Process, String> processes);

    int getTotalTimeForSimulation();

    default void setWaitingAndCompleteTimeForProcesses(Process process) {
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
