package ch.heigvd.amt.unicorns.api.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.isGetter;

@Component
public class PayloadVerification {
    /**
     * This method check that the payload is valid. A valid payload mean that
     * all value were given and that they were not empty
     * @param payload the payload that we want to test if it's valid
     * @param list the list with all getter of the payload's class
     * @return true is the payload is valid, false otherwise
     */
    private boolean checkPayloadIsValidImplementation(Object payload, List<Method> list) {

        Object object;
        for (Method method : list) {
            try {
                object = method.invoke(payload);
                if (object == null || object.toString().isEmpty()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will return the list of all getter of a class
     * @param c the class that we want to analyse
     * @return a list of method
     */
    public boolean checkPayloadIsValid(Class<?> c, Object payload) {
        ArrayList<Method> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isGetter(method))
                list.add(method);
        return checkPayloadIsValidImplementation(payload, list);
    }
}
