package structure;

/**
 * This file is aim to change PowerVm into PowerVmPlus.
 *
 * @author HayaKus
 */

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmStateHistoryEntry;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.util.MathUtil;

import java.util.*;

public class PowerMonitor {
	public static double npaPower = -1;
	public static double maximunSimulation = -1;
	// The power of hosts
	private ArrayList<double[]> hostsPower = new ArrayList<>();
	// The average power of hosts
	private ArrayList<double[]> hostPowerAverage = new ArrayList<>();
	private double tempHostPowerAverage = 0;
	private double tempUtilPowerAverage = 0;
	private double tempHostLoadAverage = 0;
	private double maxTime = 0;
	private boolean firstTimeU = true;
	private double currentTime;
	private String times = "";
	private String averPower = "";
	private String averUtil = "";
	private String name = "";
	private int hostNumber = 0;
	private String color = "red";
	private List<Host> hostList;
	private int totalHostListMips;
	private int vmListTotalMips;
	private int vmListTotalInstCompleted = 0;
	private PowerDatacenter datacenter;
	private List<Vm> vmList;
	private List<Double> cpuAndHostload;

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 * @return {hostId, power, maxPower, utilization, time, hostNum };
	 */

	public ArrayList<double[]> getAllInfo() {
		return hostsPower;
	}

	public double getMaxTime() {
		return maxTime;
	}

	public double getMaxSimTime() {
		return maxTime;
	}

