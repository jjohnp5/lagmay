package cardgame;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;

/**
 * Created by quickfire on 1/16/2017.
 */
public class Player {
    private Card[] cards = new Card[13];
    private VBox cardgroup;


    public Card getCard(int index) {
        return cards[index];
    }

    public void setCard(Card card, int index) {
        this.cards[index] = card;
    }

    public VBox getCardgroup() {
        return cardgroup;
    }

    public void setCardgroup(VBox cardgroup) {
        this.cardgroup = cardgroup;
    }
    public void addToCard(Card card, int index){
        this.cards[index] = card;
    }


    ImageView[] imagesview = new ImageView[13];

    public VBox createVBox(){
        for(int i = 0; i<cards.length;i++){
            imagesview[i].setImage(cards[i].getImage());
            cardgroup.getChildren().add(imagesview[i]);
        }
        return cardgroup;
    }
    public void Player(){

    }
}
