package com.k7.configuration;

import com.k7.annotation.ReadProp;
import lombok.Data;

@Data
public class SystemProperties {
    @ReadProp("contactbook.profile")
    private String profile;
}
