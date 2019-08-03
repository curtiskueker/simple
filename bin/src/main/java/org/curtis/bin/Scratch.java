package org.curtis.bin;

import java.util.Arrays;
import java.util.List;

public class Scratch {
    public static void main(String args[]){
        Scratch scratch = new Scratch();
        scratch.execute();
    }

    public void execute() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Integer sum = integers.stream().reduce((x, y) -> (x * x) + y).get();
        System.err.println(sum);
        Integer sum2 = integers.stream().filter(i -> i > 2).reduce(0, (x, y) -> x + y);
        System.err.println(sum2);
        System.err.println("sum: " + sum + sum2);
        System.err.println(sum + sum2 + " sum");
        System.err.println("Done");
    }

    public static <T> void checkList (List<T> list) {

    }
}
