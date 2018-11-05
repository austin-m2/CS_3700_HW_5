package CS_3700;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        {
            LockQueue queue = new LockQueue();
            queue.set_max_items(500);

            ArrayList<LockProducer> producers = new ArrayList<>();
            ArrayList<LockConsumer> consumers = new ArrayList<>();

            long elapsedTime = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                producers.add(new LockProducer(queue));
                producers.get(i).start();
            }
            for (int i = 0; i < 2; i++) {
                consumers.add(new LockConsumer(queue));
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
            System.out.println("LOCKS: 5 PRODUCERS, 2 CONSUMERS: " + elapsedTimeInSeconds + " seconds");


            //remove dead threads from lists
            producers.clear();
            consumers.clear();
            queue = new LockQueue();
            queue.set_max_items(200);

            elapsedTime = System.currentTimeMillis();

            for (int i = 0; i < 2; i++) {
                producers.add(new LockProducer(queue));
                producers.get(i).start();
            }
            for (int i = 0; i < 5; i++) {
                consumers.add(new LockConsumer(queue));
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
            System.out.println("LOCKS: 2 PRODUCERS, 5 CONSUMERS: " + elapsedTimeInSeconds + " seconds");


        }


        //TIME FOR ISOLATED SECTIONS
        {
            IsolatedQueue queue = new IsolatedQueue();
            queue.set_max_items(500);

            ArrayList<IsolatedProducer> producers = new ArrayList<>();
            ArrayList<IsolatedConsumer> consumers = new ArrayList<>();

            long elapsedTime = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                producers.add(new IsolatedProducer(queue));
                producers.get(i).start();
            }
            for (int i = 0; i < 2; i++) {
                consumers.add(new IsolatedConsumer(queue));
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
            System.out.println("ISOLATED: 5 PRODUCERS, 2 CONSUMERS: " + elapsedTimeInSeconds + " seconds");


            //remove dead threads from lists
            producers.clear();
            consumers.clear();
            queue = new IsolatedQueue();
            queue.set_max_items(200);

            elapsedTime = System.currentTimeMillis();

            for (int i = 0; i < 2; i++) {
                producers.add(new IsolatedProducer(queue));
                producers.get(i).start();
            }
            for (int i = 0; i < 5; i++) {
                consumers.add(new IsolatedConsumer(queue));
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
            System.out.println("ISOLATED: 2 PRODUCERS, 5 CONSUMERS: " + elapsedTimeInSeconds + " seconds");

        }


        Atomic atomic = new Atomic();
        atomic.go1();
        atomic.go2();


        //actor time!
        ActorMain actorstuff = new ActorMain();
        actorstuff.go1();
        actorstuff.go2();


        //serial sieve time!
        sieve();

        //actor sieve time
        actorstuff.sieve();





    }

    private static void sieve() {
        long elapsedTime = System.currentTimeMillis();
        final int MAX = 1000000;
        boolean[] array = new boolean[MAX];
        double limit = Math.sqrt(MAX);

        for (int i = 2; i < limit; i++) {
            if (!array[i]) {
                for (int j = i * i; j < MAX; j += i) {
                    array[j] = true;
                }
            }
        }

        elapsedTime = System.currentTimeMillis() - elapsedTime;

        System.out.println("SINGLE-THREADED SIEVE: " + elapsedTime + " ms");
    }
}
