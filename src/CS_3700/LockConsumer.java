package CS_3700;

public class LockConsumer extends Thread {
    LockQueue queue = null;



    public LockConsumer(LockQueue q) {
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
                sleep(10);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
