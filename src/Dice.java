import java.util.Random;

class Dice {
    private int walls;

    public Dice() {
    setWalls();
    }

    public int roll() {
        return new Random().nextInt(6) + 1;
    }
    private void setWalls(){
        System.out.println("How many walls should the dice have?");
        try {
            int tmp = Integer.parseInt(System.console().readLine());
            if (tmp < 4) {
                System.out.println("The dice must have at least 4 walls");
                setWalls();
            } else if (tmp > 20) {
                System.out.println("The dice must have at most 20 walls");
                setWalls();
            } else {
                walls = tmp;
            }
        }
        catch(NumberFormatException e){
            System.out.println("Please enter a number");
            setWalls();
        }
    }
}
