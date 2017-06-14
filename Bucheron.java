import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Bucheron extends Thread
{
	//affichage des commentaires
	private boolean debugON = false;
	
	private int state = 0;
	private int maxTime = 3;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private String _benneAAttendre;
	private int _numBenneARemplir = 0;
	private int _nbrDeBenneDansParc = 0;
	Object _lock;

	public Bucheron(String name, ArrayList<Benne> listDeBennes, ArrayList<Integer> listEvenement, int nbrDeBenneDansParc, Object lock)
	{
		super(name);
		_listDeBennes = listDeBennes;
		_listEvenement = listEvenement;
		_benneAAttendre = listDeBennes.get(0).getName();
		_nbrDeBenneDansParc = nbrDeBenneDansParc;
		_lock = lock;
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
		if(debugON)
		{
			System.out.println(this.getName() + "    , coupe du bois");
		}
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
		if(debugON)
		{
			System.out.println(this.getName() + "    , apporte le bois vers benne");
		}
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
		if(debugON)
		{
			System.out.println(this.getName() + "    , arrivé vers la benne");
		}
		try {
			//contï¿½le si la benne ï¿½ remplir est arrivï¿½e
			if(_listDeBennes.get(_numBenneARemplir).getEtat() == EnumEtatBenne.DESAMARRER_EN_FORET)
			{
				if(debugON)
				{
					System.out.println(this.getName() + "    , " + _benneAAttendre + " est arrivée");
				
				//rempli la benne
				System.out.println(this.getName() + "    , decharge le bois");
				}
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));

				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneARemplir).setEtat(EnumEtatBenne.REMPLIE);
				if(debugON)
				{
					System.out.println(this.getName() + "    , NOTIFY : la "+ _benneAAttendre + " est pleine");
				}
				//benne suivante ï¿½ remplir
				_numBenneARemplir = (_numBenneARemplir+1)%_nbrDeBenneDansParc;
				_benneAAttendre = _listDeBennes.get(_numBenneARemplir).getName();

				//changement d'ï¿½tat du bucheron
				state++;
				
				synchronized(_lock) 
				{
					_lock.notifyAll();
					_listEvenement.add(2);
				};
			}	
			else
			{
				//s'endort j'usque la benne soit arrivï¿½e
				synchronized(_lock) 
				{
					if(debugON)
					{
						System.out.println(this.getName() + "    , WAIT : j'attends sur la benne " + _benneAAttendre);
					}
					_lock.wait();
				};		
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void retourneForet()
	{
		if(debugON)
		{
			System.out.println(this.getName() + "    , retourne en foret");
		}
		state=0;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDebugOn(boolean value)
	{
		debugON = value;
	}

}