package CS_3700;

public class IsolatedConsumer extends Thread {
    IsolatedQueue queue = null;



    public IsolatedConsumer(IsolatedQueue q) {
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
