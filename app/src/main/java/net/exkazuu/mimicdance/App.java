package net.exkazuu.mimicdance;

import com.deploygate.sdk.DeployGate;

public class App extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this);
    }
}
