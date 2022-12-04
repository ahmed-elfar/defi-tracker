package com.xyvo.defi.repository.api;

import com.xyvo.defi.domain.profile.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepo extends JpaRepository<Settings, Long> {
}
