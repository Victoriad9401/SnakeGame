import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameP extends JPanel implements ActionListener {
    private static final long serialVersionUID =1L;

    static final int WIDTH= 1000;
    static final int HEIGHT = 800;
    static final int UNIT_SIZE =20;
    static final int NUMBER_OF_UNIT = (WIDTH*HEIGHT)/(UNIT_SIZE  * UNIT_SIZE);

    //body part of snake's x & Y
    final int x[] = new int[NUMBER_OF_UNIT];
    final int y[] = new  int [NUMBER_OF_UNIT];

    int length= 5;
    int foodeaten;
    int foodx;
    int foody;
    char direct = 'D';
    boolean running = false;
    Random ran;
    Timer time;
private JButton playagain;

    GameP(){
        setLayout(new BorderLayout());
        ran = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        play();


        //play again button
        playagain = new JButton("Play Again???");
        playagain.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 20 ));
        playagain.setFocusable(false);
        playagain.addActionListener(e -> restartGame());
        playagain.setVisible(false);
        playagain.setBackground(Color.LIGHT_GRAY);



        this.add(playagain, BorderLayout.SOUTH);
        }

        public void play(){
        addFood();
        running = true;

        time = new Timer(90, this);
        time.start();

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (direct == 'L') {
            x[0] = x[0] - UNIT_SIZE;
        } else if (direct == 'R') {
            x[0] = x[0] + UNIT_SIZE;
        } else if (direct == 'U') {
            y[0] = y[0] - UNIT_SIZE;
        } else {
            y[0] = y[0] + UNIT_SIZE;

        }
    }
    public void checkfood(){
            if(x[0] == foodx && y[0] == foody){
                length++;
                foodeaten++;
                addFood();
            }
        }
    public void draw(Graphics g){
        if(running) {
            g.setColor(new Color(75, 212, 90));
            g.fillOval(foodx, foody, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.pink);
            g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            for (int i = 1; i < length; i++) {
                g.setColor(new Color(50, 20, 155));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
                g.setColor(Color.white);
                g.setFont(new Font("San serif", Font.ROMAN_BASELINE, 25));
                FontMetrics m = getFontMetrics(g.getFont());
                g.drawString("Your Score: " + foodeaten, (WIDTH - m.stringWidth("Score: " + foodeaten)) / 2, g.getFont().getSize());
            }
        else {
                gameOver(g);
            }
        }
        public void addFood() {
            foodx = ran.nextInt((WIDTH/UNIT_SIZE))* UNIT_SIZE;
            foody = ran.nextInt((HEIGHT /UNIT_SIZE))* UNIT_SIZE;
        }

        public void checkHit() {
            for (int i = length; i > 0; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    running = false;
                }
            }
            if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
                running = false;
            }
            if (!running) {
                time.stop();
            }
        }
         public void gameOver(Graphics g){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
            FontMetrics m = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", (WIDTH - m.stringWidth("GAME OVER")) /2, HEIGHT/2);

            g.setColor(Color.black);
            g.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
            m = getFontMetrics(g.getFont());
            g.drawString("Score: "+ foodeaten, (WIDTH - m.stringWidth("Score: " + foodeaten))/2, g.getFont().getSize());

             playagain.setVisible(true);



    }

    private void restartGame(){
        foodeaten =0;
        length = 5;
        direct='D';
        for(int i =0; i<length; i++){
            x[i] =0;
            y[i]=0;
        }

        playagain.setVisible(false);
        addFood();
        running = true;
        time = new Timer(90, this);
        repaint();
        play();
    }


    @Override
    public void actionPerformed(ActionEvent arg0){
        if(running){
            move();
            checkfood();
            checkHit();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if (direct != 'R') {
                    direct  = 'L';
                }
                break;

                case KeyEvent.VK_RIGHT:
                    if (direct != 'L') {
                        direct  = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direct != 'D') {
                        direct  = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direct != 'U') {
                        direct  = 'D';
                    }
                    break;

            }
        }
    }
}
