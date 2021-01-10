package model;

import structure.Constant;

import java.util.ArrayList;
import java.util.List;

public class Evaluation_QoS {
    /**
     * 基于QoS感知区域的评估计算
     * @param Vars
     * @return
     */
    public static List<Double> getEvaluationByCpuAndHostload(List<Double> Vars) {
        // 进行数据格式的转换：将列表转换为数组
        double[] row1 = new double[Vars.size() / 2];
        double[] row2 = new double[Vars.size() / 2];

        for(int i = 0; i < Vars.size() / 2; i++) {
            row1[i] = Vars.get(i * 2);
            row2[i] = Vars.get(i * 2 + 1);
        }

        // 将结果存在动态数组中输出
        List<Double> resultDou = new ArrayList<>();

        // 算法：得到QoS评估结果
        for(int i = 0; i < Vars.size() / 2; i++) {
            // cpu使用率 例：50 单位：%
            double cpu = row1[i];
            // 服务器负载 例：15 单位：台
            double hostload = row2[i];

            // 最佳cpu值
            double bestCpu = (double)Constant.BEST_CPU / 100;

            // 最差cpu值
            double worstCpu = (double)Constant.WORST_CPU / 100;

            // 最佳服务器负载值
            double bestHostload = Constant.BEST_HOSTLOAD;

            // 最差服务器负载值
            double worstHostload = Constant.WORST_HOSTLOAD;

            // cpu权重
            double weightOfCpu = (double)Constant.WEIGHT_CPU / 100;

            // 服务器负载权重
            double weightOfHostload = (double)Constant.WEIGHT_HOSTLOAD / 100;

            // cpu方向特征值
            int dirOfCpu = (bestCpu > worstCpu ? 1 : -1);

            // 服务器负载方向特征值
            int dirOfHostload = (bestHostload > worstHostload ? 1 : -1);

            double tempQoS = getQoSByCpuAndHostload(cpu, hostload, bestCpu, worstCpu, bestHostload, worstHostload,
                    weightOfCpu, weightOfHostload, dirOfCpu, dirOfHostload);
            resultDou.add(tempQoS);

        }

        return resultDou;
    }

    /**
     * 整合该类中方法，根据Cpu和服务器负载计算QoS
     * @param cpu
     * @param hostload
     * @param bestCpu
     * @param worstCpu
     * @param bestHostload
     * @param worstHostload
     * @param weightOfCpu
     * @param weightOfHostload
     * @param dirOfCpu
     * @param dirOfHostload
     * @return
     */
    private static double getQoSByCpuAndHostload(double cpu, double hostload, double bestCpu, double worstCpu,
                                                 double bestHostload, double worstHostload, double weightOfCpu,
                                                 double weightOfHostload, int dirOfCpu, int dirOfHostload) {
        // 情况灵活参数
        double situationOfCpu = getSituation(cpu, bestCpu, worstCpu, dirOfCpu);
        double situationOfHostload = getSituation(hostload, bestHostload, worstHostload, dirOfHostload);

        // 差值
        double differenceOfCpu = getDifference(cpu, bestCpu, situationOfCpu, dirOfCpu);
        double differenceOfHostload = getDifference(hostload, bestHostload, situationOfHostload, dirOfHostload);

        // 用户满意度
        double satisfactionOfCpu = getSatisfaction(cpu, bestCpu, worstCpu, differenceOfCpu, dirOfCpu);
        double satisfactionOfHostload = getSatisfaction(hostload, bestHostload, worstHostload, differenceOfHostload, dirOfHostload);

        // 最终QoS评估值
        double qos = getQoS(satisfactionOfCpu, satisfactionOfHostload, weightOfCpu, weightOfHostload);
//        System.out.println(qos);

        return qos;
    }

    /**
     * 计算 情况灵活参数
     * @param realValue
     * @param bestValue
     * @param worstValue
     * @param dir
     * @return
     */
    private static double getSituation(double realValue, double bestValue, double worstValue, int dir) {
        double situation = 0;

        // 处于不易察觉区域
        if((dir == -1 && realValue <= bestValue) || (dir == 1 && realValue >= bestValue)) {
            // 用 -1 代替 正无穷
            situation = -1;
        }
        // 处于不可用区域
        else if ((dir == -1 && realValue >= worstValue) || (dir == 1 && realValue <= worstValue)) {
            situation = 1;
        }
        // 处于可容忍区域
        else {
            situation = (bestValue - worstValue) / (bestValue - realValue);
        }
        return situation;
    }

    /**
     * 计算 差值
     * @param realValue
     * @param bestValue
     * @param situation
     * @param dir
     * @return
     */
    private static double getDifference(double realValue, double bestValue, double situation, int dir) {
        double difference = 0;

        // 处于不易察觉区域
        if(situation == -1) {
            difference = 0;
        }
        // 处于不可用区域
        else if (situation == 1) {
            // 用 -1 代替 正无穷
            difference = -1;
        }
        // 处于可容忍区域
        else {
            difference = (bestValue - realValue) * dir / situation;
        }

        return difference;
    }

    /**
     * 计算 用户满意度
     * @param realValue
     * @param bestValue
     * @param worstValue
     * @param difference
     * @param dir
     * @return
     */
    private static double getSatisfaction(double realValue, double bestValue, double worstValue, double difference, int dir) {
        double satisfaction = 0;

        // 处于不易察觉区域
        if(difference == 0) {
            satisfaction = 1;
        }
        // 处于不可用区域
        else if (difference == -1) {
            // 用 -1 代替 正无穷
            satisfaction = 0;
        }
        // 处于可容忍区域
        else {
            double temp = (bestValue - worstValue) * dir / 2;
            satisfaction = 1 - 1 / (1 + Math.pow(Math.E, -5 * (difference - temp) / temp));
        }

        return satisfaction;
    }

    /**
     * 计算 最终QoS评估值
     * @param satisfactionOfCpu
     * @param satisfactionOfHostload
     * @param weightOfCpu
     * @param weightOfHostload
     * @return
     */
    private static double getQoS(double satisfactionOfCpu, double satisfactionOfHostload, double weightOfCpu, double weightOfHostload) {
        double qos = (satisfactionOfCpu * weightOfCpu + satisfactionOfHostload * weightOfHostload) * 100;

        return qos;
    }

    /**
     * 单元测试
     * @param args
     */
    public static void main(String[] args){

        List<Double> Vars =  new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            Vars.add(1.0);
        }

        List<Double> qoss = getEvaluationByCpuAndHostload(Vars);

        for(double qos :qoss) {
            System.out.println("qos:" + qos);
        }
        System.out.println("The number of this result: " + qoss.size());
    }
}
