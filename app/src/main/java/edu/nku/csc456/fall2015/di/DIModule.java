package edu.nku.csc456.fall2015.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import edu.nku.csc456.fall2015.repository.ChapterRepository;
import edu.nku.csc456.fall2015.repository.ChapterSharedPrefRepository;
import edu.nku.csc456.fall2015.service.ApiServiceFactory;
import edu.nku.csc456.fall2015.service.Csc456ApiService;

/**
 * Created by Benjamin on 8/23/2015.
 */
@Module
public class DIModule {

    Context context;

    public DIModule(Context context) {
        this.context = context;
    }

    @Provides
    public ChapterRepository chapterRepository(Context context) {
        return new ChapterSharedPrefRepository(context);
    }

    @Provides
    public Csc456ApiService csc456ApiService() {
        return ApiServiceFactory.getCsc456ApiService();
    }

    @Provides
    public Context provideContext() {
        return context;
    }

}
