package main;

/**
 * This file is the main file.
 *
 * @author HayaKus
 */

import gui.GUI;
import gui.LogThreadSendToArea;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.power.PowerVmSelectionPolicyMinimumMigrationTime;
import output.OutputHtml;
import process.GetPower;
import structure.PowerMonitor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
	public static List<PowerMonitor> monitorList = new ArrayList<PowerMonitor>();

	public static void main(String[] args){

		GUI.showGUI();

	}
	
	public static void main2() throws FileNotFoundException {

		//This exports simulation log to file
		OutputStream output = new FileOutputStream("output-text.txt");
		Log.setOutput(output); 
		
		try {

			Collection<Object> conArgs = new ArrayList<Object>();
			//All Schedulers must be under  vmSchedulers
		
			//GetPower(ShortName, enable log (1-0),ClassName,Constructor Arguments)
			
			//Round Robin
			GetPower RR = new GetPower("RR",1,"pink","RoundRobinVmAllocationPolicy",conArgs);
			conArgs.clear();
			
			//DVFS
			GetPower DVFS = new GetPower("DVFS", 1, "Red", "DVFS" ,conArgs);
			conArgs.clear();

			
			//Single Threshold
			//Single threshold constructor (hostList <- this goes auto ,vmSelectionPolicy,utilizationThreshold);
			conArgs.add(new PowerVmSelectionPolicyMinimumMigrationTime()); // adding vmSelectionPolicy
			conArgs.add(0.8);// adding utilizationThreshold
			GetPower ST = new GetPower("ST", 1, "yellow", "PowerVmAllocationPolicyMigrationStaticThreshold", conArgs);

			
			GetPower npa = new GetPower("NPA", 1, "nocolor", null,null); // must be last, it runs at maximun of simulation time

			//Adding some static parameters from one GetPowerObject
			String simParAttr[] = { "" + PowerMonitor.maximunSimulation + "",
					"" + DVFS.getHostList().size() + "",
					"" + DVFS.getVmListSize() + "",
					"" + DVFS.getCloudletListSize() + "",
					"" + DVFS.getHostListTotalPower() + "",
					"" + DVFS.getMonitor().getVmListTotalMips() + "" };
			
			OutputHtml.printResults(simParAttr, monitorList);
			//CLEAR THE STATIC LIST
			monitorList = new ArrayList<PowerMonitor>();
		}

		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
		System.out.println("Success");

		GUI.setProgressBar("Success");
		LogThreadSendToArea l = new LogThreadSendToArea("模拟完成！");
		l.start();

	}
	public static void AddToMonitorList(PowerMonitor monitor){
	
		System.out.println("Lam:Main.AddToMonitorList");

		LogThreadSendToArea l = new LogThreadSendToArea("=====================================\n正在 读取监听列表……");
		l.start();

		monitorList.add(monitor);
	}
	
	/**
	 * Prints the Cloudlet objects
	 * 
	 * @param list
	 *            list of Cloudlets
	 */
	public static void printCloudletList(List<Cloudlet> list) {
		
		System.out.println("Lam:Main.printCloudletList");
		
		int size = list.size();
		Cloudlet cloudlet;
	
		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time" + indent + "USER");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet
						.getVmId() + indent + indent + indent + dft
						.format(cloudlet.getActualCPUTime()) + indent + indent + dft
						.format(cloudlet.getExecStartTime()) + indent + indent + indent + dft
						.format(cloudlet.getFinishTime()) + indent + cloudlet
						.getUserId());
			}
		}
	}
}