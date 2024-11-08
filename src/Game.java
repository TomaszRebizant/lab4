import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


class Game {
    private HashMap<Integer, Integer> fields = new HashMap<Integer, Integer>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private Dice dice;
    private int maxTurns = 0;

    public Game() {
        this.dice = new Dice();
        this.players = new ArrayList<Player>(
                new ArrayList<Player>(
                        Arrays.asList(
                                new Player("Alice"),
                                new Player("Bob")
                        )
                )
        );

    }

    private int FieldsNumber() {
        System.out.println("Do you want to play with 100 fields? (yes/no)");
        String answer = System.console().readLine();
        if (answer.equals("yes")) {
            return 100;
        } else if (answer.equals("no")) {
            System.out.println("How many fields should the game have?");
            int fieldsNumber = Integer.parseInt(System.console().readLine());
            return fieldsNumber;
        } else {
            System.out.println("Please answer yes or no");
            return FieldsNumber();
        }
    }

    private void TurnsNumber() {
        System.out.println("Do you want to define the number of turns? (yes/no)");
        try {
            String answer = System.console().readLine();
            if (answer.equals("yes")) {
                System.out.println("How many turns should the game have?");
                maxTurns = Integer.parseInt(System.console().readLine());
            } else if (answer.equals("no")) {
                maxTurns = 0;
            } else {
                System.out.println("Please answer yes or no");
                TurnsNumber();
            }
        } catch (Exception e) {
            System.out.println("Please answer yes or no");
            TurnsNumber();
        }
    }

    public void prepareGame() {
        int index = 0;
        for (Player player : this.players) {
            this.fields.put(index, 0);
            index++;
        }
        TurnsNumber();
    }

    public void run() {
        this.prepareGame();
        Player winner = null;
        int fieldsNumber = FieldsNumber();
        int turns = 0;

        while (winner == null) {
            int index = 0;
            for (Player player : this.players) {
                int result = this.dice.roll();
                int position = this.fields.get(index) + result;
                this.fields.put(index, position);

                if (position >= fieldsNumber) {
                    position = fieldsNumber;
                }
                if(position%7==0){
                    int hit = this.dice.roll();
                    System.out.println("\n Player "+player.getName() +" take "+ hit + " damage.");
                    player.setHp(player.getHp()-hit);
                    if(player.getHp()<=0){
                        System.out.println("Player: " + player.getName()+" DEAD");
                        players.remove(player);
                    }
                    if(players.size()==1){
                        System.out.println("Only 1 player left. Player " + players.get(0).getName() + " is the winner");
                        return;
                    }
                }

                int index2 = 0;
                for(Player player2 : players)
                {
                    if(position!=0 && position == fields.get(index2) && !player2.equals(player)){
                        System.out.println("Player " + player.getName() + " get hit by" + player2.getName());
                        int hit = this.dice.roll();
                        System.out.println("Player "+player.getName() +" take "+ hit + " damage.");
                        player.setHp(player.getHp()-hit);
                        if(player.getHp()<=0){
                            System.out.println("Player: " + player.getName()+" DEAD");
                            players.remove(player);
                        }
                        if(players.size()==1){
                            System.out.println("Only 1 player left. Player " + players.get(0).getName() + " is the winner");
                            return;
                        }
                    }
                    index2++;
                }

                System.out.println(player.getName() + " rolled " + result +" at the turn: "  +(turns+1)+ ". Now is on position " + position);

                index++;

                if (position >= fieldsNumber) {
                    System.out.println(player.getName() + " won!");
                    winner = player;
                    break;
                }
            }
            turns++;
            if(maxTurns<=turns && maxTurns!=0){
                System.out.println("Game achieve max turns number");
                System.out.println("The winner is: ");
                int winnerPosition = 0;
                for (int i = 0; i < index; i++) {
                  if(this.fields.get(i)>winnerPosition){
                      winner = players.get(i);
                  }
                }
                assert winner != null;
                System.out.println(winner.getName());
                break;
            }
        }
    }
}

