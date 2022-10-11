package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        Junit4Test junit4Test = clazz.getDeclaredConstructor().newInstance();
        Stream.of(clazz.getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(MyTest.class))
                .forEach(method -> invoke(junit4Test, method));
    }

    private void invoke(Junit4Test junit4Test, Method method) {
        try {
            method.invoke(junit4Test);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
