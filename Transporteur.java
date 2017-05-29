import java.util.concurrent.TimeUnit;

public class Transporteur extends Thread 
{
	private int state = 0;
	private int maxTime = 10;

	public Transporteur(String name)
	{
		super(name);
	}

	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(true)
		{
			switch (state){
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



	public void transporteBenneDeUsineAForet()
	{
		System.out.println(this.getName() + " transporte la benne usine -> foret");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void desamarreBenneForet()
	{
		System.out.println(this.getName() + " desamarre benne à la foret");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void amarreBenneForet()
	{
		System.out.println(this.getName() + " amarre benne à la foret");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void transporteBenneDeForetAUsine()
	{
		System.out.println(this.getName() + " transporte la benne foret -> usine");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void desamarreBenneUsine()
	{
		System.out.println(this.getName() + " desamarre benne à l'usine");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public void amarreBenneUsine()
	{
		System.out.println(this.getName() + " amarre benne à l'usine");
		state=0;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
