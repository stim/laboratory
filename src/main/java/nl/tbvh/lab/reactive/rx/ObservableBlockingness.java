package nl.tbvh.lab.reactive.rx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

public class ObservableBlockingness {
    enum Callback {
        COMPLETED, ERROR, ONNEXT, CALL, DEFERED
    }

    private static int msPerTicks = 100;

    private final Func1<Callback, Void> callback;
    private Observable<Long> observable;

    public ObservableBlockingness(Func1<Callback, Void> callback) {
        this.callback = callback;
    }

    public ObservableBlockingness startObserving() {
        observable = Observable.create(new Sleeper());
        return this;
    }

    public ObservableBlockingness thenSubscribe() {
        observable
            .subscribe(new Spy());
        return this;
    }

    /**
     * Sleeps a short while to prevent race condition when other callbacks run in other threads.
     */
    public ObservableBlockingness thenCallback() {
        sleep(1, "For callback, to prevent race condition.");
        callBack(Callback.CALL);
        return this;
    }

    public ObservableBlockingness thenWait() {
        sleep(5, "Waiting..");
        return this;
    }

    public ObservableBlockingness withSubscribeOn() {
        observable = observable.subscribeOn(Schedulers.newThread());
        return this;
    }

    public ObservableBlockingness withReplaySubject() {
        final ReplaySubject<Long> subject = ReplaySubject.create();
        observable.subscribe(subject);
        observable = subject;
        return this;
    }

    private void callBack(Callback reason) {
        println(reason.name());
        callback.call(reason);
    }

    class Spy extends Subscriber<Long> {

        @Override
        public void onCompleted() {
            callBack(Callback.COMPLETED);
        }

        @Override
        public void onError(Throwable e) {
            callBack(Callback.ERROR);
        }

        @Override
        public void onNext(Long t) {
            callBack(Callback.ONNEXT);
        }
    }

    static class Sleeper implements Observable.OnSubscribe<Long> {

        @Override
        public void call(Subscriber<? super Long> t1) {
            println("subscribed");
            sleep(3, "In Observable.");
            t1.onNext(1L);
            t1.onCompleted();
        }
    }

    private static void sleep(int ticks, String msg) {
        int ms = ticks * msPerTicks;
        try {
            println("Sleep " + ticks + " tick(s). " + msg);
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void println(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS ");
        Date now = new Date();
        String strDate = sdf.format(now);
        long id = Thread.currentThread().getId();
        System.out.println(strDate + "Thread " + id + "> " + msg);
    }

    public ObservableBlockingness deferCallback() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        int ticks = 2;
        executor.schedule(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                callBack(Callback.DEFERED);
                return null;
            }
        }, ticks * msPerTicks, TimeUnit.MILLISECONDS);
        println("Scheduled DEFERED in " + ticks + " tick(s)");
        return this;
    }
}
