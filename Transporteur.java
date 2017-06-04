import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Transporteur extends Thread 
{
	private int _state = 1;
	private int _maxTime = 10;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private String _benneAAttendre;
	private int i = 0;
	private int _numBenneAAmmarerEnForet = 1;
	private int _nbrDeBenneDansParc = 0;
	private boolean _benneRemplie = false;

	public Transporteur(String name, ArrayList<Benne> listDeBennes, int nbrDeBenneDansParc)
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
			switch (_state){
			case 0: transporteBenneDeUsineAForet();
			break;	
			case 1: desamarreBenneForet();
			break;	
			case 2: amarreBenneForet();
			break;
			case 3: transporteBenneDeForetAUsine();
			break;
			case 4: desamarreBenneUsine();
			break;
			case 5: amarreBenneUsine();
			break;

			default : break;
			}
		} 
	} 

	public void setBenneAAttendre(String benneAAttendre)
	{
		this._benneAAttendre = benneAAttendre;
	}

	public void transporteBenneDeUsineAForet()
	{
		System.out.println(this.getName() + " transporte la benne usine -> foret");
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void desamarreBenneForet()
	{
		System.out.println(this.getName() + " desamarre benne à la foret");
		_state++;
		try 
		{
			//TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));

			//notify all pour reveiller le bucheron si il attend sur la benne
			//synchronized(this) 
			//{
				System.out.println(this.getName() + " NOTIFY : la benne est desamarrée à la foret");
				this.notify();
			//}
			//s'endort jausque la benne soie remplie
			//synchronized(this) 
			//{
				System.out.println(this.getName() + " WAIT : j'attends la benne pour l'ammarer en la foret");
				this.wait();
			//};
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public synchronized void amarreBenneForet()
	{
		System.out.println(this.getName() + " le bucheron a fini de remplir une benne");
		
		try {
			//TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));			
			i=0;		
			do
			{
				//contôle si la benne est remplie
				if(_listDeBennes.get(i).getName() == _benneAAttendre && 
						_listDeBennes.get(i).getEtat() == EnumEtatBenne.REMPLIR)
				{
					System.out.println(this.getName() + ", " + _numBenneAAmmarerEnForet + " est bien remplie");
					_benneRemplie = true;
					_numBenneAAmmarerEnForet = i;
					_state++;
				}
				i++;
			}
			while((i < (_nbrDeBenneDansParc)) && (!_benneRemplie));
			i = 0;

			if(_benneRemplie)
			{
				//ammarer la benne
				System.out.println(this.getName() + "prête pour être ammarée");
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.AMMARER_EN_FORET);
				
				//benne suivante à remplir
				_numBenneAAmmarerEnForet = (_numBenneAAmmarerEnForet+1)%_nbrDeBenneDansParc;
				_benneAAttendre = _listDeBennes.get(_numBenneAAmmarerEnForet).getName();
				
				//avertissement quelle est pleine
				_benneRemplie = false;
				//synchronized(this) 
				//{
					System.out.println(this.getName() + " NOTIFY : la benne est ammarée en foret");
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

	public void transporteBenneDeForetAUsine()
	{
		System.out.println(this.getName() + " transporte la benne foret -> usine");
		_state++;
		try {
			for(int i = 0; i< _nbrDeBenneDansParc; i++)
			{
				System.out.println(_listDeBennes.get(i).getName()); 
			}

			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void desamarreBenneUsine()
	{
		System.out.println(this.getName() + " desamarre benne à l'usine");
		_state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public void amarreBenneUsine()
	{
		System.out.println(this.getName() + " amarre benne à l'usine");
		_state=0;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
