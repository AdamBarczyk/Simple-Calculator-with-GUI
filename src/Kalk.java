import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Kalk implements ActionListener
{
    JTextField t1;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    JButton[] numericButtons = {b1,b2,b3,b4,b5,b6,b7,b8,b9};
    JButton bplus,bminus,brow,bdot;

    double x,buf;
    String mathOperation="null";
    boolean dotted = false;

    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource();

        if(target==numericButtons[0] || target==b2 || target==b3 || target==b4 || target==b5 ||
                target==b6 || target==b7 || target==b8 || target==b9 || target==b0)
        {
            t1.setText(t1.getText()+((JButton)target).getText());
            t1.requestFocus();
        }

        else if(target==bdot)
        {
            if(!dotted)
            {
                t1.setText(t1.getText()+((JButton)target).getText());
                t1.requestFocus();
                dotted = true;
            }
        }

        else if(target==bplus || target==bminus)
        {
            if(target==bplus) mathOperation="bplus";
            if(target==bminus) mathOperation="bminus";

            buf=Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            dotted = false;
        }

        else if(target==brow || target==t1)
        {
            x=Double.parseDouble(t1.getText());
            if(mathOperation.equals("bplus")) x=buf+x;
            if(mathOperation.equals("bminus")) x=buf-x;
            t1.setText(Double.toString(x));
            t1.requestFocus();
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
        Container c=f.getContentPane();

        GridBagLayout gbl=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        c.setLayout(gbl);



        t1=new JTextField(15);
        t1.addActionListener(this);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=5;
        gbc.ipadx=0;
        gbc.ipady=5;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(t1,gbc);
        c.add(t1);

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
        b1=new JButton("1");
        b1.addActionListener(this);
        b1.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b1,gbc);
        c.add(b1);

        b2=new JButton("2");
        b2.addActionListener(this);
        b2.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b2,gbc);
        c.add(b2);

        b3=new JButton("3");
        b3.addActionListener(this);
        b3.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b3,gbc);
        c.add(b3);

        b4=new JButton("4");
        b4.addActionListener(this);
        b4.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b4,gbc);
        c.add(b4);

        b5=new JButton("5");
        b5.addActionListener(this);
        b5.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b5,gbc);
        c.add(b5);

        b6=new JButton("6");
        b6.addActionListener(this);
        b6.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b6,gbc);
        c.add(b6);

        b7=new JButton("7");
        b7.addActionListener(this);
        b7.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b7,gbc);
        c.add(b7);

        b8=new JButton("8");
        b8.addActionListener(this);
        b8.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b8,gbc);
        c.add(b8);

        b9=new JButton("9");
        b9.addActionListener(this);
        b9.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b9,gbc);
        c.add(b9);
        */
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

        bminus=new JButton("-");
        bminus.addActionListener(this);
        bminus.setFocusable(false);
        bminus.setToolTipText("odejmowanie");
        gbc.gridx=4;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(bminus,gbc);
        c.add(bminus);



        brow=new JButton("=");
        brow.addActionListener(this);
        brow.setFocusable(false);
        brow.setToolTipText("wykonaj działanie");
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(brow,gbc);
        c.add(brow);



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