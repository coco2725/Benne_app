import java.util.concurrent.TimeUnit;


public class Bucheron extends Thread
{
	private int state = 0;
	private int maxTime = 10;
	public Bucheron(String name)
	{
		super(name);
	}

	public void run()
	{
		System.out.println(this.getName() + " creer");
		while(true)
		{
			switch (state){
			case 0: coupe();
			break;	
			case 1: ammenerBenne();
			break;	
			case 2: decharge();
			break;
			case 3: retourneForet();
			break;
			default : break;
			}
		} 
	}


		public void coupe()
		{
			System.out.println(this.getName() + " coupe du bois");
			state++;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void ammenerBenne()
		{
			System.out.println(this.getName() + " apporte le bois vers benne");
			state++;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void decharge()
		{
			System.out.println(this.getName() + " decharge le bois");
			state++;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void retourneForet()
		{
			System.out.println(this.getName() + " retourne en foret");
			state=0;
			try {
				TimeUnit.SECONDS.sleep((int)(1+Math.random()*maxTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}