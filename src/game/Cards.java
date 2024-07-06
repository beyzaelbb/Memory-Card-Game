package game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cards extends JButton {
    
    private int cardNo;
    private boolean isFlipped = false;
    private ImageIcon defaultImage;
    private ImageIcon cardImage;
    
    public Cards(int no, ImageIcon defaultImage, ImageIcon cardImage) {
        this.cardNo = no;
        this.defaultImage = defaultImage;
        this.cardImage = cardImage;
        this.setIcon(defaultImage);
    }

    public int getCardNo() {
        return cardNo;
    }

    public boolean getisFlipped() {
        return isFlipped;
    }

    public void flip() {
        this.isFlipped = !this.isFlipped;
        if(isFlipped) {
        	this.setIcon(cardImage);
        }
        else this.setIcon(defaultImage);
    }

	public Icon getDefaultImage() {
		return this.defaultImage;
	}
}
