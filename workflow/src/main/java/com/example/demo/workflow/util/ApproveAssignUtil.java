package com.example.demo.workflow.util;

import org.springframework.stereotype.Component;

@Component
public class ApproveAssignUtil {
    /**
     * 模拟：根据申请人查询直属领导（部门经理）
     */
    public String getDirectLeader(String applicant) {
        // 实际场景：调用权限系统接口查询（如从数据库/缓存获取）
        if ("zhangSan".equals(applicant)) {
            return "lisi"; // 张三的部门经理是李四
        } else if ("wangWu".equals(applicant)) {
            return "zhaoLiu"; // 王五的部门经理是赵六
        }
        return "admin"; // 默认审批人
    }

    /**
     * 模拟：获取总经理审批人
     */
    public String getGmManager() {
        return "gm_zhang"; // 总经理账号
    }
}
