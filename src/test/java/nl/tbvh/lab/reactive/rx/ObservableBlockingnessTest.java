package nl.tbvh.lab.reactive.rx;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import nl.tbvh.lab.reactive.rx.ObservableBlockingness.Callback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.InOrder;

import rx.functions.Func1;

public class ObservableBlockingnessTest {
    ObservableBlockingness obs;
    private Func1<Callback, Void> callback;
    private InOrder inOrder;

    @Rule
    public TestName testName = new TestName();

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        System.out.println();
        System.out.println();
        System.out.println("Now running: " + testName.getMethodName());
        callback = mock(Func1.class);
        obs = new ObservableBlockingness(callback);
        inOrder = inOrder(callback);
    }

    @Test
    public void subscribeShouldBlock() {
        obs.startObserving()
            .thenSubscribe()
            .thenCallback();

        inOrder.verify(callback).call(Callback.ONNEXT);
        inOrder.verify(callback).call(Callback.COMPLETED);
        inOrder.verify(callback).call(Callback.CALL);
    }

    @Test
    public void subscribeOnShouldNotBlock() {
        obs.startObserving()
            .withSubscribeOn()
            .thenSubscribe()
            .thenCallback()
            .thenWait();

        inOrder.verify(callback).call(Callback.CALL);
        inOrder.verify(callback).call(Callback.ONNEXT);
        inOrder.verify(callback).call(Callback.COMPLETED);
    }

    @Test
    public void subscribeWithReplaySubjectShouldBlock() {
        obs.startObserving()
            .withReplaySubject()
            .thenSubscribe()
            .thenCallback();

        inOrder.verify(callback).call(Callback.ONNEXT);
        inOrder.verify(callback).call(Callback.COMPLETED);
        inOrder.verify(callback).call(Callback.CALL);
    }

    @Test
    public void subscribeWithReplaySubjectSubscribeOnShouldBlock() {
        obs.startObserving()
            .withReplaySubject() // Blocking here
            .withSubscribeOn()
            .thenSubscribe()
            .thenCallback();

        inOrder.verify(callback).call(Callback.ONNEXT);
        inOrder.verify(callback).call(Callback.COMPLETED);
        inOrder.verify(callback).call(Callback.CALL);
    }

    @Test
    public void withReplaySubjectIsBlocking() {
        obs.startObserving()
            .deferCallback()
            .thenCallback()
            .withReplaySubject() // Blocking here
            .thenCallback();

        inOrder.verify(callback).call(Callback.CALL);
        inOrder.verify(callback).call(Callback.DEFERED);
        inOrder.verify(callback).call(Callback.CALL);
    }

    @Test
    public void subscribeOnWithReplaySubjectShouldNotBlock() {
        obs.startObserving()
            .withSubscribeOn()
            .withReplaySubject()
            .thenSubscribe()
            .thenCallback()
            .thenWait();

        inOrder.verify(callback).call(Callback.CALL);
        inOrder.verify(callback).call(Callback.ONNEXT);
        inOrder.verify(callback).call(Callback.COMPLETED);
    }
}
