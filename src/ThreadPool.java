import java.util.*;

public class ThreadPool {

	private Vector freeThreads = new Vector();
	private Vector inUseThreads = new Vector();
	
	private static int INITIAL_SIZE = 10;
	
	public ThreadPool() {
		fillPool(INITIAL_SIZE);
	}
	
	private void fillPool(int poolSize) {
		for(int i =0;  i < poolSize; i++) {
			PoolableThread pt = new PoolableThread(this);
			pt.start();
			freeThreads.add(pt);
		}
		
		try {
			Thread.sleep(2000);			
		}
		catch(InterruptedException e) {
			
		}
	}
		public synchronized void runTask(Runnable task) {
			if(freeThreads.isEmpty()) {
				throw new RuntimeException("All threads are in use");
			}
			
			PoolableThread t = (PoolableThread) freeThreads.remove(0);
			inUseThreads.add(t);
			t.setTask(task);
		}
		
		synchronized void free(PoolableThread t) {
			inUseThreads.remove(t);
			freeThreads.add(t);
		}
	
}
