import java.util.Random;
public class Deck {

	Random card = new Random();  	
    private String[] tens = new String[] { "10", "Jack", "Queen", "King" };  
	
    public int Draw()
	{
		return card.nextInt(11) + 1;
	}
	
    public String getTen()
    {
    	int rand = card.nextInt(3);
    	return tens[rand];
    }
	
}
 