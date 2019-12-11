package structure;

/**
 * This file is aim to change Broker into BrokerPlus.
 *
 * @author HayaKus
 */

import java.util.ArrayList;
import java.util.List;

import gui.LogThreadSendToArea;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

import initialization.InitialCloudlet;
import initialization.InitialVm;
import structure.Constant;
import structure.CloudletPlus;
import structure.PowerVmPlus;

public class BrokerPlus extends SimEntity {
	
	private static final int CREATE_BROKER = 0;
	private List<PowerVmPlus> vmList;
	private List<CloudletPlus> cloudletList;
	private DatacenterBroker broker;
	private int userId;
	public BrokerPlus(String name, int userId) {
		super(name);
		this.userId=userId;
	}

	@Override
	public void processEvent(SimEvent ev) {
		
		System.out.println("Lam:BrokerPlus.processEvent");

		switch (ev.getTag()) {
		case CREATE_BROKER:
			setBroker(createBroker(super.getName()+"_"));

			//Create VMs and Cloudlets and send them to broker
			setVmList(InitialVm.createVmList(getBroker().getId(), userId));				
			setCloudletList(InitialCloudlet.createCloudletList(getBroker().getId(), userId,getVmList()));

			broker.submitVmList(getVmList());
			broker.submitCloudletList(getCloudletList());
			CloudSim.resumeSimulation();
			break;

		default:
			Log.printLine(getName() + ": unknown event type");
			break;
		}
	}

	@Override
	public void startEntity() {
		
		System.out.println("Lam:BrokerPlus.startEntity");

		Log.printLine(super.getName()+" is starting...");
		schedule(getId(), Constant.USERS_DELAY[userId], CREATE_BROKER);
	}

	@Override
	public void shutdownEntity() {
	}

	public List<PowerVmPlus> getVmList() {
		return vmList;
	}

	protected void setVmList(ArrayList<PowerVmPlus> arrayList) {
		this.vmList = arrayList;
	}

	public List<CloudletPlus> getCloudletList() {
		return cloudletList;
	}

	protected void setCloudletList(ArrayList<CloudletPlus> arrayList) {
		this.cloudletList = arrayList;
	}

	public DatacenterBroker getBroker() {
		return broker;
	}

	protected void setBroker(DatacenterBroker broker) {
		this.broker = broker;
	}
	
	
	private static DatacenterBroker createBroker(String name){
		
		System.out.println("Lam:BrokerPlus.createBroker");

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
}