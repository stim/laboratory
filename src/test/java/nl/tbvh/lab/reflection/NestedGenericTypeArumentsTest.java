package nl.tbvh.lab.reflection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

public class NestedGenericTypeArumentsTest {

    private NestedGenericTypeAruments instance;

    @Test
    public void obtainTypes() {
        instance = new NestedGenericTypeAruments() {

            @Override
            public String contains(List<String> a) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        instance = new NGTA();

        Class<? extends NestedGenericTypeAruments> clazz = instance.getClass();
        System.out.println(clazz.getName());
        Type type = clazz.getInterfaces()[0];
        ParameterizedType superType = (ParameterizedType) type;
        Type[] typeArguments = superType.getActualTypeArguments();

        ParameterizedType nestedType = (ParameterizedType) typeArguments[0];
        Type[] nestedTypeArguments = nestedType.getActualTypeArguments();

        assertEquals("java.lang.String", nestedTypeArguments[0].getTypeName());
    }

    @Test
    public void unpackTypes() {
        instance = new NGTA();
        instance = new NestedGenericTypeAruments() {

            @Override
            public String contains(List<String> a) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        recursiveUnpack(instance.getClass(), "");

    }

    void recursiveUnpack(Type type, String prefix) {
        System.out.println(prefix + type.getTypeName());
        if (!type.getTypeName().startsWith("nl.tbvh") && !type.getTypeName().startsWith("java.util")) {
            return;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            System.out.println(prefix + "Generic arguments: ");
            Type[] actualTypeArguments = ptype.getActualTypeArguments();
            for (Type typeArgument : actualTypeArguments) {
                recursiveUnpack(typeArgument, prefix + "\t");
            }
        }
        if (type instanceof Class) {
            Class<?> clazz = (Class) type;
            Type superclass = clazz.getGenericSuperclass();
            if (superclass != null) {
                System.out.println(prefix + "Superclass: ");
                recursiveUnpack(superclass, prefix + "\t");
            }
            Type[] interfaces = clazz.getGenericInterfaces();
            if (interfaces != null) {
                System.out.println(prefix + "Interfaces: ");
                for (Type itype : interfaces) {
                    recursiveUnpack(itype, prefix + "\t");
                }
            }
        }
    }

    public static interface Nester<A> {
        public String contains(A a);
    }

    public static interface NestedGenericTypeAruments extends Nester<List<String>> {
    }

    public static class NGTA implements NestedGenericTypeAruments {

        @Override
        public String contains(List<String> a) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
