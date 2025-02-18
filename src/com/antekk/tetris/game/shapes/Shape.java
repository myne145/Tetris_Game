package com.antekk.tetris.game.shapes;

import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;

import static com.antekk.tetris.game.Shapes.getShadow;
import static com.antekk.tetris.game.Shapes.getStationaryShapes;


public abstract class Shape implements Cloneable, HeldShape, NextShape, ShadowShape {
    protected ArrayList<Point> collisionPoints;
    protected Color shapeColor;
    private boolean wasHardDropUsed = false;
    public boolean hasLanded = false;

    public void setDefaultValues() {
        collisionPoints = getDefaultCollisionPoints();
        shapeColor = getDefaultColor();
    }

    public abstract ArrayList<Point> getDefaultCollisionPoints();

    public abstract Color getDefaultColor();

    public Shape() {
        setDefaultValues();
    }

    public boolean rotateLeft() {
        reloadShadow();
        getShadow().rotate(-1);
        while(getShadow().moveDown());
        return rotate(-1);
    }

    public boolean rotateRight() {
        reloadShadow();
        getShadow().rotate(1);
        while(getShadow().moveDown());
        return rotate(1);
    }

    public boolean moveLeft() {
        return moveHorizontaly(-1);
    }

    public boolean moveRight() {
        return moveHorizontaly(1);
    }

    public void hardDrop() {
        wasHardDropUsed = true;
        while(moveVertically());
    }

    public boolean moveDown() {
        return moveVertically();
    }

