//package com.cloudnative.common;
//
//@Component("demoTaskBean")
//public class DemoTaskBean  extends IScheduleTaskDealSingle<SubDetailDO> {
//
//    /**
//     * 任务数据来源，例如从一个数据库获取数据
//     *
//     * @param taskParameter
//     * @param ownSign
//     * @param taskItemNum
//     * @param taskItemList
//     * @param eachFetchDataNum
//     * @return
//     * @throws Exception
//     */
//    public List<SubDetailDO> selectTasks(String taskParameter, String ownSign,
//                                         int taskItemNum, List<TaskItemDefine> taskItemList,
//                                         int eachFetchDataNum) throws Exception {
//        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(new Date());
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            List<SubDetailDO> details = null;
//
//            details = subDetailDAO.selectForSchedule(
//                    getScopeByQueueCondition(taskItemNum, taskItemList),
//                    confirmTypes, DETAIL_STATUS_ONE, eachFetchDataNum);
//            return details;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw e;
//        }
//
//    }
//
//    /**
//     * 处理业务的函数，例如将前面查询出来的数据插入另外一个库
//     *
//     * @param subDetail
//     * @param ownSign
//     * @return
//     * @throws Exception
//     */
//    public boolean execute(SubDetailDO subDetail, String ownSign)
//            throws Exception {
//        try {
//            yourProcess.process(subDetail);
//            return true;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//    public void afterPropertiesSet() throws Exception {
//        Properties p = getProperties(configInfo);
//        tbscheduleManagerFactory = new TBScheduleManagerFactory();
//        tbscheduleManagerFactory.setApplicationContext(applicationcontext);
//        tbscheduleManagerFactory.init(p);
//        tbscheduleManagerFactory.setZkConfig(convert(p));
//        logger.warn("TBBPM 成功启动schedule调度引擎 ...");
//
//    }
//}