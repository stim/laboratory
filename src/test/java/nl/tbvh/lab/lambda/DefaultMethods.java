package nl.tbvh.lab.lambda;

import org.junit.Test;

public class DefaultMethods {

    @Test
    public void expectingDefaultViaLambda() {
        expectBar(() -> "LOL");
        // expectFoo(() -> "LOL");//The method expectFoo(DefaultMethods.Foo) in the type DefaultMethods is not applicable for the arguments (() -> {})
        expectFooBar(() -> "LOL");
        // expectAllDefaults(() -> "LOL");//The method expectAllDefaults(DefaultMethods.AllDefaults) in the type DefaultMethods is not applicable for the arguments (() -> {})
    }

    static interface Bar {
        String bar();
    }

    static interface Foo {
        default String foo() {
            return "foo";
        }
    }

    static interface FooBar extends Foo, Bar {

    }

    static interface AllDefaults extends FooBar {

        @Override
        default String bar() {
            return "bar";
        }
    }

    static void expectFoo(Foo foo) {
    }

    static void expectBar(Bar bar) {
    }

    static void expectFooBar(FooBar bar) {
    }

    static void expectAllDefaults(AllDefaults all) {
    }
}
