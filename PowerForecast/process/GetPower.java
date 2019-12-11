package process;

/**
 * This file is aim to get power.
 *
 *@author HayaKus
*/

import gui.GUI;
import gui.LogThreadSendToArea;
import initialization.InitialDatacenter;
import main.Main;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Helper;
import org.cloudbus.cloudsim.power.PowerDatacenterNonPowerAware;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.power.models.PowerModelLinear;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import structure.BrokerPlus;
import structure.Constant;
import structure.PowerDatacenterPlus;
import structure.PowerMonitor;
import test.DVFS;

import java.util.*;

//import org.cloudbus.cloudsim.NetworkTopology;

public class GetPower {
	private double lastClock = 0;
	private List<Host> hostList;
	private ArrayList<Vm> vmList = new ArrayList<Vm>();;
	private ArrayList<Cloudlet> newList = new ArrayList<Cloudlet>();;
	private ArrayList<BrokerPlus> glBrokerList = new ArrayList<BrokerPlus>();;
	private double datacenterPower;
	private PowerMonitor monitor;
	private static String allocName;

	public GetPower(String allocator, int logOutput, String color,
			String allocationClassName, Collection<Object> conArg) {
		
		System.out.println("Lam:GetPower:"+allocator);
		GUI.setProgressBar(allocator);

		if(!allocator.equals("NPA")){
			LogThreadSendToArea l = new LogThreadSendToArea("=====================================\n算法 "+allocator+" 模拟：\n=====================================");
			l.start();
		}
		
		// The list of our brokers

		Log.printLine("=========== " + allocator + " Algorithm ============");
		if (logOutput == 0) {
			Log.disable();
		} else {
			Log.enable();
		}
		try {

			int num_user = Constant.USERS_DELAY.length; // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
			if (allocator == "NPA") {
				CloudSim.terminateSimulation(PowerMonitor.maximunSimulation);
			} else {
				CloudSim.terminateSimulation(Constant.SIMULATION_LIMIT);
			}
			setAllocName(allocator);
			// First Step : Create brokers, Vms , Cloudlets
			for (int userId = 0; userId < Constant.USERS_DELAY.length; userId++) {
				BrokerPlus globalBroker = new BrokerPlus(
						"GlobalBroker" + userId, userId);
				glBrokerList.add(globalBroker);
			}
			
			// Second step: Create Datacenters
			PowerDatacenterPlus datacenter = null;
			PowerDatacenterNonPowerAware datacenterNPA = null;
			if (allocator == "NPA") {
				datacenterNPA = createNPADatacenter("Datacenter_0");
			} else {
				datacenter = InitialDatacenter.createDatacenter(allocator, 1,
						allocationClassName, conArg);
			}
			
			// Topology addition (future work)
			if (allocator != "NPA") {

				for (BrokerPlus glBroker : glBrokerList) {
					Log.printLine("Linking " + datacenter.getId() + " with user " + glBroker
							.getId());
					NetworkTopology.addLink(datacenter.getId(),
							glBroker.getId(), 10.0, 10);
				}
			}
			
			// Third step: Starts the simulation
			lastClock = CloudSim.startSimulation();
			
			// Fourth : We get all cloudlets and VMs to print
			for (BrokerPlus glBroker : glBrokerList) {
				vmList.addAll(glBroker.getVmList());
				newList.addAll(glBroker.getBroker().getCloudletReceivedList());
			}

			CloudSim.stopSimulation();
			if (allocator != "NPA") {
				monitor = datacenter.getPowerMonitor();
				// Print the debt of each user to each datacenter
				hostList = datacenter.getHostList();

				Helper.printResults(datacenter, vmList, lastClock, allocator,
						Constant.OUTPUT_CSV, "");

				setDatacenterPower(datacenter.getPower() / (3600 * 1000));
				monitor.setDatacenter(datacenter);
				monitor.setVmList(vmList);
				monitor.setName(allocator.replaceAll(" ", "_"));
				monitor.setColor(color);
				Main.AddToMonitorList(monitor);

			} else {
				hostList = datacenterNPA.getHostList();
				Helper.printResults(datacenterNPA, vmList, lastClock,
						allocator, Constant.OUTPUT_CSV, "");
				PowerMonitor.npaPower = datacenterNPA.getPower() / (3600 * 1000);
				setDatacenterPower(datacenterNPA.getPower() / (3600 * 1000));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	public static String getAllocName() {
		return allocName;
	}

	private void setAllocName(String name) {
		allocName = name;
	}

	private void setDatacenterPower(double d) {
		this.datacenterPower = d;
	}

	public int getVmListSize() {
		return vmList.size();
	}

	public int getHostListTotalPower() {
		int sum = 0;
		for (Host host : hostList) {
			sum += host.getTotalMips();
		}
		return sum;
	}

	public double getLastClock() {
		return lastClock;
	}

	public List<Host> getHostList() {
		return hostList;
	}

	public int getCloudletListSize() {
		return newList.size();
	}

	public PowerMonitor getMonitor() {
		return monitor;
	}

	public double getDatacenterPower() {
		return datacenterPower;
	}

	protected PowerDatacenterNonPowerAware createNPADatacenter(String name) {

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

				hostList.add(new PowerHostUtilizationHistory(id,
						new RamProvisionerSimple(Constant.HOST_RAM[hostType]),
						new BwProvisionerSimple(Constant.HOST_BW),
						Constant.HOST_STORAGE, peList, vmScheduler,
						new PowerModelLinear(Constant.HOST_MAX_POWER[hostType],
								Constant.HOST_MAX_POWER_PERCENT[hostType])

				));
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
		PowerDatacenterNonPowerAware datacenter = null;
		try {
			VmAllocationPolicy vmAllocationPolicy = new DVFS(hostList);
			datacenter = new PowerDatacenterNonPowerAware(name,
					characteristics, vmAllocationPolicy, storageList,
					Constant.SCHEDULING_INTERVAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		datacenter.setDisableMigrations(true);
		return datacenter;
	}
}