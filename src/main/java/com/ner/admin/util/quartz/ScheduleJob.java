package com.ner.admin.util.quartz;

import com.ner.admin.entity.QuartzTask;
import com.ner.admin.entity.QuartzTaskLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 类ScheduleJob的功能描述:
 * 定时任务
 * @auther hxy
 * @date 2017-08-25 11:48:34
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
	private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzTask scheduleJob = (QuartzTask) context.getMergedJobDataMap().get(QuartzTask.JOB_PARAM_KEY);
        String param = scheduleJob.getParams();
        //数据库保存执行记录
        QuartzTaskLog qtLog = new QuartzTaskLog();
        qtLog.setJobId(scheduleJob.getId());
        qtLog.setTargetBean(scheduleJob.getTargetBean());
        qtLog.setTrgetMethod(scheduleJob.getTrgetMethod());
        qtLog.setParams(param);
        qtLog.setName("执行定时任务【"+scheduleJob.getName()+"】");
        if(StringUtils.isNotBlank(param) && StringUtils.isNumeric(param)){
			qtLog.setCreateId(Long.valueOf(param));
			qtLog.setUpdateId(Long.valueOf(param));
		}else{
        	//定义死
			qtLog.setCreateId(1L);
			qtLog.setUpdateId(1L);
		}
        qtLog.setCreateDate(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	log.info("任务准备执行，任务ID：" + scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getTargetBean(),
            		scheduleJob.getTrgetMethod(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			qtLog.setTimes((int)times);
			//任务状态    0：成功    1：失败
			qtLog.setStatus(0);
			
			log.info("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			qtLog.setTimes((int)times);

			//任务状态    0：成功    1：失败
			qtLog.setStatus(1);
			qtLog.setError(e.getMessage());
		}finally {
			qtLog.insert();
		}
    }
}
