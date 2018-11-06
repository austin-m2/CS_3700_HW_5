package CS_3700;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ActorConsumer extends AbstractActor {

    public static Props props() {
        return Props.create(ActorConsumer.class, () -> new ActorConsumer());
    }

    public ActorConsumer() {}

    static public class ConsumeFromQueue {
        private ArrayBlockingQueue queue;
        private AtomicInteger counter;
        private final int MAX;
        public ConsumeFromQueue(ArrayBlockingQueue queue, AtomicInteger counter, int MAX) {
            this.queue = queue;
            this.counter = counter;
            this.MAX = MAX;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ConsumeFromQueue.class, biu -> {
                    while(biu.counter.get() < biu.MAX) {
                        biu.queue.take();
                        biu.counter.getAndIncrement();
                        //System.out.println(biu.counter.get());
                        Thread.sleep(1000);
                    }
                })
                .build();
    }
}
