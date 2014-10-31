package EarthSim.buffer;

public class RBuffer<E> extends Buffer<E> {

    protected boolean produce;
    protected boolean produceUntilFull;

    public RBuffer(int capacity) {
	super(capacity);

	produce = false;

	produceUntilFull = false;
    }

    @Override
    public void put(E item) throws InterruptedException {
	lock.lock();

	try {
	    while (!produce || queue.size() == capacity) {
		System.out.println("Can't produce...");

		produceState.await();
	    }

	    //System.out.println("Producing " + item);
	    queue.offer(item);

	    if (!produceUntilFull
		    || (produceUntilFull && queue.size() == capacity)) {
		produce = false;

		consumeState.signal();
	    }
	} finally {
	    lock.unlock();
	}
    }

    @Override
    public E get() throws InterruptedException {
	lock.lock();

	try {
	    while (queue.size() == 0) {
		System.out
			.println("Queue is empty - asking Producers to produce");

		produce = true;
		produceState.signal();

		consumeState.await();
	    }

	    E item = queue.poll();
	    //System.out.println("Consuming " + item);

	    return item;
	} finally {
	    lock.unlock();
	}
    }
}
