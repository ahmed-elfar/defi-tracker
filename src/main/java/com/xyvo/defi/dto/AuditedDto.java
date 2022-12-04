package com.xyvo.defi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyvo.defi.utils.DomainUtils;

import java.sql.Timestamp;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public abstract class AuditedDto {

    @JsonIgnore
    protected Timestamp createdTimestamp;
    @JsonIgnore
    protected Timestamp updatedTimestamp;
    protected String createdString;
    protected String updatedString;

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    @JsonProperty(value = "created")
    public void setCreatedString(String createdString) {
        this.createdString = createdString;
    }

    @JsonProperty("updated")
    public void setUpdatedString(String updatedString) {
        this.updatedString = updatedString;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        this.createdString = DomainUtils.timestampToString(createdTimestamp);
    }

    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        this.updatedString = DomainUtils.timestampToString(updatedTimestamp);
    }

    public String getCreatedString() {
        return createdString;
    }

    public String getUpdatedString() {
        return updatedString;
    }

    public AuditedDto removeNanos() {
        DomainUtils.timestampFormatter(createdTimestamp);
        DomainUtils.timestampFormatter(updatedTimestamp);
        return this;
    }
}
