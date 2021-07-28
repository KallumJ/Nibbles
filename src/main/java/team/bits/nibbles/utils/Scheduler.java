package team.bits.nibbles.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Scheduler {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Collection<ScheduledTask> tasks = new LinkedList<>();

    public static void schedule(@NotNull Runnable command, long delay) {
        tasks.add(new ScheduledTask(command, delay, 0));
    }

    public static void scheduleAtFixedRate(@NotNull Runnable command, long initialDelay, long period) {
        tasks.add(new ScheduledTask(command, initialDelay, period));
    }

    public static void runOffThread(@NotNull Runnable runnable) {
        Throwable origin = new Throwable();
        EXECUTOR.submit(() -> {
            try {
                runnable.run();
            } catch (Exception ex) {
                LOGGER.error("Error on off-thread");
                ex.printStackTrace();
                LOGGER.error("Task start stacktrace:");
                origin.printStackTrace();
            }
        });
    }

    public static void tick() {
        for (ScheduledTask task : new ArrayList<>(tasks)) {
            try {
                task.tick();
            } catch (Exception ex) {
                System.err.println("Error while executing task:");
                ex.printStackTrace();
            }
        }

        tasks.removeIf(ScheduledTask::isDone);
    }

    public static void stop() {
        EXECUTOR.shutdownNow();
    }
}

class ScheduledTask {

    private final Runnable command;
    private final long period;

    private long clock;

    ScheduledTask(@NotNull Runnable command, long initialDelay, long period) {
        this.command = Objects.requireNonNull(command);
        this.period = period;
        this.clock = initialDelay;
    }

    public void tick() {
        this.clock--;
        if (this.clock <= 0) {

            this.command.run();

            if (this.period > 0) {
                this.clock = this.period;
            }
        }
    }

    public boolean isDone() {
        return this.clock == 0 && this.period == 0;
    }
}

