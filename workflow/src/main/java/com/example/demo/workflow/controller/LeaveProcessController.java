package com.example.demo.workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.Task;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
@Slf4j
@RequiredArgsConstructor
public class LeaveProcessController {
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HistoryService historyService;

    /**
     * 1. 启动请假流程实例
     * @param applicant 申请人（如 zhangSan）
     * @param days 请假天数
     * @return 流程实例ID
     */
    @PostMapping("/start")
    public String startProcess(@RequestParam String applicant, @RequestParam Integer days) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", applicant);
        variables.put("days", days);

        // 启动流程实例
        String processInstanceId = runtimeService.startProcessInstanceByKey("leaveProcess", variables)
                .getId();

        log.info("流程启动成功！实例ID：{}，申请人：{}，天数：{}", processInstanceId, applicant, days);
        return "流程启动成功！实例ID：" + processInstanceId;
    }

    /**
     * 2. 查询指定用户的待办任务
     */
    @GetMapping("/tasks")
    public List<Task> queryTasks(@RequestParam String assignee) {
        // 查询待办任务
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();

        log.info("审批人 {} 的待办任务数：{}", assignee, tasks.size());
        tasks.forEach(task -> log.info("任务ID：{}，名称：{}，实例ID：{}",
                task.getId(), task.getName(), task.getProcessInstanceId()));
        return tasks;
    }

    /**
     * 3. 完成任务（审批通过）
     */
    @PostMapping("/complete")
    public String completeTask(@RequestParam String taskId) {
        taskService.complete(taskId); // 完成任务
        log.info("任务 {} 完成成功！", taskId);
        return "任务完成成功！";
    }

    /**
     * 4. 查询流程历史（审计用）
     */
    @GetMapping("/history/{processInstanceId}")
    public Map<String, Object> queryProcessHistory(@PathVariable String processInstanceId) {
        Map<String, Object> history = new HashMap<>();

        // 查询流程实例历史
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // 查询任务历史
        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime().asc()
                .list();

        history.put("流程实例信息", processInstance);
        history.put("任务历史", taskInstances);
        return history;
    }
}
