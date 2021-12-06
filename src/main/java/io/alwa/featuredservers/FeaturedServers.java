package io.alwa.featuredservers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FeaturedServers implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    private static File FMLConfigFolder;

    @Override
    public void onInitializeClient() {
        FMLConfigFolder = FabricLoaderImpl.INSTANCE.getConfigDir().toFile();
        try {
            doClientStuff();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void doClientStuff() throws IOException {
        File featuredServerList = new File(FMLConfigFolder, "featuredservers.json");
        if (!featuredServerList.exists()) {
            featuredServerList.createNewFile();
            FileWriter writer = new FileWriter(featuredServerList);
            writer.write("""
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
                    ]""");
            writer.close();
        }

        FileReader serversFile = new FileReader(featuredServerList.getPath());

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(serversFile);
        ServerDataHelper[] featuredList = gson.fromJson(reader, ServerDataHelper[].class);
        new FeaturedList().doFeaturedListStuff(featuredList);
    }

    public static class ServerDataHelper {

        public String serverName;
        public String serverIP;
        public Boolean forceResourcePack;
        public Boolean disableButtons;
    }
}
