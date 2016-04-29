package h4311.hexanome.insa.lyonrewards;


import javax.inject.Singleton;

import dagger.Component;
import h4311.hexanome.insa.lyonrewards.di.component.AppComponent;
import h4311.hexanome.insa.lyonrewards.di.module.AppModule;
import h4311.hexanome.insa.lyonrewards.di.module.api.NetModule;
import h4311.hexanome.insa.lyonrewards.di.module.auth.AuthModule;
import h4311.hexanome.insa.lyonrewards.di.module.image.ImageModule;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, ImageModule.class, AuthModule.class})
public interface MockAppComponent extends AppComponent {
    void inject(ApiTest apiTest);
}