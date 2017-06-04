import java.util.ArrayList;

//import thread_01BASE_THREAD.Personne;

public class Benne_application {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int NbBenne = 3;
		
		/*
		 * au d�part: toutes les bennes sont vide et desamar�e en foret pr�te pour �tre remplie
		 * Le transporteur peux ammarer la premi�re quand elle serra remplie par le bucheron
		 * Le bucheron peut aller couper du bois.
		 * L'ouvrier attends l'arriv�e de la premi�re benne.
		 * 
		 */
		
		//cr�ation de la liste de benne
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
		
		
		Transporteur T1 = new Transporteur("Transporteur 1", listDeBennes, NbBenne);
		Ouvrier O1 = new Ouvrier("Ouvrier 1");
		Bucheron B1 = new Bucheron("Bucheron 1", listDeBennes, NbBenne);
		
        
		T1.start();
		//O1.start();
		B1.start();
	}

}
