import java.util.Scanner;

public class Game {

	public static void main(String[] args) {

		// variable to determine if you want to keep playing	
		boolean leave = false;

		// variable to determine if player hits or stays	
		String choice = "";

		System.out.println("Welcome to Jeff's Blackjack Game!");
		System.out.print("Enter player name: ");
		Scanner scan = new Scanner(System.in);
		Player player = new Player(scan.nextLine());

		System.out.println();
		Deck card = new Deck();
		System.out.println("Welcome "+player.Name()+"! Your starting pot is $"+player.Pot());
		System.out.println("Let's play...");
		System.out.println();	

		//Region - GamePlay		
		while(leave == false)
		{	
			//Region - Wager 
			int bet = 0;
			System.out.print("Enter bet amount: $");
			bet = scan.nextInt();

			// bet amount validation
			while(bet < 1 || bet > player.Pot())
			{
				System.out.print("Invalid amount. Enter another: ");
				bet = scan.nextInt();
			}
			//EndRegion - Wager			

			//Region - Player Turn 

			System.out.println();

			// show players first two cards
			System.out.print("Your cards are: ");
			int card1 = card.Draw();
			// print out a random ten value
			if(card1 == 10){ System.out.print(card.getTen()+" and "); }
			// print out ace instead of 1 or 11
			else if(card1 == 11 || card1 == 1){ System.out.print("Ace and "); }
			else System.out.print(card1+" and ");
			int card2 = card.Draw();	
			if(card2 == 10){ System.out.println(card.getTen()); }
			else if(card2 == 11 || card2 == 1){ System.out.println("Ace"); }
			else System.out.println(card2);
			int total = 0;
			// change ace values if two 11's are drawn
			if((card1 + card2) > 21)
			{
				card1 = 1; card2 = 1;
				total = card1 + card2;
			}
			else total = card1 + card2;

			// go to dealers turn if total is 21
			if(total == 21)
			{
				System.out.println("Your total is "+total);
				choice = "stay";
				break; 
			}
			else
			{
				System.out.println("Your total is "+total);
			}
			System.out.println();
			System.out.print("Hit or Stay: ");
			Scanner hit = new Scanner(System.in);
			choice = hit.nextLine();

			// make sure you can only hit or stay
			while(!(choice.equalsIgnoreCase("stay") || choice.equalsIgnoreCase("hit")))
			{
				System.out.print("Please enter HIT or STAY: ");
				choice = hit.nextLine();
			}

			// hit loop
			while(choice.equalsIgnoreCase("hit"))
			{


				System.out.println();
				int nextCard = card.Draw();
				System.out.print("Your next card is: ");
				if(nextCard == 10){ System.out.println(card.getTen()); }

				//change ace value if total exceeds 21
				else if(nextCard == 11 || nextCard == 1)
				{ 
					System.out.println("Ace"); 
					if((nextCard + total) > 21){ nextCard = 1;}
					else nextCard = 11;
				}
				else System.out.println(nextCard);
				total += nextCard;

				// bust scenario
				if(total > 21)
				{
					System.out.println(total+" You bust!");
					player.Loss(bet);
					System.out.println("Your pot is now $"+player.Pot());
					break;
				}

				// move to dealer turn if total is 21
				else if(total == 21) 
				{ 
					System.out.println("Your total is "+total);
					choice = "stay"; 
					break; 
				}
				else
				{
					System.out.println("Your total is "+total);
					System.out.println();
					System.out.print("Hit or Stay: ");
					choice = hit.nextLine();
					while(!(choice.equalsIgnoreCase("stay") || choice.equalsIgnoreCase("hit")))
					{
						System.out.print("Please enter HIT or STAY: ");
						choice = hit.nextLine();
					}
				}
			}
			//EndRegion - Player Turn

			// use scanner to move through dealer turn one card hit at a time	
			Scanner next = new Scanner(System.in);

			//Region - Dealer Turn 
			if(choice.equalsIgnoreCase("stay"))
			{
				System.out.println();
				// get dealers' first two cards
				System.out.print("The dealers' cards are: ");
				int dcard1 = card.Draw();
				// print random ten value
				if(dcard1 == 10){ System.out.print(card.getTen()+" and "); }
				// print ace instead of 1 or 11
				else if(dcard1 == 11 || dcard1 == 1){ System.out.print("Ace and "); }
				else System.out.print(dcard1+" and ");
				int dcard2 = card.Draw();
				if(dcard2 == 10){ System.out.println(card.getTen()); }
				else if(dcard2 == 11 || dcard2 == 1){ System.out.println("Ace"); }
				else System.out.println(dcard2);
				int dtotal = 0;
				// change ace value if two 11's are drawn
				if((dcard1 + dcard2) > 21)
				{
					dcard1 = 1; dcard2 = 1;
					dtotal = dcard1 + dcard2;
				}
				else dtotal = dcard1 + dcard2;
				// if first two cards equal 21
				if(dtotal == 21)
				{
					// if player total is 21, then push
					if(total == 21)
					{
						System.out.println("--Push--");
					}
					// dealer blackjack scenario
					else
					{
						player.Loss(bet);
						System.out.println("The dealer has blackjack! Sorry, you lost.");
						System.out.println("Your pot is now $"+player.Pot());
					}
				}
				// dealer win scenario
				else if(dtotal > total && dtotal < 22)
				{
					System.out.println(dtotal+" Dealer Wins!");
					player.Loss(bet);
					System.out.println("Your pot is now $"+player.Pot());
				}
				// if player and dealer total are equal and player total is 17+, then push
				else if(dtotal == total && total >= 17)
				{
					System.out.println("--Push--");
				}
				else
				{
					String cont = "";
					// dealer hit loop
					do{
						System.out.println("The dealers' total is "+dtotal);
						cont = next.nextLine();
						int nextCard = card.Draw();
						System.out.print("The dealers' next card is: ");
						if(nextCard == 10){ System.out.println(card.getTen()); }
						// change ace value if total exceeds 21
						else if(nextCard == 11 || nextCard == 1)
						{ 
							System.out.println("Ace"); 
							if((nextCard + dtotal) > 21){ nextCard = 1;}
							else nextCard = 11;
						}
						else System.out.println(nextCard);
						dtotal += nextCard;
						if(dtotal == 21)
						{
							if(total == 21)
							{
								System.out.println("--Push--");
							}
							else
							{
								player.Loss(bet);
								System.out.println("The dealer has blackjack! Sorry, you lost.");
								System.out.println("Your pot is now $"+player.Pot());
								break;
							}
						}
						else if(dtotal > total && dtotal < 22)
						{
							System.out.println(dtotal+" Dealer Wins!");
							player.Loss(bet);
							System.out.println("Your pot is now $"+player.Pot());
							break;
						}
						else if(dtotal == total && total >= 17)
						{
							System.out.println("--Push--");
							break;
						}
					}while(dtotal < 22 && cont.isEmpty());

					if(dtotal > 21)
					{
						// player blackjack scenario
						if(total == 21)
						{
							System.out.println("BLACKJACK!!!!!!!!!!!");
							player.BlackJack(bet);
							System.out.println("Your pot is now $"+player.Pot());
						}
						// dealer bust scenario
						else
						{
							System.out.println();  
							System.out.println(dtotal+" Dealer busts. You WIN!");
							player.Win(bet);
							System.out.println("Your pot is now $"+player.Pot());
						}
					}
				}
			}
			//EndRegion - Dealer Turn			

			System.out.println();
			
			//Region - End of Turn
			
			// check if player is broke
			if(player.Pot() == 0)
			{
				leave = true;
				System.out.println("You have no more money. Thanks for playing!");
				hit.close();
				scan.close();
				next.close();

			}
            // get input to keep playing
			else
			{
				System.out.print("Play another hand? (Y/n) ");
				Scanner end = new Scanner(System.in);
				String yesNo = end.nextLine();
				if(yesNo.equalsIgnoreCase("n"))
				{
					leave = true;
					System.out.println();
					System.out.println("You have left the table with $"+player.Pot());
					end.close();
					hit.close();
					scan.close();
					next.close();
				}
				System.out.println();
			}
			//EndRegion - End of Turn
		}
		//EndRegion - GamePlay		
	}
}
