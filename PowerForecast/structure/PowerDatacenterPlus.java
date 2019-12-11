package structure;

/**
 * This file is aim to change PowerDatacenter into PowerDatacenterPlus.
 *
 * @author HayaKus
 */

import gui.LogThreadSendToArea;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.power.PowerHost;

import java.util.ArrayList;
import java.util.List;

public class PowerDatacenterPlus extends PowerDatacenter {

	public PowerDatacenterPlus(String name,
			DatacenterCharacteristics characteristics,
			VmAllocationPolicy vmAllocationPolicy, List<Storage> storageList,
			double schedulingInterval) throws Exception {
		super(name, characteristics, vmAllocationPolicy, storageList,
				schedulingInterval);
	}
	private PowerMonitor monitor=new PowerMonitor(getName());

	public PowerMonitor getPowerMonitor(){
		
		System.out.println("Lam:PowerDatacenterPlus.getPowerMonitor");
		
		return monitor;
	}
	@Override
	protected double updateCloudetProcessingWithoutSchedulingFutureEventsForce() {

		System.out.println("Lam:PowerDatacenterPlus.updateCloudetProcessingWithoutSchedulingFutureEventsForce");

		String logString = String.format("%.2f", CloudSim.clock());
		LogThreadSendToArea l = new LogThreadSendToArea("时间：" + logString + "   模拟进行中……");
		l.start();

		double currentTime = CloudSim.clock();
		double minTime = Double.MAX_VALUE;
		double timeDiff = currentTime - getLastProcessTime();
		double timeFrameDatacenterEnergy = 0.0;

		Log.printLine("\n\n--------------------------------------------------------------\n\n");
		Log.formatLine(
				"New resource usage for the time frame starting at %.2f:",
				currentTime);

		List<Double> hostloadList = new ArrayList<>();
		for (PowerHost host : this.<PowerHost> getHostList()) {

			Log.printLine();

			/**
			 * This is to update the processes of every host.
			 * 
			 * Format：
			 * 300.10: [Host #208] Total allocated MIPS for VM #13077 (Host #208) is 1500.00, was requested 1500.00 out of total 1500.00 (100.00%)
			 * 300.10: [Host #208] MIPS for VM #13077 by PEs (1 * 2000.0). PE #0: 1500.00.
			 */
			double time = host.updateVmsProcessing(currentTime);
			hostloadList.add(HostDynamicWorkload.getTempHostLoad());

			if (time < minTime) {
				minTime = time;
			}
			
			//The usage of Cpu
			Log.formatLine("%.2f: [Host #%d] utilization is %.2f%%",
					currentTime, host.getId(), host.getUtilizationOfCpu() * 100);
		}


		if (timeDiff > 0) {
			
			Log.formatLine(
					"\nEnergy consumption for the last time frame from %.2f to %.2f:",
					getLastProcessTime(), currentTime);

			int counter = 0;
			for (PowerHost host : this.<PowerHost> getHostList()) {
				double previousUtilizationOfCpu = host
						.getPreviousUtilizationOfCpu();
				double utilizationOfCpu = host.getUtilizationOfCpu();
				double timeFrameHostEnergy = host.getEnergyLinearInterpolation(
						previousUtilizationOfCpu, utilizationOfCpu, timeDiff);

				timeFrameDatacenterEnergy += timeFrameHostEnergy;

				Log.printLine();

				Log.formatLine(
						"%.2f: [Host #%d] utilization at %.2f was %.2f%%, now is %.2f%%",
						currentTime, host.getId(), getLastProcessTime(),
						previousUtilizationOfCpu * 100, utilizationOfCpu * 100);
				// Log.formatLine("%.2f: [Host #%d] energy is %.2f W*sec",
				// 		currentTime, host.getId(), timeFrameHostEnergy);

				// ================================


				this.monitor.hostDetails(host.getId(),host.getPower(),host.getMaxPower(),utilizationOfCpu
				 * 100,currentTime,timeFrameDatacenterEnergy,getHostList().size(),hostloadList.get(counter++));
				// ====================================
			}

			// Log.formatLine("\n%.2f: Data center's energy is %.2f W*sec\n",
			// 		currentTime, timeFrameDatacenterEnergy);
		}

		// Log.printLine("Current Total Power "
		// 		+ (getPower() + timeFrameDatacenterEnergy));

		setPower(getPower() + timeFrameDatacenterEnergy);

		checkCloudletCompletion();

		/** Remove completed VMs **/
		for (PowerHost host : this.<PowerHost> getHostList()) {
			
			for (Vm vm : host.getCompletedVms()) {
				getVmAllocationPolicy().deallocateHostForVm(vm);
				getVmList().remove(vm);
				Log.printLine("VM #" + vm.getId() + " has been deallocated from host #" + host.getId() + " from user "+vm.getUserId() + " vm cloudlets " );
			}
		}


		setLastProcessTime(currentTime);
		return minTime;
	}


}