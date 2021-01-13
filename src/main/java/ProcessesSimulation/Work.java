package ProcessesSimulation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

// Class Work is sigle for all simulation. Singleton PATTERN
public class Work {
    private static Work instance;

    //Key - Process, Value - name
    private static Map<Process, String> processes;

    private Work(Map<Process, String> processes) {
        Work.processes = processes;
    }

    public Map<Process, String> getProcesses() {
        return processes;
    }

    public static Work getWork(Map<Process, String> processes) {
        if (instance == null) {
            instance = new Work(processes);
        }
        return instance;
    }

    public static Work getWork() {
        return instance;
    }

    public Boolean areCompleted() {
        int mapSize = processes.size();
        for (Map.Entry<Process, String> process : processes.entrySet()) {
            if (process.getKey().isComplete()) {
                mapSize--;
            }
        }
        return mapSize == 0;
    }

    public void start(Algorithm algorithm) throws Exception {
        algorithm.run();
    }


}
