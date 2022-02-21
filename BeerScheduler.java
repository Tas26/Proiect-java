package controllers;

import models.Beer;

import java.util.ArrayList;
import java.util.Random;

public class BeerScheduler implements Runnable {

    private final Random r;
    ArrayList<Beer> list = new ArrayList<>();

    public BeerScheduler() {
        r = new Random();
    }

    @Override
    public void run() {

        int db = 0;
        int speed = 10;
        int sleepTime = 4000;
        while (true) {
            int x = r.nextInt(1200);

            Beer beer = new Beer(x, speed);
            list.add(beer);
            db++;
            if (db == 5) {
                speed += 5;
                db = 0;
                if (sleepTime - 250 > 0) {
                    sleepTime -= 250;
                }

            }
            Thread thread = new Thread(beer);
            thread.start();


            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void removeBeer(int index) {
        list.remove(index);
    }

    public ArrayList<Beer> getList() {
        return list;
    }
}
