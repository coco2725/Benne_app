import java.util.ArrayList;

//import thread_01BASE_THREAD.Personne;

public class Benne_application {

	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int NbBenne = 3;
		Object lock = new Object();
		Object lockRDP = new Object();

		
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
		T1.start();
		O1.start();
		B1.start();
		tick.start();
		rdpScheduler.start();
	}

}
