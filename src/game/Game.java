package game;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    
    private ArrayList<Cards> cards;
    private Cards selectionOne, selectionTwo;  
    private int matchCount = 0; 
    private int score = 0;
    private int level;
    private boolean tryflag;
    private BoardGui bg;
    
    
    public Game(int level, int score) {
        this.level = level;
        cards = new ArrayList<>();  
        initializeCards();
        shuffleCards();
        bg = new BoardGui(this);
        this.score = score;
        bg.initialShow();
    }

    private void initializeCards() {
        cards.clear(); // Clear the previous cards if any
        if (level == 1) {
            for (int i = 0; i < 16; i++) {
                cards.add(new Cards(i / 2, new ImageIcon("src/assets/Level1-InternetAssets/no_image.png"), new ImageIcon("src/assets/Level1-InternetAssets/" + (i / 2) + ".png")));
            }
        } else if (level == 2) {
            for (int i = 0; i < 16; i++) {
                cards.add(new Cards(i / 2, new ImageIcon("src/assets/Level2-CyberSecurityAssets/no_image.png"), new ImageIcon("src/assets/Level2-CyberSecurityAssets/" + (i / 2) + ".png")));
            }
        } else if (level == 3) {
            for (int i = 0; i < 16; i++) {
                cards.add(new Cards(i / 2, new ImageIcon("src/assets/Level3-GamingComputerAssets/no_image.png"), new ImageIcon("src/assets/Level3-GamingComputerAssets/" + (i / 2) + ".png")));
            }
        }
    }

    private void shuffleCards() { // initial show here?
        Thread shuffleThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	Collections.shuffle(cards);
            }
        });
        shuffleThread.start();
        try {
            shuffleThread.join(); // make sure suffling is done 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Cards> getCards() {
        return cards;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        initializeCards();
        shuffleCards();
    }

    public void Match(Cards card) {
        if (selectionOne == null) {
            selectionOne = card;
        } else if (selectionTwo == null && card != selectionOne) {
            selectionTwo = card;
            tryflag = true;
            if (selectionOne.getCardNo() == selectionTwo.getCardNo()) {
                System.out.println("Match");
                if(level == 1) {
                    score += 5;
                }
                else if(level == 2) {
                    score += 4;
                }
                else if(level == 3) {
                    score += 3;
                }
                matchCount++;
                if(matchCount == 8) {
                    levelUp();
                }
                System.out.println(score);
                selectionOne = null;
                selectionTwo = null; 
            } else {
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                        selectionOne.flip();
                        selectionTwo.flip();
                        selectionOne = null;
                        selectionTwo = null;
            
                    }
                });
                
                if(level == 1) {
                    score = score - 1;
                }
                else if(level == 2) {
                    score = score - 2;
                }
                else if(level == 3) {
                    score = score - 3;
                }
                System.out.println(score);
                
                timer.setRepeats(false);
                timer.start();
                
                if(level == 3) {
                	shuffleCards();
                	bg.reprint();
                }
            }
        }
    }

    private void levelUp() {
        if(level != 3) {
            String congrats = "YOU WON! \n Your score for this level is: " + this.score;
            int input = JOptionPane.showConfirmDialog(this, congrats, "Level Terminated!!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(input == JOptionPane.OK_OPTION) {
                new Game(level + 1, score);
                dispose();
            }
        }
        
        if(level == 3) {
            finishGame();
        }
    }

    private void finishGame() {
        String won = "You have Won the game! \n Your overall score is: " + this.score;
        JOptionPane.showConfirmDialog(this, won, "Game Finished", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        String username = JOptionPane.showInputDialog("Enter your user name ");
        System.out.println(username);
        ScoreBoard.saveHighScore(username, score);
        dispose();
        new Menu();
    }

    public boolean isTryflag() {
        return tryflag;
    }

    public void setTryflag(boolean tryflag) {
        this.tryflag = tryflag;
    }

    public Cards getSelectionOne() {
        return selectionOne;
    }

    public void setSelectionOne(Cards selectionOne) {
        this.selectionOne = selectionOne;
    }

    public Cards getSelectionTwo() {
        return selectionTwo;
    }

    public void setSelectionTwo(Cards selectionTwo) {
        this.selectionTwo = selectionTwo;
    }
}
