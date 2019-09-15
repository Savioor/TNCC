package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple3Int implements Iterable<Integer> {
    public int first;
    public int second;
    public int third;

    private List<Integer> asList;

    public Tuple3Int(int first, int second, int third){
        this.first = first;
        this.second = second;
        this.third = third;
        asList = new ArrayList<>();
        asList.add(first);
        asList.add(second);
        asList.add(third);
    }

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

    public int sum(){
        return first + second + third;
    }

    public int size(){
        return 3;
    }
}
