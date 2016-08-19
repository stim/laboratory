package nl.tbvh.lab.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import nl.tbvh.lab.reflection.Functions.Consumer;
import nl.tbvh.lab.reflection.Functions.Mapper;

public class Pipeline {

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
            if (interfaces != null && interfaces.length > 0) {
                System.out.println(prefix + "Interfaces: ");
                for (Type itype : interfaces) {
                    recursiveUnpack(itype, prefix + "\t");
                }
            }
        }
    }

    public static Pipeline produce(Functions.Producer ... producers) {
        return new Pipeline().produce2(producers);
    }

    public Pipeline produce2(Functions.Producer ... producers) {
        for (Functions.Producer producer : producers) {
            recursiveUnpack(producer.getClass(), "producer: ");
        }
        return this;
    }

    public Pipeline map(Functions.Mapper ... mappers) {
        for (Mapper mapper : mappers) {
            recursiveUnpack(mapper.getClass(), "Mapper: ");
        }
        return this;
    }

    public Pipeline consume(Functions.Consumer ... consumers) {
        for (Consumer consumer : consumers) {
            recursiveUnpack(consumer.getClass(), "Consumer: ");
        }
        return this;
    }
}
