import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class RDP_Scheduler extends Thread 
{

	Object _lock;
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();

	public RDP_Scheduler(String name, ArrayList<Integer> listEvenement, Object lock)
	{
		super(name);
		_listEvenement = listEvenement;
		_lock = lock;
		
	}
	
	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(true)
		{		
			runScheduler();		
		} 
	} 

	public void runScheduler()
	{
		System.out.println(this.getName() + " demmarrage");
		try 
		{

			synchronized(_lock) 
			{
				System.out.println(this.getName() + " WAIT : j'attends sur le tick ");
				_lock.wait();
			};		

		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}

