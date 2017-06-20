import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
//import thread_01BASE_THREAD.Personne;

public class Benne_application {

	

	private static Scanner sc;

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int NbBenne = 3;
		int timer = 0;
		
		//running à true fait tourner l'application en boucle infinie
		//la lettre q pour quitter
				
		boolean running = true;
		boolean debugTransporteur = false;
		boolean debugOuvrier = false;
		boolean debugBucheron = false;
		boolean debugRDP = false;
		
		Object lock = new Object();
		Object lockRDP = new Object();

		System.out.println("******************************************************************");				
		System.out.println("Bienvenu dasn l'application des bennes");
		System.out.println("Créé par Vincent et Sébastien");
		System.out.println("******************************************************************\n");				

		char choix = confirugartionApplication();
		while(choix == 'd')
		{
			//on ne peut pas passer des valeurspar référence en Java
			//pas d'autre choix que de l'écrire ici....
			System.out.println("******************************************");		
			System.out.println("***            Debug MSG               ***");
			System.out.println("******************************************");		

			sc = new Scanner(System.in);	
			String str = " ";
			char carac = str.charAt(0);
			
			do
			{
				System.out.println("MSG Tranporteur : "+ debugTransporteur);
				System.out.println("MSG Ouvrier :     "+ debugOuvrier);
				System.out.println("MSG Bucheron :    "+ debugBucheron);
				System.out.println("MSG RDP :         "+ debugRDP);
				System.out.println("tapez : t, o, b, r pour modifier, ou q pour sortir");	
				
				str = sc.nextLine();
				carac = str.charAt(0);

				switch (carac)
				{
				case 't' : 
				{
					debugTransporteur = debugTransporteur == true ? false : true;
					break;
				}
				case 'o' :
				{
					debugOuvrier = debugOuvrier == true ? false : true;
					break;
				}
				case 'b' :
				{
					debugBucheron = debugBucheron == true ? false : true;
					break;
				}
				case 'r' :
				{
					debugRDP = debugRDP == true ? false : true;
					break;
				}
				default : break;
				}
			}
			while(carac != 'q');
			
			choix = confirugartionApplication();
		}
		
		if(choix == 't')
		{
			timer = configurationTimer();
			running = false;
		}
		else
		{
			running = true;
		}
		
		/*
		 * au départ: toutes les bennes sont vide et desamarée en foret prête pour être remplie
		 * Le transporteur peux ammarer la première quand elle serra remplie par le bucheron
		 * Le bucheron peut aller couper du bois.
		 * L'ouvrier attends l'arrivée de la première benne.
		 * 
		 */

		//création de la liste de benne
		ArrayList<Benne> listDeBennes = new ArrayList<Benne>();

		//création de la liste du buffer d'évenement
		ArrayList<Integer> listEvenement = new ArrayList<Integer>();

		//création des bennes de base toutes à la foret
		for(int i = 1; i <= NbBenne ; i++)
		{
			Benne benne = new Benne("Benne" + i, i);
			listDeBennes.add(benne);
		}

		//départ de la benne n°3 à l'usine et vide
		listDeBennes.get(2).setEtat(EnumEtatBenne.VIDE);

		for(int i = 0; i < listDeBennes.size() ; i++)
		{
			System.out.println("nom de benne " + i + " : " + listDeBennes.get(i).getName());
		}


		Transporteur T1 = new Transporteur("Transporteur 1", listDeBennes, listEvenement, NbBenne, lock);
		Ouvrier O1 = new Ouvrier("Ouvrier 1", listDeBennes, listEvenement, NbBenne, lock);
		Bucheron B1 = new Bucheron("Bucheron 1", listDeBennes, listEvenement,  NbBenne, lock);
		RDP_Scheduler rdpScheduler = new RDP_Scheduler("rdpScheduler", listEvenement, lockRDP);
		Tick tick = new Tick(1, lockRDP);

		rdpScheduler.displayPlace();

