/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;


import javafx.animation.KeyValue;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;

/**
 *
 * @author quickfire
 */
public class Card {
    private String rank;
    private String suit;
    private Image image;
    private Image backImage;
    private int suitScore;
    private int rankScore;
    private ImageView imageview;

    public ImageView getImageview() {
        return imageview;
    }

    public void setImageview(ImageView imageview) {
        this.imageview = imageview;
    }

    private TranslateTransition trans;

    public int getSuitScore() {
        return suitScore;
    }

    public void setSuitScore(int suitScore) {
        this.suitScore = suitScore;
    }

    public int getRankScore() {
        return rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public Image getBackImage() {
        return backImage;
    }

    public void setBackImage(Image backImage) {
        this.backImage = backImage;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public void generateImage(){
        String filePath = "resources/"+this.rank+"_of_"+this.suit+".png";
        this.image = new Image(filePath.toLowerCase());
        this.backImage = new Image("resources/back_image.png");
        this.imageview.setImage(backImage);
    }

    //basic constructor with no parameters
    public Card() {
    }
    //constructor with just rank and suit parameters
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        this.imageview = new ImageView();
        this.imageview.setImage(this.image);
        int suitFlex = Arrays.asList(Config.rank).indexOf(rank);
        for(int i = 0; i < Config.rank.length; i++){
            if (suitFlex == i && i == 0) {
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+1;
            }
            if(i == 1 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+4;
            }
            if(i == 2 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+7;
            }
            if(i == 3 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+10;
            }
            if (suitFlex == i && i == 4) {
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+13;
            }
            if(i == 5 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+16;
            }
            if(i == 6 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+19;
            }
            if(i == 7 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+21;
            }
            if (suitFlex == i && i == 8) {
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+24;
            }
            if(i == 9 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+27;
            }
            if(i == 10 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+30;
            }
            if(i == 11 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+33;
            }
            if(i == 12 && suitFlex == i){
                this.rankScore = Arrays.asList(Config.rank).indexOf(rank)+36;
            }

        }
        this.suitScore = Arrays.asList(Config.suit).indexOf(suit) + 1;
    }
    //full constructor
    public Card(String rank, String suit, Image image) {
        this.rank = rank;
        this.suit = suit;
        this.image = image;
    }
    public KeyValue kvX(Duration duration, int endX){

        return new KeyValue(this.imageview.translateXProperty(), endX);
    }
    public KeyValue kvY(Duration duration, int endY){
        return new KeyValue(this.imageview.translateXProperty(), endY);
    }


    
}
