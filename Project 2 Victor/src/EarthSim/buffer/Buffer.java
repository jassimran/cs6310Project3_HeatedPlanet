package EarthSim.buffer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer<E> {
    protected volatile Queue<E> queue;
    protected final int capacity;
    protected final Lock lock;
    protected final Condition produceState;
    protected final Condition consumeState;
    
    protected final int awaitTimeout;

    public Buffer (int capacity) {
	this(capacity, Integer.MAX_VALUE);
    }
    
    public Buffer(int capacity, int awaitTimeout) {
	this.capacity = capacity;
	this.awaitTimeout = awaitTimeout;

	lock = new ReentrantLock();

	produceState = lock.newCondition();
	consumeState = lock.newCondition();

	queue = new LinkedList<E>();
    }

    public void put(E item) throws InterruptedException {
	lock.lock();

	try {
	    while (queue.size() == capacity) {
		produceState.await();
		//produceState.await(awaitTimeout, TimeUnit.MILLISECONDS);
	    }

	    // System.out.println("Producing " + item);
	    queue.offer(item);

	    consumeState.signal();
	} finally {
	    lock.unlock();
	}
    }

    public E get() throws InterruptedException {
	lock.lock();

	try {
	    while (queue.size() == 0) {
		consumeState.await();
		//consumeState.await(awaitTimeout, TimeUnit.MILLISECONDS);
	    }

	    E item = queue.poll();
	    // System.out.println("Consuming " + item);

	    produceState.signal();

	    return item;
	} finally {
	    lock.unlock();
	}
    }
}
