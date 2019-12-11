package initialization;

/**
 * This file is aim to initial PowerVmPlus.
 *
 * @author HayaKus
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import gui.LogThreadSendToArea;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.CloudletSchedulerDynamicWorkload;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;

import structure.Constant;
import structure.PowerVmPlus;
import process.GetPower;

public class InitialVm{
	public static ArrayList<PowerVmPlus> createVmList(int brokerId, int usr) {
		ArrayList<PowerVmPlus> vms = new ArrayList<PowerVmPlus>();		
		
		System.out.println("Lam:InitialVm.createVmList");

		for (int type_of_vms=0;type_of_vms<Constant.USR_VMNUM_OWNED[usr].length;type_of_vms++){
			for(int vm_number=0;vm_number<Constant.USR_VMNUM_OWNED[usr][type_of_vms];vm_number++){
				
//				Log.printLine("Dhmiourgoume typou "+Constants.USR_TYPE_OWNED[usr][type_of_vms]+
//						" to vm " + vm_number + " week shows "+Constants.USR_VMNUM_OWNED[0][type_of_vms] );
				
				
				int hostTypeId = 1000 *( Constant.USR_TYPE_OWNED[usr][type_of_vms]+1)+10000*(usr+1);
				int i =Constant.USR_TYPE_OWNED[usr][type_of_vms];
				int vmType = i;
				CloudletScheduler cloudScheduler = new CloudletSchedulerTimeShared();
				int id = hostTypeId + vm_number+1;
				//Log.printLine("to id toy vm toy xristi "+ usr + " einai " + id);

				if (Constant.CLOUD_ALLOCATION[i] == 1)
					cloudScheduler = new CloudletSchedulerTimeShared();
				

				if (Constant.CLOUD_ALLOCATION[i] == 3)
					cloudScheduler = new CloudletSchedulerDynamicWorkload(
							Constant.VM_MIPS[vmType], Constant.VM_PES[vmType]);
				if  ((Constant.CLOUD_ALLOCATION[i] == 2) || (GetPower.getAllocName()=="NPA"))
					cloudScheduler = new CloudletSchedulerSpaceShared();
				vms.add(new PowerVmPlus(
						id,
						brokerId,
						Constant.VM_MIPS[vmType],
						Constant.VM_PES[vmType], 
						Constant.VM_RAM[vmType],
						Constant.VM_BW,
						Constant.VM_SIZE, 1, "Xen",
						cloudScheduler,
						Constant.SCHEDULING_INTERVAL,0,0,0));
				
				
			}
		}
		
	 long seed = System.nanoTime();
		 Collections.shuffle(vms, new Random(5));

		return vms;
	}
}