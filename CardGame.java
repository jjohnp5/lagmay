/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;



/**
 *
 * @author quickfire
 */
public class CardGame extends Application {
    Card[] newDeck = new Card[52];
    Timer timer = new Timer();
    Label label = new Label("This");
    Button cards = new Button();
    Button toCenter = new Button();
    Button generate;
    Button shuffle;
    Pane root;
    Scene scene;
    Timeline timeline;
    TranslateTransition[] seq = new TranslateTransition[52];
    Stage stage;
    HBox menuBox = new HBox(10);
    Group cardGroup = new Group();
    TranslateTransition[] seq2 = new TranslateTransition[52];
    static int dx = 1;
    static int dy = 1;
    Timeline menuTime;
    Player p1 = new Player();
    double currentX, currentY, curTransX, curTransY;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create GUI elements with properties
        this.stage = primaryStage;
        root = new Pane();
        root.setStyle("-fx-background-color: blue;");
        scene = new Scene(root, 800, 600);

        cardGroup.prefHeight(scene.getHeight());
        cardGroup.prefWidth(scene.getWidth());
        generate = new Button();
        shuffle = new Button();
        cards.setText("Lay Cards");
        generate.setText("Generate Deck");
        toCenter.setText("ReShuffle");
        menuBox.setMinHeight(50);
        menuBox.setMinWidth(200);
        menuBox.setTranslateY(scene.getHeight() - menuBox.getMinHeight());
        menuBox.setTranslateX(-1000);
        menuBox.setMargin(generate, new Insets(10, 0, 0, 0));
        menuBox.setMargin(shuffle, new Insets(10, 0, 0, 0));
        menuBox.setMargin(cards, new Insets(10, 0, 0, 0));
        menuBox.setMargin(toCenter, new Insets(10, 0, 0, 0));


        //button action events
        generate.setOnAction((ActionEvent event) -> {
            generateDeck();
        });
        shuffle.setText("Shuffle Deck");
        shuffle.setOnAction((ActionEvent event) -> {
            shuffleDeck();
//            moveCard();
            setToCenter();

        });
        cards.setOnAction(event -> dealCards());
        toCenter.setOnAction(e -> {shuffleDeck(); reshuffle(); });
//        images.setTranslateX((scene.getWidth() / 2) - images.getFitWidth() / 2);
//        images.setTranslateY((scene.getHeight() / 2) - images.getFitHeight() / 2);
//        images.setOnMouseClicked(e -> {
//            images.setTranslateX(400);
//            images.setTranslateY(300);
//        });

        //timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
//        KeyValue keyv1 = new KeyValue(images.translateXProperty(), (scene.getWidth() - images.getFitWidth() * 2));
//        KeyValue kev2 = new KeyValue(images.translateYProperty(), (scene.getHeight() / 2 - images.getFitHeight() / 2));
        KeyFrame keyFrame = new KeyFrame(Config.ANIMATION_DURATION, new EventHandler<ActionEvent>() {
            double i = Math.random();
            @Override
            public void handle(ActionEvent event) {
                double xMin = newDeck[0].getImageview().getBoundsInParent().getMinX();
                double yMin = newDeck[0].getImageview().getBoundsInParent().getMinY();
                double xMax = newDeck[0].getImageview().getBoundsInParent().getMaxX();
                double yMax = newDeck[0].getImageview().getBoundsInParent().getMaxY() + 50;

                if(xMin < 0 || xMax > root.getWidth()){
                    dx = dx * -1;
                }
                if(yMin < 0 || yMax > root.getHeight()){
                    dy = dy * -1;
                }
                newDeck[0].getImageview().setTranslateX(newDeck[0].getImageview().getTranslateX()+dx);
                newDeck[0].getImageview().setTranslateY(newDeck[0].getImageview().getTranslateY()+dy);
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        menuTime = new Timeline();
        menuTime.setCycleCount(1);
        KeyValue mKey = new KeyValue(menuBox.translateXProperty(), 1);
        menuTime.getKeyFrames().add(new KeyFrame(Duration.millis(2000), mKey));



        //whole screen elements/nodes

        menuBox.getChildren().addAll(generate, shuffle, toCenter, cards);

        root.getChildren().addAll(menuBox);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
                    e.consume();
                    closeRequest();
                    timeline.stop();
                }
        );
        //show GUI
        menuTime.play();
        stage.show();
    }

    public void generateDeck() {
        int index = 0;
        for (String suit1 : Config.suit) {
            for (String rank1 : Config.rank) {
                newDeck[index] = new Card(rank1, suit1);
                newDeck[index].generateImage();
                newDeck[index].getImageview().setOnMousePressed(cardOnPressed);
                newDeck[index].getImageview().setOnMouseDragged(cardOnDrag);
                newDeck[index].getImageview().setOnMouseClicked(e->{

                    ((ImageView)e.getSource()).setImage(((Card)e.getSource()).getImage());
                });
//                newDeck[index].getImageview().setImage(newDeck[index].getBackImage());
//                cardFaces.add(new ImageView(newDeck[index].getBackImage()));
//                cardGroup.getChildren().add(newDeck[index].getImageview());
                index++;
            }
        }
        layCards();
        generate.setDisable(true);
        for (Card newDeck1 : newDeck) {
            int i = newDeck1.getRankScore() + newDeck1.getSuitScore();
            System.out.print(newDeck1.getRank() + "_of_" + newDeck1.getSuit() + "\n");
            System.out.print(i + "\n");
        }
    }

