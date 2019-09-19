package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of three integers
 */
public class Tuple3Int implements Iterable<Integer> {
    public int first;
    public int second;
    public int third;

    private List<Integer> asList;

    /**
     *
     * @param first
     * @param second
     * @param third
     */
    public Tuple3Int(int first, int second, int third){
        this.first = first;
        this.second = second;
        this.third = third;
        asList = new ArrayList<>();
        asList.add(first);
        asList.add(second);
        asList.add(third);
    }

    /**
     * Treat this class as a array of length three and get a given value
     * @param i the index
     * @return the integer of the index
     */
    public int get(int i){
        switch (i){
            case 0:
                return first;
            case 1:
                return second;
            case 2:
                return third;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    /**
     *
     * @param i the index to change
     * @param value the new value
     */
    public void set(int i, int value){
        switch (i){
            case 0:
                first = value;
                break;
            case 1:
                second = value;
                break;
            case 2:
                third = value;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        asList.set(0,first);
        asList.set(1,second);
        asList.set(2,third);
        return asList.iterator();
    }

    /**
     *
     * @return The sum of the three integers.
     */
    public int sum(){
        return first + second + third;
    }

    /**
     *
     * @return 3
     */
    public int size(){
        return 3;
    }
}
