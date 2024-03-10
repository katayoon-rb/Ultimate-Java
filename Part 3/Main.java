import myPack.FlightService;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        var start = LocalTime.now();

        var service = new FlightService();
        var futures = service.getQuotes()
                .map(future -> future.thenAccept(System.out::println))
                .toList();

        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    var duration = Duration.between(start, LocalTime.now());
                    System.out.println("Retrieved all quotes in " + duration.toMillis() + " msec.");
                });


        try {
            Thread.sleep(10_000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}