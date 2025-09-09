package net.hecco.nexuslib.platform;

import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.platform.services.NLClientHelper;
import net.hecco.nexuslib.platform.services.NLNetworkingHelper;
import net.hecco.nexuslib.platform.services.NLRegistryHelper;
import net.hecco.nexuslib.platform.services.NLPlatformHelper;

import java.util.ServiceLoader;

public class NLServices {

    public static NLRegistryHelper REGISTRY = load(NLRegistryHelper.class);

    public static final NLPlatformHelper PLATFORM = load(NLPlatformHelper.class);

    public static NLNetworkingHelper NETWORK = load(NLNetworkingHelper.class);

    public static NLClientHelper client() {
        if (!PLATFORM.isClientSide()) {
            throw new IllegalStateException("Client helper requested on server!");
        }
        return load(NLClientHelper.class);
    }

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        NexusLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}