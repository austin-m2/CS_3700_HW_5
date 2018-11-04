package CS_3700;

public class IsolatedProducer extends Thread{
    IsolatedQueue queue = null;

    public IsolatedProducer(IsolatedQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            queue.put(new Object());
        }
    }
}
