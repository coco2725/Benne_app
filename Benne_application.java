//import thread_01BASE_THREAD.Personne;

public class Benne_application {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		Transporteur T1 = new Transporteur("Transporteur 1");
		Ouvrier O1 = new Ouvrier("Ouvrier 1");
		Bucheron B1 = new Bucheron("Bucheron 1");
		
		T1.start();
		O1.start();
		B1.start();
	}

}
