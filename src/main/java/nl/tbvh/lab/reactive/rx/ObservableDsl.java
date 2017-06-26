package nl.tbvh.lab.reactive.rx;

import static com.google.common.base.Preconditions.checkState;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

public class ObservableDsl {
    private static int instanceCounter = 0;

    enum Callback {
        CREATED, COMPLETED, ERROR, ONNEXT, CALL, DEFERED, ZIPPED
    }

    private static int msPerTicks = 100;

    private final int id = instanceCounter++;
    private final Func1<Callback, Void> callback;
    Observable<Long> observable;

    private ObservableDsl(Func1<Callback, Void> callback) {
        this.callback = callback;
    }

    private ObservableDsl(Func1<Callback, Void> callback, Observable<Long> observable) {
        this.callback = callback;
        this.observable = observable;
    }

    public static ObservableDsl start(Func1<Callback, Void> callback) {
        return new ObservableDsl(callback)
            .startObserving();
    }

    private ObservableDsl startObserving() {
        checkState(observable == null);
        observable = Observable.create(new Sleeper());
        callBack(Callback.CREATED);
        return this;
    }

    public ObservableDsl thenSubscribe() {
        observable
            .subscribe(new Spy());
        return this;
    }

    /**
     * Sleeps a short while to prevent race condition when other callbacks run in other threads.
     */
    public ObservableDsl thenCallback() {
        sleep(1, "For callback, to prevent race condition.");
        callBack(Callback.CALL);
        return this;
    }

    public ObservableDsl thenWait() {
        return thenWait(5);
    }

    public ObservableDsl thenWaitLong() {
        return thenWait(15);
    }

    public ObservableDsl thenWait(int ticks) {
        sleep(ticks, "Waiting..");
        return this;
    }

    public ObservableDsl withSubscribeOn() {
        observable = observable.subscribeOn(Schedulers.newThread());
        return this;
    }

    public ObservableDsl withReplaySubject() {
        final ReplaySubject<Long> subject = ReplaySubject.create();
        observable.subscribe(subject);
        observable = subject;
        return this;
    }

    public ObservableDsl zippedWith(ObservableDsl other) {
        Observable<Long> zipped = Observable.zip(observable, other.observable, zipper());
        return new ObservableDsl(callback, zipped);
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

    class Sleeper implements Observable.OnSubscribe<Long> {

        @Override
        public void call(Subscriber<? super Long> t1) {
            println("subscribed");
            sleep(3, "In Observable.");
            t1.onNext(1L);
            t1.onCompleted();
        }
    }

    private void sleep(int ticks, String msg) {
        int ms = ticks * msPerTicks;
        try {
            println("Sleep " + ticks + " ticks. " + msg);
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void println(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        long threadId = Thread.currentThread().getId();
        String str = String.format("%s Id: %2d Thread %2d> %s", strDate, id, threadId, msg);
        System.out.println(str);
    }

    /*
     * Schedules a callback.
     */
    public ObservableDsl deferCallback() {
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

    private Func2<Long, Long, Long> zipper() {
        return new Func2<Long, Long, Long>() {

            @Override
            public Long call(Long a, Long b) {
                callBack(Callback.ZIPPED);
                return a + b;
            }
        };
    }
}
