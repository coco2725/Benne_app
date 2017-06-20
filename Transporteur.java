import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Transporteur extends Thread 
{
	//arrêt du thread
	protected volatile boolean running = true;

	//affichage des commentaires
	private boolean debugON = false;

	private int _state = 2;
	private int _maxTime = 10;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private String _benneAAmmarerEnForet;
	private String _benneAAmmarerAUsine;
	private int _numBenneAAmmarerEnForet = 0;
	private int _numBenneAAmmarerAUsine = 2;	
	private int _nbrDeBenneDansParc = 0;
	Object _lock;

	public Transporteur(String name, ArrayList<Benne> listDeBennes, ArrayList<Integer> listEvenement, int nbrDeBenneDansParc, Object lock)
	{
		super(name);
		_listDeBennes = listDeBennes;
		_listEvenement = listEvenement;
		_benneAAmmarerEnForet = listDeBennes.get(0).getName();
		_benneAAmmarerAUsine = listDeBennes.get(2).getName();
		_nbrDeBenneDansParc = nbrDeBenneDansParc;
		_lock = lock;
	}

	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(running)
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
		this.interrupt();
	} 

	public void arret() 
	{
		running = false;
	}

	public void transporteBenneDeUsineAForet()
	{
		if(debugON)
		{
			System.out.println(this.getName() + ", transporte la " + _benneAAmmarerAUsine + " usine -> foret");
		}
		_listDeBennes.get(_numBenneAAmmarerAUsine).setEtat(EnumEtatBenne.TRANSPORTER_DE_USINE_VERS_FORET);
		_listEvenement.add(8);
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
		if(debugON)
		{
			System.out.println(this.getName() + ", " + _benneAAmmarerAUsine + " desamarré en foret");
		}
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
			if(debugON)
			{
				System.out.println(this.getName() + ", NOTIFY : la "+ _benneAAmmarerAUsine + " est desmarrée en forêt");
			}
			_listDeBennes.get(_numBenneAAmmarerAUsine).setEtat(EnumEtatBenne.DESAMARRER_EN_FORET);
			_listEvenement.add(1);

			//prend la benne suivante
			_numBenneAAmmarerEnForet = (_numBenneAAmmarerEnForet+1)%_nbrDeBenneDansParc;
			_benneAAmmarerEnForet = _listDeBennes.get(_numBenneAAmmarerEnForet).getName();
			synchronized(_lock) 
			{
				_lock.notifyAll();
			};		

		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void amarreBenneForet()
	{
		if(debugON)
		{
			System.out.println(this.getName() + ", le bucheron a peut-être fini de remplir une benne");
		}

		try {
			//controle si la benne est remplie
			if(	_listDeBennes.get(_numBenneAAmmarerEnForet).getEtat() == EnumEtatBenne.REMPLIE)
			{
				if(debugON)
				{
					System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " est bien remplie");
					//ammarer la benne
					System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " prête pour être ammarée");
				}
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));	
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.AMARER_EN_FORET);
				if(debugON)
				{
					System.out.println(this.getName() + ", " +  _benneAAmmarerEnForet + " est ammarée en foret");
				}
				_listEvenement.add(3);

				//Changement de l'etat du transporteur
				_state++;
				
			}
			else
			{
				//s'endort j'usque la benne soit arrivï¿½e
				synchronized(_lock) 
				{
					if(debugON)
					{
						System.out.println(this.getName() + ", WAIT : j'attends sur la benne " + _benneAAmmarerEnForet + " en forêt");
					}
					_lock.wait();
				};		
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void transporteBenneDeForetAUsine()
	{
		if(debugON)
		{
			System.out.println(this.getName() + ", transporte la "+ _benneAAmmarerEnForet +" foret -> usine");
		}
		_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.TRANSPORTER_DE_FORET_VERS_USINE);
		_listEvenement.add(4);
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void desamarreBenneUsine()
	{
		if(debugON)
		{
			System.out.println(this.getName() + ", desamarre benne à l'usine");
		}
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
			if(debugON)
			{
				System.out.println(this.getName() + ", NOTIFY : le benne "+ _benneAAmmarerEnForet + " est desmarrée à l'usine");
			}
			_listEvenement.add(5);
			
			//changement de l'état de la benne desamaré à l'usine
			_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.DESAMARER_USINE);
			
			synchronized(_lock) 
			{
				_lock.notifyAll();
			};		

		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void amarreBenneUsine()
	{		
		//benne suivante à amarer à l'usine
		_numBenneAAmmarerAUsine = 
				((_numBenneAAmmarerEnForet+_nbrDeBenneDansParc)-1)%_nbrDeBenneDansParc;		
		_benneAAmmarerAUsine = _listDeBennes.get(_numBenneAAmmarerAUsine).getName();


		try {
			//controle si la benne est vide
			if(debugON)
			{
				System.out.println(this.getName() + ", controle si la " + _benneAAmmarerAUsine + " est vide");
			}

			if(	_listDeBennes.get(_numBenneAAmmarerAUsine).getEtat() == EnumEtatBenne.VIDE)
			{
				if(debugON)
				{
					System.out.println(this.getName() + ", " + _benneAAmmarerAUsine + " est bien vide");
					//ammarer la benne
					System.out.println(this.getName() + ", " + _benneAAmmarerAUsine + " prête pour être ammarée");
				}
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));	
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAAmmarerAUsine).setEtat(EnumEtatBenne.AMARER_USINE);
				if(debugON)
				{
					System.out.println(this.getName() + ", " +  _benneAAmmarerAUsine + " est ammarée à l'usine");
				}
				_listEvenement.add(7);

				//Changement de l'etat du transporteur
				_state=0;
				try {
					TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				//s'endort j'usque la benne soit arrivï¿½e
				synchronized(_lock) 
				{
					if(debugON)
					{
						System.out.println(this.getName() + ", WAIT : j'attends sur la benne " + _numBenneAAmmarerAUsine + " à l'usine");
					}
					_lock.wait();
				};		
			}
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
