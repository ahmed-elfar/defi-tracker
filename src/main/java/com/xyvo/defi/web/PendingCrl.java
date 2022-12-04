package com.xyvo.defi.web;

import com.xyvo.defi.dto.PendingDto;
import com.xyvo.defi.service.PendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.xyvo.defi.utils.WebUtils.PENDING_ALL;
import static com.xyvo.defi.utils.WebUtils.PENDING_BY_NETWORK;

@RestController
class PendingCrl {

    private PendingService pendingService;

    @Autowired
    public PendingCrl(PendingService pendingService) {
        this.pendingService = pendingService;
    }

    @GetMapping(PENDING_ALL)
    @ResponseStatus(HttpStatus.OK)
    public Collection<PendingDto> getAllPending() {
        return pendingService.getPendingForAllNetworks();
    }

    @GetMapping(PENDING_BY_NETWORK)
    @ResponseStatus(HttpStatus.OK)
    public List<PendingDto> getAllPendingForNetwork(@PathVariable(name = "networkId") Integer id) {
        return pendingService.getAllPendingForNetwork(id);
    }

    @PostMapping(PENDING_ALL)
    @ResponseStatus(HttpStatus.CREATED)
    public PendingDto getOrCreate(@RequestBody PendingDto pendingDto) {
        return pendingService.getOrCreate(pendingDto);
    }
}
