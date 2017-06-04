import java.util.concurrent.TimeUnit;

public class Benne {

	private int state = 0;
	private int maxTime = 10;
	private String _name = "unknow";

	public Benne(String name)
	{
		this._name = name;
		this.state = 0;
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
	
	public String getName()
	{
		return this._name;
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
	}
	
	public void vider()
	{
		System.out.println(this.getName() + " est vidée");
	}

	
	public void amarerUsine()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
	}

	public void transportDeUsineAForet()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
	}

	public void desammarerForet()
	{
		System.out.println(this.getName() + " ammarer à l'usine");
	}

}
