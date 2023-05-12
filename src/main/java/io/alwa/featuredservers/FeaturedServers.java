package io.alwa.featuredservers;

import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FeaturedServers implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    private static ServerInfo currentServerInfo;
    private Path fmlConfigFolder;

    @Override
    public void onInitializeClient() {
        fmlConfigFolder = FabricLoaderImpl.INSTANCE.getConfigDir();
        try {
            doClientStuff();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void doClientStuff() throws IOException {
        Path featuredServerList = Paths.get(fmlConfigFolder.toAbsolutePath().toString(), "featuredservers.json");
        if(!Files.exists(featuredServerList)) {
            Files.writeString(featuredServerList,
                    """
                    [
                        {
                            "serverName": "Featured Server",
                            "serverIP": "127.0.0.1",
                            "forceResourcePack": "true",
                            "disableButtons": "true"
                        },
                        {
                            "serverName": "Another Server!",
                            "serverIP": "192.168.1.1",
                            "forceResourcePack": "false",
                            "disableButtons": "false"
                        }
                    ]
                    """, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        }
        String json = Files.readString(featuredServerList);
        new FeaturedList().doFeaturedListStuff(new Gson().fromJson(json, ServerDataHelper[].class));
    }

    public static ServerInfo getCurrentServerInfo() {
        return currentServerInfo;
    }

    public static void setCurrentServerInfo(@NotNull ServerInfo info) {
        currentServerInfo = info;
    }


    public static class ServerDataHelper {
        private String serverName;
        private String serverIP;
        private Boolean forceResourcePack;
        private Boolean disableButtons;

        public String getServerName() {
            return serverName;
        }

        public String getServerIP() {
            return serverIP;
        }

        public Boolean doesForceResourcePack() {
            return forceResourcePack;
        }

        public Boolean doesDisableButtons() {
            return disableButtons;
        }
    }
}
