package CS_3700;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Atomic {
    ArrayBlockingQueue<AtomicReference<Object>> queue = new ArrayBlockingQueue<>(10);
    public int max_items = 0;
    public int num_items_consumed = 0;

    public void go1() throws InterruptedException {
        ArrayList<P> producers = new ArrayList<>();
        ArrayList<C> consumers = new ArrayList<>();
        max_items = 500;
        num_items_consumed = 0;

        long elapsedTime = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            producers.add(new P(queue));
            producers.get(i).start();
        }
        for (int i = 0; i < 2; i++) {
            consumers.add(new C(this, queue));
            consumers.get(i).start();
        }

        for (int i = 0; i < producers.size(); i++) {
            producers.get(i).join();
        }

        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).join();
        }

        elapsedTime = System.currentTimeMillis() - elapsedTime;
        float elapsedTimeSeconds = (float) elapsedTime / 1000f;

        System.out.println("ATOMIC: 5 PRODUCERS, 2 CONSUMERS: " + elapsedTimeSeconds + " seconds");
    }

    public void go2() throws InterruptedException {
        ArrayList<P> producers = new ArrayList<>();
        ArrayList<C> consumers = new ArrayList<>();
        max_items = 200;
        num_items_consumed = 0;

        long elapsedTime = System.currentTimeMillis();

        for (int i = 0; i < 2; i++) {
            producers.add(new P(queue));
            producers.get(i).start();
        }
        for (int i = 0; i < 5; i++) {
            consumers.add(new C(this, queue));
            consumers.get(i).start();
        }

        for (int i = 0; i < producers.size(); i++) {
            producers.get(i).join();
        }

        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).join();
        }

        elapsedTime = System.currentTimeMillis() - elapsedTime;
        float elapsedTimeSeconds = (float) elapsedTime / 1000f;

        System.out.println("ATOMIC: 2 PRODUCERS, 5 CONSUMERS: " + elapsedTimeSeconds + " seconds");
    }

    public synchronized void increment_counter() {
        num_items_consumed++;
    }

}

class P extends Thread {
    ArrayBlockingQueue<AtomicReference<Object>> queue = null;

    public P(ArrayBlockingQueue q){
        queue = q;
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                queue.put(new AtomicReference<>(new Object()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class C extends Thread {
    Atomic atomic;
    ArrayBlockingQueue<AtomicReference<Object>> queue;

    public C(Atomic a, ArrayBlockingQueue q){
        this.atomic = a;
        queue = q;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (atomic.num_items_consumed == atomic.max_items) { interrupt(); }
                queue.take();
                atomic.increment_counter();
                sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
