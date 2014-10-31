package EarthSim.core;

public interface Producer {
    boolean produce();
    void pause();
    void resume();
    void stop();
}
