package net.ptnetwork.controller.admin.gencode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-10-24
 * @Version V1.0
 */
@Component
public class CodeConst {

    private String ipPort;
    private String defaultPwd;

    @Value("${svn.ipPort}")
    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getIpPort() {
        return String.format("svn://%s", ipPort);
    }

    public String getDefaultPwd() {
        return defaultPwd;
    }

    @Value("${svn.defaultPwd}")
    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd;
    }



}
