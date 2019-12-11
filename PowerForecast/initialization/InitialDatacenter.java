package initialization;

/**
 * This file is aim to initial PowerDatacenterPlus.
 *
 * @author HayaKus
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.VmSchedulerTimeSharedOverSubscription;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import structure.Constant;
import structure.PowerDatacenterPlus;
import structure.PowerHostPlus;
import test.PowerModuleMinimumUtil;

public class InitialDatacenter {

	public static PowerDatacenterPlus createDatacenter(String name,
			int allocator, String allocationClassName,
			Collection<Object> myCollection) {
		
		System.out.println("Lam:InitialDatacenter.createDatacenter");
		
		List<PowerHost> hostList = new ArrayList<PowerHost>();
		int machineRoot = 100;
		for (int i = 0; i < Constant.HOST_TYPES; i++) {
			for (int host = 0; host < Constant.VM_HOST_NUMBERS[i]; host++) {
				// int hostType = i % Week2t2c.HOST_TYPES;
				int hostType = i;
				List<Pe> peList = new ArrayList<Pe>();
				for (int j = 0; j < Constant.HOST_PES[hostType]; j++) {
					peList.add(new Pe(j, new PeProvisionerSimple(
							Constant.HOST_MIPS[hostType])));
				}
				int id = host + machineRoot;
				// Log.printLine("Created Host" + id);
				VmScheduler vmScheduler = new VmSchedulerTimeShared(peList);
				
				if (Constant.VM_ALLOCATION[i] == 1)
					vmScheduler = new VmSchedulerTimeShared(peList);
				if (Constant.VM_ALLOCATION[i] == 2)
					vmScheduler = new VmSchedulerSpaceShared(peList);
				if (Constant.VM_ALLOCATION[i] == 3)
					vmScheduler = new VmSchedulerTimeSharedOverSubscription(
							peList);

				hostList.add(new PowerHostPlus(
						id,
						new RamProvisionerSimple(Constant.HOST_RAM[hostType]),
						new BwProvisionerSimple(Constant.HOST_BW),
						Constant.HOST_STORAGE,
						peList,
						vmScheduler,
						new PowerModuleMinimumUtil(
								Constant.HOST_MAX_POWER[hostType],
								Constant.HOST_MAX_POWER_PERCENT[hostType],
								Constant.HOST_MIN_UTIL[hostType]),
						Constant.HOST_MAX_POWER_PERCENT[hostType] * Constant.HOST_MAX_POWER[hostType]));
			}
			machineRoot += 100;
		}
		
		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 0.05; // the cost of using memory in this resource
		double costPerStorage = 0.001; // the cost of using storage in this
										// resource
		double costPerBw = 0.0; // the cost of using bw in this resource

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);
		LinkedList<Storage> storageList = new LinkedList<Storage>();
		PowerDatacenterPlus datacenter = null;

		// This is a buggy part, to make easier the import of arguments to
		// constructors i'm getting the super-est class of the argument. If its
		// not working,
		// i recommend to download the "manual way" from github and replace the
		// classes
		Class<?>[] conClassa = new Class[(myCollection.size() + 1)];
		Collection<Object> myCollectionNew = new ArrayList<Object>();
		try {
			int i = 0;
			conClassa[0] = List.class;
			myCollectionNew.add(hostList);
			for (Object obj : myCollection) {
				i++;
				myCollectionNew.add(obj);
				conClassa[i] = obj.getClass();

				if (ClassUtils.isPrimitiveWrapper(obj.getClass())) {
					conClassa[i] = ClassUtils
							.wrapperToPrimitive(obj.getClass());
				} else {
					conClassa[i] = (Class<?>) getSuperclass(obj);
				}

			}
			VmAllocationPolicy allocationPolicy = null;
			Class<?> allocationClass = Class
					.forName("test." + allocationClassName);
			if (allocationClass != null)
				allocationPolicy = (VmAllocationPolicy) allocationClass
						.getConstructor(conClassa).newInstance(
								myCollectionNew.toArray());
			datacenter = new PowerDatacenterPlus(name.replaceAll(" ", "_"),
					characteristics, allocationPolicy, storageList,
					Constant.SCHEDULING_INTERVAL);
		} catch (ClassNotFoundException e) {
			Log.enable();
			Log.printLine("Class Not found, make sure its added under vmSchedulers folder");
			e.printStackTrace();
			System.exit(0);

		} catch (InstantiationException e) {
			Log.enable();
			Log.printLine("InstantiationException error");
			e.printStackTrace();
			System.exit(0);

		} catch (IllegalAccessException e) {
			Log.enable();
			Log.printLine("IllegalAccessException error");
			e.printStackTrace();
			System.exit(0);

		} catch (NoSuchMethodException e) {
			Log.enable();
			Log.printLine("Method Not found, check your constructor");
			e.printStackTrace();
			System.exit(0);

		} catch (NullPointerException e) {
			Log.enable();
			Log.printLine("Class Not found (null pointer)");
			e.printStackTrace();
			System.exit(0);

		} catch (ArrayIndexOutOfBoundsException e) {
			Log.printLine("Array Out");
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datacenter.setDisableMigrations(false);
		return datacenter;
	}

	private static Object getSuperclass(Object o) {
		Class<?> subclass = o.getClass();
		Class<?> superclass = subclass.getSuperclass();
		try {
			while (superclass.getSuperclass() != Class
					.forName("java.lang.Object")) {

				subclass = superclass;
				superclass = subclass.getSuperclass();
			}
		} catch (ClassNotFoundException e) {
			Log.disable();
			Log.printLine("SuperClass Not Found");
			e.printStackTrace();
			System.exit(0);
		}
		return superclass;
	}
}
