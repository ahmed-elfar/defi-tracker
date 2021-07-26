package com.xyvo.defi.utils;

public enum BlockChain {

    ETHEREUM(1, "Ethereum Mainnet", "https://mainnet.infura.io/v3/9aa3d95b3bc440fa88ea12eaa4456161", "ETH"),
    BINANCE(56, "Smart Chain", "https://bsc-dataseed.binance.org/", "BNB"),
    POLYGON(137, "Matic Mainnet", "https://rpc-mainnet.maticvigil.com/", "MATIC");

    public final int chainId;
    public final String networkName;
    public final String rpcUrl;
    public final String symbol;

    BlockChain(int chainId, String networkName, String rpcUrl, String symbol) {
        this.chainId = chainId;
        this.networkName = networkName;
        this.rpcUrl = rpcUrl;
        this.symbol = symbol;
    }
}

