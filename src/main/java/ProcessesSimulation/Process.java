package ProcessesSimulation;

public class Process {
    private final int burstTime; // Czas wykonywania / Время выполнения процесса
    private final int startTime; // Czas wykonywania / Время выполнения процесса
    private int leftTime;
    private int completedIn = 0;
    private int totalWaitingTime = 0;
    private final boolean canBeInterrupted;

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public int getCompletedIn() {
        return completedIn;
    }

    public void setCompletedIn(int completedIn) {
        this.completedIn = completedIn;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public boolean isComplete() {
        return leftTime == 0;
    }

    public boolean isCanBeInterrupted() {
        return canBeInterrupted;
    }

    public int getStartTime() {
        return startTime;
    }

    public boolean isStarted(Algorithm algorithm){
        return startTime <= algorithm.getTime();
    }

    public Process(int burstTime, int startTime, boolean canBeInterrupted) {
        this.startTime = startTime;
        this.burstTime = burstTime;
        this.leftTime = burstTime;
        this.canBeInterrupted = canBeInterrupted;
    }
}
