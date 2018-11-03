package CS_3700;

import java.util.LinkedList;
import java.util.concurrent.locks.*;


public class LockQueue {
    LinkedList<Object> list = new LinkedList();
    ReentrantLock lock = new ReentrantLock();
    Condition notFullCondition = lock.newCondition();
    Condition notEmptyCondition = lock.newCondition();
    private static final int CAPACITY = 10;
    private int max_items = 0;
    private int items_consumed = 0;

    public void put(Object object) {
        lock.lock();
        while (list.size() == CAPACITY) {
            //System.out.println(Thread.currentThread().getName() + ": Buffer is full. Waiting...");
            try {
                notFullCondition.await();
                //System.out.println(Thread.currentThread().getName() + ": Buffer is no longer full!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(object);
        //System.out.println(Thread.currentThread().getName() + ": Produced object. Size of list: " + list.size());
        notEmptyCondition.signal();
        lock.unlock();
    }



    /**
     *
     * @return true if all the objects have been consumed
     * (if items_consumed == max_items)
     */
    public boolean get() {

        lock.lock();
        while (list.size() == 0) {
            if (items_consumed == max_items) {
                lock.unlock();
                return true;
            }
            //System.out.println(Thread.currentThread().getName() + ": Buffer is empty. Waiting...");
            try {
                notEmptyCondition.await();
                //System.out.println(Thread.currentThread().getName() + ": Buffer is no longer empty! Consuming object...");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        Object object = list.poll();
        items_consumed++;

        //System.out.println(Thread.currentThread().getName() + ": Consumed object. Size of list: " + list.size());
        notFullCondition.signal();
        lock.unlock();
        return (items_consumed == max_items);
    }

    public void set_max_items(int max) {
        max_items = max;
    }

    public void reset_items_consumed() {
        items_consumed = 0;
    }

}
