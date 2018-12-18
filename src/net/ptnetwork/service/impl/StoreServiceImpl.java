/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.ProductDao;
import net.ptnetwork.dao.StoreDao;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.BusinessDepositLog;
import net.ptnetwork.entity.CategoryApplication;
import net.ptnetwork.entity.ProductCategory;
import net.ptnetwork.entity.Store;
import net.ptnetwork.plugin.PromotionPlugin;
import net.ptnetwork.plugin.discountPromotion.DiscountPromotionPlugin;
import net.ptnetwork.plugin.fullReductionPromotion.FullReductionPromotionPlugin;
import net.ptnetwork.service.BusinessService;
import net.ptnetwork.service.MailService;
import net.ptnetwork.service.SmsService;
import net.ptnetwork.service.StoreService;
import net.ptnetwork.service.UserService;

/**
 * Service - 店铺
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

	@Inject
	private StoreDao storeDao;
	@Inject
	private ProductDao productDao;
	@Inject
	private UserService userService;
	@Inject
	private BusinessService businessService;
	@Inject
	private MailService mailService;
	@Inject
	private SmsService smsService;

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeDao.exists("name", name, true);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(Long id, String name) {
		return storeDao.unique(id, "name", name, true);
	}

	public boolean productCategoryExists(Store store, final ProductCategory productCategory) {
		Assert.notNull(productCategory);
		Assert.notNull(store);

		return CollectionUtils.exists(store.getProductCategories(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ProductCategory storeProductCategory = (ProductCategory) object;
				return storeProductCategory != null && storeProductCategory == productCategory;
			}
		});
	}

	@Transactional(readOnly = true)
	public Store findByName(String name) {
		return storeDao.find("name", name, true);
	}

	@Transactional(readOnly = true)
	public List<Store> findList(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Integer first, Integer count) {
		return storeDao.findList(type, status, isEnabled, hasExpired, first, count);
	}

	@Transactional(readOnly = true)
	public List<ProductCategory> findProductCategoryList(Store store, CategoryApplication.Status status) {
		return storeDao.findProductCategoryList(store, status);
	}

	@Transactional(readOnly = true)
	public Page<Store> findPage(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Pageable pageable) {
		return storeDao.findPage(type, status, isEnabled, hasExpired, pageable);
	}

	@Transactional(readOnly = true)
	public Store getCurrent() {
		Business currentUser = userService.getCurrent(Business.class);
		return currentUser != null ? currentUser.getStore() : null;
	}

	@CacheEvict(value = "authorization", allEntries = true)
	public void addEndDays(Store store, int amount) {
		Assert.notNull(store);

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Date now = new Date();
		Date currentEndDate = store.getEndDate();
		if (amount > 0) {
			store.setEndDate(DateUtils.addDays(currentEndDate.after(now) ? currentEndDate : now, amount));
		} else {
			store.setEndDate(DateUtils.addDays(currentEndDate, amount));
		}
		storeDao.flush();
	}

	public void addDiscountPromotionEndDays(Store store, int amount) {
		Assert.notNull(store);

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Date now = new Date();
		Date currentDiscountPromotionEndDate = store.getDiscountPromotionEndDate() != null ? store.getDiscountPromotionEndDate() : now;
		if (amount > 0) {
			store.setDiscountPromotionEndDate(DateUtils.addDays(currentDiscountPromotionEndDate.after(now) ? currentDiscountPromotionEndDate : now, amount));
		} else {
			store.setDiscountPromotionEndDate(DateUtils.addDays(currentDiscountPromotionEndDate, amount));
		}
		storeDao.flush();
	}

	public void addFullReductionPromotionEndDays(Store store, int amount) {
		Assert.notNull(store);

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Date now = new Date();
		Date currentFullReductionPromotionEndDate = store.getFullReductionPromotionEndDate() != null ? store.getFullReductionPromotionEndDate() : now;
		if (amount > 0) {
			store.setFullReductionPromotionEndDate(DateUtils.addDays(currentFullReductionPromotionEndDate.after(now) ? currentFullReductionPromotionEndDate : now, amount));
		} else {
			store.setFullReductionPromotionEndDate(DateUtils.addDays(currentFullReductionPromotionEndDate, amount));
		}
		storeDao.flush();
	}

	@CacheEvict(value = "authorization", allEntries = true)
	public void addBailPaid(Store store, BigDecimal amount) {
		Assert.notNull(store);
		Assert.notNull(amount);

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(storeDao.getLockMode(store))) {
			storeDao.flush();
			storeDao.refresh(store, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(store.getBailPaid());
		Assert.state(store.getBailPaid().add(amount).compareTo(BigDecimal.ZERO) >= 0);

		store.setBailPaid(store.getBailPaid().add(amount));
		storeDao.flush();
	}

	@CacheEvict(value = "authorization", allEntries = true)
	public void review(Store store, boolean passed, String content) {
		Assert.notNull(store);
		Assert.state(Store.Status.pending.equals(store.getStatus()));
		Assert.state(passed || StringUtils.isNotEmpty(content));

		if (passed) {
			BigDecimal serviceFee = store.getStoreRank().getServiceFee();
			BigDecimal bail = store.getStoreCategory().getBail();
			if (serviceFee.compareTo(BigDecimal.ZERO) <= 0 && bail.compareTo(BigDecimal.ZERO) <= 0) {
				store.setStatus(Store.Status.success);
				store.setEndDate(DateUtils.addYears(new Date(), 1));
			} else {
				store.setStatus(Store.Status.approved);
				store.setEndDate(new Date());
			}
			smsService.sendApprovalStoreSms(store);
			mailService.sendApprovalStoreMail(store);
		} else {
			store.setStatus(Store.Status.failed);
			smsService.sendFailStoreSms(store, content);
			mailService.sendFailStoreMail(store, content);
		}
	}

	public void buy(Store store, PromotionPlugin promotionPlugin, int months) {
		Assert.notNull(store);
		Assert.notNull(promotionPlugin);
		Assert.state(promotionPlugin.getIsEnabled());
		Assert.state(months > 0);

		BigDecimal amount = promotionPlugin.getPrice().multiply(new BigDecimal(months));
		Business business = store.getBusiness();
		Assert.state(business.getBalance() != null && business.getBalance().compareTo(amount) >= 0);

		int days = months * 30;
		if (promotionPlugin instanceof DiscountPromotionPlugin) {
			addDiscountPromotionEndDays(store, days);
			businessService.addBalance(business, amount.negate(), BusinessDepositLog.Type.svcPayment, null);
		} else if (promotionPlugin instanceof FullReductionPromotionPlugin) {
			addFullReductionPromotionEndDays(store, days);
			businessService.addBalance(business, amount.negate(), BusinessDepositLog.Type.svcPayment, null);
		}
	}

	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void expiredStoreProcessing() {
		productDao.refreshExpiredStoreProductActive();
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public Store update(Store store) {
		productDao.refreshActive(store);

		return super.update(store);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public Store update(Store store, String... ignoreProperties) {
		return super.update(store, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "authorization", "product", "productCategory" }, allEntries = true)
	public void delete(Store store) {
		super.delete(store);
	}

}