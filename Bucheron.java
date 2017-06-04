import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Bucheron extends Thread
{
	private int state = 0;
	private int maxTime = 10;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private String _benneAAttendre;
	private int _numBenneAAttendre = 1;
	private int _nbrDeBenneDansParc = 0;
	private int i = 0;

	public Bucheron(String name, ArrayList<Benne> listDeBennes, int nbrDeBenneDansParc)
	{
		super(name);
		_listDeBennes = listDeBennes;
		_benneAAttendre = listDeBennes.get(0).getName();
		_nbrDeBenneDansParc = nbrDeBenneDansParc;
	}

	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(true)
		{
			switch (state){
			case 0: coupe();
			break;	
			case 1: ammenerBenne();
			break;	
			case 2: decharge();
			break;
			case 3: retourneForet();
			break;
			default : break;
			}
		} 
	}


		public void coupe()
		{
			System.out.println(this.getName() + " coupe du bois");
			state++;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void ammenerBenne()
		{
			System.out.println(this.getName() + " apporte le bois vers benne");
			state++;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void decharge()
		{
			System.out.println(this.getName() + " decharge le bois");
			state++;
			try {
				//TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
				
				//contôle si la benne à remplir est arrivée
				do
				{
					
					System.out.println(_listDeBennes.get(i).getName()); 
				}
				while(i < _nbrDeBenneDansParc);

				//s'endort jausque la benne soie remplie
				synchronized(this) 
				{
					System.out.println(this.getName() + " WAIT : j'attends sur la benne " + _benneAAttendre);
					this.wait();
				};				
				synchronized(this) 
				{
					System.out.println(this.getName() + " NOTIFY : la benne est desamarrée à la foret");
					this.notify();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void retourneForet()
		{
			System.out.println(this.getName() + " retourne en foret");
			state=0;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}