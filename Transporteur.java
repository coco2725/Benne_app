import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Transporteur extends Thread 
{
	private int _state = 0;
	private int _maxTime = 10;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private String _benneAAttendre;
	private int _numBenneAAttendre = 1;
	private int _nbrDeBenneDansParc = 0;

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

	public void desamarreBenneForet()
	{
		System.out.println(this.getName() + " desamarre benne à la foret");
		_state++;

		try 
		{
			//TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));

			//notify all pour reveiller le bucheron si il attend sur la benne
			synchronized(this) 
			{
				System.out.println(this.getName() + " NOTIFY : la benne est desamarrée à la foret");
				this.notify();
			}
			//s'endort jausque la benne soie remplie
			synchronized(this) 
			{
				System.out.println(this.getName() + " VAIT : j'attends la benne pour l'ammarer en la foret");
				this.wait();
			};
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void amarreBenneForet()
	{
		System.out.println(this.getName() + " amarre benne à la foret");
		_state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
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
