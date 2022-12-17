package name.klamm;

import java.util.Random;

public class RandomEnemy implements Enemy {
    private final Random random = new Random();

    @Override
    public void turn(Field field, Figure figure) {
        boolean isOk = false;

        while (!isOk) {
            try {
                int row = random.nextInt(3);
                int column = random.nextInt(3);
                // System.out.println("Bot turn: " + row + " " + column);

                field.set(row, column, figure);
                isOk = true;
            } catch (Exception ex) {

            }
        }
    }
}