	public int getHostNum() {
		return hostNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAverPower() {
		return averPower;
	}

	public String getAverUtil() {
		return averUtil;
	}

	public String getTimes() {
		return times;
	}

	public PowerMonitor(String name) {
		this.name = name;
	}

	public List<Double> getCpuAndHostload() {
		return this.cpuAndHostload;
	}

	public void PowerMonitorMath() {

		// The interval of step
		double step = Constant.STEP_INTERVAL;
		// The interval of scheduling
		double sched_int = Constant.SCHEDULING_INTERVAL;
		boolean firstTime = true;
		double previoustime = maxTime;

		cpuAndHostload = new ArrayList<>();

		// int deigmata = (int) (maxTime / sched_int / step);
		int deigmata = (int) (maximunSimulation / sched_int / step); // edited
																		// for
																		// different
																		// end
																		// times

		int pos = 1;
		// The utilizaiton in the hostPowerAverage is multiplied by 100.
		for (double[] host : hostPowerAverage) {
			// this is for dropping unwanted times between sceduling
			if ((host[0] < (previoustime + sched_int)) && !firstTime) {
				continue;
			}

			// Log.disable();
			// ends heres
			if (!firstTime) {
				times += ",";
				averUtil += ",";
				averPower += ",";
			}
			firstTime = false;
			if ((int) host[0] <= ((int) (previoustime + sched_int))) {
				if (pos == deigmata) {
					times += "'" + ((int) (host[0] / Constant.SCALEX)) + "'";
					pos = 1;
				} else {
					times += "''";
					pos++;

				}

				averPower += " " + String.format("%.2f", host[1]);
				averUtil += " " + String.format("%.2f", host[2]);
				cpuAndHostload.add(host[2]/100);
				cpuAndHostload.add(host[3]);
				previoustime = host[0];
			} else {
				while (previoustime < (host[0] - 2 * sched_int)) {
					Log.printLine(host[0] + "  <--------zeroeAVERAGE " + previoustime);

					previoustime += sched_int;
					if (pos == deigmata) {
						times += "'" + ((int) (previoustime / Constant.SCALEX)) + "',";
						pos = 1;
					} else {
						times += "'',";
						pos++;
					}
					averPower += "0, ";
					averUtil += "0, ";
					cpuAndHostload.add((double)0);
					cpuAndHostload.add((double)0);
				}
				if (pos == deigmata) {
					times += "'" + ((int) (host[0] / Constant.SCALEX)) + "'";
					pos = 1;
				} else {
					times += "''";
					pos++;
				}
				averPower += " " + String.format("%.2f", host[1]);
				averUtil += " " + String.format("%.2f", host[2]);
				cpuAndHostload.add(host[2]/100);
				cpuAndHostload.add(host[3]);
				previoustime = host[0];
			}

		}
		// This is for differnent Ending Time
		// Log.printLine("PREVIOUS TIME = " + previoustime);
		if (previoustime < maximunSimulation) {
			while (previoustime < (maximunSimulation - sched_int)) {
				previoustime += sched_int;
				if (pos == deigmata) {
					times += ",'" + ((int) (previoustime / Constant.SCALEX)) + "'";
					pos = 1;
				} else {
					times += ",''";
					pos++;
				}
				averPower += "0, ";
				averUtil += "0, ";
			}
			previoustime += sched_int;
			if (pos == deigmata) {
				times += ",'" + ((int) (previoustime / Constant.SCALEX)) + "'";
			} else {
				times += ",''";
			}
			averPower += "0";
			averUtil += "0";

		}
		// ends here

	}

	// This utilization is multiplied by 100.
	public void hostDetails(int hostId, double power, double maxPower,
			double utilization, double time, double datacenterPower, int hostNum, double hostload) {
		// Log.printLine("Netowrk " + NetworkTopology.isNetworkEnabled() +
		// " responseTime of 3 " +NetworkTopology.getDelay(5,3) );
		// this is for dropping unwanted times between sceduling ( 300
		// ---308 309--- 600)
		// Log.enable();
		// Log.printLine();
		// if (!firstTimeU) {
		// double check[] = hostPowerAverage.get(hostPowerAverage.size() - 1);

		// if ((time < (check[0] + Constant.SCHEDULING_INTERVAL))
		// && !firstTimeU && (time != check[0])) {
		// Log.printLine(time + " <----- droppeda " + check[0]);
		// return;
		// }
		// }

		// Log.disable();
		// ends heres
		if (firstTimeU) {
			currentTime = time;
		}

		if (currentTime == time) {
			if (!firstTimeU) {
				hostPowerAverage.remove(hostPowerAverage.size() - 1);
			}
			firstTimeU = false;
			tempHostPowerAverage += power;
			tempUtilPowerAverage += utilization;
			tempHostLoadAverage += hostload;
		} else {
			tempHostPowerAverage = 0;
			tempHostPowerAverage += power;
			tempUtilPowerAverage = 0;
			tempUtilPowerAverage += utilization;
			tempHostLoadAverage = 0;
			tempHostLoadAverage += hostload;
		}
		maxTime = time;
		double sad[] = { time, tempHostPowerAverage / hostNum,
				tempUtilPowerAverage / hostNum , tempHostLoadAverage / hostNum};
		hostPowerAverage.add(sad);

		hostNumber = hostNum;
		double hsad[] = { hostId, power, maxPower, utilization, time, hostload};
		hostsPower.add(hsad);
		currentTime = time;

		// this is for different ending time
		if (time > maximunSimulation) {
			maximunSimulation = time;
		}

	}

	public List<Host> getHostList() {
		return datacenter.getHostList();
	}

	public void setDatacenter(PowerDatacenter datacenter) {
		this.datacenter = datacenter;
		this.hostList = datacenter.getHostList();
	}

	public List<Vm> getVmList() {
		return vmList;
	}

	public void setVmList(List<Vm> vmLista) {
		this.vmList = vmLista;
	}

	// Returns total Datacenter Mips power
	public int getDatacenterMipsPower() {
		totalHostListMips = 0;
		for (Host host : hostList) {
			totalHostListMips += host.getTotalMips();
		}
		return totalHostListMips;

	}

	// Returns total Requested Mips from VM
	public int getVmListTotalMips() {
		vmListTotalMips = 0;
		for (Vm vm : vmList) {
			vmListTotalMips += vm.getMips();
		}
		return vmListTotalMips;
	}

	// Returns total Processed Instructions of VMs
	public int getVmListTotalInstCompleted() {
		Log.printLine("Started");
		Log.print(hostList.size() + " ");

		for (Host host : hostList) {
			Log.print(host.getId() + " vmListsize " + host.getVmList().size() + ", ");

			for (Vm vm : vmList) {

				for (VmStateHistoryEntry vmhistory : vm.getStateHistory()) {
				//	if (!vmhistory.isInMigration())
						vmListTotalInstCompleted += vmhistory.getRequestedMips();

				}
			}
		}

		return vmListTotalInstCompleted;
	}

	// Returns total Power consumed by the Datacenter
	public double getDatacenterPower() {
		return (datacenter.getPower() / (3600 * 1000));

	}

	// Returns Average SLA violations
	public double getAvSLA() {
		Map<String, Double> slaMetrics = getSlaMetrics(vmList);
		return slaMetrics.get("average");
	}

	// Returns the Number of migrations
	public int getMigrations() {
		return datacenter.getMigrationCount();
	}

	// Get SLAs
	protected static Map<String, Double> getSlaMetrics(List<Vm> vms) {
		Map<String, Double> metrics = new HashMap<String, Double>();
		List<Double> slaViolation = new LinkedList<Double>();
		double totalAllocated = 0;
		double totalRequested = 0;
		double totalUnderAllocatedDueToMigration = 0;

		for (Vm vm : vms) {
			double vmTotalAllocated = 0;
			double vmTotalRequested = 0;
			double vmUnderAllocatedDueToMigration = 0;
			double previousTime = -1;
			double previousAllocated = 0;
			double previousRequested = 0;
			boolean previousIsInMigration = false;

			for (VmStateHistoryEntry entry : vm.getStateHistory()) {
				if (previousTime != -1) {
					double timeDiff = entry.getTime() - previousTime;
					vmTotalAllocated += previousAllocated * timeDiff;
					vmTotalRequested += previousRequested * timeDiff;

					if (previousAllocated < previousRequested) {
						slaViolation
								.add((previousRequested - previousAllocated) / previousRequested);
						if (previousIsInMigration) {
							vmUnderAllocatedDueToMigration += (previousRequested - previousAllocated) * timeDiff;
						}
					}
				}

				previousAllocated = entry.getAllocatedMips();
				previousRequested = entry.getRequestedMips();
				previousTime = entry.getTime();
				previousIsInMigration = entry.isInMigration();
			}

			totalAllocated += vmTotalAllocated;
			totalRequested += vmTotalRequested;
			totalUnderAllocatedDueToMigration += vmUnderAllocatedDueToMigration;
		}

		metrics.put("overall",
				(totalRequested - totalAllocated) / totalRequested);
		if (slaViolation.isEmpty()) {
			metrics.put("average", 0.);
		} else {
			metrics.put("average", MathUtil.mean(slaViolation));
		}
		metrics.put("underallocated_migration",
				totalUnderAllocatedDueToMigration / totalRequested);
		// metrics.put("sla_time_per_vm_with_migration",
		// slaViolationTimePerVmWithMigration /
		// totalTime);
		// metrics.put("sla_time_per_vm_without_migration",
		// slaViolationTimePerVmWithoutMigration /
		// totalTime);

		return metrics;
	}

}
