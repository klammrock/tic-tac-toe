package name.klamm;

public class Field {
    private final Figure[][] figures = new Figure[3][3];

    public Field() {
//        for (Figure[] figureArray : figures) {
//            for (Figure figure : figureArray) {
//                System.out.println(figure);
//            }
//        }
    }

    public Figure[][] getFigures() {
        return figures;
    }

    public void set(int row, int column, Figure figure) {
        if (figures[row][column] != null) {
            throw new FieldAlreadyUsedException();
        }
        figures[row][column] = figure;
    }
}
