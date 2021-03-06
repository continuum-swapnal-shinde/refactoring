package continuum.cucumber.PageKafkaConfigUtilities;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * defines a name of the field that will be used to soft collection: Collection.sort(ArrayList<Map<Object,Object>, AbstractDTO)</>>)
 */
public @interface SortBy {
    String value() default "";
}
