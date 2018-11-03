package CS_3700;

public class Producer extends Thread {
    LockQueue queue = null;

    public Producer(LockQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            queue.put(new Object());
        }
    }

}
