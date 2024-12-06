package dev.porthouse.solutions;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import dev.porthouse.BaseSolution;

public class Day6Solution extends BaseSolution {

    enum Entity {
        Empty,
        Object,
        GuardVisited
    }

    enum Direction {
        Up,
        Right,
        Down,
        Left;

        public Direction rotate() {
            switch (this) {
                case Up -> {
                    return Right;
                }
                case Right -> {
                    return Down;
                }
                case Down -> {
                    return Left;
                }
                default -> {
                    return Up;
                }
            }
        }
    }

    private Map<Point, Entity> map = new HashMap<>();
    private Integer mapWidth = 0;
    private Integer mapHeight = 0;

    private Direction guardDirection = Direction.Up;
    private Point guardPosition;

    public Day6Solution(String file) {
        super(file);

        var pos = new Object(){ Integer row = 0; };
        input.lines().forEach((line) -> {
            if (line.isBlank()) {
                return;
            }

            var cols = List.of(line.split(""));

            IntStream.range(0, cols.size()).forEach((col) -> {
                var token = cols.get(col);

                if (token.equals("#")) {
                    map.put(new Point(col, pos.row), Entity.Object);
                }

                if (token.equals("^")) {
                    guardPosition = new Point(col, pos.row);
                }
            });

            mapHeight += 1;
            mapWidth = line.length();

            pos.row += 1;
        });
        mapHeight = pos.row;
    }

    @Override
    public Integer runPart1() {
        do {
            var nextPos = getNextGuardPosition();
            var nextTile = map.getOrDefault(nextPos, Entity.Empty);

            if (nextTile.equals(Entity.Object)) {
                guardDirection = guardDirection.rotate();
                continue;
            }

            guardPosition = nextPos;
            map.put(guardPosition, Entity.GuardVisited);
        } while (!isNextPositionOutOfBounds());

        Integer total = 0;

        for (var row : map.entrySet()) {
            if (row.getValue().equals(Entity.GuardVisited)) {
                total += 1;
            }
        }

        return total;
    }

    @Override
    public Integer runPart2() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Point getNextGuardPosition() {

        switch (guardDirection) {
            case Direction.Up -> {
                return new Point(guardPosition.x, guardPosition.y - 1);
            }

            case Direction.Right -> {
                return new Point(guardPosition.x + 1, guardPosition.y);
            }

            case Direction.Down -> {
                return new Point(guardPosition.x, guardPosition.y + 1);
            }

            case Direction.Left -> {
                return new Point(guardPosition.x - 1, guardPosition.y);
            }
        }

        return guardPosition;
    }

    private Boolean isNextPositionOutOfBounds() {
        var pos = getNextGuardPosition();

        return pos.x < 0 || pos.x >= mapWidth || pos.y < 0 || pos.y >= mapHeight;
    }
}
