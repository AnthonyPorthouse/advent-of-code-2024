package dev.porthouse.solutions;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dev.porthouse.BaseSolution;

public class Day6Solution extends BaseSolution {

    public static final Logger LOGGER = Logger.getLogger(Day2Solution.class.getName());

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
    private Map<Point, List<Direction>> guardPath = new HashMap<>();
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

                    guardPath.put(guardPosition, new ArrayList<>());
                    guardPath.get(guardPosition).add(Direction.Up);
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
            var nextPos = getNextGuardPosition(guardPosition);
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
        Set<Point> newObjects = new HashSet<>();

        do {
            var nextPos = getNextGuardPosition(guardPosition);
            var nextTile = map.getOrDefault(nextPos, Entity.Empty);

            if (nextTile.equals(Entity.Object)) {
                guardDirection = guardDirection.rotate();
                continue;
            }

            guardPosition = nextPos;

            var posPoint = guardPath.getOrDefault(guardPosition, new ArrayList<>());
            posPoint.add(guardDirection);

            LOGGER.info(guardPosition.toString());

            // Check if we can trigger looping
            if (objectCausesLoop(guardPath.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> new ArrayList<>(e.getValue()))), new Point(guardPosition), guardDirection.rotate())) {
                newObjects.add(getNextGuardPosition(guardPosition));
            }

            guardPath.put(guardPosition, posPoint);
        } while (!isNextPositionOutOfBounds());

        return newObjects.size();
    }

    private Point getNextGuardPosition(Point position) {
        return getNextGuardPosition(position, guardDirection);
    }

    private Point getNextGuardPosition(Point position, Direction direction) {
        switch (direction) {
            case Direction.Up -> {
                return new Point(position.x, position.y - 1);
            }

            case Direction.Right -> {
                return new Point(position.x + 1, position.y);
            }

            case Direction.Down -> {
                return new Point(position.x, position.y + 1);
            }

            case Direction.Left -> {
                return new Point(position.x - 1, position.y);
            }
        }

        return guardPosition;
    }

    private Boolean objectCausesLoop(Map<Point, List<Direction>> curPath, Point position, Direction direction) {
        do {
            var dirs = curPath.getOrDefault(position, new ArrayList<>());
            dirs.add(direction);
            curPath.put(position, dirs);

            var nextPos = getNextGuardPosition(position, direction);

            if (map.getOrDefault(nextPos, Entity.Empty).equals(Entity.Object)) {
                direction = direction.rotate();
                continue;
            }

            if (curPath.getOrDefault(nextPos, new ArrayList<>()).contains(direction)) {
                return true;
            }

            position = nextPos;
        } while (!isNextPositionOutOfBounds(position, direction));

        return false;
    }

    private Boolean isNextPositionOutOfBounds() {
        return isNextPositionOutOfBounds(guardPosition, guardDirection);
    }

    private Boolean isNextPositionOutOfBounds(Point position, Direction direction) {
        var pos = getNextGuardPosition(position, direction);

        return pos.x < 0 || pos.x >= mapWidth || pos.y < 0 || pos.y >= mapHeight;
    }
}
