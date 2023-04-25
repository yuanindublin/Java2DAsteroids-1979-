package com.example.asteroids;
import javafx.util.Duration;
import java.util.Random;


public class StarParticle {
    Random Random = new Random();
    int x;
    int y;
    double t = 0.0;
    
    Duration delay;
    
    StarParticle(int x, int y, Duration delay){
        this.x = x;
        this.y = y;
        this.delay = delay;
    }

    void onUpdate(){
        t += 0.01;

        if(t < delay.toSeconds()){
            return;
        }

        y += Random.nextInt(10);
    }

    
}
