package nl.tbvh.lab.reactive.rx;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import nl.tbvh.lab.reactive.rx.ObservableBlockingness.Callback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.InOrder;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class ObservableSubscribedOnTest {
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
    public void zipOfTwoSubscribedOn() {
        ObservableBlockingness obsA = new ObservableBlockingness(callback)
            .startObserving()
            .withSubscribeOn();
        ObservableBlockingness obsB = new ObservableBlockingness(callback)
            .startObserving()
            .withSubscribeOn();
        Observable<Long> zipped = Observable.zip(obsA.observable, obsB.observable, zip());
        obs = new ObservableBlockingness(zipped);

        obs.withSubscribeOn()
            .thenSubscribe();
    }

    private Func2<Long, Long, Long> zip() {
        return new Func2<Long, Long, Long>() {

            @Override
            public Long call(Long a, Long b) {
                return a + b;
            }
        };
    }
}
