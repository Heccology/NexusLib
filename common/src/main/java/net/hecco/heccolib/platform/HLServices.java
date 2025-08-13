package net.hecco.heccolib.platform;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.lib.compat.CompatManager;
import net.hecco.heccolib.platform.services.HLClientHelper;
import net.hecco.heccolib.platform.services.HLNetworkingHelper;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.hecco.heccolib.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class HLServices {

    public static HLRegistryHelper REGISTRY = load(HLRegistryHelper.class);

    public static HLClientHelper CLIENT = load(HLClientHelper.class);

    public static HLNetworkingHelper NETWORK = load(HLNetworkingHelper.class);

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static final CompatManager COMPATMANAGER = load(CompatManager.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        HeccoLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    public static <T> T loadClient(Class<T> clazz) {
        if () {
            final T loadedService = ServiceLoader.load(clazz)
                    .findFirst()
                    .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
            HeccoLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
            return loadedService;
        }
        return null;
    }
}