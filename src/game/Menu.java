package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Menu extends JFrame implements ActionListener {
    
    JButton button1 = new JButton();
    JButton button2 = new JButton();
    JButton button3 = new JButton();
    JButton button4 = new JButton();
    
    
    public Menu() {
        
        ImageIcon image = new ImageIcon("src/assets/background.jpg");
        
        JLabel background = new JLabel(image);
        background.setLayout(null);
        background.setOpaque(true);
        
        JLabel textLabel = new JLabel("Memory Card Game");
        textLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));
        textLabel.setForeground(Color.cyan);
        textLabel.setBounds(200, 0, 400, 400);
       
        button1.setFont(new Font("MV Boli", Font.BOLD, 10));
        button1.setText("Start Game");
        button1.setBounds(320, 250, 120, 30);
        button1.addActionListener(this);

        button2.setFont(new Font("MV Boli", Font.BOLD, 10));
        button2.setText("Select Level");
        button2.setBounds(320, 300, 120, 30);
        button2.addActionListener(this);
        
        button3.setFont(new Font("MV Boli", Font.BOLD, 10));
        button3.setText("Instructions");
        button3.setBounds(320, 350, 120, 30);
        button3.addActionListener(this);
        
        button4.setFont(new Font("MV Boli", Font.BOLD, 10));
        button4.setText("Exit");
        button4.setBounds(320, 400, 95, 30);
        button4.addActionListener(this);
        
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        background.add(textLabel);
        background.add(button1);
        background.add(button2);
        background.add(button3);
        background.add(button4);
        
        this.add(background);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            dispose();
            new Game(1, 0);
        }
        
        if (e.getSource() == button2) {
            String[] options = {"Level 1", "Level 2", "Level 3"};
            int level = JOptionPane.showOptionDialog(this, "Select a level to start", "Select Level",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            dispose();
            new Game(level+1, 0);
            

        }
        
        if (e.getSource() == button3) {
            String msg = "Instructions: " + "\n" + "There are 3 levels in the game. It gets gradually harder!" + "\n" + "Match all pairs of cards to win!";
            JOptionPane.showConfirmDialog(this, msg, "Message", JOptionPane.PLAIN_MESSAGE);
        }
        
        if (e.getSource() == button4) {
            System.exit(0);
        }
    }

}
