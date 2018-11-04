package CS_3700;

public class LockProducer extends Thread {
    LockQueue queue = null;

    public LockProducer(LockQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            queue.put(new Object());
        }
    }

}
