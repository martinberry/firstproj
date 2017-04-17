package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 用户后台模块配置，需要和后端分开，在必要的时候做项目拆分
 * @author liuzhuo
 *
 */

@Configuration
@ComponentScan({"com.ztravel.member.opera.controller","com.ztravel.member.opera.dao.impl","com.ztravel.member.opera.service.impl"})
public class MemberOperaModuleConfig {

}
