package com.jq.user.customer.fallbackfactory;

import com.jq.user.customer.dto.SysDeptDTO;
import com.jq.user.customer.dto.SysUserDTO;
import com.jq.user.customer.service.DepartmentService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentServiceFallbackFactory implements FallbackFactory<DepartmentService> {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceFallbackFactory.class);

    @Override
    public DepartmentService create(Throwable throwable) {
        return new DepartmentService() {
            @Override
            public ApiResult addSysDeptApi(SysDeptDTO sysDeptDTO, String updateUserName,
                                           String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateSysDeptApi(SysDeptDTO sysDeptDTO, String updateUserName,
                                              Long siteId, String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult disabledSysDeptApi(Long deptId, String updateUserName,
                                                Long siteId, String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteSysDeptApi(Long deptId, String updateUserName,
                                              Long siteId, String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult enableSysDeptApi(Long deptId, String updateUserName,
                                              Long siteId, String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<SysDeptDTO>> querySysDeptApi(SysDeptDTO sysDeptDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<SysDeptDTO>> querySysDeptListApi(Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<SysDeptDTO> getSysDeptApi(Long deptId, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult verifySysDeptNameApi(Long siteId, String sysDeptName) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<SysUserDTO>> querySysUserByDeptIdApi(Long deptId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
