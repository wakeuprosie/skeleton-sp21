package gh2;

import deque.ArrayDeque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private ArrayDeque buffer;


    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        /** Fill buffer with 0s */
        buffer = new ArrayDeque();

        int capacity = (int) Math.round(SR / frequency);

        /** Extend length of LLD to match capacity */

        for (int k = this.buffer.size(); k < capacity; k += 1) {
            this.buffer.addFirst(0.0);
        }
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        /** replace every item in buffer with random double */
        for (int i = 0; i < buffer.size(); i += 1) {
            /** generate random number */
            buffer.removeLast();
            buffer.addFirst(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        /** Calculate new Double */
        double firstItem = (double) buffer.removeFirst();
        double nextItem = (double) buffer.get(0);
        double newDouble = ((firstItem + nextItem) / 2) * DECAY;

        /** Enqueue new Double */
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return (double) buffer.get(0);
    }
}
