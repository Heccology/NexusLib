package net.hecco.heccolib.platform;

import net.fabricmc.api.EnvType;
import net.hecco.heccolib.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isDatagen() {
        try {
            Class.forName("net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint");
            return System.getProperty("fabric-api.datagen") != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
