import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Transporteur extends Thread 
{
	private int _state = 1;
	private int _maxTime = 10;
	private ArrayList<Benne> _listDeBennes = new ArrayList<Benne>();
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private String _benneAAmmarerEnForet;
	private int i = 0;
	private int _numBenneAAmmarerEnForet = 2;
	private int _nbrDeBenneDansParc = 0;
	Object _lock;

	public Transporteur(String name, ArrayList<Benne> listDeBennes, ArrayList<Integer> listEvenement, int nbrDeBenneDansParc, Object lock)
	{
		super(name);
		_listDeBennes = listDeBennes;
		_listEvenement = listEvenement;
		_benneAAmmarerEnForet = listDeBennes.get(0).getName();
		_nbrDeBenneDansParc = nbrDeBenneDansParc;
		_lock = lock;
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
		this._benneAAmmarerEnForet = benneAAttendre;
	}

	public void transporteBenneDeUsineAForet()
	{
		System.out.println(this.getName() + " transporte la benne usine -> foret");
		_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.TRANSPORTER_DE_USINE_VERS_FORET);
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
		System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " desamarre en foret");
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
			System.out.println(this.getName() + ", NOTIFY : le benne "+ _benneAAmmarerEnForet + " est desmarr�e en for�t");
			_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.DESAMARRER_EN_FORET);
			//predn la benne suivante
			_numBenneAAmmarerEnForet = (_numBenneAAmmarerEnForet+1)%_nbrDeBenneDansParc;
			_benneAAmmarerEnForet = _listDeBennes.get(_numBenneAAmmarerEnForet).getName();
			synchronized(_lock) 
			{
				_lock.notifyAll();
				_listEvenement.add(1);
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
		System.out.println(this.getName() + ", le bucheron a fini de remplir une benne");

		try {
			//cont�le si la benne est remplie
			if(	_listDeBennes.get(_numBenneAAmmarerEnForet).getEtat() == EnumEtatBenne.REMPLIE)
			{
				System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " est bien remplie");
				//ammarer la benne
				System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " pr�te pour �tre ammar�e");
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));	
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.AMARER_EN_FORET);
				System.out.println(this.getName() + ", " +  _benneAAmmarerEnForet + " est ammar�e en foret");

				//Changement de l'etat du transporteur
				_listEvenement.add(3);
				_state++;
				
			}
			else
			{
				//s'endort j'usque la benne soit arriv�e
				synchronized(_lock) 
				{
					System.out.println(this.getName() + ", WAIT : j'attends sur la benne " + _benneAAmmarerEnForet + " en for�t");
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
		System.out.println(this.getName() + ", transporte la benne foret -> usine");
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
		System.out.println(this.getName() + ", desamarre benne � l'usine");
		_state++;
		try 
		{
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
			System.out.println(this.getName() + ", NOTIFY : le benne "+ _benneAAmmarerEnForet + " est desmarr�e � l'usine");
			_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.DESAMARER_USINE);
			synchronized(_lock) 
			{
				_listEvenement.add(5);
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
		System.out.println(this.getName() + ", l'ouvrier � fini de vider la benne");

		try {
			//cont�le si la benne est vide
			if(	_listDeBennes.get(_numBenneAAmmarerEnForet).getEtat() == EnumEtatBenne.VIDE)
			{
				System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " est bien vid�e");
				//ammarer la benne
				System.out.println(this.getName() + ", " + _benneAAmmarerEnForet + " pr�te pour �tre ammar�e");
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));	
				
				//Changement de l'etat de la benne
				_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.AMARER_USINE);
				System.out.println(this.getName() + ", " +  _benneAAmmarerEnForet + " est ammar�e � l'usine");

				//Changement de l'etat du transporteur
				_listEvenement.add(7);
				_state++;
			}
			else
			{
				//s'endort j'usque la benne soit arriv�e
				synchronized(_lock) 
				{
					System.out.println(this.getName() + ", WAIT : j'attends sur la benne " + _benneAAmmarerEnForet + " � l'usine");
					_lock.wait();
				};		
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		System.out.println(this.getName() + ", amarre benne � l'usine");
		_listDeBennes.get(_numBenneAAmmarerEnForet).setEtat(EnumEtatBenne.AMARER_USINE);
		_state=0;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*_maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
