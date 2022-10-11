package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Junit3TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Junit3Test junit3Test = clazz.getDeclaredConstructor().newInstance();
        Stream.of(clazz.getDeclaredMethods())
                .filter(it -> it.getName().startsWith("test"))
                .forEach(method -> invoke(junit3Test, method));
    }

    private void invoke(Junit3Test junit3Test, Method method) {
        try {
            method.invoke(junit3Test);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
