import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Bucheron extends Thread
{
	private int state = 0;
	private int maxTime = 2;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private String _benneAAttendre;
	private int _numBenneAremplir = 0;
	private int _nbrDeBenneDansParc = 0;
	private int i = 0;
	private boolean _benneArrivee = false;

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

	public synchronized void decharge()
	{
		System.out.println(this.getName() + " arrivé vers la benne");
		try {
			//TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			do
			{
				//contôle si la benne à remplir est arrivée
				if(_listDeBennes.get(i).getName() == _benneAAttendre && 
						_listDeBennes.get(i).getEtat() == EnumEtatBenne.DESAMARRER_EN_FORET)
				{
					System.out.println(this.getName() + ", " + _benneAAttendre + " est arrivée");
					_benneArrivee = true;
					//_benneAAttendre =  _listDeBennes.get((i+1)%3).getName();
					_numBenneAremplir = i;
					state++;
				}
				i++;
			}
			while((i < (_nbrDeBenneDansParc)) && (!_benneArrivee));
			i = 0;

			if(_benneArrivee)
			{
				//rempli la benne
				System.out.println(this.getName() + " decharge le bois");
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAremplir).setEtat(EnumEtatBenne.REMPLIR);
				
				//benne suivante à remplir
				_numBenneAremplir = (_numBenneAremplir+1)%_nbrDeBenneDansParc;
				_benneAAttendre = _listDeBennes.get(_numBenneAremplir).getName();
				
				//avertissement quelle est pleine
				_benneArrivee = false;
				//synchronized(this) 
				//{
					System.out.println(this.getName() + " NOTIFY : la benne est pleine");
					notifyAll();
				//}
			}	
			else
			{
				//s'endort j'usque la benne soit arrivée
				//synchronized(this) 
				//{
					System.out.println(this.getName() + " WAIT : j'attends sur la benne " + _benneAAttendre);
					this.wait();
				//};		
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