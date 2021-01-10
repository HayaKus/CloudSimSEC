package structure;

import java.util.ArrayList;

/**
 * This file is aim to store the constants which are used.
 *
 * @author HayaKus
 */

public class InitialConstant {

	public static boolean ENABLE_OUTPUT = true;
	public static boolean OUTPUT_CSV = false;
	public static double SCHEDULING_INTERVAL = 300;
	public static double SIMULATION_LIMIT = 70 * 60 * 60;

	// ================== DataCenter Parameters=================//
	public static String DATACENTER_NAME ="南京邮电大学";
	public static String IT_ENERGYMODEL = "rbf.mat";
	public static String ITENVIRONMENT_ENERGYMODEL = "rbf2.mat";
	public static double FIREENERGY = 500;
	public static double INFRASTRUCTUREENERGY = 1000;

	public static double ITENVIRONMENTMAXENERGY = 10000;
	
	// ================== Users Parameters =================//
	
	public static double[] USERS_DELAY = {0};

	// The Types of the VM broker has (starts from 0)
	public static int[][] USR_TYPE_OWNED = {{0,1,2,3}};
	// The Amount of the VM broker has
	public static int[][] USR_VMNUM_OWNED = {{100,50,100,50}};
	// The Types of the Cloudlets broker has (starts from 0)
	public static int[][] USR_CLOUDLET_TYPE = {{3,2,1,0}};


	// ================== Cloudlet Parameters =================//

//	public static int CLOUDLET_LENGTH[] = { (int) (100000*SIMULATION_LIMIT), (int) (20000*SIMULATION_LIMIT), (int) (3000*SIMULATION_LIMIT), (int) (3000*SIMULATION_LIMIT), (int) (3000*SIMULATION_LIMIT) };// * (int) SIMULATION_LIMIT;
	public static long CLOUDLET_LENGTH[] = { 20000000, 4000000, 60000000,16000000 };// * (int) SIMULATION_LIMIT;
	public static int CLOUDLET_FILESIZE[] = { 300, 300, 300, 300 };
	public static int CLOUDLET_OUTPUTSIZE[] = { 300, 300, 300, 300 };
	public static int CLOUDLET_PES[] = { 1, 1, 1, 1 };

	
	//This is for random utilization	(under construction)
	public static long CLOUDLET_UTILIZATION_SEED[] = { 1, 1, 1, 1 };
	public static long RANDOM_UTILIZATION = 0 ; //Set to 1 , requires TimeSharedOverSubscription & SchedulerDynamicWorkload (3,3) (buggy)
	
	// 1 Time Shared *
	// 2 Space Shared
	// 3 TimeSharedOverSubscription <2>
	public static int[] VM_ALLOCATION = { 1,1,1, 1,1 };
//	public static int[] VM_ALLOCATION = { 2,2,2,2,2 };
//	public static int[] VM_ALLOCATION = { 3,3,3, 3,3 };
	// 1 Time Shared
	// 2 Space Shared * <- set this for NOT RANDOM / WORKING FOR migrations / infinity run for full utilization
	// 3 SchedulerDynamicWorkload <- set this for RANDOM / WORKING FOR migrations (not for adding second user) <2>
//	public static int[] CLOUD_ALLOCATION = { 1,1,1,1 };
//	public static int[] CLOUD_ALLOCATION = { 2,2,2,2 };
	public static int[] CLOUD_ALLOCATION = { 3,3,3,3 };


	// ================== Host Parameters =================//
	public static int HOST_TYPES = 5;
//	public static int[] VM_HOST_NUMBERS = { 0,0, 0, 11,0 }; //Homogeneous
	public static int[] VM_HOST_NUMBERS = { 10,10, 10, 10,10 };

	public static int[] HOST_MIPS = { 1500, 2000, 2500, 6000, 4200 };
	public static int[] HOST_PES = { 1, 1, 2, 4, 4 };
	public static int[] HOST_MAX_POWER = { 300, 300, 300, 300, 300 };
	public static int[] HOST_MIN_POWER = { 90, 90, 90, 90, 90 };
	public static double[] HOST_MAX_POWER_PERCENT = { 0.3, 0.3, 0.3, 0.4,0.3 };
	public static double[] HOST_MIN_UTIL = { 0.3, 0.3, 0.3, 0.3, 0.3 };//this is minimun mips for minimun power
//	public static double[] HOST_MIN_UTIL = { -1,-1,-1, -1, -1 };//disable

	public static int[] HOST_RAM = { 24576, 24576, 24576, 24576, 24576 };
	public static long HOST_BW = 1000000; // 1 Gbit/s
	public static long HOST_STORAGE = 1000000000; // 1 TB


	//To enable output Html set VM_POLICY to 1 and OUTPUTHTML to 1
	public static int SCALEX =60*60;
	public static int STEP_INTERVAL =10;
	// The maximum power
	public static int MAXPOWERS =300;
	// The power is set to 0, aim to make the bottom of graph to 0.
	public static int MINPOWERS =0;


	
	
	
	// ================== VM Parameters =================//

	public static int[] VM_MIPS = { 750, 1000, 1500, 2000 };
	public static int[] VM_PES = { 1, 1, 1, 1 };
	public static int[] VM_RAM = { 512, 512, 1024, 1024 };
	public static long VM_BW = 1000;
	public static long VM_SIZE = 25000; // 25 GB

	// ================== Powers Result after Computing =================//

	public static ArrayList<Double> RRList;

	// ================== 基于QoS感知区域的评估参数 =================//

	// cpu 范围 0% - 100%
	public static int BEST_CPU = 0;
	public static int WORST_CPU = 100;
	public static int WEIGHT_CPU = 50;

	// 服务器负载 范围 0台 - 50台
	public static int BEST_HOSTLOAD = 0;
	public static int WORST_HOSTLOAD = 50;
	public static int WEIGHT_HOSTLOAD = 50;

	// 带宽 范围 10GB - 1GB
	public static int BEST_BANDWIDTH = 10;
	public static int WORST_BANDWIDTH = 1;
	public static int WEIGHT_BANDWIDTH = 0;

	// 内存 范围 0% - 100%
	public static int BEST_RAM = 0;
	public static int WORST_RAM = 100;
	public static int WEIGHT_RAM = 0;

}