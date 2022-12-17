package name.klamm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConsoleGame implements Game {
    private final Field field = new Field();
    private final Enemy enemy = new RandomEnemy();
    private final BufferedReader reader;

    public ConsoleGame() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        boolean isEnd = false;
        while (!isEnd) {
            draw();
            try {
                control();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            enemy();
            isEnd = logic();

            if (isEnd) {
                // TODO: draw with ---
                draw();
            }
        }
    }

    private void draw() {
        var figures = field.getFigures();

        System.out.println("  123 ");
        // https://en.wikipedia.org/wiki/Code_page_437
        System.out.println(" ┌───┐");
        int line = 1;
        for (var figuresArray : figures) {
            System.out.print(line++ + "│");
            for (var figure : figuresArray) {
                char c = ' ';

                if (figure != null) {
                    switch (figure) {
                        case O -> c = 'O';
                        case X -> c = 'X';
                    }
                }

                System.out.print(c);
            }
            System.out.print('│');
            System.out.println();
        }
        System.out.println(" └───┘");
    }

    private void control() throws IOException {
        boolean isTurnOk = false;
        while (!isTurnOk) {
            System.out.print("Your turn (<row> <column>): ");

            var line = reader.readLine();
            var items = line.split(" ");

            if (items.length != 2) {
                System.out.println("Error: bad input");
                continue;
            }

            int row = Integer.parseInt(items[0]);
            int column = Integer.parseInt(items[1]);
            --row;
            --column;

            try {
                field.set(row, column, Figure.X);
                isTurnOk = true;
            } catch (FieldAlreadyUsedException ex) {
                System.out.println("Error: field already used");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Error: row/column must be in 1..3");
            }
        }
    }

    private void enemy() {
        enemy.turn(field, Figure.O);
    }

    private boolean logic() {
        boolean yourWin = false;
        boolean enemyWin = false;

        var figures = field.getFigures();

        for (int i = 0; i < 3; ++i) {
            yourWin = true;
            enemyWin = true;

            // check row
            for (int j = 0; j < 3; ++j) {
                var figure = figures[i][j];
                if (figure != Figure.X) {
                    yourWin = false;
                }
                if (figure != Figure.O) {
                    enemyWin = false;
                }
            }

            if (yourWin || enemyWin) {
                break;
            }

            yourWin = true;
            enemyWin = true;

            // check column
            for (int j = 0; j < 3; ++j) {
                var figure = figures[j][i];
                if (figure != Figure.X) {
                    yourWin = false;
                }
                if (figure != Figure.O) {
                    enemyWin = false;
                }
            }

            if (yourWin || enemyWin) {
                break;
            }
        }

        if (!yourWin && !enemyWin) {
            for (int i = 0; i < 2; ++i) {
                // check diag
                yourWin = true;
                enemyWin = true;

                for (int j = 0; j < 3; ++j) {
                    var figure = figures[j][i == 0 ? j : 2 - j];
                    if (figure != Figure.X) {
                        yourWin = false;
                    }
                    if (figure != Figure.O) {
                        enemyWin = false;
                    }
                }
            }
        }

        if (yourWin) {
            System.out.println("You WIN!!!");
        } else if (enemyWin) {
            System.out.println("You LOSE...");
        }

        return yourWin || enemyWin;
    }
}
