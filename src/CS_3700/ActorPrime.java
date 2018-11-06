package CS_3700;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.Vector;

public class ActorPrime extends AbstractActor{
    private int firstPrime = -1;
    private Vector<Integer> primes;
    private boolean[] primeArray;
    private static ActorRef childActor = null;

    public static Props props(Vector<Integer> primes, boolean[] primeArray) {
        return Props.create(ActorPrime.class, () -> new ActorPrime(primes, primeArray));
    }

    public ActorPrime(Vector<Integer> primes, boolean[] primeArray) {
        this.primes = primes;
        this.primeArray = primeArray;
    }

    public static class CheckIfLocallyPrime {
        int number;
        public CheckIfLocallyPrime(int number) {
            this.number = number;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CheckIfLocallyPrime.class, biu -> {
                    if (firstPrime == -1) {
                        firstPrime = biu.number;
                        primeArray[biu.number] = true;
                        //System.out.println(biu.number);
                        System.out.println("New Actor: " + biu.number);
                        childActor = getContext().actorOf(ActorPrime.props(primes, primeArray));
                    } else if ((biu.number % firstPrime) != 0) {
                            //give to child actor
                            //System.out.println("local prime: " + firstPrime + "new prime: " + biu.number);
                            //Thread.sleep(100);
                            childActor.tell(new CheckIfLocallyPrime(biu.number),ActorRef.noSender());
                    }


                    /*if (childActor == null) {
                        childActor = getContext().actorOf(ActorPrime.props(biu.number));
                    } else {

                    }*/


                })
                .build();
    }
}
