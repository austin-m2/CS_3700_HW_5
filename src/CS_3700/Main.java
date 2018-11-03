package CS_3700;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	    LockQueue queue = new LockQueue();
	    queue.set_max_items(500);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        long elapsedTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            producers.add(new Producer(queue));
            producers.get(i).start();
        }
        for (int i = 0; i < 2; i++) {
            consumers.add(new Consumer(queue));
            consumers.get(i).start();
        }
        for (int i = 0; i < producers.size(); i++) {
            producers.get(i).join();
        }
        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).join();
        }

        elapsedTime = System.currentTimeMillis() - elapsedTime;
        float elapsedTimeInSeconds = (float) elapsedTime / 1000f;
        System.out.println("LOCKS: 5 PRODUCERS, 2 CONSUMERS: " + elapsedTimeInSeconds);


        //remove dead threads from lists
        producers.clear();
        consumers.clear();
        queue = new LockQueue();
        queue.set_max_items(200);

        elapsedTime = System.currentTimeMillis();

        for (int i = 0; i < 2; i++) {
            producers.add(new Producer(queue));
            producers.get(i).start();
        }
        for (int i = 0; i < 5; i++) {
            consumers.add(new Consumer(queue));
            consumers.get(i).start();
        }
        for (int i = 0; i < producers.size(); i++) {
            producers.get(i).join();
        }
        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).join();
        }

        elapsedTime = System.currentTimeMillis() - elapsedTime;
        elapsedTimeInSeconds = (float) elapsedTime / 1000f;
        System.out.println("LOCKS: 2 PRODUCERS, 5 CONSUMERS: " + elapsedTimeInSeconds);


    }
}
