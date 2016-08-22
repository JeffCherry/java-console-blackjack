
public class Player {

	private String name;
	public String Name(){ return name; }
	private int pot;
	public int Pot(){ return pot; }

	public Player(String pName)
	{ 
		name = pName; 
		pot = 100;
	}

	public void BlackJack(int bet)
	{
		pot +=  bet + (bet * .15);
	}

	public void Win(int bet)
	{
		pot += bet; 
	}

	public void Loss(int bet)
	{
		pot -= bet;
	}
}