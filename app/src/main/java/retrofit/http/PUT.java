package retrofit.http;

import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@RestMethod(hasBody = Droid2InoConstants.D, value = "PUT")
public @interface PUT {
    String value();
}
