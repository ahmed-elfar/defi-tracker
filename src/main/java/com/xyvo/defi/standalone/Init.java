package com.xyvo.defi.standalone;

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
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.xyvo.defi.mapper.NetworkMapper.toDex;
import static com.xyvo.defi.mapper.NetworkMapper.toNetwork;

@Component
@Scope("prototype")
public class Init {

    private final NetWorkRepo netWorkRepo;
    private final DexRepo dexRepo;
    private final UserRepo userRepo;

    @Autowired
    public Init(NetWorkRepo netWorkRepo, DexRepo dexRepo, UserRepo userRepo) {
        this.netWorkRepo = netWorkRepo;
        this.dexRepo = dexRepo;
        this.userRepo = userRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void addNetworks() {
        for (BlockChain bc: BlockChain.values()) {
            Network netWork = toNetwork(bc);
            netWorkRepo.insert(netWork.getChainId(), netWork.getName(), netWork.getRpcUrl(), netWork.getSymbol());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void addDexS() {
        for (DexC dexC: DexC.values()) {
            Dex dex = toDex(dexC);
            dexRepo.insert(dexC.ordinal(), dex.getNetworkId(), dex.getName(), dex.getAddress(),
                    dex.getVersion(),dex.getTokenSymbol(), dex.getStatus());
        }

//        //TODO remove
//        List<Dex> l = new LinkedList<>();
//        for (DexC dexC: DexC.values()) {
//            Network n = new Network();
//            n.setChainId(1);
//            Dex dex = toDex(dexC);
//            dex.setNetWork(n);
//            l.add(dex);
//        }
//        l.forEach(x -> System.out.println(x));
//        dexRepo.saveAllAndFlush(l);

//        List<Integer> ids = new LinkedList<>();
//        for (DexC dexC: DexC.values()) {
//            ids.add(dexC.id);
//        }
//        dexRepo.findAllById(ids);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void importData() {
        ImportExportRunner.getInstance().runImportScript();
    }

//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void adduser() {
//        User user = new User();
//        user.setUserName("user1");
//        userRepo.save(user);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        user = new User();
//        user.setUserName("user2");
//        userRepo.save(user);
//    }

}
