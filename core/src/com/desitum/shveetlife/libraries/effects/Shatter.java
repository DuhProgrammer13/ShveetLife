package com.desitum.shveetlife.libraries.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kody on 5/15/15.
 * can be used by kody and people in []
 */
public class Shatter {

    private int splitAmount;
    private int timesToSplit;
    private float gravity;
    Texture shatterTexture;
    ArrayList<ShatteredPiece> pieces;

    public Shatter(Texture shatterTexture, int timesToSplit, int splitAmount, float gravity) {
        if (timesToSplit < 4) timesToSplit = 4;
        this.timesToSplit = timesToSplit;
        this.splitAmount = splitAmount;
        this.gravity = gravity;
        this.shatterTexture(shatterTexture, timesToSplit, splitAmount, new Vector2(shatterTexture.getWidth()/2, shatterTexture.getHeight()/2));
    }

    public Shatter(Texture shatterTexture, int timesToSplit, int splitAmount, float gravity, Vector2 shatterFrom) {
        this.timesToSplit = timesToSplit;
        this.splitAmount = splitAmount;
        this.gravity = gravity;
        this.shatterTexture(shatterTexture, timesToSplit, splitAmount, shatterFrom);
    }

    public void start() {

    }

    public void update() {

    }

    public void setRotationAmount(float rotationAmount) {

    }

    private ArrayList<Texture> shatterTexture(Texture textureToShatter, int insideTimesToSplit, int insideSplitAmount, Vector2 shatterFrom) {
        ArrayList<Texture> returnArray = new ArrayList<Texture>();
        returnArray.add(textureToShatter);

        if (insideTimesToSplit == 0){
            return returnArray;
        }

        for (int y = 0; y < insideTimesToSplit; y++) {
            ArrayList<Float> angles = new ArrayList<Float>();

            ArrayList<Texture> tempArray = new ArrayList<Texture>();
            for (int x = 0; x < insideSplitAmount; x++) {
                angles.add((x * 360/insideSplitAmount) + (float) Math.random() * (360 / insideSplitAmount));
            }

            Collections.sort(angles);

            ArrayList<PieceArea> pieces = new ArrayList<PieceArea>();

            float firstAngle = angles.get(0);

            for (int x = 0; x < angles.size() - 1; x++) {
                pieces.add(buildArea(angles.get(x), angles.get(x + 1), shatterFrom, textureToShatter, insideTimesToSplit));
            }

            pieces.add(buildArea(angles.get(angles.size() - 1), firstAngle, shatterFrom, textureToShatter, insideTimesToSplit));

            if (insideTimesToSplit > 1){
                for (Texture text: shatterTexture(textureToShatter, insideTimesToSplit - 1, insideSplitAmount, shatterFrom, textureToShatter));
            }
        }
        return returnArray;
    }

    private PieceArea buildArea(float angle1, float angle2, Vector2 shatterFrom, Texture shatterableTexture, int insideTimesToSplit) {
        boolean greaterThan1 = false;
        boolean greaterThan2 = false;

        if (angle1 < 90) {
            greaterThan1 = true;
            if (angle2 > 90) greaterThan2 = true;
        }
        if (angle1 > 90 && angle1 < 270) {
            if (angle2 < 270) greaterThan2 = true;
        }
        if (angle1 > 270) {
            greaterThan1 = true;
        }

        return new PieceArea(angle1, greaterThan1, angle2, greaterThan2, shatterFrom.x, shatterFrom.y, shatterableTexture);
    }

    private class ShatteredPiece extends Sprite {

        public ShatteredPiece(Texture texture, float gravity, float rotation, float speedX, float speedY) {

        }
    }

    private class PieceArea {

        private float averageAngle;
        private float angle1;
        private float slope1;
        private boolean greaterThanAngle1;
        private float angle2;
        private float slope2;
        private boolean greaterThanAngle2;
        private float horizontalShift;
        private float verticalShift;

        private Rectangle boundingRect;
        private float textureWidth;
        private float textureHeight;
        private Pixmap pixmap;

