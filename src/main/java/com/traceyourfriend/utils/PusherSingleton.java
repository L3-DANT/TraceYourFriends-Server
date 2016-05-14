package com.traceyourfriend.utils;

import com.pusher.rest.Pusher;

/**
 * Created by romann on 11/05/2016.
 */
public class PusherSingleton {

    private final Pusher pusher = new Pusher("197476","37c3b876be2d4696857a","8a50d3125ff5b6d81d3f");

    private static final PusherSingleton INSTANCE = new PusherSingleton();

    private PusherSingleton(){

    }

    public static PusherSingleton getInstance(){return INSTANCE;}

    public Pusher GetPusher(){
        return this.pusher;
    }

}
