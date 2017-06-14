import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Ouvrier extends Thread 
{
	//affichage des commentaires
	private boolean debugON = false;

	private int state = 0;
	private int maxTime = 3;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private String _benneAAttendre;
	private int _numBenneAVider = 0;
	private int _nbrDeBenneDansParc = 0;
	private int i = 0;
	private boolean _benneArrivee = false;
	Object _lock;

	public Ouvrier(String name, ArrayList<Benne> listDeBennes, ArrayList<Integer> listEvenement, int nbrDeBenneDansParc, Object lock)
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
			case 0: dechargeBenne();
			break;	
			case 1: ammenerUsine();
			break;	
			case 2: scieBois();
			break;
			case 3: retourneBenne();
			break;
			default : break;
			}
		} 
	} 
	
	public void dechargeBenne()
	{
		if(debugON)
		{
			System.out.println(this.getName() + "     , arrivee vers la benne");
		}
		try {
			//TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			do
			{
				//contï¿½le si la benne ï¿½ vider est arrivï¿½e
				if(_listDeBennes.get(i).getName() == _benneAAttendre && 
						_listDeBennes.get(i).getEtat() == EnumEtatBenne.DESAMARER_USINE)
				{
					if(debugON)
					{
						System.out.println(this.getName() + "     , " + _benneAAttendre + " est arrivée");
					}
					_benneArrivee = true;
					//_benneAAttendre =  _listDeBennes.get((i+1)%3).getName();
					_numBenneAVider = i;
					state++;
				}
				i++;
			}
			while((i < (_nbrDeBenneDansParc)) && (!_benneArrivee));
			i = 0;

			if(_benneArrivee)
			{
				if(debugON)
				{
				//vide la benne
					System.out.println(this.getName() + "     , decharge le bois");
				}
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAVider).setEtat(EnumEtatBenne.VIDE);
				
				//benne suivante ï¿½ vider
				_numBenneAVider = (_numBenneAVider+1)%_nbrDeBenneDansParc;
				_benneAAttendre = _listDeBennes.get(_numBenneAVider).getName();
				
				//avertissement quelle est pleine
				_benneArrivee = false;
				synchronized(_lock) 
				{
					if(debugON)
					{
						System.out.println(this.getName() + "    , NOTIFY : la benne est vide");
					}
					_listEvenement.add(6);
					_lock.notifyAll();
				}
			}	
			else
			{
				//s'endort j'usque la benne soit arrivï¿½e
				synchronized(_lock) 
				{
					if(debugON)
					{
						System.out.println(this.getName() + "     , WAIT : j'attends sur la benne " + _benneAAttendre);
					}
					_lock.wait();
				};		
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ammenerUsine()
	{
		if(debugON)
		{
			System.out.println(this.getName() + "     , ammene a l'usine");
		}
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void scieBois()
	{
		if(debugON)
		{
			System.out.println(this.getName() + "      , scie le bois");
		}
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void retourneBenne()
	{
		if(debugON)
		{
			System.out.println(this.getName() + "      , retourne a la benne");
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
