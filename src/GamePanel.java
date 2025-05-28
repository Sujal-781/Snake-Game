import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {

    static final int SCREN_WIDTH=600;
    static final int SCREN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREN_WIDTH*SCREN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=100;  //Speed of snake
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;  //initial body parts of snake
    int applesEaten=0;  //initial apple eaten count is 0
    int appleX;    //x coordinate of the apple
    int appleY;    //y coordinate of the apple
    //both appear randomly

    char direction='R';   //Direction of snake movement
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){

        random=new Random();
        this.setPreferredSize(new Dimension(SCREN_WIDTH,SCREN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame(){
        newApple();
        running=true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running) {
            /*
            //grid lines
            for (int i = 0; i < SCREN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREN_WIDTH, i * UNIT_SIZE);
            }
             */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //drawing snake body
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);

            g.setFont(new Font("MV BOLI",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());  //Displaying font in the middle
            g.drawString("Score: "+applesEaten,(SCREN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2 ,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){     //to generate new apple coordinates
        appleX= random.nextInt((int)SCREN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY= random.nextInt((int)SCREN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        //This checks if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                running = false;
            }
        }
        if (x[0] < 0 || x[0] >= SCREN_WIDTH) running = false;
        if (y[0] < 0 || y[0] >= SCREN_HEIGHT) running = false;

        if(!running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g){

        //score at game over
        g.setColor(Color.red);
        g.setFont(new Font("MV BOLI",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());  //Displaying font in the middle
        g.drawString("Score: "+applesEaten,(SCREN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2 ,g.getFont().getSize());


        //game over text
        g.setColor(Color.red);
        g.setFont(new Font("MV BOLI",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());  //Displaying font in the middle
        g.drawString("Game Over",(SCREN_WIDTH - metrics1.stringWidth("Game Over"))/2 ,SCREN_HEIGHT/2);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }



    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
