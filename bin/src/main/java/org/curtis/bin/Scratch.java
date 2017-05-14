package org.curtis.bin;

public class Scratch {
    public static void main(String args[]){
        Scratch scratch = new Scratch();
        scratch.execute();
    }

    public void execute() {
        String input = "Alive is  awesome";

        String[] words = input.split("\\s+");
        System.err.println("Word count: " + words.length);
    }
}
