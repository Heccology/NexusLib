package net.hecco.heccolib.platform;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.hecco.heccolib.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static HLRegistryHelper REGISTRIES = load(HLRegistryHelper.class);

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        HeccoLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}