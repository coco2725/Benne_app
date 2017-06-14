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
		 * au d�part: toutes les bennes sont vide et desamar�e en foret pr�te pour �tre remplie
		 * Le transporteur peux ammarer la premi�re quand elle serra remplie par le bucheron
		 * Le bucheron peut aller couper du bois.
		 * L'ouvrier attends l'arriv�e de la premi�re benne.
		 * 
		 */
		
		//cr�ation de la liste de benne
		ArrayList<Benne> listDeBennes = new ArrayList<Benne>();

		//cr�ation de la liste du buffer d'�venement
		ArrayList<Integer> listEvenement = new ArrayList<Integer>();
		
		//cr�ation des bennes de base toutes � la foret
		for(int i = 1; i <= NbBenne ; i++)
		{
			Benne benne = new Benne("Benne" + i, i);
			listDeBennes.add(benne);
		}
		
		//d�part de la benne n�3 � l'usine et vide
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
