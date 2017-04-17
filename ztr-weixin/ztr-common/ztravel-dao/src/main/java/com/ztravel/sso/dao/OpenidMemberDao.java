package com.ztravel.sso.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.sso.po.OpenidMemberEntity;

public interface OpenidMemberDao extends BaseDao<OpenidMemberEntity> {

    void updateOpenidMemberToUnBind(OpenidMemberEntity openidMember);

}
