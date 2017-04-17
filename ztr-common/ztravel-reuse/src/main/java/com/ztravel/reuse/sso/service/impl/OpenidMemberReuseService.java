package com.ztravel.reuse.sso.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.reuse.sso.service.IOpenidMemberReuseService;
import com.ztravel.sso.dao.OpenidMemberDao;
import com.ztravel.sso.po.OpenidMemberEntity;

/**
 * @author wanhaofan
 */
@Service
public class OpenidMemberReuseService implements IOpenidMemberReuseService {

    @Resource
    private OpenidMemberDao openidMemberDaoImpl;

    @Override
    public void insertOpenidMember(OpenidMemberEntity openidMember) {
        openidMemberDaoImpl.insert(openidMember);
    }

    @Override
    public void updateOpenidMember(OpenidMemberEntity openidMember) {
        openidMemberDaoImpl.update(openidMember);
    }

    @Override
    public void updateOpenidMemberToUnBind(OpenidMemberEntity openidMember) {
        openidMemberDaoImpl.updateOpenidMemberToUnBind(openidMember);
    }

    @Override
    public void deleteOpenidMemberByOpenId(String openId) {
        openidMemberDaoImpl.deleteById(openId);

    }

    @Override
    public OpenidMemberEntity selectOpenidMemberByOpenId(String openId) {
        return openidMemberDaoImpl.selectById(openId);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<OpenidMemberEntity> selectOpenidMemberBymemberId(String memberId) {
        Map params = new HashMap();
        params.put("memberId", memberId);
        return openidMemberDaoImpl.select(params);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int countOpenidMemberBymemberId(String memberId) {
        Map params = new HashMap();
        params.put("memberId", memberId);
        return openidMemberDaoImpl.count(params);
    }

}
