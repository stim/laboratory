package nl.tbvh.lab.so;

/**
 * http://stackoverflow.com/questions/30384955/java-generics-puzzler
 */
public class NestedGenerics {
    static class Addable<T> {
        void add(T t) {
        }
    }

    static class Tbin<T> extends Addable<T> {
    }

    static class TbinList<T> extends Addable<Tbin<T>> {
    }

    static class Base {
    }

    static class Derived extends Base {
    }

    public static void main(String[] args) {
        Addable<Tbin<? extends Base>> test = new Addable<>();
        test.add(new Tbin<Derived>());

        TbinList<? extends Base> test2 = new TbinList<>();
        // test2.add(new Tbin<Derived>());

        Addable<? extends Tbin<? extends Base>> test3 = new Addable<Tbin<Base>>();
        // ArrayList<Tbin<? extends Base>> test3 = new TbinList<>();
        // test2.add(new Tbin<Derived>());
    }

    // Replacing the definition of TbinList with
    // class TbinList<T> extends ArrayList<Tbin<? extends T>> {}
    // and defining test2 with
    // TbinList<Base> test2 = new TbinList<>();
    // instead would solve the issue.
    // With your definition you're ending up with an ArrayList<Tbin<T>> where T is any fixed class extending Base.

}
