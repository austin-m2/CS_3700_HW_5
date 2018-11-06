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
                .match(CheckIfLocallyPrime.class, cilp -> {
                    if (firstPrime == -1) {
                        firstPrime = cilp.number;
                        primeArray[cilp.number] = true;
                        System.out.println("New Actor: " + cilp.number);
                        childActor = getContext().actorOf(ActorPrime.props(primes, primeArray));
                    } else if ((cilp.number % firstPrime) != 0) {
                            childActor.tell(new CheckIfLocallyPrime(cilp.number),ActorRef.noSender());
                    }
                })
                .build();
    }
}
