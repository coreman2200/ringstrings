package com.coreman2200.ringstrings.app.di

import android.content.Context
import com.coreman2200.ringstrings.data.di.DataSourceModule
import com.coreman2200.ringstrings.data.di.RepositoryModule
import com.coreman2200.ringstrings.domain.di.DomainLayerModule
import com.coreman2200.ringstrings.presentation.di.ApplicationScope
import com.coreman2200.ringstrings.presentation.di.PresentationModule
import dagger.Component
import dagger.Module
import dagger.Provides

@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationModule::class, DomainLayerModule::class,
        RepositoryModule::class, DataSourceModule::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule): ApplicationComponent
    }


}

@Module
class UtilsModule(private val ctx: Context) {

    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context = ctx

}