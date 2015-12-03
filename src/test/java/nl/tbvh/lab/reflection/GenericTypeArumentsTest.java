package nl.tbvh.lab.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

public class GenericTypeArumentsTest {
    static interface StringToIntMap extends Map<String, Integer> {
        Map<Integer, String> invertMap();
    }

    Class<?> c = StringToIntMap.class;

    @Test
    public void genericTypeOfInterfaceIsStringAndInteger() throws NoSuchMethodException {
        System.out.println();
        System.out.println("genericTypeOfInterfaceIsStringAndInteger");

        print(c);
    }

    @Test
    public void genericTypeOfMethodIsIntegerAndString() throws NoSuchMethodException, SecurityException {
        System.out.println();
        System.out.println("genericTypeOfMethodIsIntegerAndString");

        print(c.getMethod("invertMap").getReturnType());
    }

    @Test
    public void genericTypeOfMethodUsingGuava2() throws NoSuchMethodException, SecurityException, NoSuchFieldException {
        TypeToken<?> typeToken = TypeToken.of(getClass().getDeclaredField("c").getGenericType())
                .resolveType(Map.class.getTypeParameters()[0]);

        typeToken = TypeToken.of(StringToIntMap.class.getMethod("invertMap").getGenericReturnType());
        System.out.println(typeToken);
        // Class<?> type = (Class<?>) typeToken.getType();
        Class<?> type = typeToken.getRawType();
        print(type);
    }

    @Test
    public void genericTypeOfMethodUsingGuava() throws NoSuchMethodException, SecurityException {
        System.out.println();
        System.out.println("genericTypeOfMethodUsingGuava");

        Method method = StringToIntMap.class.getMethod("invertMap");
        Invokable<StringToIntMap, Object> invokable = new TypeToken<StringToIntMap>() {
        }.method(method);
        TypeToken<?> typeToken = invokable.getReturnType();
        System.out.println("Type " + typeToken);
        print(typeToken.getRawType());

        TypeToken<?> resolvedType = TypeToken.of(StringToIntMap.class)
                .resolveType(StringToIntMap.class.getMethod("invertMap").getGenericReturnType());
        System.out.println("ResolvedType " + resolvedType);
        Type type = resolvedType.getType();
        // print(type);
        System.out.println(type);
    }

    private void print(Class<?> c) throws NoSuchMethodException {
        System.out.println("Class: " + c);

        Type[] genericInterfaces = c.getGenericInterfaces();
        System.out.println("GenericInterfaces: " + Arrays.toString(genericInterfaces));

        TypeVariable<?>[] typeParameters = c.getTypeParameters();
        System.out.println("TypeParameters: " + Arrays.toString(typeParameters));

        ParameterizedType genericSuperclass = (ParameterizedType) c.getGenericSuperclass();
        if (genericSuperclass == null) {
            System.out.println("GenericSuperclass: null");
        } else {
            System.out.println("GenericSuperclass: " + Arrays.toString(genericSuperclass.getActualTypeArguments()));
        }
    }
}
