package EarthSim.buffer;

public class TBuffer<E> extends Buffer<E> {

    protected boolean consume;

    protected boolean produceIndifinitely;

    public TBuffer(int capacity) {
	super(capacity);

	consume = false;

	produceIndifinitely = true;
    }

    @Override
    public void put(E item) throws InterruptedException {
	lock.lock();

	try {
	    while ((!produceIndifinitely && (consume || queue.size() == capacity))
		    || (produceIndifinitely && queue.size() == capacity)) {
		produceState.await();
	    }

	    // System.out.println("Producing " + item);
	    queue.offer(item);

	    consume = true;

	    consumeState.signal();
	} finally {
	    lock.unlock();
	}
    }

    @Override
    public E get() throws InterruptedException {
	lock.lock();

	try {
	    while (!consume || queue.size() == 0) {
		consumeState.await();
	    }

	    E item = queue.poll();
	    // System.out.println("Consuming " + item);

	    consume = false;

	    produceState.signal();

	    return item;
	} finally {
	    lock.unlock();
	}
    }
}
