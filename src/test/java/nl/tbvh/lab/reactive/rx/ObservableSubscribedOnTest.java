package nl.tbvh.lab.reactive.rx;

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
        // obs = ObservableDsl.start(callback);
        // inOrder = inOrder(callback);
    }

    @Test
    public void zipOfTwoSubscribedOnSubscribedOn() {
        ObservableDsl obsA = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl obsB = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped.withSubscribeOn()
            .thenSubscribe()
            .thenWait();
    }

    @Test
    public void zipOfTwoSubscribedOn() {
        ObservableDsl obsA = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl obsB = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped
            .thenSubscribe()
            .thenWait();
    }

    @Test
    public void zipOfOnlySecondSubscribedOn() {
        ObservableDsl obsA = ObservableDsl.start(callback);
        ObservableDsl obsB = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped
            .thenSubscribe()
            .thenWait();
    }

    @Test
    public void zipOfOnlyFirstSubscribedOn() {
        ObservableDsl obsA = ObservableDsl.start(callback)
            .withSubscribeOn();
        ObservableDsl obsB = ObservableDsl.start(callback);
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped
            .thenSubscribe();
    }

    @Test
    public void zipOfTwo() {
        ObservableDsl obsA = ObservableDsl.start(callback);
        ObservableDsl obsB = ObservableDsl.start(callback);
        ObservableDsl zipped = obsA.zippedWith(obsB);

        zipped.withSubscribeOn()
            .thenSubscribe()
            .thenWaitLong();
    }

}
