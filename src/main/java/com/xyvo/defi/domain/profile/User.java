package com.xyvo.defi.domain.profile;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

import static com.xyvo.defi.utils.DomainUtils.DOMAIN_SCHEMA;
import static com.xyvo.defi.utils.DomainUtils.USER_NAME_LENGTH;

@Entity
@Table(name = "user", schema = DOMAIN_SCHEMA)
@Cacheable
@org.hibernate.annotations.Cache(region = "UserCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends Audited {

//    @Transient
//    private static final transient String ID_GEN = "id_gen1";
//
//    @Id
//    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = USER_NAME_LENGTH)
    private String userName;

    @OneToMany(targetEntity = Address.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne(targetEntity = Settings.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Settings settings;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
