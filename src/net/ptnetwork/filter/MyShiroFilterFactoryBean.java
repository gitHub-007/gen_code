package net.ptnetwork.filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

//    @Resource
//    AuthorityService authorityService;

    /**
     * 初始化设置过滤链
     */
    @Override
    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        //记录配置的静态过滤链
        Map<String, List<String>> perms = new HashMap<>();
//        // 数据库中的url权限
//        List<Authority> permissions = authorityService.findAll();
//        permissions.forEach(permiss -> {
//            String url = permiss.getUrl();
//            String itemCode = permiss.getCode();
//            if (perms.containsKey(url)) {
//                perms.get(url).add(itemCode);
//            } else {
//                List<String> list = new ArrayList<>();
//                list.add(itemCode);
//                perms.put(url, list);
//            }
//        });
        String string = "perms[\"%s\"]";
        Map<String, String> otherChains =
                perms.entrySet().stream().collect(Collectors.toMap((entry) -> entry.getKey(),
                                                                   (entry) -> String.format(string, String.join(",",
                                                                                                                entry.getValue()))));
        //加上数据库中过滤链
        if (filterChainDefinitionMap == null) filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.putAll(otherChains);
        super.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
}
