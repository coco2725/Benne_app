import java.util.ArrayList;

//import thread_01BASE_THREAD.Personne;

public class Benne_application {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int NbBenne = 3;
		
		/*
		 * au départ: toutes les bennes sont vide et dans le parque
		 * Le transporteur peux ammarer la première
		 * Le bucheron peut aller couper du bois.
		 * L'ouvrier attends l'arrivée de la première benne
		 * 
		 */
		
		//création de la liste de benne
		ArrayList<Benne> listDeBennes = new ArrayList<Benne>();
		
		for(int i = 1; i <= NbBenne ; i++)
		{
			Benne benne = new Benne("Benne" + i, i);
			listDeBennes.add(benne);
		}
		
		for(int i = 0; i < listDeBennes.size() ; i++)
		{
			System.out.println("nom de benne " + i + " : " + listDeBennes.get(i).getName());
		}
		
		
		Transporteur T1 = new Transporteur("Transporteur 1");
		Ouvrier O1 = new Ouvrier("Ouvrier 1");
		Bucheron B1 = new Bucheron("Bucheron 1");
		
		T1.start();
		O1.start();
		B1.start();
	}

}
