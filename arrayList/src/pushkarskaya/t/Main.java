package pushkarskaya.t;


public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(3);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(4,5);
        System.out.println(list.size());
    }
}
