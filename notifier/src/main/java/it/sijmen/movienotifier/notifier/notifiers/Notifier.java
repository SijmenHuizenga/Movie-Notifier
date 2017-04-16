package it.sijmen.movienotifier.notifier.notifiers;

import com.google.inject.BindingAnnotation;
import it.sijmen.movienotifier.data.Recipient;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sijmen on 7-4-2017.
 */
public abstract class Notifier {

    public abstract void notify(Recipient recipient, String message) throws IOException;

    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SMS {}

    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MAIL {}

    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FBMSG {}

}
