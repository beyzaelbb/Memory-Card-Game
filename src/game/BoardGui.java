package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardGui extends JFrame implements ActionListener {
	
	// level dan sonra menuye dön dropdown menu for exit

    JMenuItem restart = new JMenuItem("Restart");
    JMenuItem hs = new JMenuItem("High Scores");
    JMenuItem about_dev = new JMenuItem("About the Developer");
    JMenuItem about_game = new JMenuItem("About the Game");
    JMenuItem exit_menu = new JMenuItem("Exit to Menu");
    JMenuItem exit_desk = new JMenuItem("Exit to Desktop");
    
    private Game game;
    private ArrayList<Cards> cardButtons;
    private JLabel label;
    private int levelNo;
    private int tries;
    JPanel gridPanel = new JPanel();

    public BoardGui(Game game) {
        this.game = game;
        this.levelNo = game.getLevel();
        
        if (levelNo == 1) {
            tries = 18;
        } else if (levelNo == 2) {
            tries = 15;
            
        } else if (levelNo == 3) {
            tries = 12;
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 700);
        this.setLayout(new BorderLayout());
        
        label = new JLabel("Level: " + levelNo + " Tries left: " + tries, JLabel.CENTER);
        label.setFont(new Font("MV Boli", Font.BOLD, 40));
        label.setForeground(Color.white);
        if(levelNo == 1) {
        	label.setBackground(new Color(137, 207, 240	));
        }
        else if(levelNo == 2) {
        	label.setBackground(new Color(112, 41, 99));
        }
        else if(levelNo == 3) {
        	label.setBackground(new Color(210, 4, 45));
        }
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(75, 75));
        this.add(label, BorderLayout.NORTH);
        
        
        gridPanel.setLayout(new GridLayout(4, 4, 5, 5));
        printCards(gridPanel);
        this.add(gridPanel, BorderLayout.CENTER);
        
        JMenuBar menu = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        JMenu about = new JMenu("About");
        JMenu exit = new JMenu("Exit");
        
        restart.addActionListener(this);
        hs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Map.Entry<String, Integer>> highScores = ScoreBoard.getTop10Scores();
                StringBuilder sb = new StringBuilder("Top 10 High Scores:\n");
                for (Map.Entry<String, Integer> entry : highScores) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        about_dev.addActionListener(this);
        about_game.addActionListener(this);
        exit_menu.addActionListener(this);
        exit_desk.addActionListener(this);
        
        gameMenu.add(restart);
        gameMenu.add(hs);
        
        about.add(about_dev);
        about.add(about_game);
        
        exit.add(exit_menu);
        exit.add(exit_desk);
        
        
        menu.add(gameMenu);
        menu.add(about);
        menu.add(exit);
        
        
        
        this.setJMenuBar(menu);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            dispose();
            new BoardGui(new Game(1, 0));
        }
        
        if (e.getSource() == exit_desk) {
            System.exit(0);
        }
        
        if(e.getSource() == exit_menu) {
        	dispose();
        	new Menu();
        }
        
        if (e.getSource() == about_dev) {
            JOptionPane.showMessageDialog(null, "Developer Name: Beyza Elbeyoğlu \n Student no: 20220702104", "About Developer", JOptionPane.PLAIN_MESSAGE);
        }
        
        if (e.getSource() == about_game) {
            String str = "This is a memory card game where you will have to remember same cards places \n and select them in order to win! Good Luck :)";
            JOptionPane.showMessageDialog(null, str, "About Game", JOptionPane.PLAIN_MESSAGE);
        }
        
        for (Cards card : cardButtons) {
            if (e.getSource() == card) {
                if(card.getisFlipped() == true || game.getSelectionOne() == card) {
                    return;
                }
                
                card.flip(); // is flipped is true now  
                game.Match(card);
                if(game.isTryflag()) { // in every two cards
                    tries--;
                    updateLabel();
                    game.setTryflag(false);
                    if(tries <= 0) {
                    	JOptionPane.showMessageDialog(null, "You have lost the game :(", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
                    	new Game(1,0);
                    }
                }
            }
        }
    }

    private void updateLabel() {
        label.setText("Level: " + levelNo + " Tries left: " + tries);
        
    }

    void printCards(JPanel gridPanel) {
    	
        cardButtons = game.getCards();
        for (Cards card : cardButtons) {
            card.setPreferredSize(new Dimension(120, 120));
            card.addActionListener(this);
            gridPanel.add(card);
        }
    }

    public void initialShow() {
        for(Cards c : cardButtons) {
            c.flip();
        }
        
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(Cards c : cardButtons) {
                    c.flip();
                }
            }

        });
        
        timer.setRepeats(false);
        timer.start();
    }
    
    public void reprint() {
    	gridPanel.removeAll();
    	printCards(gridPanel);
    }
    
    
    
}
