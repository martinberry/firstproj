package com.ztravel.rbac.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.po.UserListResponse;
import com.ztravel.rbac.po.UserListSearchRequest;
import com.ztravel.rbac.po.UserSaveRequest;
import com.ztravel.rbac.service.RoleService;
import com.ztravel.rbac.service.UserService;

/**
 *
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/rbac/user")
public class UserController {

	private static Logger logger = RequestIdentityLogger.getLogger(RoleController.class);

	@Resource
	private RoleService roleServiceImpl;

	@Resource
	private UserService userServiceImpl;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getUserListMain(Model model){

		List<Role> roles = null;
		try{
			roles = roleServiceImpl.getAllRoles();
		}catch(SQLException ex){
	    	logger.error("failed to get all roles from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1001, Const.FO_RBAC_REASON_1001) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1002, Const.FO_RBAC_REASON_1002) ;
		}

		model.addAttribute("roles", roles);

		return "operator/user/user_list";

	}

	@RequestMapping(value="/role/get", method = RequestMethod.POST)
	public String getUserAllRoles(Model model){

		List<Role> roles = null;
		try{
			roles = roleServiceImpl.getAllRoles();
		}catch(SQLException ex){
	    	logger.error("failed to get all roles from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1001, Const.FO_RBAC_REASON_1001) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1002, Const.FO_RBAC_REASON_1002) ;
		}

		model.addAttribute("roles", roles);

		return "operator/user/user_role_dropdown";

	}


	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String getUserListPage(@RequestBody UserListSearchRequest request, Model model) throws Exception{

		List<UserListResponse> users = new ArrayList<>();
		Integer userNum = 0;
		try{
			userNum = userServiceImpl.getAllUsersWithPage(request, users);
		} catch(SQLException ex){
	    	logger.error("failed to get all users from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1010, Const.FO_RBAC_REASON_1010) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1011, Const.FO_RBAC_REASON_1011) ;
		}

		model.addAttribute("users", users);

		if( userNum != 0 ){
			model.addAttribute("pageNo", request.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", request.getPageSize());
		model.addAttribute("totalItemCount", userNum);
		Integer totalPageCount = (int) Math.ceil( (double)userNum/request.getPageSize() );
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		model.addAttribute("totalPageCount", totalPageCount);

		return "operator/user/user_list_tbody";
	}

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteUser(@RequestParam(value="userId") String userId) throws Exception{

		if( StringUtils.isBlank(userId)){
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1012, Const.FO_RBAC_REASON_1012);
		}

		try{
			userServiceImpl.deleteUser(userId);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1013, Const.FO_RBAC_REASON_1013);
		}

		return AjaxResponse.instance(Const.SO_RBAC_CODE_1003, Const.SUCCESS);

	}

	@RequestMapping(value="/suspend/{type}", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifyUserStatus(@PathVariable("type") String type, @RequestParam(value="userId") String userId){
		try{
			if( type.equals("lock") ){               //挂起
				userServiceImpl.modifyUserStatus(userId, false);
			}else if( type.equals("unlock") ){    //解挂
				userServiceImpl.modifyUserStatus(userId, true);
			}
		} catch (SQLException ex){
			logger.error("failed to modify user status in DB", ex);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1014, Const.FO_RBAC_REASON_1014);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1015, Const.FO_RBAC_REASON_1015);
		}

		return AjaxResponse.instance(Const.SO_RBAC_CODE_1004, Const.SUCCESS);
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveUser(@RequestBody UserSaveRequest request, Model model) throws Exception{

		try{
			if( StringUtils.isBlank(request.getUserId())){
				userServiceImpl.insertUser(request);
			}else{
				userServiceImpl.updateUser(request);
			}
		} catch(SQLException se){
	    	logger.error(se.getMessage(), se);
	    	return AjaxResponse.instance(Const.FO_RBAC_CODE_1016, Const.FO_RBAC_REASON_1016) ;
	    }catch(ZtrBizException ze){
	    	logger.error(ze.getRetMsg(), ze);
	    	return AjaxResponse.instance(ze.getRetCode(), ze.getRetMsg());
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1017, Const.FO_RBAC_REASON_1017);
		}

		return AjaxResponse.instance(Const.SO_RBAC_CODE_1005, Const.SUCCESS);

	}

}
