public class Benne {

	private int state = 0;
	private int _numBenne = 0;
	private String _name = "unknow";

	public Benne(String name, int numBenne)
	{
		this._name = name;
		this._numBenne = numBenne;
		this.state = 0;
	}

	public void getEtat()
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
		case 4: vider();
		break;
		case 5: amarerUsine();
		break;
		case 6: transportDeUsineAForet();
		break;
		case 7: desammarerForet();
		break;
		default : break;
		}
	}

	public String getName()
	{
		return this._name;
	}
	
	public void setEtat(int etat)
	{
		this.state = etat;
	}
	
	public void remplire()
	{
		System.out.println(this.getName() + " remplie");
	}

	public void ammarerForet()
	{
		System.out.println(this.getName() + " est ammarée");
	}

	public void transportDeForetVersUsine()
	{
		System.out.println(this.getName() + " transport de foret vers usine");
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
