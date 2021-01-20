package ProcessesSimulation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Single class object PATTERN.
 **/
public class Work {
    private static Work instance;

    //Key - Process, Value - id
    private static Map<Process, Integer> processes;
    private static Map<Process, Integer> readyProcesses = new LinkedHashMap<>();
    private static Map<Process, Integer> newProcesses = new LinkedHashMap<>();

    private Work(Map<Process, Integer> processes) {
        Work.processes = processes;
    }

    public Map<Process, Integer> getProcesses() {
        return processes;
    }

    public static Work getWork(Map<Process, Integer> processes) {
        if (instance == null) {
            instance = new Work(processes);
        } else {
            Work.processes = processes;
        }
        return instance;
    }

    public static Work getWork() {
        return instance;
    }

    public Map<Process, Integer> getNewProcesses() {
        return newProcesses;
    }

    public Boolean areCompleted() {
        int mapSize = processes.size();
        for (Map.Entry<Process, Integer> process : processes.entrySet()) {
            if (process.getKey().isComplete()) {
                mapSize--;
            }
        }
        return mapSize == 0;
    }

    public Map<Process, Integer> getReadyProcesses() {
        return readyProcesses;
    }

    public void updateReadyProcesses(Algorithm algorithm) {
        Map<Process, Integer> readyProcesses = new LinkedHashMap<>();
        for (Map.Entry<Process, Integer> process : getProcesses().entrySet()) {
            if (!process.getKey().isComplete()) {
                if (process.getKey().getStartTime() <= algorithm.getTime()) {
                    if (process.getKey().getBurstTime() > process.getKey().getLeftTime()) {
                        readyProcesses.put(process.getKey(), process.getValue());
                    }
                }
            }
        }
        Work.readyProcesses = readyProcesses;
    }

    public void updateNewProcesses(Algorithm algorithm) {
        Map<Process, Integer> newProcesses = new LinkedHashMap<>();
        for (Map.Entry<Process, Integer> process : getProcesses().entrySet()) {
            if (!process.getKey().isComplete()) {
                if (process.getKey().getStartTime() <= algorithm.getTime()) {
                    if (process.getKey().getBurstTime() == process.getKey().getLeftTime()) {
                        newProcesses.put(process.getKey(), process.getValue());
                    }
                }
            }
        }
        Work.newProcesses = newProcesses;
    }

    public void start(Algorithm algorithm) throws Exception {
        algorithm.run(algorithm);
    }
}
