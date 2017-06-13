import java.util.ArrayList;
import java.util.ListIterator;


public class RDP_Scheduler extends Thread 
{

	Object _lock;
	private Integer _numEvenement;
	
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private ListIterator<Integer> _it = _listEvenement.listIterator();
	
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
		System.out.println(this.getName() + " Wake up de tick");
		try 
		{
			int j = 0;
			while (_listEvenement.size() > j) 
			{
				//System.out.println(_listEvenement.get(j));
				j++;
			}			
			
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

