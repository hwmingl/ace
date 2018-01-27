package com.fighter.ace.cms.service.external.impl;


import com.fighter.ace.cms.dao.external.NoticeDao;
import com.fighter.ace.cms.entity.external.Notice;
import com.fighter.ace.cms.service.external.NoticeService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Ma Senyu on 2016/6/26 0026.
 *
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	@Override
	public long addNotice(Notice notice) {
		Long noticeId = noticeDao.insert(notice);
		return noticeId;
	}

	@Override
	public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {

		return noticeDao.listPage(pageParam,paramMap);
	}

	@Override
	public int deleteNotice(int id) {
		return noticeDao.deleteById(id);
	}

	@Override
	public int batchDeleteNotice(int[] ids) {
		return noticeDao.batchDelete(ids);
	}

	@Override
	public Notice getById(int id) {
		Notice notice =noticeDao.getById(id);
		return notice;
	}

	@Override
	public int updateNotice(Notice notice) {

		return noticeDao.update(notice);
	}

	@Override
	public int checkNotice(int[] ids) {
		return 0;
	}
}