		//affichage des commentaires
		T1.setDebugOn(debugTransporteur);
		O1.setDebugOn(debugOuvrier);
		B1.setDebugOn(debugBucheron);
		rdpScheduler.setDebugOn(debugRDP);

		//demmarrage des threads
		T1.start();
		O1.start();
		B1.start();
		tick.start();
		rdpScheduler.start();

		if(!running)
		{
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			

		while(running)
		{	      
			running = stopApplication();
		}
		
		System.out.println("******************************************");		
		System.out.println("Arrêt du programme");
		System.out.println("Arrêt des threads");
		System.out.println("******************************************");		
		//arrêt des thread		
		B1.arret();
		O1.arret();
		T1.arret();
		tick.arret();
		rdpScheduler.arret();

		//attente de l'arret des thread
		try {
			TimeUnit.SECONDS.sleep(timer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fermerApplication(T1, O1, B1, rdpScheduler, tick, lock, lockRDP);
	}
	

	private static boolean stopApplication() 
	{
		sc = new Scanner(System.in);	
		String str = " ";
		char carac = str.charAt(0);
		
		do
		{
			str = sc.nextLine();
			carac = str.charAt(0);
			System.out.println("Vous avez taper : "+ carac);
			if(carac !='q')
			{
				System.out.println("tapper q pour quitter");
			}
		}
		while(carac != 'q');
		
		return false;
	}

	private static char confirugartionApplication()
	{
		sc = new Scanner(System.in);	
		String str = " ";
		char carac = str.charAt(0);
		System.out.println("Pour lancer l'application choisser parmis les deux option suivante\n");
		System.out.println("t : timer , l'application ce fini après un temps défini par vous");
		System.out.println("l : live  , boucle infinie, tapez q pour quitter");
		System.out.println("d : debug , affichage des messages de chaque thread\n");
		System.out.println("par défaut, les message de debug sont masqués\n");
		System.out.println("entrez : t, l, d");

		do
		{
			str = sc.nextLine();
			carac = str.charAt(0);
			System.out.println("Vous avez taper : "+ carac + "\n");
			if(carac != 't' && carac != 'l' && carac != 'd')
			{
			System.out.println("t : timer, l : live, d : debug");
			}

		}
		while(carac != 't' && carac != 'l' && carac != 'd');
		
		return carac;
		
	}

	private static int configurationTimer() 
	{	
		sc = new Scanner(System.in);	
		int timer = 0;
		do
		{
			System.out.println("choix du timer en secondes :");
			timer = sc.nextInt();
			System.out.println("Vous avez taper : "+ timer);
		}
		while(timer <= 0);
		return 0;
	}

	private static void fermerApplication(Transporteur T1, Ouvrier O1, Bucheron B1,
			RDP_Scheduler rdpScheduler, Tick tick, Object lock, Object lockRDP )
	{
		//temps d'attente d'affichage des thread
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(B1.isAlive() || O1.isAlive() || 
				T1.isAlive() || tick.isAlive() ||
				rdpScheduler.isAlive())
		{	
			System.out.println("\ntous les thread ne sont pas encore arrêtés");

			if (B1.isAlive())
				System.out.println(B1.getName() + " est en vie");		
			if (O1.isAlive())
				System.out.println(O1.getName() + " est en vie");
			if (T1.isAlive())
				System.out.println(T1.getName() + " est en vie");		
			if (tick.isAlive())
				System.out.println("Tick est en vie");		
			if (rdpScheduler.isAlive())
				System.out.println(rdpScheduler.getName() + "est en vie");		

			//temps d'attente avant de recontoler les états
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//reveille des threads en attente
			synchronized(lock) 
			{
				lock.notifyAll();
			}
			synchronized(lockRDP) 
			{
				lockRDP.notifyAll();
			}						
		};

		System.out.println("\ntous les thread sont arrêté correctements\n");
		System.out.println("******************************************");		
		System.out.println("***              BYE BYE               ***");
		System.out.println("******************************************");		

	}
	

}
