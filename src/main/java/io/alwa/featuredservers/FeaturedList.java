package io.alwa.featuredservers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeaturedList {

    public static final Map<String, FeaturedServerData> servers = new HashMap<>();

    public void doFeaturedListStuff(FeaturedServers.ServerDataHelper[] featuredList) {
        if (featuredList != null) {
            ServerList serverList = new ServerList(MinecraftClient.getInstance());
            for (FeaturedServers.ServerDataHelper serverhelp : featuredList) {
                FeaturedServerData server = new FeaturedServerData(serverhelp.serverName, serverhelp.serverIP, false, serverhelp.disableButtons);
                if(serverhelp.forceResourcePack != null && serverhelp.forceResourcePack) server.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
                if (inList(server, serverList)) {
                    FeaturedServers.LOGGER.log(Level.INFO, "Featured server already in server list");
                } else {
                    FeaturedServers.LOGGER.log(Level.INFO, "Adding featured server");
                    serverList.add(server);
                    serverList.saveFile();
                }
                servers.put(server.address, server);
            }
        }
    }

    public static boolean inList(ServerInfo server, ServerList list) {
        return list != null && toList(list).stream().anyMatch(serverData -> serverData.name != null && serverData.address != null
                && serverData.name.equalsIgnoreCase(server.name) && serverData.address.equalsIgnoreCase(server.address));
    }

    public static List<ServerInfo> toList(ServerList list) {
        List<ServerInfo> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            data.add(list.get(i));
        }
        return data;
    }

    public static class FeaturedServerData extends ServerInfo {

        public final boolean disableButtons;

        public FeaturedServerData(String name, String ip, boolean forceResourcePack, Boolean disableButtons) {
            super(name, ip, forceResourcePack);
            this.disableButtons = disableButtons != null && disableButtons;
        }
    }

}
