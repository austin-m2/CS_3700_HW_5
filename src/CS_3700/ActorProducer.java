package CS_3700;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.lang.reflect.Array;
import java.util.concurrent.ArrayBlockingQueue;

public class ActorProducer extends AbstractActor {

    public ArrayBlockingQueue queue;

    public static Props props(ArrayBlockingQueue q) {
        return Props.create(ActorProducer.class, () -> new ActorProducer(q));
    }

    public ActorProducer(ArrayBlockingQueue q) {
        queue = q;
    }

    static public class ProduceAndPutInQueue {
        private ArrayBlockingQueue queue;
        public ProduceAndPutInQueue(ArrayBlockingQueue queue) {
            this.queue = queue;
        }
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProduceAndPutInQueue.class, biu -> {
                    for (int i = 0; i < 100; i++) {
                        biu.queue.put(new Object());
                    }
                })
                .build();
    }
}
