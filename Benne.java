import java.util.concurrent.TimeUnit;

public class Benne {

	private int state = 0;
	private int maxTime = 10;

	public Bucheron(String name)
	{
		super(name);
	}

	public void run()
	{
		System.out.println(this.getName() + "creer");
		while(true)
		{
			switch (state){
			case 0: remplire();
			break;	
			case 1: ammarerForet();
			break;	
			case 2: transportDeForetVersUsine();
			break;
			case 3: desamreUsine();
			break;
			case 4: amarerUsine();
			break;
			case 5: transportDeUsineAForet();
			break;
			case 6: desammarerForet();
			break;
			default : break;
			}
		} 
	}
	
	public void remplire()
	{
		System.out.println(this.getName() + " remplie");
		state++;
	}

	public void ammarerForet()
	{
		System.out.println(this.getName() + " est ammarée");
		state++;
	}

	public void transportDeForetVersUsine()
	{
		System.out.println(this.getName() + " transport de foret vers usine");
		state++;
	}

	public void desamreUsine()
	{
		System.out.println(this.getName() + " desammarer à l'usine");
		state=++;
	}
	
	public void vider()
	{
		System.out.println(this.getName() + " est vidée");
		state=++;
	}

	
	public void amarerUsine()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
		state=++;
	}

	public void transportDeUsineAForet()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
		state=++;
	}

	public void desammarerForet()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
		state=0;
	}

}
