package CS_3700;

public class Consumer extends Thread {
    LockQueue queue = null;

    public Consumer(LockQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (queue.get()) {
                    interrupt();
                }
                //System.out.println(Thread.currentThread().getName() + ": Sleeping for 1 second...");
                sleep(100);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
