/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.listener;

import net.ptnetwork.entity.Article;
import net.ptnetwork.entity.Product;
import net.ptnetwork.entity.Store;
import net.ptnetwork.service.ConfigService;
import net.ptnetwork.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Listener - 初始化
 *
 * @author PTNETWORK Team
 * @version 5.0
 */
@Component
public class InitListener {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitListener.class);

    @Value("${system.version}")
    private String systemVersion;

    @Inject
    private ConfigService configService;
    @Inject
    private SearchService searchService;

    /**
     * 事件处理
     *
     * @param contextRefreshedEvent ContextRefreshedEvent
     */
    @EventListener
    public void handle(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
            return;
        }

        String info = "Initializing PTNETWORK " + systemVersion;
        LOGGER.info(info);
        configService.init();
        searchService.index(Article.class);
        searchService.index(Product.class);
        searchService.index(Store.class);
    }

}
