package ru.effectivemobile.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.*
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.effectivemobile.domain.api.TestApi
import ru.effectivemobile.application.TestApplication
import ru.effectivemobile.domain.deserializers.GoodsQueryDeserializer
import ru.effectivemobile.model.GoodsQuery

import ru.effectivemobile.domain.repository.RemoteRepositoryImpl
import ru.effectivemobile.repository.GoodsQueryRepository
import ru.effectivemobile.repository.SmartphoneDetailsRepository
import ru.effectivemobile.ui.cart.CartFragment
import ru.effectivemobile.ui.explorer.ExplorerFragment
import ru.effectivemobile.ui.smartphonedetails.SmartphoneDetailsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    InjectionModule::class,
    BindingModule::class,
    CommonModule::class,
    AndroidInjectionModule::class
])
interface MainComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): MainComponent
    }

    fun inject(application: TestApplication)
}

@Module
abstract class InjectionModule {
    @ContributesAndroidInjector
    abstract fun explorerFragmentInjector(): ExplorerFragment

    @ContributesAndroidInjector
    abstract fun smartphoneDetailsFragmentInjector(): SmartphoneDetailsFragment

    @ContributesAndroidInjector
    abstract fun cartFragmentInjector(): CartFragment

}

@Module
interface BindingModule {
    @Singleton
    @Binds
    fun bindGoodsQueryRepository(impl: RemoteRepositoryImpl): GoodsQueryRepository

    @Singleton
    @Binds
    fun bindSmartphoneDetailsRepository(impl: RemoteRepositoryImpl): SmartphoneDetailsRepository
}

@Module
class CommonModule {
    @Singleton
    @Provides
    fun provideApi(): TestApi {
        val gson = GsonBuilder()
            .registerTypeAdapter(GoodsQuery::class.java, GoodsQueryDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TestApi::class.java)
    }

}


