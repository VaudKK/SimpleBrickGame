public class Brick {
    private int brickWidth;
    private int brickHeight;
    private int brickX;
    private int brickY;
    private boolean visible = true;

    public Brick(int brickX,int brickY,int brickWidth,int brickHeight){
        this.brickX = brickX;
        this.brickY = brickY;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public void setBrickWidth(int brickWidth) {
        this.brickWidth = brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public void setBrickHeight(int brickHeight) {
        this.brickHeight = brickHeight;
    }

    public int getBrickX() {
        return brickX;
    }

    public void setBrickX(int brickX) {
        this.brickX = brickX;
    }

    public int getBrickY() {
        return brickY;
    }

    public void setBrickY(int brickY) {
        this.brickY = brickY;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
