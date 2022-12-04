package com.xyvo.defi.mapper;

import com.xyvo.defi.domain.profile.Audited;
import com.xyvo.defi.dto.AuditedDto;

import java.sql.Timestamp;

public class AuditedMapper {

    private AuditedMapper() {
    }

    public static void mapTimeStamp(AuditedDto auditedDto, Audited audited) {
        auditedDto.setCreatedTimestamp(new Timestamp(audited.getCreated().getTime()));
        auditedDto.setUpdatedTimestamp(new Timestamp(audited.getUpdated().getTime()));
    }
}
