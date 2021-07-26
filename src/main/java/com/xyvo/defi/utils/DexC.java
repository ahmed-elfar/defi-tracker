package com.xyvo.defi.utils;

public enum DexC {

    UNI_SWAP_V3(BlockChain.ETHEREUM.chainId, "UniswapV3", "x01", "v3", null, false),
    UNI_SWAP_V2(BlockChain.ETHEREUM.chainId, "UniswapV2", "x02", "v2", null, false),

    PAN_CAKE_SWAP(BlockChain.BINANCE.chainId, "PancakeSwap", "x03", "v2", null, false),

    QUICK_SWAP(BlockChain.POLYGON.chainId, "QuickSwap", "x04", "v2", null, true),
    SUSHI_SWAP(BlockChain.POLYGON.chainId, "SushiSwap", "x05", "v2", null, false);

    public final int chainId;
    public final String dexName;
    public final String address;
    public final String version;
    public final String tokenSymbol;
    public final Boolean status;

    DexC(int chainId, String dexName, String address, String version, String tokenSymbol, Boolean status) {
        this.chainId = chainId;
        this.dexName = dexName;
        this.address = address;
        this.version = version;
        this.tokenSymbol = tokenSymbol;
        this.status = status;
    }
}
