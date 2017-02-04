package com.example.dagger2andretrofit2.root;

import com.example.dagger2andretrofit2.MainActivity;
import com.example.dagger2andretrofit2.http.ApiModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity target);
}