        public PieceArea(float angle1, boolean greaterThan1, float angle2, boolean greaterThanAngle2,
                         float horizontalShift, float verticalShift, Texture text) {
            // By personal preference I want no flat lines
            if (angle1 == 90 || angle1 == 180 || angle1 == 270 || angle1 == 360) {
                angle1 -= 0.1f;
            }
            if (angle2 == 90 || angle2 == 180 || angle2 == 270 || angle2 == 360) {
                angle2 -= 0.1f;
            }
            this.averageAngle = (angle1 + angle2) / 2;
            this.angle1 = angle1;
            this.slope1 = (float) Math.tan(angle1);
            this.greaterThanAngle1 = greaterThan1;
            this.angle2 = angle2;
            this.slope2 = (float) Math.tan(angle2);
            this.greaterThanAngle2 = greaterThanAngle2;
            this.horizontalShift = horizontalShift;
            this.verticalShift = verticalShift;

            this.boundingRect = buildRect();
            this.textureWidth = text.getWidth();
            this.textureHeight = text.getHeight();
            this.pixmap = new Pixmap(getWidth(), getHeight(), Pixmap.Format.RGBA8888);
        }

        public void setPixelAt(int x, int y, Color color) {
            pixmap.setColor(color);
            pixmap.drawPixel(x, y);
        }

        public boolean isInArea(Vector2 pos) {
            boolean inArea = true;
            if (greaterThanAngle1) {
                if (pos.y <= f1(pos.x)) {
                    inArea = false;
                }
            } else {
                if (pos.y >= f1(pos.x)) {
                    inArea = false;
                }
            }
            if (greaterThanAngle2) {
                if (pos.y <= f2(pos.x)) {
                    inArea = false;
                }
            } else {
                if (pos.y >= f2(pos.x)) {
                    inArea = false;
                }
            }
            return inArea;
        }

        private Rectangle buildRect() {
            float minX = horizontalShift;
            float maxX = horizontalShift;
            float minY = verticalShift;
            float maxY = verticalShift;

            if (angle1 > 90 && angle1 < 270) {
                if (f1(0) < minY) minY = f1(0);
                if (f1(0) > maxY) maxY = f1(0);
            } else if (angle1 < 90 || angle1 > 270) {
                if (f1(textureWidth) < minY) minY = f1(textureWidth);
                if (f1(textureWidth) > maxY) maxY = f1(textureWidth);
            }
            if (angle2 > 90 && angle2 < 270) {
                if (f2(0) < minY) minY = f2(0);
                if (f2(0) > maxY) maxY = f2(0);
            } else if (angle2 < 90 || angle2 > 270) {
                if (f2(textureWidth) < minY) minY = f1(textureWidth);
                if (f2(textureWidth) > maxY) maxY = f1(textureWidth);
            }

            if (angle1 < 180) {
                if (g1(textureHeight) < minX) minX = g1(textureHeight);
                if (g1(textureHeight) > maxX) maxX = g1(textureHeight);
            } else {
                if (g1(0) < minX) minX = g1(0);
                if (g1(0) > maxX) maxX = g1(0);
            }
            if (angle2 < 180) {
                if (g2(textureHeight) < minX) minX = g2(textureHeight);
                if (g2(textureHeight) > maxX) maxX = g2(textureHeight);
            } else {
                if (g2(0) < minX) minX = g2(0);
                if (g2(0) > maxX) maxX = g2(0);
            }

            if (minY < 0) minY = 0;
            if (minX < 0) minX = 0;

            return new Rectangle(minX, minY, maxX - minX, maxY - minY);
        }

        public Rectangle getBoundingRect() {
            return boundingRect;
        }

        public int getHeight() {
            return (int) boundingRect.getHeight();
        }

        public int getWidth() {
            return (int) boundingRect.getWidth();
        }

        private float f1(float x) {
            return slope1 * (x - horizontalShift) + verticalShift;
        }

        private float g1(float y) {
            return (verticalShift - y) / -slope1 + horizontalShift;
        }

        private float f2(float x) {
            return slope1 * (x - horizontalShift) + verticalShift;
        }

        private float g2(float y) {
            return (verticalShift - y) / -slope1 + horizontalShift;
        }

        public Pixmap getPixmap() {
            return pixmap;
        }

        public Texture getTexture() {
            Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
            returnTexture.draw(pixmap, 0, 0);
            return returnTexture;
        }
    }
}
