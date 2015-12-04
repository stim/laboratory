package nl.tbvh.lab.reactive.rx;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import nl.tbvh.lab.reactive.rx.ObservableDsl.Callback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.InOrder;

import rx.functions.Func1;

public class ObservableSubscribedOnTest {
    ObservableDsl obs;
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
        obs = new ObservableDsl(callback);
        inOrder = inOrder(callback);
    }

    @Test
    public void zipOfTwoSubscribedOnSubscribedOn() {
        ObservableDsl obsA = new ObservableDsl(callback)
            .startObserving()
            .withSubscribeOn();
        ObservableDsl obsB = new ObservableDsl(callback)
            .startObserving()
            .withSubscribeOn();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped.withSubscribeOn()
            .thenSubscribe()
            .thenWait();
    }

    @Test
    public void zipOfTwoSubscribedOn() {
        ObservableDsl obsA = new ObservableDsl(callback)
            .startObserving()
            .withSubscribeOn();
        ObservableDsl obsB = new ObservableDsl(callback)
            .startObserving()
            .withSubscribeOn();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped
            .thenSubscribe()
            .thenWait();
    }

    @Test
    public void zipOfTwo() {
        ObservableDsl obsA = new ObservableDsl(callback)
            .startObserving();
        ObservableDsl obsB = new ObservableDsl(callback)
            .startObserving();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped.withSubscribeOn()
            .thenSubscribe()
            .thenWaitLong();
    }

}
