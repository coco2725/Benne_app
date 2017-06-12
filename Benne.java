public class Benne {

	private EnumEtatBenne state;
	private int _numBenne = 0;
	private String _name = "unknow";

	public Benne(String name, int numBenne)
	{
		this._name = name;
		this._numBenne = numBenne;
		this.state = EnumEtatBenne.DESAMARRER_EN_FORET;
	}

	public void EtatSuivant()
	{
		switch (state){
		case DESAMARRER_EN_FORET: desammarerForet();
		break;
		case REMPLIE: remplire();
		break;	
		case AMARER_EN_FORET: ammarerForet();
		break;	
		case TRANSPORTER_DE_FORET_VERS_USINE: transportDeForetVersUsine();
		break;
		case DESAMARER_USINE: desamreUsine();
		break;
		case VIDE: vider();
		break;
		case AMARER_USINE: amarerUsine();
		break;
		case TRANSPORTER_DE_USINE_VERS_FORET: transportDeUsineAForet();
		break;
		default : break;
		}
	}

	public String getName()
	{
		return this._name;
	}
	
	public void setEtat(EnumEtatBenne etat)
	{
		this.state = etat;
	}
	
	public EnumEtatBenne getEtat()
	{
		return state;
	}
	
	public void desammarerForet()
	{
		System.out.println(this.getName() + " desammarer en for�t");
	}
		
	public void remplire()
	{
		System.out.println(this.getName() + " remplie");
	}

	public void ammarerForet()
	{
		System.out.println(this.getName() + " est ammar�e en For�t");
	}

	public void transportDeForetVersUsine()
	{
		System.out.println(this.getName() + " transport de foret vers usine");
	}

	public void desamreUsine()
	{
		System.out.println(this.getName() + " desammarer � l'usine");//
	}

	public void vider()
	{
		System.out.println(this.getName() + " est vid�e");
	}


	public void amarerUsine()
	{
		System.out.println(this.getName() + " ammarer � l'usine");
	}

	public void transportDeUsineAForet()
	{
		System.out.println(this.getName() + " ammarer � l'usine");
	}

}
