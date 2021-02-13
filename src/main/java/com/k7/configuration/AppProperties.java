package com.k7.configuration;

import com.k7.annotation.ReadProp;
import lombok.Data;

@Data
public class AppProperties {
    @ReadProp("api.base-uri")
    private String baseUri;
    @ReadProp("app.service.workmode")
    private String workmode;
    @ReadProp("file.path")
    private String filePath;
}
