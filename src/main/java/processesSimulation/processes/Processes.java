package processesSimulation.processes;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public interface Processes {
    static Map<Process, Integer> generateProcesses(int numberOfProcesses) throws IOException {
        Map<Process, Integer> processes = new LinkedHashMap<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            Random rand = new Random();
            //10 max burstTime
            //100 max startTime
            int burstTime = ThreadLocalRandom.current().nextInt(1, 11);
            int startTime = ThreadLocalRandom.current().nextInt(0, 101);
            int trueFalse = ThreadLocalRandom.current().nextInt(1, 3);
            boolean isInterrupted;
            isInterrupted = trueFalse == 1;
            int processId = (i + 1);
            processes.put(new Process(burstTime, startTime, isInterrupted), processId);
        }
        writeProcessesIntoFile(processes);
        return processes;
    }

    static void writeProcessesIntoFile(Map<Process, Integer> processes) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("src/main/java/processesSimulation/processes/processes.txt", false));
        } catch (FileNotFoundException e) {
            System.out.println("\n!! Problem with write processes into file: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null;
        writer.write("process id, burst time, start time, can be interrupted\n");
        for (Map.Entry<Process, Integer> process : processes.entrySet()) {
            writer.write(process.getValue() + "," + process.getKey().getBurstTime() + "," + process.getKey().getStartTime() + "," + process.getKey().isCanBeInterrupted() + "\n");
        }
        writer.flush();
        writer.close();
    }

    static Map<Process, Integer> readProcessesFromFile() throws IOException {
        Map<Process, Integer> processes = new LinkedHashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/processesSimulation/processes/processes.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("\n!! Problem with write processes into file: " + e.getMessage());
        }
        assert reader != null;
        reader.readLine();
        String line = reader.readLine();
        int processId;
        int burstTime;
        int startTime;
        boolean isCanBeInterrupted = true;
        while (line != null) {
            processId = Integer.parseInt(line.substring(0, line.indexOf(',')));
            burstTime = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf(',', line.indexOf(',') + 1)));
            startTime = Integer.parseInt(line.substring(line.indexOf(',', line.indexOf(',') + 1)+1, line.indexOf(",", line.indexOf(",", line.indexOf(",") + 1) + 1)));
            if (line.contains("true")) {
                isCanBeInterrupted = true;
            }
            if (line.contains("false")) {
                isCanBeInterrupted = false;
            }
            processes.put(new Process(burstTime, startTime, isCanBeInterrupted), processId);
            line = reader.readLine();
        }
        reader.close();
        return processes;
    }

    static Map<Process, Integer> getSortedProcessesByStartTime(Map<Process, Integer> processes){
        //Sort by using List
        List<Map.Entry<Process, Integer>> listOfProcesses = new ArrayList<>(processes.entrySet());
        int n = listOfProcesses.size();
        Map.Entry<Process, Integer> temp;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(listOfProcesses.get(j - 1).getKey().getStartTime() > listOfProcesses.get(j).getKey().getStartTime()){
                    //swap elements
                    temp = listOfProcesses.get(j - 1);
                    listOfProcesses.set(j - 1, listOfProcesses.get(j));
                    listOfProcesses.set(j, temp);
                }

            }
        }
        Map<Process, Integer> sortedProcesses = new LinkedHashMap<>();
        for(Map.Entry<Process, Integer> process : listOfProcesses){
            sortedProcesses.put(process.getKey(), process.getValue());
        }
        return sortedProcesses;
    }
}
