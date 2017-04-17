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
import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.rbac.service.PermissionCommonService;
import com.ztravel.common.rbac.wo.PermissionWo;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.po.RoleListResponse;
import com.ztravel.rbac.po.RoleSaveRequest;
import com.ztravel.rbac.service.RoleService;

/**
 *
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/rbac/role")
public class RoleController {

	private static Logger logger = RequestIdentityLogger.getLogger(RoleController.class);

	@Resource
	private RoleService roleServiceImpl;

	@Resource
	private PermissionCommonService permissionCommonServiceImpl;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getRoleListMain(){

		return "operator/role/role_list";

	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String addRole(Model model){

		List<PermissionWo> allPerms = null;
		try{
			allPerms = permissionCommonServiceImpl.getAllPermissions();
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1006, Const.FO_RBAC_REASON_1006) ;
		}

		model.addAttribute("allPerms", allPerms);

		return "operator/role/role_edit";

	}

	@RequestMapping(value="/copy/{roleId}", method = RequestMethod.GET)
	public String copyRole(@PathVariable("roleId") String roleId, Model model){

		if( StringUtils.isBlank(roleId)){
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1003, Const.FO_RBAC_REASON_1003);
		}

		Role role = new Role();
		List<String> permissions = null;
		List<PermissionWo> allPerms = null;
		try{
			role = roleServiceImpl.getRoleById(roleId);
			permissions = roleServiceImpl.getPermissionByRoleId(roleId);
			allPerms = permissionCommonServiceImpl.getAllPermissions();
		} catch(SQLException ex){
	    	logger.error("failed to get role detail from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1005, Const.FO_RBAC_REASON_1005) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1006, Const.FO_RBAC_REASON_1006) ;
		}

		role.setRoleId("");
		model.addAttribute("role", role);
		model.addAttribute("permissions", permissions);
		model.addAttribute("allPerms", allPerms);

		return "operator/role/role_edit";

	}

	@RequestMapping(value="/edit/{roleId}", method = RequestMethod.GET)
	public String editRole(@PathVariable("roleId") String roleId, Model model){

		if( StringUtils.isBlank(roleId)){
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1003, Const.FO_RBAC_REASON_1003);
		}

		Role role = new Role();
		List<String> permissions = null;
		List<PermissionWo> allPerms = null;
		try{
			role = roleServiceImpl.getRoleById(roleId);
			permissions = roleServiceImpl.getPermissionByRoleId(roleId);
			allPerms = permissionCommonServiceImpl.getAllPermissions();
		} catch(SQLException ex){
	    	logger.error("failed to get role detail from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1005, Const.FO_RBAC_REASON_1005) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1006, Const.FO_RBAC_REASON_1006) ;
		}

		model.addAttribute("role", role);
		model.addAttribute("permissions", permissions);
		model.addAttribute("allPerms", allPerms);

		return "operator/role/role_edit";

	}

	@RequestMapping(value="/list/page", method = RequestMethod.POST)
	public String getRoleListPage(@RequestBody PaginationEntity page, Model model) throws Exception{

		List<RoleListResponse> roles = new ArrayList<>();
		Integer roleNum = 0;
		try{
			roleNum = roleServiceImpl.getAllRolesWithPage(page, roles);
		} catch(SQLException ex){
	    	logger.error("failed to get all roles from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_RBAC_CODE_1001, Const.FO_RBAC_REASON_1001) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1002, Const.FO_RBAC_REASON_1002) ;
		}

		model.addAttribute("roles", roles);

		if( roleNum != 0 ){
			model.addAttribute("pageNo", page.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", page.getPageSize());
		model.addAttribute("totalItemCount", roleNum);
		Integer totalPageCount = (int) Math.ceil( (double)roleNum/page.getPageSize() );
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		model.addAttribute("totalPageCount", totalPageCount);

		return "operator/role/role_list_tbody";
	}

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteRole(@RequestParam(value="roleId") String roleId) throws Exception{

		if( StringUtils.isBlank(roleId)){
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1003, Const.FO_RBAC_REASON_1003);
		}

		try{
			roleServiceImpl.deleteRole(roleId);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1004, Const.FO_RBAC_REASON_1004);
		}

		return AjaxResponse.instance(Const.SO_RBAC_CODE_1001, Const.SUCCESS);

	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveRole(@RequestBody RoleSaveRequest request, Model model) throws Exception{

		try{
			if( StringUtils.isBlank(request.getRoleId())){
				roleServiceImpl.insertRole(request);
			}else{
				roleServiceImpl.updateRole(request);
			}
		} catch(SQLException se){
	    	logger.error(se.getMessage(), se);
	    	return AjaxResponse.instance(Const.FO_RBAC_CODE_1008, Const.FO_RBAC_REASON_1008) ;
	    }catch(ZtrBizException ze){
	    	logger.error("the role has been in DB", ze);
	    	return AjaxResponse.instance(Const.FO_RBAC_CODE_1007, Const.FO_RBAC_REASON_1007);
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FO_RBAC_CODE_1009, Const.FO_RBAC_REASON_1009);
		}

		return AjaxResponse.instance(Const.SO_RBAC_CODE_1002, Const.SUCCESS);

	}

}
