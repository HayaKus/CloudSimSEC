package structure;

/**
 * This file is aim to change PowerHost into PowerHostPlus.
 *
 * @author HayaKus
 */
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.power.models.PowerModel;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

public class PowerHostPlus extends PowerHostUtilizationHistory {
	private double staticPower;

	public PowerHostPlus(int id, RamProvisioner ramProvisioner,
			BwProvisioner bwProvisioner, long storage,
			List<? extends Pe> peList, VmScheduler vmScheduler,
			PowerModel powerModel, double staticPower) {
		super(id, ramProvisioner, bwProvisioner, storage, peList, vmScheduler,
				powerModel);
		this.staticPower = staticPower;
	}

	/**
	 * Allocates PEs and memory to a new VM in the Host.
	 * 
	 * @param vm
	 *            Vm being started
	 * @return $true if the VM could be started in the host; $false otherwise
	 * @pre $none
	 * @post $none
	 */
	@Override
	public boolean vmCreate(Vm vm) {
		
		if (getStorage() < vm.getSize()) {
			Log.printLine("[VmScheduler.vmCreate] Allocation of VM #" + vm
					.getId() + " to Host  #" + getId() + " failed by storage");
			return false;
		}

		if (!getRamProvisioner().allocateRamForVm(vm,
				vm.getCurrentRequestedRam())) {
			Log.printLine("[VmScheduler.vmCreate] Allocation of VM #" + vm
					.getId() + " to Host #" + getId() + " failed by RAM");
			return false;
		}

		if (!getBwProvisioner().allocateBwForVm(vm, vm.getCurrentRequestedBw())) {
			Log.printLine("[VmScheduler.vmCreate] Allocation of VM #" + vm
					.getId() + " to Host #" + getId() + " failed by BW");
			getRamProvisioner().deallocateRamForVm(vm);
			return false;
		}

		if (!getVmScheduler()
				.allocatePesForVm(vm, vm.getCurrentRequestedMips())) {
			Log.printLine("[VmScheduler.vmCreate] Allocation of VM #" + vm
					.getId() + " to Host #" + getId() + " failed by MIPS");
			getRamProvisioner().deallocateRamForVm(vm);
			getBwProvisioner().deallocateBwForVm(vm);
			return false;
		}

		setStorage(getStorage() - vm.getSize());
		getVmList().add(vm);
		 // added for multiusers with different time
		if (CloudSim.clock() != getClockJustAdded()) {
			setClockJustAdded(CloudSim.clock());
			getVmListJustAdded().clear();
		}
		getVmListJustAdded().add(vm);

	//ends here
		vm.setHost(this);
		return true;
	}
	
	/**
	 * Gets the completed vms.
	 * 
	 * @return the completed vms
	 */
	@Override
	public List<Vm> getCompletedVms() {
		List<Vm> vmsToRemove = new ArrayList<Vm>();
		for (Vm vm : getVmList()) {
			//This is added for dynamic workload because it removes added vm's on multiuser
	//		if (((int) getClockJustAdded())==((int) CloudSim.clock()) && getVmListJustAdded().contains(vm)){
	//			Log.printLine("Vm id "+vm.getId()+" is contained at just added list");
	//			continue;
	//		}
			//ends here
			if (vm.isInMigration()) {
				continue;
			}			
			if (vm.getCurrentRequestedTotalMips() == 0) {
				vmsToRemove.add(vm);
			}
		}
		return vmsToRemove;
	}

	/**
	 * Destroys a VM running in the host.
	 * 
	 * @param vm
	 *            the VM
	 * @pre $none
	 * @post $none
	 */
	@Override
	public void vmDestroy(Vm vm) {

		if (vm != null) {
			Log.printLine("Vm id "+vm.getId()+" destroyeed");

			vmDeallocate(vm);
			getVmList().remove(vm);
			vm.setHost(null);
		}
	}

	public double getStaticPower() {
		return staticPower;
	}

	// this is for multiusers with different time
	private final static List<? extends Vm> vmListJustAdded = new ArrayList<Vm>();
	private static double clockJustAdded = -1;
	
	public static double getClockJustAdded(){
		return clockJustAdded;
	}
	
public static void setClockJustAdded( double time){
		clockJustAdded=time;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Vm> List<T> getVmListJustAdded() {
		return (List<T>) vmListJustAdded;
	}
}