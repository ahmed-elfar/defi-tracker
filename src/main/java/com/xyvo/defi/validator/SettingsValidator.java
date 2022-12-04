package com.xyvo.defi.validator;

import com.xyvo.defi.dto.SettingsDto;
import com.xyvo.defi.server.ValidationException;

public class SettingsValidator {

    public static void validateSettings (SettingsDto settingsDto) {
        //id check
        UserValidator.validateId(settingsDto.getId());

        //slippage check
        if(!(settingsDto.getSlippage() != null && settingsDto.getSlippage() >= 1 && settingsDto.getSlippage() <= 60)) {
            throw new ValidationException("slippage");
        }

        //interval check
        if(settingsDto.getOrderInterval() <= 10) {
            throw new ValidationException("Order Interval");
        }

        //liquidity check
        if(settingsDto.getMinLiquidity() <= 500) {
            throw new ValidationException("Order Interval");
        }

//        lockedLiquidityOnly;
//        Settings.Strategy buyStrategy;
//        Settings.Strategy sellStrategy;
//        Integer maxTrx;
//        Integer autoSellPercent;
//        Double trx;
    }
}
