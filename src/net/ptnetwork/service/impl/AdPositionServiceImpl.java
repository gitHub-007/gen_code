/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.dao.AdPositionDao;
import net.ptnetwork.entity.AdPosition;
import net.ptnetwork.service.AdPositionService;

/**
 * Service - 广告位
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class AdPositionServiceImpl extends BaseServiceImpl<AdPosition, Long> implements AdPositionService {

	@Inject
	private AdPositionDao adPositionDao;

	@Transactional(readOnly = true)
	@Cacheable(value = "adPosition", condition = "#useCache")
	public AdPosition find(Long id, boolean useCache) {
		return adPositionDao.find(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition save(AdPosition adPosition) {
		return super.save(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition) {
		return super.update(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition, String... ignoreProperties) {
		return super.update(adPosition, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(AdPosition adPosition) {
		super.delete(adPosition);
	}

}