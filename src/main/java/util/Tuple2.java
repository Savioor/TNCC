package util;

/**
 *
 * @param <U> First type of the tuple
 * @param <V> Second type of the tuple
 */
public class Tuple2<U,V> {

    public U first;
    public V second;

    /**
     *
     * @param first
     * @param second
     */
    public Tuple2(U first, V second){
        this.first = first;
        this.second = second;
    }
}
