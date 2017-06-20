import java.util.ArrayList;


public class RDP_Scheduler extends Thread 
{

	//arrêt du thread
	protected volatile boolean running = true;

	private boolean debugON = false;
	Object _lock;
	private Integer _nbPlace = 8;
	private ArrayList<Integer> _listEvenement = new ArrayList<Integer>();
	private ArrayList<Integer> _listSensibilisee = new ArrayList<Integer>();
	private ArrayList<Integer> _listFranchie = new ArrayList<Integer>();

	private int tabAncienMarquage[] =
		/*P1*/		{	2, //benne 1 et 2 en foret disponible pour être remplie
		/*P2*/			0, 
		/*P3*/			0, 
		/*P4*/			0, 
		/*P5*/			1, //benne 3 à l'usine disponible pour être amarée
		/*P6*/			0, 
		/*P7*/			0, 
		/*P8*/			0};  

	private int tabNouveauMarquage[] =
		/*P1*/		{	2, //benne 1 et 2
		/*P2*/			0, 
		/*P3*/			0, 
		/*P4*/			0, 
		/*P5*/			1, //benne 3
		/*P6*/			0, 
		/*P7*/			0, 
		/*P8*/			0};  

	private int tabPreIncidence[][] = //consomation de jeton
		{
				//			 T1, T2, T3, T4, T5, T6, T7, T8
				/*P1*/		{0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 }, 
				/*P2*/		{0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 }, 
				/*P3*/		{0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 }, 
				/*P4*/		{0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 }, 
				/*P5*/		{0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 }, 
				/*P6*/		{0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 }, 
				/*P7*/		{0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 }, 
				/*P8*/		{1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 }  
		};

	private int tabPostIncidence[][] = //production de jeton
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

	public RDP_Scheduler(String name, ArrayList<Integer> listEvenement, Object lock)
	{
		super(name);
		_listEvenement = listEvenement;
		_lock = lock;

	}

	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(running)
		{		
			if(_listEvenement.size()>0)
			{
				//copie du nouveau marquage dans l'ancien
				for(int i = 0; i<_nbPlace ; i++)	
				{
					tabAncienMarquage[i] = tabNouveauMarquage[i];
				}
				phaseFi1();
				phaseFi2();
				phaseFi3();
				displayPlace();
			}

			try {
				synchronized(_lock) 
				{
					_lock.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		this.interrupt();
	} 

	public void arret() 
	{
		running = false;
	}

	//ajout de la transition dans la lsite des transition sensibilisée
	private void phaseFi1()
	{
		int j=0;
		int numTransition = 0;
		while (_listEvenement.size() > j) 
		{			
			numTransition = (_listEvenement.get(j));
			displayMSG(numTransition);
			
			//contôle quelles sont les transition potentiellement sensibilisable 			[1]
			//contrôle si il y assez de jeton sur la place pour sensibiliser la transition 	[2]
			for(int i = 0; i<_nbPlace ; i++)	
			{			                                     
				if((tabPreIncidence[i][numTransition-1] > 0) && // [1]
						(tabAncienMarquage[i] >0))				// [2]
				{
					_listSensibilisee.add(numTransition);
					if(debugON)
					{
						System.out.println("transition sensibilisée, nbevent: " + _listEvenement.size() + 
								", place : " + (i+1) + ", T : " + numTransition);
					}
					_listEvenement.remove(j);
				}
			}	
			j++;
		}
	}
	
	//ajout de la transion dans la lsite e transition franchie
	private void phaseFi2()
	{
		int j=0;
		int numTransition = 0;
		while (_listSensibilisee.size() > j) 
		{
			numTransition = _listSensibilisee.get(j);
			
			for(int i = 0; i<_nbPlace ; i++)	
			{
				//contôle quelles sont les transition potentiellement sensibilisable 	[1]
				//la transition est franchie si il y a assez de jeton à manger 			[2]
				if((tabPreIncidence[i][numTransition-1] > 0) && 						// [1]
						tabAncienMarquage[i] > tabPostIncidence[i][numTransition-1])	// [2]
				{
					tabAncienMarquage[i] = 
							tabAncienMarquage[i] 
									- tabPreIncidence[i][numTransition-1];
					_listFranchie.add(numTransition);
					if(debugON)
					{
						System.out.println("transition franchie,     nbevent: " + _listSensibilisee.size() + 
								", place : " + (i+1) + ", T : " + numTransition);
					}
					_listSensibilisee.remove(j);
				}
				
			}
			j++;
		}
	}
	
	private void phaseFi3()
	{
		//copie du nouveau marquage dans l'ancien
		for(int i = 0; i<_nbPlace ; i++)	
		{
			tabNouveauMarquage[i] = tabAncienMarquage[i];
		}
		
		//ajout des jeton dans les places
		int j=0;
		int numTransition = 0;
		while (_listFranchie.size() > j) 
		{
			numTransition = _listFranchie.get(j);
			
			//on ajoute les jetons
			for(int i = 0; i<_nbPlace ; i++)	
			{
				tabNouveauMarquage[i] = 
						tabNouveauMarquage[i] 
								+ tabPostIncidence[i][numTransition-1];
				if(debugON)
				{
					System.out.println("Jeton ajouter   , nb: " + tabPostIncidence[i][numTransition-1] + 
							", place : " + (i) + ", T : " + numTransition);
				}

			}
			
			_listFranchie.remove(j);
			j++;
		}
	}
	private void displayMSG(int transition)
	{
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
		switch (transition)
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

	}
	
	public void displayPlace()
	{
	System.out.println("  " + tabNouveauMarquage[0] + " ---||--- " + tabNouveauMarquage[7] + " ---||--- " + tabNouveauMarquage[6] + " ---||--- " + tabNouveauMarquage[5]);
	System.out.println("  |                                 | ");	
	System.out.println("  |                                 | ");	
	System.out.println(" ---                               ---");	
	System.out.println(" ---                               ---");	
	System.out.println("  |                                 | ");	
	System.out.println("  |                                 | ");	
	System.out.println("  " + tabNouveauMarquage[1] + " ---||--- " + tabNouveauMarquage[2] + " ---||--- " + tabNouveauMarquage[3] + " ---||--- " + tabNouveauMarquage[4]);	
	}
	
	public void setDebugOn(boolean value)
	{
		debugON = value;
	}

}

