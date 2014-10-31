package EarthSim.core;

public interface Consumer {
    boolean consume();
    void pause();
    void resume();
    void stop();
}
