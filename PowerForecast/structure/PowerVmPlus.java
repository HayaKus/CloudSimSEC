package structure;

/**
 * This file is aim to change PowerVm into PowerVmPlus.
 *
 * @author HayaKus
 */

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.power.PowerVm;

public class PowerVmPlus extends PowerVm {

	private double deadline;
	private double maxMips;
	private double minMips;
	public PowerVmPlus(int id, int userId, double mips, int pesNumber,
			int ram, long bw, long size, int priority, String vmm,
			CloudletScheduler cloudletScheduler, double schedulingInterval,double deadline,double maxMips,double minMips) {
		super(id, userId, mips, pesNumber, ram, bw, size, priority, vmm,
				cloudletScheduler, schedulingInterval);
		this.setDeadline(deadline);
		this.setMaxMips(maxMips);
		this.setMinMips(minMips);
	}

	public void setNewMips(double mips){
		setMips(mips);
	}

	public double getMinMips() {
		return minMips;
	}

	public void setMinMips(double minMips) {
		this.minMips = minMips;
	}

	public double getMaxMips() {
		return maxMips;
	}

	public void setMaxMips(double maxMips) {
		this.maxMips = maxMips;
	}

	public double getDeadline() {
		return deadline;
	}

	public void setDeadline(double deadline) {
		this.deadline = deadline;
	}
	
}