package com.traceyourfriend.utils;

import com.pusher.rest.Pusher;

/**
 * Created by romann on 11/05/2016.
 */
public class PusherSingleton {

    private final Pusher pusher = new Pusher("211950","e19d3311d6540da19604","8f510258822fd44e44b0");

    private static final PusherSingleton INSTANCE = new PusherSingleton();

    private PusherSingleton(){
        pusher.setEncrypted(true);
    }

    public static PusherSingleton getInstance(){return INSTANCE;}

    public Pusher GetPusher(){
        return this.pusher;
    }

}
