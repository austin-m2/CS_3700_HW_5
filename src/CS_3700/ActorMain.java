package CS_3700;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
//import scala.concurrent.duration.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ActorMain {

    public void go1() {
        long elapsedTime = System.currentTimeMillis();

        ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
        AtomicInteger counter = new AtomicInteger(0);

        final ActorSystem system = ActorSystem.create();

        final ActorRef[] actorProducers = new ActorRef[5];
        final ActorRef[] actorConsumers = new ActorRef[2];
        for (int i = 0; i < 5; i++) {
            actorProducers[i] = system.actorOf(ActorProducer.props(queue));
        }
        for (int i = 0; i < 2; i++) {
            actorConsumers[i] = system.actorOf(ActorConsumer.props());
        }

        for (int i = 0; i < 5; i++) {
            actorProducers[i].tell(new ActorProducer.ProduceAndPutInQueue(queue), ActorRef.noSender());
        }

        for (int i = 0; i < 2; i++) {
            actorConsumers[i].tell(new ActorConsumer.ConsumeFromQueue(queue, counter, 500), ActorRef.noSender());
        }


        try {
            Timeout timeout = Timeout.create(Duration.ofHours(1));
            Future<Terminated> terminated = system.terminate();
            Await.result(terminated, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }


        elapsedTime = System.currentTimeMillis() - elapsedTime;

        System.out.println("ACTORS: 5 PRODUCERS, 2 CONSUMERS: " + (elapsedTime / 1000f) + " seconds");

    }

    public void go2() {
        long elapsedTime = System.currentTimeMillis();

        ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
        AtomicInteger counter = new AtomicInteger(0);

        final ActorSystem system = ActorSystem.create();

        final ActorRef[] actorProducers = new ActorRef[2];
        final ActorRef[] actorConsumers = new ActorRef[5];
        for (int i = 0; i < 2; i++) {
            actorProducers[i] = system.actorOf(ActorProducer.props(queue));
        }
        for (int i = 0; i < 5; i++) {
            actorConsumers[i] = system.actorOf(ActorConsumer.props());
        }

        for (int i = 0; i < 2; i++) {
            actorProducers[i].tell(new ActorProducer.ProduceAndPutInQueue(queue), ActorRef.noSender());
        }

        for (int i = 0; i < 5; i++) {
            actorConsumers[i].tell(new ActorConsumer.ConsumeFromQueue(queue, counter, 200), ActorRef.noSender());
        }


        try {
            Timeout timeout = Timeout.create(Duration.ofHours(1));
            Future<Terminated> terminated = system.terminate();
            Await.result(terminated, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }


        elapsedTime = System.currentTimeMillis() - elapsedTime;

        System.out.println("ACTORS: 2 PRODUCERS, 5 CONSUMERS: " + (elapsedTime / 1000f) + " seconds");

    }

    //parallel Sieve of Eratosthenes
    public void sieve() {

        long elapsedTime = System.currentTimeMillis();

        Vector<Integer> primes = new Vector<>(78498);
        boolean[] primeArray = new boolean[1000000];

        final ActorSystem system = ActorSystem.create();
        final ActorRef actorPrime = system.actorOf(ActorPrime.props(primes, primeArray));

/*
        actorPrime.tell(new ActorPrime.CheckIfLocallyPrime(2), ActorRef.noSender());
        for (int i = 3; i < 1000000; i+= 2) {
            actorPrime.tell(new ActorPrime.CheckIfLocallyPrime(i), ActorRef.noSender());
        }
*/

        actorPrime.tell(new ActorPrime.CheckIfLocallyPrime(3), ActorRef.noSender());
        for (int i = 5; i < 1000000; i+= 2) {
            actorPrime.tell(new ActorPrime.CheckIfLocallyPrime(i), ActorRef.noSender());
        }

 /*       try {
            Timeout timeout = Timeout.create(Duration.ofHours(1));
            Future<Terminated> terminated = system.terminate();
            Await.result(terminated, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        elapsedTime = System.currentTimeMillis() - elapsedTime;

        System.out.println("parallel sieve: " + elapsedTime + " ms");
        System.out.println("Number of primes in list: " + primes.size());

    }
}
