/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import javafx.util.Duration;

/**
 *
 * @author quickfire
 */
public final class Config {
    public static final Duration ANIMATION_DURATION = Duration.millis(10);
    public static final String[] suit = {"clubs", "spades", "hearts", "diamonds"};
    public static final String[] rank = { "2", "3", "4", "5", "6", "7", "8","9","10","jack","queen","king", "ace"};

    

    private Config(){

    }

}

