import java.util.concurrent.TimeUnit;

public class Ouvrier extends Thread 
{
	private int state = 0;
	private int maxTime = 10;

	public Ouvrier(String name)
	{
		super(name);
	}
	
	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(true)
		{
			switch (state){
			case 0: dechargeBenne();
			break;	
			case 1: ammenerUsine();
			break;	
			case 2: scieBois();
			break;
			case 3: retourneBenne();
			break;
			default : break;
			}
		} 
	} 
	
	public void dechargeBenne()
	{
		System.out.println(this.getName() + " decharge la benne");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ammenerUsine()
	{
		System.out.println(this.getName() + " ammene a l'usine");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void scieBois()
	{
		System.out.println(this.getName() + " scie le bois");
		state++;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void retourneBenne()
	{
		System.out.println(this.getName() + " retourne a la benne");
		state=0;
		try {
			TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
