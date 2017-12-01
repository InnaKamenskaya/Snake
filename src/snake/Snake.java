package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kamenskaya on 10.06.2016.
 */
public class Snake implements ActionListener, KeyListener {

    public static Snake snake;
    public JFrame jframe;
    public RenderPanel renderPanel;

    public Timer timer = new Timer(100, this);
    public ArrayList<Point> snakeParts = new ArrayList<Point>();
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
    public int ticks = 0, direction = DOWN, score, tailLength = 5, time;
    public Point head, cherry;
    public Random random;
    public boolean over = false, paused;
    public Dimension dim;


    public Snake() {

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake");
        jframe.setVisible(true);
        jframe.setSize(805, 705);
        jframe.setResizable(false);
        jframe.addKeyListener(this);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new RenderPanel());
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        startGame();

    }

    public void startGame(){
        over = false;
        paused = false;
        score = 0;
        time = 0;
        tailLength = 5;
        direction = DOWN;
        snakeParts.clear();
        head = new Point(5, 5);
        random = new Random();
        cherry = new Point(random.nextInt(70),random.nextInt(60));
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        renderPanel.repaint();
        ticks++;
        if (ticks % 2 == 0 && head != null && !over && !paused){
            time++;
            snakeParts.add(new Point(head.x, head.y));

            if (direction == UP)
                if (head.y - 1 >= 0  && noTailAt(head.x, head.y - 1))
                    head = new Point(head.x, head.y - 1);
                else
                    over = true;

            if (direction == DOWN)
                if (head.y + 1 < 68 && noTailAt(head.x, head.y + 1))
                    head = new Point(head.x, head.y + 1);
                else
                    over = true;

            if (direction == LEFT)
                if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
                    head = new Point(head.x - 1, head.y);
                else
                    over = true;

            if (direction == RIGHT)
                if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
                    head = new Point(head.x + 1, head.y);
                else
                    over = true;

            }
        if (snakeParts.size() > tailLength){
            snakeParts.remove(0);

                if (cherry != null) {
                    if (head.equals(cherry)) {
                        cherry.setLocation(random.nextInt(70),random.nextInt(60));
                        score += 10;
                        tailLength++;
                    }
                }


            }

        }

    private boolean noTailAt(int x, int y){
        for (Point point : snakeParts){
            if (point.equals(new Point(x , y))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        snake = new Snake();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT && direction != RIGHT)
            direction = LEFT;
        if (i == KeyEvent.VK_RIGHT && direction != LEFT)
            direction = RIGHT;
        if (i == KeyEvent.VK_UP && direction != DOWN)
            direction = UP;
        if (i == KeyEvent.VK_DOWN && direction != UP)
            direction = DOWN;
        if (i == KeyEvent.VK_SPACE)
            if (over)
            startGame();
        else
                paused = !paused;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

