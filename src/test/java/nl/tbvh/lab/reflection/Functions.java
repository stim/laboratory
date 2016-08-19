package nl.tbvh.lab.reflection;

public final class Functions {
    private Functions() {
        // ...
    }

    interface VarArgFunction {
        // ... marker ...
    }

    interface Producer extends VarArgFunction {
        // ... marker ...
    }

    interface Mapper extends VarArgFunction {
        // ... marker ...
    }

    interface Consumer extends VarArgFunction {
        // ... marker ...
    }

    public interface Producer0<A> extends Producer {
        A apply();
    }

    public interface Mapper1<A, B> extends Mapper {
        B apply(A a);
    }

    public interface Mapper2<A, B, C> extends Mapper {
        C apply(A a, B b);
    }

    public interface Consumer1<A> extends Consumer {
        void apply(A a);
    }

    public interface Consumer2<A, B> extends Consumer {
        void apply(A a, B b);
    }
}
