package com.xyvo.defi.mapper;

import com.xyvo.defi.domain.profile.Settings;
import com.xyvo.defi.dto.SettingsDto;
import com.xyvo.defi.validator.SettingsValidator;


public class SettingsMapper {

    private SettingsMapper(){}

    public static SettingsDto toDto(Settings settings) {
        SettingsDto settingsDto = new SettingsDto();
        AuditedMapper.mapTimeStamp(settingsDto, settings);
        settingsDto.setId(settings.getId());
        settingsDto.setBuyStrategy(settings.getBuyStrategy());
        settingsDto.setLockedLiquidityOnly(settings.getLockedLiquidityOnly());
        settingsDto.setMaxTrx(settings.getMaxTrx());
        settingsDto.setMinLiquidity(settings.getMinLiquidity());
        settingsDto.setTrxValue(settings.getTrxValue());
        settingsDto.setSlippage(settings.getSlippage());
        settingsDto.setSellStrategy(settings.getSellStrategy());
        settingsDto.setAutoSellPercent(settings.getAutoSellPercent());
        settingsDto.setOrderInterval(settings.getExchangeEvery());
        return settingsDto;
    }

    public static Settings toEntity(SettingsDto settingsDto) {
        SettingsValidator.validateSettings(settingsDto);
        Settings settings = new Settings();
        settings.setId(settingsDto.getId());
        settings.setBuyStrategy(settingsDto.getBuyStrategy());
        settings.setLockedLiquidityOnly(settingsDto.getLockedLiquidityOnly());
        settings.setMaxTrx(settingsDto.getMaxTrx());
        settings.setMinLiquidity(settingsDto.getMinLiquidity());
        settings.setTrxValue(settingsDto.getTrxValue());
        settings.setSlippage(settingsDto.getSlippage());
        settings.setSellStrategy(settingsDto.getSellStrategy());
        settings.setAutoSellPercent(settingsDto.getAutoSellPercent());
        settings.setExchangeEvery(settingsDto.getOrderInterval());
        return settings;
    }

}
