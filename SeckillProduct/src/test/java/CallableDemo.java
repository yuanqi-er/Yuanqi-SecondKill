import java.util.concurrent.*;

public class CallableDemo {
    private static class SumTask implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            long sum = 0;
            for (int i = 0; i < 9000; i++) {
                sum = sum + i;
            }
            return sum;
        }
    }

    /*
        把秒杀过程封装到future里面，再通过一个队列去到结果，判断是否成功
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Start:" + System.nanoTime());

        FutureTask<Long> futureTask = new FutureTask<>(new SumTask());
        //用线程池，提高线程利用率
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        System.out.println(futureTask.get());

        System.out.println("End:" + System.nanoTime());
    }
}
