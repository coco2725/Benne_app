import java.util.ArrayList;
import java.util.ListIterator;


public class RDP_Scheduler extends Thread 
{

	Object _lock;
	private Integer _numEvenement;

	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private ListIterator<Integer> _it = _listEvenement.listIterator();

	private int tabPreIncidence[][] =
		{
		//			 T1, T2, T3, T4, T5, T6, T7, T8
		/*P1*/		{1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 }, 
		/*P2*/		{0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 }, 
		/*P3*/		{0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 }, 
		/*P4*/		{0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 }, 
		/*P5*/		{0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 }, 
		/*P6*/		{0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 }, 
		/*P7*/		{0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 }, 
		/*P8*/		{0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 }  
		};
	
	private int tabPostIncidence[][] =
		{
		//			 T1, T2, T3, T4, T5, T6, T7, T8
		/*P1*/		{0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 }, 
		/*P2*/		{1 , 0 , 0 , 0 , 0 , 0 , 1 , 0 }, 
		/*P3*/		{0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 }, 
		/*P4*/		{0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 }, 
		/*P5*/		{0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 }, 
		/*P6*/		{0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 }, 
		/*P7*/		{0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 }, 
		/*P8*/		{0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 }  
		};
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
		//System.out.println(this.getName() + "  , Wake up de tick");
		try 
		{
			int j = 0;
			while (_listEvenement.size() > j) 
			{
				//System.out.println(_listEvenement.get(j));

				/*affichage des evenements
				1 = Transporteur notiﬁe que Benne est désamarrée à la forte	
				2 = Un bucheron notifie que la benne est remplie
				3 = Transporteur amarre en forêt
				4 = Benne arrivée à l'usine
				5 = Transporteur notifie que la benne est désamarée à l'usine
				6 = Un ouvrier indique que la benne est vide
				7 = Transporteur amarre à l'usine
				8 = Benne arrivée en foret
				 */
				_numEvenement = _listEvenement.get(j);
				switch (_numEvenement)
				{
				case 1: 
				{
					System.out.println(this.getName() + "  , Transporteur notifie que Benne est désamarrée à la forêt");
					break;
				}	
				case 2: 	
				{
					System.out.println(this.getName() + "  , Un bucheron notifie que la benne est remplie");
					break;
				}	
				case 3: 
				{
					System.out.println(this.getName() + "  , Transporteur amarre en forêt");
					break;
				}	
				case 4: 
				{
					System.out.println(this.getName() + "  , Benne arrivée à l'usine");
					break;
				}	
				case 5: 
				{
					System.out.println(this.getName() + "  , Transporteur notifie que Benne est désamarrée à l'usine");
					break;
				}	

				case 6: 
				{
					System.out.println(this.getName() + "  , Un ouvrier indique que la benne est videe");
					break;
				}	

				case 7: 
				{
					System.out.println(this.getName() + "  , Transporteur amarre à l'usine");
					break;
				}	
				case 8: 
				{
					System.out.println(this.getName() + "  , Benne arrivée en foret");
					break;
				}	
				default : break;
				}
				_listEvenement.remove(j);
				j++;
			}			

			synchronized(_lock) 
			{
				//System.out.println(this.getName() + "  , WAIT : j'attends sur le tick ");
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

