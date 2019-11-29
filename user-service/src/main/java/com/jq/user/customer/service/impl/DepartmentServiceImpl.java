package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.SysDeptDao;
import com.jq.user.customer.dao.SysUserDao;
import com.jq.user.customer.dto.SysDeptDTO;
import com.jq.user.customer.dto.SysUserDTO;
import com.jq.user.customer.entity.SysDeptEntity;
import com.jq.user.customer.entity.SysUserEntity;
import com.jq.user.customer.service.DepartmentInnerService;
import com.jq.user.exception.UserException;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.support.PageUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/14
 */

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentInnerService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Resource
    private SysDeptDao sysDeptDao;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private LogUserInnerService logUserInnerService;

    @Override
    public Boolean addSysDept(SysDeptEntity sysDeptEntity) {
        Date date = new Date();
        sysDeptEntity.setCreateTime(date);
        sysDeptEntity.setIsEnable(UserConstant.IS_T);
        sysDeptEntity.setUpdateTime(date);
        sysDeptEntity.setIsDel(UserConstant.IS_F);
        sysDeptEntity.setId(IdWorker.getId());
        Integer flag = sysDeptDao.insert(sysDeptEntity);
        return flag == 1;
    }

    @Override
    public Boolean updateSysDept(SysDeptEntity sysDeptEntity) {
        SysDeptEntity sysDept = new SysDeptEntity();
        sysDept.setId(sysDeptEntity.getId());
        sysDept.setSiteId(sysDeptEntity.getSiteId());
        SysDeptEntity dept = sysDeptDao.selectOne(new QueryWrapper<>(sysDept));
        if (dept == null) {
            throw new UserException(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        sysDeptEntity.setUpdateTime(new Date());
        Integer flag = sysDeptDao.updateById(sysDeptEntity);
        return flag == 1;
    }

    @Override
    public Boolean disableSysDept(SysDeptEntity sysDeptEntity) {
        if (sysDeptDao.selectById(sysDeptEntity.getId()) == null) {
            throw new UserException(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        sysDeptEntity.setIsEnable(UserConstant.IS_F);
        sysDeptEntity.setUpdateTime(new Date());
        Integer flag = sysDeptDao.updateById(sysDeptEntity);
        return flag == 1;
    }

    @Override
    public Boolean deleteSysDept(SysDeptEntity sysDeptEntity) {
        SysDeptEntity sysDept = new SysDeptEntity();
        sysDept.setId(sysDeptEntity.getId());
        sysDept.setSiteId(sysDeptEntity.getSiteId());
        sysDept.setIsDel(UserConstant.IS_F);
        SysDeptEntity dept = sysDeptDao.selectOne(new QueryWrapper<>(sysDept));
        if (dept == null) {
            throw new UserException(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        sysDeptEntity.setUpdateTime(new Date());
        sysDeptEntity.setIsDel(1);
        Integer flag = sysDeptDao.updateById(sysDeptEntity);
        return flag == 1;
    }

    @Override
    public Boolean enableSysDept(SysDeptEntity sysDeptEntity) {
        if (sysDeptDao.selectById(sysDeptEntity.getId()) == null) {
            throw new UserException(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        sysDeptEntity.setIsEnable(UserConstant.IS_T);
        Integer flag = sysDeptDao.updateById(sysDeptEntity);
        return flag == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> querySysDept(Page page, Long siteId) {
        QueryWrapper<SysDeptEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        if (siteId != null) {
            ew.eq("site_id", siteId);
        }
        IPage iPage = sysDeptDao.selectPage(page, ew);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultList", iPage.getRecords());
        resultMap.put("total", page.getTotal());
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public SysDeptEntity getSysDept(Long deptId, Long siteId) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(deptId);
        sysDeptEntity.setSiteId(siteId);
        SysDeptEntity sysDept = sysDeptDao.selectOne(new QueryWrapper<>(sysDeptEntity));
        if (sysDept == null) {
            throw new UserException(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        return sysDept;
    }

    @Override
    public ApiResult addSysDeptApi(SysDeptDTO sysDeptDTO, String updateUserName, String ip, String url) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setUserName(updateUserName);
            sysUserEntity.setIsDel(UserConstant.IS_F);
            if(StrUtil.isBlank(sysDeptDTO.getDeptName())){
                return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(),"请填写部门名称");
            }
            SysUserEntity updateSysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
            if (null == updateSysUser) {
                return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作人帐号不存在");
            }
            if (null == sysDeptDTO.getSiteId() || null == sysDeptDTO.getSiteCode()) {
                return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点信息不存在");
            }
            SysDeptEntity selectEntity = new SysDeptEntity();
            selectEntity.setIsDel(UserConstant.IS_F);
            selectEntity.setDeptName(sysDeptDTO.getDeptName());
            selectEntity.setSiteId(sysDeptDTO.getSiteId());
            SysDeptEntity resultDept = sysDeptDao.selectOne(new QueryWrapper<>(selectEntity));
            if(resultDept != null){
                return RPCResult.custom(UserCodeEnum.USER_INFORMATION_EXIST.getCode(),"部门名称已存在");
            }
            String userType = UserCfg.SYS;
            if (sysDeptDTO.getSiteId() != 0) {
                userType = UserCfg.SITE;
            }
            SysDeptEntity sysDeptEntity = new SysDeptEntity();
            Date date = new Date();
            BeanUtil.copyProperties(sysDeptDTO, sysDeptEntity);
            sysDeptEntity.setCreateBy(updateUserName);
            sysDeptEntity.setUpdateBy(updateUserName);
            sysDeptEntity.setUpdateTime(date);
            sysDeptEntity.setUpdateTime(date);
            Boolean flag = this.addSysDept(sysDeptEntity);
            if (flag) {
                logUserInnerService.addUserLog(updateSysUser.getId(), updateUserName, UserConstant.PC, "管理员：" + updateUserName + ",新增部门成功", UserCfg.ADD, userType, sysDeptDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
                return RPCResult.success();
            }
            return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "增加管理员部门失败");
    }

    @Override
    public ApiResult updateSysDeptApi(SysDeptDTO sysDeptDTO, String updateUserName, Long siteId, String ip, String url) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(updateUserName);
        sysUserEntity.setIsDel(UserConstant.IS_F);
        SysUserEntity updateSysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
        if (null == updateSysUser) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作人帐号不存在");
        }
        if (null == siteId) {
            return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点信息不存在");
        }
        String userType = UserCfg.SYS;
        if (siteId != 0L) {
            userType = UserCfg.SITE;
        }
        sysDeptDTO.setUpdateBy(updateUserName);
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        BeanUtil.copyProperties(sysDeptDTO, sysDeptEntity);
        Boolean flag = this.updateSysDept(sysDeptEntity);
        if (flag) {
            logUserInnerService.addUserLog(updateSysUser.getId(), updateUserName, UserConstant.PC, "管理员：" + updateUserName + ",修改部门成功", UserCfg.UPDATE, userType, sysDeptDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.success();
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "修改管理员部门失败");
    }

    @Override
    public ApiResult disabledSysDeptApi(Long deptId, String updateUserName, Long siteId, String ip, String url) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(updateUserName);
        sysUserEntity.setIsDel(UserConstant.IS_F);
        SysUserEntity updateSysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
        if (null == updateSysUser) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作人帐号不存在");
        }
        if (null == siteId) {
            return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点信息不存在");
        }
        String userType = UserCfg.SYS;
        if (siteId != 0L) {
            userType = UserCfg.SITE;
        }
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(deptId);
        Boolean flag = this.disableSysDept(sysDeptEntity);
        if (flag) {
            logUserInnerService.addUserLog(updateSysUser.getId(), updateUserName, UserConstant.PC, "管理员：" + updateUserName + ",禁用部门成功", UserCfg.ADD, userType, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.success();
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "禁用管理员部门失败");
    }

    @Override
    public ApiResult deleteSysDeptApi(Long deptId, String updateUserName, Long siteId, String ip, String url) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(updateUserName);
        sysUserEntity.setIsDel(UserConstant.IS_F);
        SysUserEntity updateSysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
        if (null == updateSysUser) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作人帐号不存在");
        }
        if (null == siteId) {
            return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点信息不存在");
        }
        String userType = UserCfg.SYS;
        if (siteId != 0L) {
            userType = UserCfg.SITE;
        }
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(deptId);
        sysDeptEntity.setUpdateBy(updateUserName);
        sysDeptEntity.setUpdateTime(new Date());
        Boolean flag = this.deleteSysDept(sysDeptEntity);
        if (flag) {
            logUserInnerService.addUserLog(updateSysUser.getId(), updateUserName, UserConstant.PC, "管理员：" + updateUserName + ",删除部门成功", UserCfg.ADD, userType, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.success();
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "删除管理员部门失败");
    }

    @Override
    public ApiResult enableSysDeptApi(Long deptId, String updateUserName, Long siteId, String ip, String url) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(updateUserName);
        sysUserEntity.setIsDel(UserConstant.IS_F);
        SysUserEntity updateSysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
        if (null == updateSysUser) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), "操作人帐号不存在");
        }
        if (null == siteId) {
            return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点信息不存在");
        }
        String userType = UserCfg.SYS;
        if (siteId != 0L) {
            userType = UserCfg.SITE;
        }
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(deptId);
        sysDeptEntity.setUpdateBy(updateUserName);
        Boolean flag = this.enableSysDept(sysDeptEntity);
        if (flag) {
            logUserInnerService.addUserLog(updateSysUser.getId(), updateUserName, UserConstant.PC, "管理员：" + updateUserName + ",启用部门成功", UserCfg.ADD, userType, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.success();
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "启用管理员部门失败");
    }

    @Override
    public ApiResult querySysDeptApi(SysDeptDTO sysDeptDTO) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        BeanUtil.copyProperties(sysDeptDTO, sysDeptEntity);
        Page page = PageUtil.buildPage(sysDeptDTO.getPage(), sysDeptDTO.getLimit(), sysDeptDTO.getOrderDirection(), sysDeptDTO.getOrderField());
        QueryWrapper<SysDeptEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        if (sysDeptEntity.getSiteId() != null) {
            ew.eq("site_id", sysDeptEntity.getSiteId());
        }
        if (sysDeptEntity.getDeptName() != null && !Objects.equals(sysDeptEntity.getDeptName(), "")) {
            ew.eq("dept_name", sysDeptEntity.getDeptName());
        }
        if (sysDeptEntity.getContact() != null && !Objects.equals(sysDeptEntity.getContact(), "")) {
            ew.eq("contact", sysDeptEntity.getContact());
        }
        IPage<SysDeptEntity> sysDeptEntityIPage = sysDeptDao.selectPage(page, ew);
        List<SysDeptDTO> listDTO = new ArrayList<>();
        for (SysDeptEntity sysDept : sysDeptEntityIPage.getRecords()) {
            SysDeptDTO finalSysDeptDTO = new SysDeptDTO();
            BeanUtil.copyProperties(sysDept, finalSysDeptDTO);
            listDTO.add(finalSysDeptDTO);
        }
        PageInfo<SysDeptDTO> pageInfo = new PageInfo<>(listDTO, sysDeptDTO.getPage(), sysDeptDTO.getLimit(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<List<SysDeptDTO>> querySysDeptListApi(Long siteId) {
        QueryWrapper<SysDeptEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("site_id", siteId);
        List<SysDeptEntity> lists = sysDeptDao.selectList(ew);
        List<SysDeptDTO> finalList = new ArrayList<>();
        for (SysDeptEntity sysDeptEntity : lists) {
            SysDeptDTO sysDeptDTO = new SysDeptDTO();
            BeanUtil.copyProperties(sysDeptEntity, sysDeptDTO);
            finalList.add(sysDeptDTO);
        }
        return RPCResult.success(finalList);
    }

    @Override
    public ApiResult<SysDeptDTO> getSysDeptApi(Long deptId, Long siteId) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(deptId);
        sysDeptEntity.setSiteId(siteId);
        sysDeptEntity.setIsDel(UserConstant.IS_F);
        SysDeptEntity sysDept = sysDeptDao.selectOne(new QueryWrapper<>(sysDeptEntity));
        if (sysDept == null) {
            return RPCResult.custom(UserCodeEnum.SYS_DEPT_NOT_EXIST.getCode(), UserCodeEnum.SYS_DEPT_NOT_EXIST.getMessage());
        }
        SysDeptDTO sysDeptDTO = new SysDeptDTO();
        BeanUtil.copyProperties(sysDept, sysDeptDTO);
        return RPCResult.success(sysDeptDTO);
    }

    @Override
    public ApiResult verifySysDeptNameApi(Long siteId, String sysDeptName) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setIsDel(UserConstant.IS_F);
        sysDeptEntity.setDeptName(sysDeptName);
        sysDeptEntity.setSiteId(siteId);
        SysDeptEntity sysDept = sysDeptDao.selectOne(new QueryWrapper<>(sysDeptEntity));
        return ObjectUtil.isNull(sysDept) ? RPCResult.success() : RPCResult.fail();

    }

    @Override
    public ApiResult<List<SysUserDTO>> querySysUserByDeptIdApi(Long deptId) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("dept_id", deptId);
        ew.eq("is_del", UserConstant.IS_F);
        List<SysUserEntity> lists = sysUserDao.selectList(ew);
        List<SysUserDTO> finalList = new ArrayList<>();
        SysUserDTO sysUserDTO = new SysUserDTO();
        for (SysUserEntity sysUser : lists) {
            BeanUtil.copyProperties(sysUser, sysUserDTO);
            finalList.add(sysUserDTO);
        }
        return RPCResult.success(finalList);
    }
}
