package com.k7.configuration;

import com.k7.annotation.ReadProp;
import lombok.Data;

@Data
public class DbProperties {
    @ReadProp("db.jdbc.url")
    private String jdbcUrl;
    @ReadProp("db.username")
    private String dbUser;
    @ReadProp("db.pass")
    private String dbPass;
}
