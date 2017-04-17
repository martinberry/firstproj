package com.ztravel.sso.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.sso.dao.OpenidMemberDao;
import com.ztravel.sso.po.OpenidMemberEntity;

@Repository
public class OpenidMemberDaoImpl extends BaseDaoImpl<OpenidMemberEntity> implements OpenidMemberDao {

    private static String UPDATE_TO_UNBIND = ".updateToUnBind";

    @Override
    public void updateOpenidMemberToUnBind(OpenidMemberEntity openidMember) {
        session.update(nameSpace + UPDATE_TO_UNBIND, openidMember);
    }

}
