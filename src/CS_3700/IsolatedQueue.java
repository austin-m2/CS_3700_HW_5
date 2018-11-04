package CS_3700;

import java.util.LinkedList;

public class IsolatedQueue {
    LinkedList<Object> list = new LinkedList();
    Object lock = new Object();
    private static final int CAPACITY = 10;
    private int max_items = 0;
    private int items_consumed = 0;

    public void put(Object object) {
        synchronized (lock) {
            while (list.size() == CAPACITY) {
                //System.out.println(Thread.currentThread().getName() + ": Buffer is full. Waiting...");
                try {
                    lock.wait();

                    //System.out.println(Thread.currentThread().getName() + ": Buffer is no longer full!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(object);
            //System.out.println(Thread.currentThread().getName() + ": Produced object. Size of list: " + list.size());
            lock.notifyAll();
        }
    }

    /**
     *
     * @return true if all the objects have been consumed
     * (if items_consumed == max_items)
     */
    public boolean get() {

        synchronized (lock) {
            while (list.size() == 0) {
                if (items_consumed == max_items) {
                    return true;
                }
                //System.out.println(Thread.currentThread().getName() + ": Buffer is empty. Waiting...");
                try {

                    lock.wait();
                    //System.out.println(Thread.currentThread().getName() + ": Buffer is no longer empty! Consuming object...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            Object object = list.poll();
            items_consumed++;

            //System.out.println(Thread.currentThread().getName() + ": Consumed object. Size of list: " + list.size());
            lock.notifyAll();

            return (items_consumed == max_items);

        }

    }


    public void set_max_items(int i) { max_items = i; }
    public void reset_items_consumed() {
        items_consumed = 0;
    }
}
