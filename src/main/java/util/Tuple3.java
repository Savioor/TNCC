package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple3<T> implements Iterable<T> {
    public T first;
    public T second;
    public T third;

    private List<T> asList;

    public Tuple3(T first, T second, T third){
        this.first = first;
        this.second = second;
        this.third = third;
        asList = new ArrayList<>();
        asList.add(first);
        asList.add(second);
        asList.add(third);
    }

    public T get(int i){
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

    public void set(int i, T value){
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
    public Iterator<T> iterator() {
        asList.set(0,first);
        asList.set(1,second);
        asList.set(2,third);
        return asList.iterator();
    }

    @Override
    public String toString(){
        return new StringBuilder().append("(").append(first).append(", ").append(second).append(", ").append(third).append(")").toString();
    }
}
