package com.xyvo.defi.mapper;

import com.xyvo.defi.domain.netwrok.Dex;
import com.xyvo.defi.domain.netwrok.Network;
import com.xyvo.defi.utils.BlockChain;
import com.xyvo.defi.utils.DexC;

public final class NetworkMapper {

    private NetworkMapper() {
    }

    public static Network toNetwork(BlockChain blockChain) {
        Network netWork = new Network();
        netWork.setChainId(blockChain.chainId);
        netWork.setName(blockChain.networkName);
        netWork.setRpcUrl(blockChain.rpcUrl);
        netWork.setSymbol(blockChain.symbol);
        return netWork;
    }

    public static Dex toDex(DexC dexCIn) {
        Dex dex = new Dex();
        dex.setNetworkId(dexCIn.chainId);
        dex.setName(dexCIn.dexName);
        dex.setAddress(dexCIn.address);
        dex.setTokenSymbol(dexCIn.tokenSymbol);
        dex.setStatus(dexCIn.status);
        dex.setVersion(dexCIn.version);
        return dex;
    }

}
