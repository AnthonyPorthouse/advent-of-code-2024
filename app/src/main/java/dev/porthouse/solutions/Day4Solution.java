package dev.porthouse.solutions;

import java.util.List;

import dev.porthouse.BaseSolution;

public class Day4Solution extends BaseSolution {

    private List<List<String>> board;

    public Day4Solution(String file) {
        super(file);

        buildBoard();
    }

    @Override
    public Integer runPart1() {
        Integer matchCount = 0;

        for (int y = 0; y < board.size(); y++) {
            var row = board.get(y);
            for (int x = 0; x < row.size(); x++) {
                var cell = row.get(x);

                if (cell.equals("X")) {
                    matchCount += checkCardinalDirections(x, y);
                }
            }
        }

        return matchCount;
    }

    @Override
    public Integer runPart2() {
        Integer matchCount = 0;

        for (int y = 0; y < board.size(); y++) {
            var row = board.get(y);
            for (int x = 0; x < row.size(); x++) {
                var cell = row.get(x);

                if (cell.equals("A")) {
                    matchCount += findXMas(x, y) ? 1 : 0;
                }
            }
        }

        return matchCount;
    }

    private void buildBoard() {
        board = input.lines().map((line) -> List.of(line.split(""))).toList();
    }

    private Integer checkCardinalDirections(Integer x, Integer y) {
        Integer matches = 0;
        matches += checkDirectionForMatch(x, y, 1, 0) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, -1, 0) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, 0, 1) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, 0, -1) ? 1 : 0;

        matches += checkDirectionForMatch(x, y, 1, 1) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, -1, 1) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, 1, -1) ? 1 : 0;
        matches += checkDirectionForMatch(x, y, -1, -1) ? 1 : 0;

        return matches;
    }

    private Boolean checkDirectionForMatch(Integer x, Integer y, Integer xMod, Integer yMod) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (int i = 0; i < 4; i++) {
                int xPos = x + (xMod * i);
                int yPos = y + (yMod * i);

                stringBuilder.append(board.get(yPos).get(xPos));
            }
            if (stringBuilder.toString().equals("XMAS")) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }

    private Boolean findXMas(Integer x, Integer y) {
        try {
            return
            (board.get(y - 1).get(x - 1).equals("M") && board.get(y + 1).get(x + 1).equals("S") ||
            board.get(y - 1).get(x - 1).equals("S") && board.get(y + 1).get(x + 1).equals("M")) &&
            (board.get(y - 1).get(x + 1).equals("M") && board.get(y + 1).get(x - 1).equals("S") ||
            board.get(y - 1).get(x + 1).equals("S") && board.get(y + 1).get(x - 1).equals("M"));
        } catch (IndexOutOfBoundsException e) {
        }

        return false;
    }
}
