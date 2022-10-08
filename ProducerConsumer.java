import java.util.LinkedList;

public class ProducerConsumer {
    final LinkedList<Integer> items =  new LinkedList<>();
    final Object lock = new Object();
    final int capacity = 10;
    public static void main(String[] args) {
        ProducerConsumer m = new ProducerConsumer();
        Thread p = new Thread(m.new Producer());
        Thread p2 = new Thread(m.new Producer());
        Thread p3 = new Thread(m.new Producer());
        Thread p4 = new Thread(m.new Producer());
        Thread p5 = new Thread(m.new Producer());
        Thread c = new Thread(m.new Consumer());
        Thread c2 = new Thread(m.new Consumer());
        Thread c3 = new Thread(m.new Consumer());
        Thread c4 = new Thread(m.new Consumer());
        Thread c5 = new Thread(m.new Consumer());
        Thread c6 = new Thread(m.new Consumer());
        Thread c7 = new Thread(m.new Consumer());

        p.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        c.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
        c6.start();
        c7.start();

    }



    class Producer implements Runnable{

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) { /** do something **/ }
        }

        public void produce() throws InterruptedException {
            int value =0;
            while(true){
                synchronized (lock){
                    while(items.size() == capacity)
                        lock.wait();
                    items.add(++value);
                    System.out.println("Item Produced by "+Thread.currentThread().getName()+" Item "+value);
                    lock.notify();
                    Thread.sleep(200);
                }
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                System.out.println("need more products");
            }
        }
        public void consume() throws InterruptedException {
            while (true){
                synchronized (lock){
                    while (items.size() == 3) //<-- poka ne stanet 5 elementov
                        lock.wait();
                    System.out.println( "Item Consumed by "
                            +Thread.currentThread().getName()+
                            " Item "+items.remove());
                    lock.notify();
                    Thread.sleep(1000);
                }
            }
        }
    }
}