    public void draw(Graphics g) {
        for(Point p : getCollisionPoints()) {
            if(p.y < 0)
                continue;

            //Fill
            g.setColor(this.getColor());
            g.fillRect(TetrisGamePanel.LEFT + p.x * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.TOP + p.y * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(TetrisGamePanel.LEFT + p.x * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.TOP + p.y * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx());


//            g.setColor(Color.BLACK);
//            for(Point collidingPoint : getCollisionsForPoint(p)) {
//                g.fillOval(TetrisGamePanel.LEFT + collidingPoint.x * Shapes.getBlockSizePx() - 8, TetrisGamePanel.TOP + collidingPoint.y * Shapes.getBlockSizePx() - 8, 16, 16);
//            }
        }
    }

    @Override
    public void reloadShadow() {
        int dx = this.getCenterPoint().x - getShadow().getCenterPoint().x;
        int dy = this.getCenterPoint().y - getShadow().getCenterPoint().y;
        getShadow().move(dx, dy);
    }

    @Override
    public void drawAsShadow(Graphics2D g) {
        //Border
        g.setColor(Color.BLACK);
        g.setStroke(ShadowShape.shadowShapeBorder);
        for(Point p : getCollisionPoints()) {
            if(p.y < 0)
                continue;

            g.drawRect(TetrisGamePanel.LEFT + p.x * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.TOP + p.y * TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx());
        }
        g.setStroke(ShadowShape.defaultBorder);
    }

    protected boolean handleWallKicks() {
        //TODO: not finished
        //Wall kicks
        int wallKickDistanceX = 0;
        int wallKickDistanceY = 0;
        for(Point p : getCollisionPoints()) {
            int distAbs = Math.abs(p.x);
            if(p.x < 0 && distAbs > wallKickDistanceX) {
                wallKickDistanceX = distAbs;
                continue;
            }

            if(p.x >= TetrisGamePanel.getBoardCols() && -(distAbs - TetrisGamePanel.getBoardCols() + 1) < wallKickDistanceX) {
                wallKickDistanceX = -(distAbs - TetrisGamePanel.getBoardCols() + 1);
            }

            if(p.y >= TetrisGamePanel.getBoardRows() - 1 && TetrisGamePanel.getBoardRows() - 1 - p.y < wallKickDistanceY) {
                wallKickDistanceY = TetrisGamePanel.getBoardRows() - 1 - p.y;
            }
        }

        move(wallKickDistanceX, wallKickDistanceY);

        ArrayList<Point> collisions = getCollisionsForPoint(getCenterPoint());
        int maxDistanceX = 0;
        int maxDistanceY = 0;

        int distanceX = 0;
        int distanceY = 0;

        for(Point p : collisions) {
            distanceX = getCenterPoint().x - p.x;
            distanceY = getCenterPoint().y - p.y;

            if(Math.abs(distanceX) > maxDistanceX) {
                maxDistanceX = Math.abs(distanceX);
            }

            if(distanceY < maxDistanceY) {
                maxDistanceY = distanceY;
            }
        }

        if(distanceX < 0)
            maxDistanceX *= -1;

        for(Point p : getCollisionPoints()) {
            if(p.x + maxDistanceX < 0)
                return false;
        }
        //TODO: max one vertical wall kick when shape lands

        move(maxDistanceX, maxDistanceY);
        return true;
    }

    /**
     *
     * @param direction 1 is right, -1 is left
     */
    protected boolean rotate(int direction) {
        Shape futureShape = (Shape) this.clone();

        //Shape rotation
        Point center = (Point) futureShape.getCenterPoint().clone();
        for(Point p : futureShape.getCollisionPoints()) {
            int temp = p.y;
            p.y = p.x;
            p.x = temp;
        }

        for(Point p : futureShape.getCollisionPoints()) {
            if(direction == 1) p.x = 3 - p.x;
            else if(direction == -1) p.y = 3 - p.y;
        }

        //Restoring original position
        int dx = center.x - futureShape.getCenterPoint().x;
        int dy = center.y - futureShape.getCenterPoint().y;

        futureShape.move(dx, dy);

        if(!futureShape.handleWallKicks())
            return false;


//        if(!futureShape.checkForCollisionsForShapeXAxis().isEmpty())
//            return false;

        this.collisionPoints = futureShape.collisionPoints;

        return true;
    }

    public ArrayList<Point> getCollisionsForPoint(Point p) {
        ArrayList<Point> points = new ArrayList<>();
        for(Shape shape : getStationaryShapes()) {
            if(shape == this)
                continue;

            for (Point pointToCheck : shape.getCollisionPoints()) {
//                if(p.x == pointToCheck.x && p.y - 1 == pointToCheck.y) {
//                    System.out.println("Collision at " + pointToCheck.x + ", " + pointToCheck.y + " for " + p.x + ", " + p.y);
//                    points.add(pointToCheck);
//                    continue;
//                }
                if ((p.x + 1 == pointToCheck.x || p.x - 1 == pointToCheck.x) && p.y == pointToCheck.y) {
//                    System.out.println("Collision at " + pointToCheck.x + ", " + pointToCheck.y + " for " + p.x + ", " + p.y);
                    points.add(pointToCheck);
                    continue;
                }

                if(p.x == pointToCheck.x && (p.y + 1 == pointToCheck.y || p.y - 1 == pointToCheck.y)) {
//                    System.out.println("Collision at " + pointToCheck.x + ", " + pointToCheck.y + " for " + p.x + ", " + p.y);
                    points.add(pointToCheck);
                }

                if (p.x - 1 == pointToCheck.x && p.y == pointToCheck.y) {
                    points.add(pointToCheck);
                }
            }
        }
        return points;
    }

    private ArrayList<Point> getCollisionsForShape() {
        ArrayList<Point> points = new ArrayList<>();
        for (Point p : getCollisionPoints()) {
            points.addAll(getCollisionsForPoint(p));
        }
        return points;
    }

    private ArrayList<Point> checkForCollisionsForShapeXAxis() {
        ArrayList<Point> points = new ArrayList<>();
        for(Point p : getCollisionPoints()) {
            points.addAll(getCollisionsForPoint(p));
        }

        return points;
    }

    private boolean checkForCollisionsForShapeYAxis(Shape shapeToCompare) {
        for(Point pointToCheck : shapeToCompare.getCollisionPoints()) {
            for (Point p : getCollisionPoints()) {
                if (p.y + 1 == pointToCheck.y && p.x == pointToCheck.x) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveHorizontaly(int amount) {
        for(Point p : getCollisionPoints()) {
            if((p.x == TetrisGamePanel.getBoardCols() - 1 && amount > 0) ||
                    (p.x == 0 && amount < 0)) {
                return false;
            }
        }

        if(!checkForCollisionsForShapeXAxis().isEmpty())
//        if(!getCollisionsForShape().isEmpty())
            return false;

        move(amount, 0);
        reloadShadow();
        while(getShadow().moveDown());

        return true;
    }

    public boolean moveVertically() {
        for(Point p : getCollisionPoints()) {
            if(p.y == TetrisGamePanel.getBoardRows() - 1) {
                return false;
            }
        }

        for(Shape shape : getStationaryShapes()) {
            if(checkForCollisionsForShapeYAxis(shape))
//            if(!shape.getCollisionsForShape().isEmpty())
                return false;
        }

        this.move(0,1);
        return true;
    }

    protected void move(int dx, int dy) {
        if(wasHardDropUsed)
            dx = 0;
        for(Point p : getCollisionPoints()) {
            p.translate(dx, dy);
        }
    }

    private void multiplyPoints() {
        setDefaultValues();
        for(Point p : getCollisionPoints()) {
            p.x *= TetrisGamePanel.getBlockSizePx();
            p.y *= TetrisGamePanel.getBlockSizePx();
        }
    }

    @Override
    public void setAsHeldShape() {
        multiplyPoints();
    }

    @Override
    public void setAsNextShape() {
        multiplyPoints();
    }

    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }

    public Color getColor() {
        return shapeColor;
    }

    public Point getCenterPoint() {
        return getCollisionPoints().getFirst();
    }

    public boolean wasHardDropUsed() {
        return wasHardDropUsed;
    }

    @Override
    public Object clone() {
        Shape shape;
        try {
            shape = (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        shape.shapeColor = getColor();
        shape.collisionPoints = new ArrayList<>();
        for(Point p : getCollisionPoints())
            shape.collisionPoints.add(new Point(p.x, p.y));

        return shape;
    }
}
