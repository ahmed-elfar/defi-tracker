package com.xyvo.defi.standalone;

import com.xyvo.defi.domain.profile.Settings;
import com.xyvo.defi.domain.profile.User;
import com.xyvo.defi.domain.netwrok.Dex;
import com.xyvo.defi.domain.netwrok.Network;

import com.xyvo.defi.repository.DexRepo;
import com.xyvo.defi.repository.NetWorkRepo;
import com.xyvo.defi.repository.UserRepo;
import com.xyvo.defi.utils.BlockChain;
import com.xyvo.defi.utils.DexC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.xyvo.defi.mapper.NetworkMapper.toDex;
import static com.xyvo.defi.mapper.NetworkMapper.toNetwork;

@Component
@Scope("prototype")
public class StandaloneInit {

    private final NetWorkRepo netWorkRepo;
    private final DexRepo dexRepo;
    private final UserRepo userRepo;
    private final JdbcTemplate jdbcTemplate;
    private static boolean invokedOnce = false;

    @Autowired
    public StandaloneInit(NetWorkRepo netWorkRepo, DexRepo dexRepo, UserRepo userRepo, JdbcTemplate jdbcTemplate) {
        this.netWorkRepo = netWorkRepo;
        this.dexRepo = dexRepo;
        this.userRepo = userRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void create(Environment env) {
        ImportExportRunner.create(env);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        if(invokedOnce) return;
        invokedOnce = true;
        addNetworks();
        addDexS();
        importData();
        //addUsers();
    }

    private void addNetworks() {
        for (BlockChain bc: BlockChain.values()) {
            Network netWork = toNetwork(bc);
            netWorkRepo.insert(netWork.getChainId(), netWork.getName(), netWork.getRpcUrl(), netWork.getSymbol());
        }
    }

    private void addDexS() {
        for (DexC dexC: DexC.values()) {
            Dex dex = toDex(dexC);
            dexRepo.insert(dexC.ordinal(), dex.getNetworkId(), dex.getName(), dex.getAddress(),
                    dex.getVersion(),dex.getTokenSymbol(), dex.getStatus());
        }
    }

    private void importData() {
        ImportExportRunner.getInstance().runImportScript(jdbcTemplate.getDataSource());
    }

    private void addUsers() {
        //TODO remove
        User user = new User();
        user.setUserName("user3");
        user.setSettings(Settings.getDefaultSettings());
        userRepo.save(user);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = new User();
        user.setUserName("user4");
        user.setSettings(Settings.getDefaultSettings());
        userRepo.save(user);
    }

}
