import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Kalk implements ActionListener, KeyListener
{
    JTextField t1;
    JButton[] numericButtons = new JButton[9];
    JButton b0;
    JButton bplus,bminus,bmul,bdiv,bpercent,bsqrt,bpow,bequal,bdot,bclear,bclearTextField;
    JButton memPlus,memMinus,memClear,memLoad;
    String stringToHistory="";
    int lettersToDelete;

    double x, buf, memory, percentBuf;
    String mathOperation = "null";
    boolean dotted = false;
    boolean mathError = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_0) t1.setText(t1.getText()+"0");
        if(key == KeyEvent.VK_1) t1.setText(t1.getText()+"1");
        if(key == KeyEvent.VK_2) t1.setText(t1.getText()+"2");
        if(key == KeyEvent.VK_3) t1.setText(t1.getText()+"3");
        if(key == KeyEvent.VK_4) t1.setText(t1.getText()+"4");
        if(key == KeyEvent.VK_5) t1.setText(t1.getText()+"5");
        if(key == KeyEvent.VK_6) t1.setText(t1.getText()+"6");
        if(key == KeyEvent.VK_7) t1.setText(t1.getText()+"7");
        if(key == KeyEvent.VK_8) t1.setText(t1.getText()+"8");
        if(key == KeyEvent.VK_9) t1.setText(t1.getText()+"9");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource();

        /**
         * Check if numeric button (1-9) has been pressed
         */
        for (JButton button : numericButtons)
        {
            if (target == button)
            {
                t1.setText(t1.getText()+((JButton)target).getText());
                t1.requestFocus();
                stringToHistory += t1.getText().substring(t1.getText().length()-1);
            }
        }

        /**
         * Check if numeric button (0) or operator button has been pressed
         */
        if(target == b0)
        {
            t1.setText(t1.getText()+((JButton)target).getText());
            t1.requestFocus();
            stringToHistory += "0";
        }
        else if(target==bdot)
        {
            if(!dotted)
            {
                t1.setText(t1.getText()+((JButton)target).getText());
                t1.requestFocus();
                stringToHistory += ".";
                dotted = true;
            }
        }
        else if(target == bclear)
        {
            buf=0;
            t1.setText("");
            t1.requestFocus();
        }
        else if(target==bclearTextField)
        {
            lettersToDelete = t1.getText().length();
            stringToHistory = stringToHistory.substring(0,stringToHistory.length()-lettersToDelete);
            t1.setText("");
            t1.requestFocus();
        }
        else if(target==bplus || target==bminus || target == bmul || target == bdiv || target == bpercent ||
                target == bpow)
        {
            if(target==bplus) {mathOperation="bplus"; stringToHistory += "+";}
            if(target==bminus) {mathOperation="bminus"; stringToHistory += "-";}
            if(target==bmul) {mathOperation="bmul"; stringToHistory += "*";}
            if(target==bdiv) {mathOperation="bdiv"; stringToHistory += "/";}
            if(target==bpercent) {mathOperation="bpercent"; stringToHistory += "%";}
            if(target==bpow) {mathOperation="bpow"; stringToHistory += "^";}

            if(target==bpercent) {
                /**
                 * first number from user is stored under 'percentBuf' variable.
                 * percent computed into double value is stored under 'percentValue' variable
                 * 100*40% = 100*0.4 = percentBuf*percentValue(it's computing after pressing button(=))
                 *
                 * 'percentValue' is a mathematical expression of %: 40%=0.4
                 */
                percentBuf = buf;
                buf=Double.parseDouble(t1.getText());
                double percentValue = Double.parseDouble(t1.getText())/100;
                t1.setText(Double.toString(percentValue));
            }
            else{
                buf=Double.parseDouble(t1.getText());
                t1.setText("");
            }
            t1.requestFocus();
            dotted = false;
        }

        else if(target==bsqrt)
        {
            buf = Double.parseDouble(t1.getText());

            //sqrt of x<0 doesn't exist
            if(buf>=0){
                x=Math.sqrt(buf);
                t1.setText(Double.toString(x));
                stringToHistory = "√" + stringToHistory + "=" + x + "\n";
            }
            else{
                t1.setText("Error");
                stringToHistory += "=Error: sqrt(x<0)";
            }
            t1.requestFocus();

            //save operation to history file
            try {
                FileWriter fw = new FileWriter("history.txt", true);
                BufferedWriter file = new BufferedWriter(fw);

                System.out.println(stringToHistory);
                file.write(stringToHistory);
                file.close();
                stringToHistory = "";
            }catch(IOException exc) {

            }
        }

        else if(target== bequal || target==t1)
        {
            x=Double.parseDouble(t1.getText());
            if(mathOperation.equals("bplus")) x=buf+x;
            if(mathOperation.equals("bminus")) x=buf-x;
            if(mathOperation.equals("bmul")) x=buf*x;
            if(mathOperation.equals("bpercent")) x=percentBuf*x;
            if(mathOperation.equals("bpow")) x=Math.pow(buf,x);
            if(mathOperation.equals("bdiv")){

                //Dividing by zero
                if(x!=0){
                    x=buf/x;
                }
                else{
                    mathError = true;
                    t1.setText("Error");
                    t1.requestFocus();
                    stringToHistory += "="+t1.getText()+"\n";
                }
            }
            if(!mathError)
            {
                t1.setText(Double.toString(x));
                t1.requestFocus();
                stringToHistory += "="+t1.getText()+"\n";
            }

            //save operation to history file
            try {
                FileWriter fw = new FileWriter("history.txt", true);
                BufferedWriter file = new BufferedWriter(fw);

                System.out.println(stringToHistory);
                file.write(stringToHistory);
                file.close();
                stringToHistory = "";
            }catch(IOException exc) {

            }
        }
        /**
         * Calculator memory handling
         */
        else if(target == memClear)
        {
            memory=0;
        }
        else if(target == memLoad)
        {
            t1.setText(Double.toString(memory));
        }
        else if(target == memPlus)
        {
            x=Double.parseDouble(t1.getText());
            memory=memory+x;
        }
        else if(target == memMinus)
        {
            x=Double.parseDouble(t1.getText());
            memory=memory-x;
        }
    }

    void init()
    {
        //try
        //{
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //}
        //catch(Exception e){}

        JFrame f=new JFrame();
        f.setFocusable(true); //This needs to be true to use KeyListener on this element
        f.addKeyListener(this);
        Container c=f.getContentPane();

        GridBagLayout gbl=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        c.setLayout(gbl);

        /**
         * Initializing text field
         */
        t1=new JTextField(15);
        t1.setEditable(false);
        //t1.addActionListener(this);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=5;
        gbc.ipadx=0;
        gbc.ipady=5;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(t1,gbc);
        c.add(t1);

        /**
         * Initializing buttons for numbers 1-9
         */
        int posX;
        int posY = 0;
        for(int i=0; i<9; i++)
        {
            posX = (i%3);
            if((i%3) == 0){
                posY++;
            }

            numericButtons[i]=new JButton(String.valueOf(i+1));
            numericButtons[i].addActionListener(this);
            numericButtons[i].setFocusable(false);
            gbc.gridx=posX ;
            gbc.gridy=posY;
            gbc.gridwidth=1;
            gbc.ipadx=0;
            gbc.ipady=0;
            gbc.insets=new Insets(5,5,0,0);
            gbl.setConstraints(numericButtons[i],gbc);
            c.add(numericButtons[i]);
        }

        /**
         * Initializing the rest of buttons
         */

        //button CE (clear text field)
        bclearTextField = new JButton("CE");
        bclearTextField.setToolTipText("wyczysc pole tekstowe");
        bclearTextField.addActionListener(this);
        bclearTextField.setFocusable(false);
        gbc.gridx=5;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,10,0,5);
        gbl.setConstraints(bclearTextField,gbc);
        c.add(bclearTextField);

        //button C (clear)
        bclear = new JButton("C");
        bclear.setToolTipText("clear");
        bclear.addActionListener(this);
        bclear.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bclear,gbc);
        c.add(bclear);

        //button 0
        b0=new JButton("0");
        b0.addActionListener(this);
        b0.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(b0,gbc);
        c.add(b0);

        //button dot (.)
        bdot=new JButton(".");
        bdot.addActionListener(this);
        bdot.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bdot,gbc);
        c.add(bdot);

        //button plus (+)
        bplus=new JButton("+");
        bplus.addActionListener(this);
        bplus.setFocusable(false);
        bplus.setToolTipText("dodawanie");
        gbc.gridx=3;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bplus,gbc);
        c.add(bplus);

        //button minus (-)
        bminus=new JButton("-");
        bminus.addActionListener(this);
        bminus.setFocusable(false);
        bminus.setToolTipText("odejmowanie");
        gbc.gridx=4;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bminus,gbc);
        c.add(bminus);

        //button multiply (*)
        bmul=new JButton("*");
        bmul.addActionListener(this);
        bmul.setFocusable(false);
        bmul.setToolTipText("mnozenie");
        gbc.gridx=3;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bmul,gbc);
        c.add(bmul);

        //button divide (/)
        bdiv=new JButton("/");
        bdiv.addActionListener(this);
        bdiv.setFocusable(false);
        bdiv.setToolTipText("mnozenie");
        gbc.gridx=4;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bdiv,gbc);
        c.add(bdiv);

        //button percent (%)
        bpercent=new JButton("%");
        bpercent.addActionListener(this);
        bpercent.setFocusable(false);
        bpercent.setToolTipText("percent");
        gbc.gridx=3;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bpercent,gbc);
        c.add(bpercent);

        //button sqrt (√)
        bsqrt=new JButton("√");
        bsqrt.addActionListener(this);
        bsqrt.setFocusable(false);
        bsqrt.setToolTipText("sqrt");
        gbc.gridx=4;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bsqrt,gbc);
        c.add(bsqrt);

        //button power (^)
        bpow=new JButton("^");
        bpow.addActionListener(this);
        bpow.setFocusable(false);
        bpow.setToolTipText("sqrt");
        gbc.gridx=3;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bpow,gbc);
        c.add(bpow);

        //button equal (=)
        bequal=new JButton("=");
        bequal.addActionListener(this);
        bequal.setFocusable(false);
        bequal.setToolTipText("wykonaj działanie");
        gbc.gridx=4;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bequal,gbc);
        c.add(bequal);

        //button memoryClear (MC)
        memClear=new JButton("MC");
        memClear.addActionListener(this);
        memClear.setFocusable(false);
        memClear.setToolTipText("wyczysc pamiec");
        gbc.gridx=5;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,10,0,5);
        gbl.setConstraints(memClear,gbc);
        c.add(memClear);

        //button memoryLoad (MR)
        memLoad=new JButton("MR");
        memLoad.addActionListener(this);
        memLoad.setFocusable(false);
        memLoad.setToolTipText("Wycofaj z pamieci");
        gbc.gridx=5;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,10,0,5);
        gbl.setConstraints(memLoad,gbc);
        c.add(memLoad);

        //button memoryPlus (M+)
        memPlus=new JButton("M+");
        memPlus.addActionListener(this);
        memPlus.setFocusable(false);
        memPlus.setToolTipText("dodaj do pamieci");
        gbc.gridx=5;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,10,0,5);
        gbl.setConstraints(memPlus,gbc);
        c.add(memPlus);

        //button memoryMinus (M-)
        memMinus=new JButton("M-");
        memMinus.addActionListener(this);
        memMinus.setFocusable(false);
        memMinus.setToolTipText("odejmij z pamieci");
        gbc.gridx=5;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,10,5,5);
        gbl.setConstraints(memMinus,gbc);
        c.add(memMinus);



        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Kalk");
        f.setVisible(true);
    }

    public static void main(String[] args)
    {
        //do wersji 1.4
        //new Kalk().init();

        //od wersji 1.5
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Kalk().init();
            }
        });
    }
}