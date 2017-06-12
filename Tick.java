import java.util.concurrent.TimeUnit;

public class Tick extends Thread {
	private int _time_tick;
	Object _lock;
	
	public Tick(int time_tick, Object lock)
	{
		_time_tick = time_tick;
		_lock = lock;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				TimeUnit.SECONDS.sleep(_time_tick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (_lock) {
				_lock.notifyAll();
			}
		}
	}

}
