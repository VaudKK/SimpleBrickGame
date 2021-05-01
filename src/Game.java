import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {

    final int SCREEN_HEIGHT = 700;
    final int SCREEN_WIDTH = 1000;
    final int UNIT_SIZE = 10;
    final int BAR_LENGTH = 12;
    final int DELAY = 1;

    int barX = 500;
    int barY =  SCREEN_HEIGHT - UNIT_SIZE;

    int ballX;
    int ballY = 400;
    int vx = 1;
    int vy = 1;
    final int BALL_WIDTH = UNIT_SIZE * 2;
    final int BALL_HEIGHT = UNIT_SIZE * 2;

    final int BRICK_SIZE = UNIT_SIZE * 5;
    int brickRows = 12;
    int brickColumns = 4;
    Brick[][] bricks = new Brick[brickRows][brickColumns];

    Random random;
    boolean paused = false;

    Timer timer;

    public Game() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.addKeyListener(this);
        timer  = new Timer(DELAY,this);
        random = new Random();

        startGame();
    }

    private void startGame(){
        createBricks();
        ballX = random.nextInt(900) + 10;
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void createBricks(){
        //Create Bricks
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickColumns; j++) {
                bricks[i][j] = new Brick((i * BRICK_SIZE) + 100,(j * BRICK_SIZE) + 50,BRICK_SIZE,BRICK_SIZE);
            }
        }
    }

    private void draw(Graphics g){
        g.setColor(Color.GREEN);

        //Draw bar
        g.fillRect(barX,barY,BAR_LENGTH * UNIT_SIZE,UNIT_SIZE);

        g.setColor(Color.ORANGE);

        //Draw circle
        g.fillOval(ballX,ballY,BALL_WIDTH,BALL_HEIGHT);

        Graphics2D g2D = (Graphics2D)g;
        //Draw bricks
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickColumns; j++) {
                if(bricks[i][j].isVisible()){
                    g2D.setColor(Color.WHITE);
                    g2D.fillRect(bricks[i][j].getBrickX(),bricks[i][j].getBrickY(),bricks[i][j].getBrickWidth(),bricks[i][j].getBrickHeight());
                    g2D.setStroke(new BasicStroke(3));
                    g2D.setColor(Color.BLACK);
                    g2D.drawRect(bricks[i][j].getBrickX(),bricks[i][j].getBrickY(),bricks[i][j].getBrickWidth(),bricks[i][j].getBrickHeight());
                }
            }
        }
    }

    private void moveBar(char direction){
        switch (direction){
            case 'L':
                if(barX > 0){
                    barX = barX - UNIT_SIZE;
                }
                break;
            case 'R':
                if(barX < (SCREEN_WIDTH - (UNIT_SIZE * BAR_LENGTH))){
                    barX = barX + UNIT_SIZE;
                }
                break;
        }
    }

    private void moveBall(){

        //Check for intersection with the bar
        if(new Rectangle(ballX,ballY,BALL_WIDTH,BALL_HEIGHT).intersects(new Rectangle(barX,barY,BAR_LENGTH * UNIT_SIZE,UNIT_SIZE))){
            vy = -1;
        }

        //Right border
        if((ballX + UNIT_SIZE) == SCREEN_WIDTH){
            vx = -1;
        }

        //Top border
        if((ballY + UNIT_SIZE) == 0){
            vy = 1;
        }

        //Left border
        if((ballX + UNIT_SIZE) == 0){
            vx = 1;
        }

        //Check for intersections
        V: for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickColumns; j++) {
                if(bricks[i][j].isVisible()){
                    Rectangle ballBounds  =  new Rectangle(ballX,ballY,BALL_WIDTH,BALL_HEIGHT);
                    Rectangle brickBounds = new Rectangle(bricks[i][j].getBrickX(),bricks[i][j].getBrickY(),bricks[i][j].getBrickWidth(),bricks[i][j].getBrickHeight());

                    if(ballBounds.intersects(brickBounds)){
                        bricks[i][j].setVisible(false);

                        if(ballX + 1 >= (bricks[i][j].getBrickX() + BRICK_SIZE) || ballX - 1 <= bricks[i][j].getBrickX()){
                            vx *= -1;
                        }else{
                            vy *= -1;
                        }

                        break V;
                    }
                }
            }
        }

        ballX += vx;
        ballY += vy;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                moveBar('R');
                break;
            case KeyEvent.VK_LEFT:
                moveBar('L');
                break;
            case KeyEvent.VK_ENTER:
                paused = !paused;
                if(paused){
                    timer.stop();
                }else{
                    timer.start();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBall();
        repaint();
    }
}
