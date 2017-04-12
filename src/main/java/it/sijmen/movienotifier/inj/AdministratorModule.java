package it.sijmen.movienotifier.inj;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import it.sijmen.movienotifier.str.Recipient;

import javax.inject.Named;

/**
 * Created by Sijmen on 12-4-2017.
 */
public class AdministratorModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Inject
    @Provides
    @Named("administrator")
    public Recipient adminProvider(
            @Named("admin-name") String name,
            @Named("admin-phone") String phone,
            @Named("admin-mail") String mail){
        return new Recipient(name, mail, phone);
    }
}