    public void shuffleDeck() {
        Random random = ThreadLocalRandom.current();
        for (int i = newDeck.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Card temp = newDeck[index];
            newDeck[index] = newDeck[i];
            newDeck[i] = temp;
            newDeck[i].getImageview().toBack();
        }
        for (Card item : newDeck) {
            System.out.print(item.getRank() + " of " + item.getSuit() + "\n");
        }
        shuffle.setDisable(true);
    }

    public void moveCard() {
        timeline.play();
    }

    public void closeRequest() {
        Boolean answer = ConfirmBox.display("Exit Program", "Are you sure you want to exit?");
        if (answer) {
            stage.close();
            System.exit(0);
        }
    }

    public void layCards() {
        int xIndex = 10;
        int yIndex = 10;
        for (int i = 0; i<newDeck.length; i++) {
            if (xIndex < root.getWidth() - newDeck[i].getImageview().getFitWidth()) {

                newDeck[i].getImageview().setFitHeight(80);
                newDeck[i].getImageview().setFitWidth(60);
                newDeck[i].getImageview().setTranslateX(xIndex);
                newDeck[i].getImageview().setTranslateY(yIndex);
                root.getChildren().add(newDeck[i].getImageview());
                xIndex += 65;

            }
            if (xIndex > root.getWidth() - newDeck[i].getImageview().getFitWidth()) {
                yIndex += 85;
                xIndex = 10;
            }
        }
    }

    public void setToCenter() {
        int dur = 150;
        for(int i = 0; i < newDeck.length;i++){
            seq[i] = new TranslateTransition(Duration.millis(dur), newDeck[i].getImageview());
            seq[i].setToX((root.getWidth()/2) - (newDeck[i].getImageview().getFitWidth()/2));
            seq[i].setToY((root.getHeight()/2)- (newDeck[i].getImageview().getFitHeight()/2));
            seq[i].setCycleCount(7);
            seq[i].setAutoReverse(true);
            dur += 10;
        }
        for(TranslateTransition i : seq){
            i.play();
        }
    }
    public void reshuffle(){
        int dur = 150;
        for(int i = 0; i < newDeck.length;i++){
            seq[i] = new TranslateTransition(Duration.millis(dur), newDeck[i].getImageview());
            seq[i].setToX(Math.random()*root.getWidth());
            seq[i].setToY(Math.random()*root.getHeight());
            seq[i].setCycleCount(6);
            seq[i].setAutoReverse(true);
            dur += 10;
        }
        for(TranslateTransition i : seq){
            i.play();
        }
        toCenter.setDisable(true);
    }
    public void dealCards(){
        int dur = 1500;
        int dur2 = 2000;
        int dur3 = 2500;
        int dur4 = 3000;
//        int z = 0;
        for(int i = 0; i < newDeck.length;i += 4){

//                p1.addToCard(newDeck[i], z);
//                z++;
                seq2[i] = new TranslateTransition(Duration.millis(dur), newDeck[i].getImageview());
                seq2[i].setToX(600);
                seq2[i].setToY(500);
                seq2[i].setCycleCount(1);
                seq2[i].setAutoReverse(true);
            dur -= 100;
            }

        for(int i = 1; i < newDeck.length;i += 4){
                seq2[i] = new TranslateTransition(Duration.millis(dur2), newDeck[i].getImageview());
                seq2[i].setToX(600);
                seq2[i].setToY(400);
                seq2[i].setCycleCount(1);
                seq2[i].setAutoReverse(true);
                dur2 -= 100;
            }
        for(int i = 2; i < newDeck.length;i += 4){
                seq2[i] = new TranslateTransition(Duration.millis(dur3), newDeck[i].getImageview());
                seq2[i].setToX(600);
                seq2[i].setToY(300);
                seq2[i].setCycleCount(1);
                seq2[i].setAutoReverse(true);
                dur3 -= 100;
            }
        for(int i = 3; i < newDeck.length;i += 4){
                seq2[i] = new TranslateTransition(Duration.millis(dur4), newDeck[i].getImageview());
                seq2[i].setToX(600);
                seq2[i].setToY(200);
                seq2[i].setCycleCount(1);
                seq2[i].setAutoReverse(true);
                dur4 -= 100;
            }
        for(TranslateTransition i : seq2){
            i.play();
        }
    }
    EventHandler<MouseEvent> cardOnPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            currentX = e.getSceneX();
            currentY = e.getSceneY();
            curTransX = ((ImageView)(e.getSource())).getTranslateX();
            curTransY = ((ImageView)(e.getSource())).getTranslateY();
        }
    };
    EventHandler<MouseEvent> cardOnDrag = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            double offsetX = e.getSceneX() - currentX;
            double offsetY = e.getSceneY() - currentY;
            double newTransX = curTransX + offsetX;
            double newTransY = curTransY + offsetY;

            ((ImageView)(e.getSource())).setTranslateX(newTransX);
            ((ImageView)(e.getSource())).setTranslateY(newTransY);
            ((ImageView)(e.getSource())).toFront();
        }
    };
    EventHandler<MouseEvent> cardOnDoubleClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

        }
    };
}


