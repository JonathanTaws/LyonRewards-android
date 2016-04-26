package h4311.hexanome.insa.lyonrewards;


import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface MockAppComponent extends AppComponent {
    void inject(ApiTest apiTest);
}