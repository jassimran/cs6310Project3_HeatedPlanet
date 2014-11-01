package events;

public interface Listener {

	public void notify(EventType e);

	public void addListener(Listener l);

	public void removeListener(Listener l);
}