package output;

/**
 * This file is aim to output the results of modeling to a web page.
 *
 * @author HayaKus
 */

import com.mathworks.toolbox.javabuilder.MWException;
import gui.GUI;
import gui.LogThreadSendToArea;
import model.Evaluation_QoS;
import model.ITEnvironment_RBF;
import model.IT_RBF;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostStateHistoryEntry;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.power.PowerHost;
import structure.Constant;
import structure.PowerHostPlus;
import structure.PowerMonitor;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputHtml {

	public static double[] ITEnergy = new double[4];

	public static double[] AllEnergy= new double[4];

	// 计算后的所有功率
	public static List<Double> allPowers;

	// 计算后的所有QoS评估值
	public static List<Double> allQoSs;

	// this is the average power of three algorithm
	public static List<Double> RRPower;
	public static List<Double> DVFSPower;
	public static List<Double> STPower;

	public static List<Double> RRQoS;
	public static List<Double> DVFSQoS;
	public static List<Double> STQoS;

	//other power of data center
	public static List<Double> otherPowers;

	//postition of interrupt in the allPowers
	public static int[] PoIs;

	//the last position of one of algorithms in the allPowers
	public static int[] threePoI;

	public static void printResults( String simParAttr[],
			List<PowerMonitor> monitors) throws IOException, MWException {
		
		System.out.println("Lam:OutputHtml.printResults");

		LogThreadSendToArea l = new LogThreadSendToArea("正在 计算结果……");
		l.start();

		exportHostStats(monitors);
		String monitorGraphs = "";
		String graphMonitor = "";		
		String menu = "<li><a href='./exports/combined.html'>混合对比</a></li>";
		String inputNames = "'无调度算法',";
		double allPower = PowerMonitor.npaPower + (Constant.ITENVIRONMENTMAXENERGY + Constant.FIREENERGY + Constant.INFRASTRUCTUREENERGY)*PowerMonitor.maximunSimulation / (3600 * 1000);
		String inputPower =allPower+",";
		String inputNamesTr = "<tr><td style=\"text-align:center;\">无调度算法</td><td style=\"text-align:center;\">" + String.format("%.2f",PowerMonitor.npaPower)  + "</td><td style=\"text-align:center;\">" + String.format("%.2f",allPower) + "</td><td style=\"text-align:center;\">-</td><td style=\"text-align:center;\">-</td><td style=\"text-align:center;\">-</td></tr>";
		String simParTr = "";
		int i=0;
		for (PowerMonitor monitor : monitors) {
			monitor.PowerMonitorMath();
			menu += "<li><a href='./exports/" + monitor.getName() + ".html'>" + monitor
					.getName() + "</a></li>";

		}

		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df2 = new DecimalFormat("###,###.###");

		// Header
		// 去除首页顶端logo
		String header = "<!doctype html><html><head><meta charset='utf-8'><title>云边融合的能耗和评估模拟平台</title><link href='css/bootstrap.min.css' rel='stylesheet'><link href='css/bootstrap.css' rel='stylesheet'><script src='RGraph.common.core.js'></script><script src='RGraph.bar.js'></script><script src='RGraph.line.js'></script><link href='css/styles.css' rel='stylesheet'></head>\t<nav id='main-nav' class='navbar navbar-default navbar-fixed-top' role='banner'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle' data-toggle='collapse' data-target='.navbar-collapse'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a class='navbar-brand' href='../output/index.html'><img src='images/logo.png' alt='logo'></a></div><div class='collapse navbar-collapse navbar-right'><ul class='nav navbar-nav'><li class='active'><a href=./index.html>主页</a></li>" + menu + "</ul></div></div></nav></header>";
//		String header = "<!doctype html><html><head><meta charset='utf-8'><title>云边融合的能耗和评估模拟平台</title><link href='css/bootstrap.min.css' rel='stylesheet'><link href='css/bootstrap.css' rel='stylesheet'><script src='RGraph.common.core.js'></script><script src='RGraph.bar.js'></script><script src='RGraph.line.js'></script><link href='css/styles.css' rel='stylesheet'></head>\t<nav id='main-nav' class='navbar navbar-default navbar-fixed-top' role='banner'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle' data-toggle='collapse' data-target='.navbar-collapse'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a class='navbar-brand' href='../output/index.html'></a></div><div class='collapse navbar-collapse navbar-right'><ul class='nav navbar-nav'><li class='active'><a href=./index.html>主页</a></li>" + menu + "</ul></div></div></nav></header>";
		String footer = "</div></body></html>";

		// Building secondary pages
		// 去除首页顶端logo
		String header2 = "<!doctype html><html><head><meta charset='utf-8'><title>云边融合的能耗和评估模拟平台</title><link href='../css/bootstrap.min.css' rel='stylesheet'><link href='../css/bootstrap.css' rel='stylesheet'><link href='../css/styles.css' rel='stylesheet'><script src='../RGraph.common.core.js'></script><script src='../RGraph.common.tooltips.js'></script><script src='../RGraph.common.dynamic.js'></script><script src='../RGraph.bar.js'></script><script src='../RGraph.line.js'></script></head><header id='header'><nav id='main-nav' class='navbar navbar-default navbar-fixed-top' role='banner'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle' data-toggle='collapse' data-target='.navbar-collapse'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a class='navbar-brand' href='../index.html'><img src='../images/logo.png' alt='logo'></a></div><div class='collapse navbar-collapse navbar-right'><ul class='nav navbar-nav'><li><a href='../index.html'>主页</a></li>" + menu + "</ul></div></div></nav></header>";
//		String header2 = "<!doctype html><html><head><meta charset='utf-8'><title>云边融合的能耗和评估模拟平台</title><link href='../css/bootstrap.min.css' rel='stylesheet'><link href='../css/bootstrap.css' rel='stylesheet'><link href='../css/styles.css' rel='stylesheet'><script src='../RGraph.common.core.js'></script><script src='../RGraph.common.tooltips.js'></script><script src='../RGraph.common.dynamic.js'></script><script src='../RGraph.bar.js'></script><script src='../RGraph.line.js'></script></head><header id='header'><nav id='main-nav' class='navbar navbar-default navbar-fixed-top' role='banner'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle' data-toggle='collapse' data-target='.navbar-collapse'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a class='navbar-brand' href='../index.html'></a></div><div class='collapse navbar-collapse navbar-right'><ul class='nav navbar-nav'><li><a href='../index.html'>主页</a></li>" + menu + "</ul></div></div></nav></header>";
		String customHeader = header2.replaceAll("./exports/", "./");

		/**
		//  AllEnergy start: collecte
		List<Double> Var1 = new ArrayList<>();
		List<Double> Var2 = new ArrayList<>();
		List<Double> Var3 = new ArrayList<>();
		List[] Vars = new List[]{Var1, Var2, Var3};
		int counter = 0;
		int hostnum;
		double sched_int = Constant.SCHEDULING_INTERVAL;
		for (PowerMonitor monitor : monitors) {
			hostnum = 0;
			for (double[] big : monitor.getAllInfo()) {
				double previoustime = monitor.getMaxSimTime();
				for (double[] host : monitor.getAllInfo()) {
					if (host[0] == big[0]) {
						if ((int) host[4] <= ((int) (previoustime + sched_int))) {
							Vars[counter].add(host[3]/100);
							Vars[counter].add(host[5]);
							previoustime = host[4];
						} else {
							while (previoustime < (host[0] - 2*sched_int)) {
								previoustime += sched_int;
								Vars[counter].add((double)0);
								Vars[counter].add((double)0);
							}
							Vars[counter].add(host[3]/100);
							Vars[counter].add(host[5]);
							previoustime = host[4];
						}
					}
				}

				if (hostnum == (monitor.getHostNum() - 1)) {
					break;
				}
				hostnum++;
			}
			counter++;
		}

		// Calculation
		List<Double> allVar = new ArrayList<>();
		for (double tempVar : Var1) {
			allVar.add(tempVar);
		}
		for (double tempVar : Var2) {
			allVar.add(tempVar);
		}
		for (double tempVar : Var3) {
			allVar.add(tempVar);
		}
		List<Double> Powers = IT_RBF.getPowerNN(allVar);
		*/

		List<Double> Powers = new ArrayList<>();
		List<Double> QoSs = new ArrayList<>();

		RRPower = new ArrayList<>();
		DVFSPower = new ArrayList<>();
		STPower = new ArrayList<>();

		RRQoS = new ArrayList<>();
		DVFSQoS = new ArrayList<>();
		STQoS = new ArrayList<>();

		double tempPower = 0;
		double tempQoS = 0;
		int lengthOfTime;

		lengthOfTime = PoIs[1]-PoIs[0];
		//267 times
		for(int tempi=0;tempi<lengthOfTime;tempi++){
			//50 times
			for(int j=0;j<Constant.NUMBER_HOST;j++){
				tempPower += allPowers.get(j*lengthOfTime+tempi);
				tempQoS += allQoSs.get(j*lengthOfTime+tempi);
			}
			Powers.add(tempPower);
			QoSs.add(tempQoS);
			RRPower.add(tempPower / Constant.NUMBER_HOST);
			RRQoS.add(tempQoS / Constant.NUMBER_HOST);
			tempPower = 0;
			tempQoS = 0;
		}
		lengthOfTime = PoIs[1+Constant.NUMBER_HOST]-PoIs[0+Constant.NUMBER_HOST];
		for(int tempi=0;tempi<lengthOfTime;tempi++){
			for(int j=0;j<Constant.NUMBER_HOST;j++){
				tempPower += allPowers.get(j*lengthOfTime+tempi+threePoI[0]+1);
				tempQoS += allQoSs.get(j*lengthOfTime+tempi+threePoI[0]+1);
			}
			Powers.add(tempPower);
			QoSs.add(tempQoS);
			DVFSPower.add(tempPower / Constant.NUMBER_HOST);
			DVFSQoS.add(tempQoS / Constant.NUMBER_HOST);
			tempPower = 0;
			tempQoS = 0;
		}
		lengthOfTime = PoIs[1+Constant.NUMBER_HOST*2]-PoIs[0+Constant.NUMBER_HOST*2];
		for(int tempi=0;tempi<lengthOfTime;tempi++){
			for(int j=0;j<Constant.NUMBER_HOST;j++){
				tempPower += allPowers.get(j*lengthOfTime+tempi+threePoI[1]+1);
				tempQoS += allQoSs.get(j*lengthOfTime+tempi+threePoI[1]+1);
			}
			Powers.add(tempPower);
			QoSs.add(tempQoS);
			STPower.add(tempPower / Constant.NUMBER_HOST);
			STQoS.add(tempQoS / Constant.NUMBER_HOST);
			tempPower = 0;
			tempQoS = 0;
		}

		otherPowers = new ArrayList<>();
		otherPowers = ITEnvironment_RBF.getPowerNN(Powers);

		for (int tempi = 0; tempi < AllEnergy.length; tempi++) {
			ITEnergy[tempi] = 0;
			AllEnergy[tempi] = 0;
		}
		//RR
		int lengthOfTime1 = PoIs[1]-PoIs[0];
		for (int j = 0;j<lengthOfTime1;j++) {
			// w·s
			ITEnergy[1] += (Powers.get(j) * Constant.SCHEDULING_INTERVAL);
			AllEnergy[1] += ((otherPowers.get(j)+ Constant.FIREENERGY + Constant.INFRASTRUCTUREENERGY) * Constant.SCHEDULING_INTERVAL);
		}
		// kw·h
		ITEnergy[1] = ITEnergy[1]/(1000*3600);
		AllEnergy[1] = ITEnergy[1] + AllEnergy[1]/(1000*3600);
		//DVFS
		int lengthOfTime2 = PoIs[1+Constant.NUMBER_HOST]-PoIs[0+Constant.NUMBER_HOST];
		for (int j = lengthOfTime1;j<(lengthOfTime1+lengthOfTime2);j++) {
			// w·s
			ITEnergy[2] += (Powers.get(j) * Constant.SCHEDULING_INTERVAL);
			AllEnergy[2] += ((otherPowers.get(j)+ Constant.FIREENERGY + Constant.INFRASTRUCTUREENERGY) * Constant.SCHEDULING_INTERVAL);
		}
		// kw·h
		ITEnergy[2] = ITEnergy[2]/(1000*3600);
		AllEnergy[2] = ITEnergy[2] + AllEnergy[2]/(1000*3600);
		//ST
		int lengthOfTime3 = PoIs[1+Constant.NUMBER_HOST*2]-PoIs[0+Constant.NUMBER_HOST*2];
		for (int j = lengthOfTime1+lengthOfTime2;j<lengthOfTime1+lengthOfTime2+lengthOfTime3;j++) {
			// w·s
			ITEnergy[3] += (Powers.get(j) * Constant.SCHEDULING_INTERVAL);
			AllEnergy[3] += ((otherPowers.get(j)+ Constant.FIREENERGY + Constant.INFRASTRUCTUREENERGY) * Constant.SCHEDULING_INTERVAL);
		}
		// kw·h
		ITEnergy[3] = ITEnergy[3]/(1000*3600);
		AllEnergy[3] = ITEnergy[3] + AllEnergy[3]/(1000*3600);

		int counter = 0;
		int counter2 = 0;
		for (PowerMonitor monitor : monitors) {
			String[] hostCanvas = makeHostLine(monitor);
			File yourFile = new File(
					"output/exports/" + monitor.getName() + ".html");
			if (!yourFile.exists()) {
				yourFile.createNewFile();
			}

			customHeader = customHeader
					.replaceAll(
							"<li><a href='./" + monitor.getName() + ".html'>" + monitor
									.getName() + "</a></li>",
							"<li class='active'><a href='./" + monitor
									.getName() + ".html'>" + monitor.getName() + "</a></li>");

			monitorGraphs = "<script> window.onload = function (){" + makeLine(monitor) + hostCanvas[1] + "}</script>";
			graphMonitor += "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h2 style='margin-top:150px;margin-bottom:30px;'>所有物理服务器的平均功率(W)</h2><div class='span12'><h3 style='margin-top:10px;'>平均功率/W</h3><canvas id='powerGraph" + monitor
					.getName() + "' width='1024' height='250'>[No canvas support]</canvas><h3 style='text-align:center;'>时间戳</h3><script></script></div></div>";
			graphMonitor += "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h2 style='margin-top:40px;margin-bottom:30px;'>所有物理服务器的平均评估值</h2><div class='span12'><h3 style='margin-top:10px;'>平均评估值</h3><canvas id='utilGraph" + monitor
					.getName() + "' width='1024' height='250'>[No canvas support]</canvas><h3 style='text-align:center;'>时间戳</h3><script></script></div></div>";
			graphMonitor += hostCanvas[0];

			try {

				PrintStream out = new PrintStream(new FileOutputStream(
						"output/exports/" + monitor.getName() + ".html"));

				out.println(customHeader + monitorGraphs + graphMonitor + footer);
				out.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			graphMonitor = "";
			customHeader = customHeader
					.replaceAll(
							"<li class='active'><a href='./" + monitor
									.getName() + ".html'>" + monitor.getName() + "</a></li>",
							"<li><a href='./" + monitor.getName() + ".html'>" + monitor
									.getName() + "</a></li>");

			//GRAPH (1stPage)
			inputNames += "'" + monitor.getName() + "'";
			inputPower += monitor.getDatacenterPower();

			// df.format(monitor.getDatacenterPower())
			inputNamesTr += "<tr><td style=\"text-align:center;\">" + monitor.getName()  + "</td><td style=\"text-align:center;\">" + df.format(ITEnergy[++counter]) + "</td><td style=\"text-align:center;\">" + df.format(AllEnergy[++counter2]) + "</td><td style=\"text-align:center;\">" +
					""+df2.format(monitor.getVmListTotalInstCompleted())+"</td><td style=\"text-align:center;\">"+( (int)( monitor.getAvSLA()*100))+"%</td><td style=\"text-align:center;\">"+String.format("%d", monitor.getMigrations())+"</td></tr>";

			if (i != (monitors.size() - 1)) {
				inputNames += ",";
				inputPower += ",";
			}
			i++;
		}
		
		
		// Graph (1st page)

		String simParName[]={"总模拟时间(s)", "总物理服务器数量","总虚拟机数量","总云任务数量","云数据中心最小能耗(kW·h)","虚拟机的总计算能力(MIPS)"};

		AllEnergy[0]=allPower;
		inputPower = AllEnergy[0] + "," + AllEnergy[1] + "," + AllEnergy[2] + "," + AllEnergy[3];
		double minEnergy = Double.MAX_VALUE;
		for (double energy : AllEnergy) {
			if (energy < minEnergy) {
				minEnergy = energy;
			}
		}
		simParAttr[4] = String.valueOf(df.format(minEnergy));
		for ( i = 0; i < simParName.length; i++) {
			simParTr += "<tr><td>" + simParName[i] + "</td><td>" + simParAttr[i] + "</td></tr>";
		}

		String graphs = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:150px;margin-bottom:30px;'>" + "总能耗对比</h1><div class='span12'><h3 style='margin-top:10px;'>能耗/kW·h</h3><script> window.onload = function (){" + "var data = [" + inputPower + "];var bar = new RGraph.Bar('myCanvas', data);bar.Set('chart.labels', [" + inputNames + "]);bar.Set('chart.gutter.left', 35); bar.Draw();}</script>" + "<canvas id='myCanvas' width='1000' height='450'>[No canvas support]</canvas><h3 style=\"text-align:center;\">调度算法名称</h3></div></div>" +
				"<div class='row' style='margin-left:30px;'>" + "<div class='span16'><table class='table table-bordered'><thead><tr><th>调度算法名称</th><th>IT系统能耗(kW·h)</th><th>总能耗(kW·h)</th><th>总处理进程数</th><th>平均SLA</th><th>迁移数</th></thead><tbody>" + inputNamesTr + "</tbody></table></div><div class='span4'><table class='table table-bordered'><thead><tr><th>模拟情况</th></thead><tbody>" + simParTr + "</tbody></table></div></div>";

		// Combined view
		String[] hostCompinedCanvas = makeHostCombinedLine(monitors);
		customHeader = header2.replaceAll("./exports/", "../exports/");
		customHeader = customHeader.replaceAll(
				"<li><a href='../exports/combined.html'>混合对比</a></li>",
				"<li class='active'><a href='#'>混合对比</a></li>");
		monitorGraphs = "<script> window.onload = function (){" + hostCompinedCanvas[1] + "}</script>";
		graphMonitor = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h2 style='margin-top:150px;margin-bottom:30px;'>所有物理服务器的平均功率(W)</h2><div class='span12'><h3 style='margin-top:10px;'>平均功率/W</h3><canvas id='powerGraph' width='1024' height='250'>[No canvas support]</canvas><h3 style='text-align:center;'>时间戳</h3></div></div>";
		graphMonitor += "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h2 style='margin-top:40px;margin-bottom:30px;'>所有物理服务器的平均评估值</h2><div class='span12'><h3 style='margin-top:10px;'>平均评估值</h3><canvas id='utilGraph' width='1024' height='250'>[No canvas support]</canvas><h3 style='text-align:center;'>时间戳</h3></div></div>";
		graphMonitor += hostCompinedCanvas[0];
		try {

			PrintStream out = new PrintStream(new FileOutputStream(
					"output/exports/combined.html"));

			out.println(customHeader + monitorGraphs + graphMonitor + footer);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// ends here

	
		// Printing Hosts
		String host = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:40px;margin-bottom:30px;'>物理服务器</h1>";
		for ( i = 0; i < Constant.HOST_TYPES; i++) {
			if (Constant.VM_HOST_NUMBERS[i] == 0) {
				continue;
			}
			host += "<div class='span4'><table class='table table-bordered'><thead><tr><th>第 " + i + " 类服务器</th></tr></thead><tbody>" + "<tr><td>数量</td><td>" + Constant.VM_HOST_NUMBERS[i] + "</td></tr>" + "<tr><td>处理能力(MIPS)</td><td>" + Constant.HOST_MIPS[i] + "</td></tr>" + "<tr><td>核心数</td><td>" + Constant.HOST_PES[i] + "</td></tr>" + "<tr><td>内存(MB)</td><td>" + Constant.HOST_RAM[i] + "</td></tr>" + "<tr><td>带宽(bit/s)</td><td>" + Constant.HOST_BW + "</td></tr>" + "<tr><td>存储(B)</td><td>" + Constant.HOST_STORAGE + "</td></tr>" + "<tr><td>最大功耗(瓦)</td><td>" + Constant.HOST_MAX_POWER[i] + "</td></tr>" + "<tr><td>空闲功耗(瓦)</td><td>" + df
					.format(Constant.HOST_MIN_POWER[i]) + "</td></tr></tbody></table></div>";
		}
		host += "</div>";

		// Printing Vm Characteristics
		String vm = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:40px;margin-bottom:30px;'>虚拟机</h1>";
		for ( i = 0; i < Constant.VM_MIPS.length; i++) {
			vm += "<div class='span4'><table class='table table-bordered'><thead><tr><th>第 " + i + "类虚拟机</th></tr></thead><tbody>" + "<tr><td>处理能力(MIPS)</td><td>" + Constant.VM_MIPS[i] + "</td></tr>" + "<tr><td>核心数</td><td>" + Constant.VM_PES[i] + "</td></tr>" + "<tr><td>内存(MB)</td><td>" + Constant.VM_RAM[i] + "</td></tr>" + "<tr><td>带宽(bit/s)</td><td>" + Constant.VM_BW + "</td></tr>" + "<tr><td>存储(MB)</td><td>" + Constant.VM_SIZE + "</td></tr></tbody></table></div>";
		}
		vm += "</div>";

		// Printing Cloudlet Characteristics
		String cloudlet = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:40px;margin-bottom:30px;'>云任务</h1>";
		for ( i = 0; i < Constant.CLOUDLET_LENGTH.length; i++) {
			cloudlet += "<div class='span4'><table class='table table-bordered'><thead><tr><th>第 " + i + " 种云任务</th></tr></thead><tbody>" + "<tr><td>任务量(MI)</td><td>" + Constant.CLOUDLET_LENGTH[i] + "</td></tr>" + "<tr><td>文件数量</td><td>" + Constant.CLOUDLET_FILESIZE[i] + "</td></tr>" + "<tr><td>输出文件数量</td><td>" + Constant.CLOUDLET_OUTPUTSIZE[i] + "</td></tr>" + "</tbody></table></div>";
		}
		cloudlet += "</div>";

		// Printing Users Characteristics
		String users = "<div class='row' style='border-top: 2px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:40px;margin-bottom:30px;'>用户</h1>";
		String vmUser = "";

		for ( i = 0; i < Constant.USERS_DELAY.length; i++) {
			for (int k = 0; k < Constant.USR_VMNUM_OWNED[i].length; k++) {

				vmUser += "<tr><td>装载第 " + Constant.USR_CLOUDLET_TYPE[i][k] + " 种云任务的第 " + Constant.USR_TYPE_OWNED[i][k] + " 种虚拟机的数量</td><td>" + Constant.USR_VMNUM_OWNED[i][k] + "</td></tr>";
			}

		/*	for (int k = 0; k < Constant.USR_CLOUDLET_TYPE[i].length; k++) {
				//vmUser += "<tr><td>Cloudlet Type " + (Constant.USR_CLOUDLET_TYPE[i][k] + 1) + "</td><td>" + Constant.USR_VMNUM_OWNED[i][k] + "</td></tr>";
				vmUser += "<tr><td>Cloudlet Type " + (Constant.USR_CLOUDLET_TYPE[i][k] + 1) + "</td><td>" + Constant.USR_VMNUM_OWNED[i][k] + "</td></tr>";
			}
*/
			users += "<div class='span4'><table class='table table-bordered'><thead><tr><th> " + i + " 号用户</th></tr></thead><tbody>" + vmUser + "</tbody></table></div>";
			vmUser = "";
		}
		users += "</div>";

		// 打印QoS参数相关指标
		String qosStr = "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><h1 style='margin-top:40px;margin-bottom:30px;'>基于QoS感知区域评估的相关参数</h1>";
		qosStr += "<div class='span4'><table class='table table-bordered'><thead><tr><th>CPU利用率</th></tr></thead><tbody>" + "<tr><td>最佳值（%）</td><td>" + Constant.BEST_CPU + "</td></tr>" + "<tr><td>最差值（%）</td><td>" + Constant.WORST_CPU + "</td></tr>" + "<tr><td>权重（%）</td><td>" + Constant.WEIGHT_CPU + "</td></tr>" + "</tbody></table></div>";
		qosStr += "<div class='span4'><table class='table table-bordered'><thead><tr><th>服务器负载</th></tr></thead><tbody>" + "<tr><td>最佳值（台）</td><td>" + Constant.BEST_HOSTLOAD + "</td></tr>" + "<tr><td>最差值（台）</td><td>" + Constant.WORST_HOSTLOAD + "</td></tr>" + "<tr><td>权重（%）</td><td>" + Constant.WEIGHT_HOSTLOAD + "</td></tr>" + "</tbody></table></div>";
		qosStr += "<div class='span4'><table class='table table-bordered'><thead><tr><th>带宽</th></tr></thead><tbody>" + "<tr><td>最佳值（GB）</td><td>" + Constant.BEST_BANDWIDTH + "</td></tr>" + "<tr><td>最差值（GB）</td><td>" + Constant.WORST_BANDWIDTH + "</td></tr>" + "<tr><td>权重（%）</td><td>" + Constant.WEIGHT_BANDWIDTH + "</td></tr>" + "</tbody></table></div>";
		qosStr += "<div class='span4'><table class='table table-bordered'><thead><tr><th>内存利用率</th></tr></thead><tbody>" + "<tr><td>最佳值（%）</td><td>" + Constant.BEST_RAM + "</td></tr>" + "<tr><td>最差值（%）</td><td>" + Constant.WORST_RAM + "</td></tr>" + "<tr><td>权重（%）</td><td>" + Constant.WEIGHT_RAM + "</td></tr>" + "</tbody></table></div>";
		qosStr += "</div>";

		try {

			PrintStream out = new PrintStream(new FileOutputStream(
					"output/index.html"));

			out.println(header + graphs + host + vm + users + cloudlet + qosStr + footer);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Log.enable();
		Log.printLine("All files writen!");
		Log.disable();
	}

	private static String makeLine(PowerMonitor monitor) throws MWException {
		
		System.out.println("Lam:" + monitor.getName()+ "OutputHtml.makeLine");
		
		String customTooltipsUtilAv = monitor.getAverUtil().replaceAll(" ",
				"'<b>" + monitor.getName() + "</b><br/>");
		customTooltipsUtilAv = customTooltipsUtilAv.replaceAll(",", "',");
		String customTooltipsPowerAv = monitor.getAverPower().replaceAll(" ",
				"'<b>" + monitor.getName() + "</b><br/>");
		customTooltipsPowerAv = customTooltipsPowerAv.replaceAll(",", "',");

		customTooltipsPowerAv = "AverPower" + monitor.getName() + ".Set('chart.tooltips', [" + customTooltipsPowerAv + "']);";
		customTooltipsUtilAv = "Util" + monitor.getName() + ".Set('chart.tooltips', [" + customTooltipsUtilAv + "']);";
		customTooltipsPowerAv = "";
		customTooltipsUtilAv = "";

		/**
		// gey the powers
		List<Double> Vars = monitor.getCpuAndHostload();
		List<Double> Powers = IT_RBF.getPowerNN(Vars);
		 */

		List<Double> Powers = new ArrayList<>();
		List<Double> QoSs = new ArrayList<>();

		double tempPower = 0;
		double tempQoS = 0;
		int lengthOfTime;

		if(monitor.getName() == "RR"){
			lengthOfTime = PoIs[1]-PoIs[0];
			//267 times
			for(int i=0;i<lengthOfTime;i++){
				//50 times
				for(int j=0;j<Constant.NUMBER_HOST;j++){
					tempPower += allPowers.get(j*lengthOfTime+i);
					tempQoS += allQoSs.get(j*lengthOfTime+i);
				}
				Powers.add(tempPower / Constant.NUMBER_HOST);
				QoSs.add(tempQoS / Constant.NUMBER_HOST);
				tempPower = 0;
				tempQoS = 0;
			}
		}else if(monitor.getName() == "DVFS"){
			lengthOfTime = PoIs[1+Constant.NUMBER_HOST]-PoIs[0+Constant.NUMBER_HOST];
			for(int i=0;i<lengthOfTime;i++){
				for(int j=0;j<Constant.NUMBER_HOST;j++){
					tempPower += allPowers.get(j*lengthOfTime+i+threePoI[0]+1);
					tempQoS += allQoSs.get(j*lengthOfTime+i+threePoI[0]+1);
				}
				Powers.add(tempPower / Constant.NUMBER_HOST);
				QoSs.add(tempQoS / Constant.NUMBER_HOST);
				tempPower = 0;
				tempQoS = 0;
			}
		}else if(monitor.getName() == "ST"){
			lengthOfTime = PoIs[1+Constant.NUMBER_HOST*2]-PoIs[0+Constant.NUMBER_HOST*2];
			for(int i=0;i<lengthOfTime;i++){
				for(int j=0;j<Constant.NUMBER_HOST;j++){
					tempPower += allPowers.get(j*lengthOfTime+i+threePoI[1]+1);
					tempQoS += allQoSs.get(j*lengthOfTime+i+threePoI[1]+1);
				}
				Powers.add(tempPower/Constant.NUMBER_HOST);
				QoSs.add(tempQoS/Constant.NUMBER_HOST);
				tempPower = 0;
				tempQoS = 0;
			}
		}

		// take it to the format of the format
		String PowersStr = " " + Powers.get(0);
		Powers.remove(0);
		for (double power : Powers) {
			PowersStr += ", " + power;
		}

		String QoSsStr = " " + QoSs.get(0);
		QoSs.remove(0);
		for (double qos : QoSs) {
			QoSsStr += ", " + qos;
		}

		String powerUtilHtml = "var AverPower" + monitor.getName() + " = new RGraph.Line('powerGraph" + monitor
				//The Average Power in active hosts(W) of three kinds of algorithm
				// Last version: monitor.getAverPower()
				.getName() + "', [" +
				PowersStr + "]);AverPower" + monitor
				.getName() + ".Set('chart.labels', [" + monitor.getTimes() + "]);" + customTooltipsPowerAv + "AverPower" + monitor
				.getName() + ".Set('chart.colors', ['" + monitor.getColor() + "']);AverPower" + monitor
				.getName() + ".Set('chart.linewidth', 3);AverPower" + monitor
				.getName() + ".Set('chart.ymax', " + Constant.MAXPOWERS + ");AverPower" + monitor
				.getName() + ".Set('chart.shadow', true);AverPower" + monitor
				.getName() + ".Draw();" + "var Util" + monitor.getName() + " = new RGraph.Line('utilGraph" + monitor
				//The Average Utilization in active hosts (%) of three kinds of algorithm
				.getName() + "', [" +
				QoSsStr + "]);" + "Util" + monitor
				.getName() + ".Set('chart.labels', [" + monitor.getTimes() + "]);Util" + monitor
				.getName() + ".Set('chart.colors', ['" + monitor.getColor() + "']);Util" + monitor
				.getName() + ".Set('chart.ymax', 100);" + customTooltipsUtilAv + "Util" + monitor
				.getName() + ".Set('chart.linewidth', 3);Util" + monitor
				.getName() + ".Set('chart.shadow', true);Util" + monitor
				.getName() + ".Draw();";
		return powerUtilHtml;
	}

	// Three times
	public static String[] makeHostLine(PowerMonitor monitor) throws MWException {

		System.out.println("Lam:" +monitor.getName() + "OutputHtml.makeHostLine");

		GUI.setProgressBar(monitor.getName() + "makeHostLine");
		LogThreadSendToArea l = new LogThreadSendToArea("正在 绘制算法 " + monitor.getName() + " 的服务器能耗图……");
		l.start();
		
		List<Host> hostList = monitor.getHostList();
		double step = Constant.STEP_INTERVAL;
		double sched_int = Constant.SCHEDULING_INTERVAL;

		String[] graphMonitor = new String[2];
		graphMonitor[0] = "";
		graphMonitor[1] = "";

		/**
		// First: collect
		List<Double> Vars = new ArrayList<>();
		// PoI means the position of interrupt
		List<Integer> PoI = new ArrayList<>();
		int tempPoI = 0;
		for (Host h : hostList) {
			PowerHostPlus big = (PowerHostPlus) h;
			double previoustime = 0;
			for (HostStateHistoryEntry host : big.getStateHistory()) {
				if (host.getTime() >= Constant.STEP_INTERVAL) {
					previoustime = host.getTime();
					break;
				}
			}
			for (HostStateHistoryEntry host : big.getStateHistory()) {
				if ((int) host.getTime() <= ((int) (previoustime + sched_int))) {
					Vars.add(host.getAllocatedMips() / big .getTotalMips());
					Vars.add((double)host.getHostLoad());
					tempPoI++;
					previoustime = host.getTime();
				} else {
					while (previoustime < (host.getTime() - 2*sched_int)) {
						previoustime += sched_int;
						Vars.add((double)0);
						Vars.add((double)0);
						tempPoI++;
					}
					Vars.add(host.getAllocatedMips() / big .getTotalMips());
					Vars.add((double)host.getHostLoad());
					tempPoI++;
					previoustime = host.getTime();
				}
			}

			// This is for differnent Ending Time
			// Log.printLine("PREVIOUS TIME = " + previoustime);
			if (previoustime < monitor.getMaxSimTime()) {
				while (previoustime < (monitor.getMaxSimTime() - sched_int)) {
					previoustime += sched_int;
					Vars.add((double)0);
					Vars.add((double)0);
					tempPoI++;
				}
				previoustime += sched_int;
				Vars.add((double)0);
				Vars.add((double)0);
				tempPoI++;
			}
			PoI.add(tempPoI);
			System.out.println(tempPoI);
			tempPoI = 0;
			// ends here
		}

		// Second: calculation
		List<Double> Powers = IT_RBF.getPowerNN(Vars);
		 */

		List<Double> Powers = new ArrayList<>();
		List<Double> QoSs = new ArrayList<>();

		List<Integer> PoI = new ArrayList<>();

		int tempCounter = 0;
		int tempCounter2 = 0;
		int lengthoftime = 0;
		if(monitor.getName() == "RR"){
			lengthoftime = PoIs[1]-PoIs[0];
			for(int i=0;i<threePoI[0]+1;i++){
				Powers.add(allPowers.get(i));
				QoSs.add(allQoSs.get(i));
			}
		}else if(monitor.getName() == "DVFS"){
			lengthoftime = PoIs[1+Constant.NUMBER_HOST]-PoIs[0+Constant.NUMBER_HOST];
			for(int i=threePoI[0]+1;i<threePoI[1]+1;i++){
				Powers.add(allPowers.get(i));
				QoSs.add(allQoSs.get(i));
			}
		}else if(monitor.getName() == "ST"){
			lengthoftime = PoIs[1+Constant.NUMBER_HOST*2]-PoIs[0+Constant.NUMBER_HOST*2];
			for(int i=threePoI[1]+1;i<threePoI[2]+1;i++){
				Powers.add(allPowers.get(i));
				QoSs.add(allQoSs.get(i));
			}
		}

		for(int i=0;i<Constant.NUMBER_HOST;i++){
			PoI.add(lengthoftime);
		}

		String[] powersStr = new String[PoI.size()];
		String[] QoSsStr = new String[PoI.size()];

		boolean firstComeIn;
		for (int i = 0; i<Constant.NUMBER_HOST; i++) {
			firstComeIn = true;
			for (int j = 0; j < lengthoftime; j++) {
				if (firstComeIn) {
					powersStr[tempCounter2] = " " + Powers.get(j + tempCounter);
					QoSsStr[tempCounter2] = " " + QoSs.get(j + tempCounter);
				} else {
					powersStr[tempCounter2] += ", " + Powers.get(j + tempCounter);
					QoSsStr[tempCounter2] += ", " + QoSs.get(j + tempCounter);
				}
				firstComeIn = false;
			}
			tempCounter2++;
			tempCounter += PoI.get(i);
		}

		// Third: take in
		int pos = 1;
		int counter = 0;
		for (Host h : hostList) {
			PowerHostPlus big = (PowerHostPlus) h;
			int nameVar = big.getId();

			graphMonitor[0] += "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><div class='span6' style=''><h3 style='margin-top:40px;margin-bottom:30px;'>服务器 " + nameVar + " 的评估值</h3><h4 style='margin-top:10px;'>评估值</h4><canvas id='util" + nameVar + "' width='450' height='250'>[No canvas support]</canvas><h4 style='text-align:center;'>时间戳</h4><script></script></div>";
			graphMonitor[0] += "<div class='span6' style=''><h3 style='margin-top:40px;margin-bottom:30px;'>服务器 " + nameVar + " 的功率(W)</h3><h4 style='margin-top:10px;'>功率/W</h4><canvas id='power" + nameVar + "' width='450' height='250'>[No canvas support]</canvas><h4 style='text-align:center;'>时间戳</h4><script></script></div></div>";

			boolean firstTime = true;
			String averUtil = "";
			String averPower = "";
			String times = "";
			double previoustime = 0;
			int deigmata = (int) (monitor.getMaxSimTime() / sched_int / step);

			for (HostStateHistoryEntry host : big.getStateHistory()) {
				if (host.getTime() >= Constant.STEP_INTERVAL) {
					previoustime = host.getTime();
					break;
				}
			}
			Log.enable();
			for (HostStateHistoryEntry host : big.getStateHistory()) {
				if ((host.getTime() < (previoustime)) && !firstTime) {
					Log.printLine(host.getTime() + "  <--------dropped " + previoustime);
					continue;
				}
				if (!firstTime) {
					times += ",";
					averUtil += ",";
					averPower += ",";
				}
				firstTime = false;
				if ((int) host.getTime() <= ((int) (previoustime + sched_int))) {

					if (pos == deigmata) {
						times += "'" + ((int) (host.getTime() / Constant.SCALEX)) + "'";
						pos = 1;
					} else {
						times += "''";
						pos++;

					}

					averPower +=" " + String.format("%.2f",  big.getPowerModel().getPower(host.getAllocatedMips() / big.getTotalMips()));
					averUtil +=" " + String.format("%.2f",  (host.getAllocatedMips() * 100 / big .getTotalMips()));
					previoustime = host.getTime();
				} else {
					while (previoustime < (host.getTime() - 2*sched_int)) {

						Log.printLine((host.getTime() - sched_int) + "  --------zeroed " + previoustime + " monitor " + monitor.getName());
						previoustime += sched_int;
						if (pos == deigmata) {
							times += "'" + ((int) (previoustime / Constant.SCALEX)) + "',";
							pos = 1;
						} else {
							times += "'',";
							pos++;
						}
						averPower += "0,";
						averUtil += "0,";
					}
					if (pos == deigmata) {
						times += "'" + ((int) (host.getTime() / Constant.SCALEX)) + "'";
						pos = 1;
					} else {
						times += "''";
						pos++;
					}
					averPower +=" " + String.format("%.2f",  big.getPowerModel().getPower(host.getAllocatedMips() / big.getTotalMips()));
					averUtil +=" " + String.format("%.2f",  (host.getAllocatedMips() * 100 / big .getTotalMips()));
					previoustime = host.getTime();
				}
			}

			// This is for differnent Ending Time
			// Log.printLine("PREVIOUS TIME = " + previoustime);
			if (previoustime < monitor.getMaxSimTime()) {
				while (previoustime < (monitor.getMaxSimTime() - sched_int)) {
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
			Log.disable();
			String customTooltipsUtil = averUtil.replaceAll(" ",
					"'<b>" + monitor.getName() + "</b><br/>");
			customTooltipsUtil = customTooltipsUtil.replaceAll(",", "',");

			String customTooltipsPower = averPower.replaceAll(" ",
					"'<b>" + monitor.getName() + "</b><br/>");
			customTooltipsPower = customTooltipsPower.replaceAll(",", "',");
			double maxPower=0;
			for (Host hfind:hostList){
				PowerHost hmax= (PowerHost) hfind;
				if (hmax.getId()==nameVar){
					maxPower=hmax.getMaxPower();
				}
			}

			// The Utilization (%) and Power at host of three kinds of algorithm
			// Last version: averPower
			graphMonitor[1] += "var Power" + nameVar + " = new RGraph.Line('power" + nameVar + "', [" +
					powersStr[counter] + "]);Power" + nameVar + ".Set('chart.tooltips', [" + customTooltipsPower + "']);Power" + nameVar + ".Set('chart.labels', [" + times + "]);Power" + nameVar + ".Set('chart.colors', ['" + monitor
					.getColor() + "']);" + "Power" + nameVar + ".Set('chart.linewidth', 3);Power" + nameVar + ".Set('chart.shadow', true);Power" + nameVar + ".Set('chart.ymax', " + maxPower + ");Power" + nameVar + ".Draw();" +
					"var Util" + nameVar + " = new RGraph.Line('util" + nameVar + "', [" +
//					averUtil +
					QoSsStr[counter++] +
					"]);Util" + nameVar + ".Set('chart.ymax', 100);Util" + nameVar + ".Set('chart.linewidth', 3);Util" + nameVar + ".Set('chart.tooltips', [" + customTooltipsUtil + "']);Util" + nameVar + ".Set('chart.colors', ['" + monitor
					.getColor() + "']);Util" + nameVar + ".Set('chart.shadow', true);" + "Util" + nameVar + ".Set('chart.labels', [" + times + "]);Util" + nameVar + ".Draw();";
		}

		return graphMonitor;
	}

	// returns [0]=canvas , [1]=js
	private static String[] makeHostCombinedLine(List<PowerMonitor> monitorList) throws MWException {
		
		System.out.println("Lam:OutputHtml.makeHostCombinedLine");

        GUI.setProgressBar("makeHostCombinedLine");
        LogThreadSendToArea l = new LogThreadSendToArea("正在 绘制混合对比图……");
        l.start();
		
		int hostnum = 0;
		double step = Constant.STEP_INTERVAL;
		double sched_int = Constant.SCHEDULING_INTERVAL;
		boolean firstMonitor = true;
		String[] graphMonitor = new String[2];
		String powerUtilHtml = "";
		graphMonitor[0] = "";
		graphMonitor[1] = "";
		int pos = 1;

		// Average
		// Three times
		for (PowerMonitor monitor : monitorList) {
			/**
			// gey the powers
			List<Double> Vars = monitor.getCpuAndHostload();
			List<Double> Powers = IT_RBF.getPowerNN(Vars);
			 */

			List<Double> Powers = new ArrayList<>();
			List<Double> QoSs = new ArrayList<>();

			if(monitor.getName() == "RR"){
				Powers = RRPower;
				QoSs = RRQoS;
			}else if(monitor.getName() == "DVFS"){
				Powers = DVFSPower;
				QoSs = DVFSQoS;
			}else if(monitor.getName() == "ST"){
				Powers = STPower;
				QoSs = STQoS;
			}

			// take it to the format of the format
			String PowersStr = " " + Powers.get(0);
			Powers.remove(0);
			for (double power : Powers) {
				PowersStr += ", " + power;
			}

			String QoSsStr = " " + QoSs.get(0);
			QoSs.remove(0);
			for (double qos : QoSs) {
				QoSsStr += ", " + qos;
			}

			String customTooltipsUtilAv = monitor.getAverUtil().replaceAll(" ",
					"'<b>" + monitor.getName() + "</b><br/>");
			customTooltipsUtilAv = customTooltipsUtilAv.replaceAll(",", "',");
			String customTooltipsPowerAv = monitor.getAverPower().replaceAll(
					" ", "'<b>" + monitor.getName() + "</b><br/>");
			customTooltipsPowerAv = customTooltipsPowerAv.replaceAll(",", "',");
			//The Average Power in active hosts (W) of Combined
			// last version: monitor.getAverPower()
			powerUtilHtml += "var AverPower" + monitor.getName() + " = new RGraph.Line('powerGraph', [" +
					PowersStr + "]);AverPower" + monitor.getName() + ".Set('chart.labels', [" + monitor
					.getTimes() + "]);AverPower" + monitor.getName() + ".Set('chart.colors', ['" + monitor
					.getColor() + "']);AverPower" + monitor.getName() + ".Set('chart.linewidth', 3);AverPower" + monitor
					.getName() + ".Set('chart.ymax', " + Constant.MAXPOWERS + ");" +
					// "AverPower" + monitor.getName() +
					// ".Set('chart.tooltips', [" + customTooltipsPowerAv +
					// "']);" +
					"AverPower" + monitor.getName() + ".Set('chart.shadow', true);AverPower" + monitor
					//The Average Utilization in active hosts (%) of Combined
					.getName() + ".Draw();" + "var Util" + monitor.getName() + " = new RGraph.Line('utilGraph', [" +
					//monitor.getAverUtil() + "]);" + "Util" + monitor.getName() + ".Set('chart.labels', [" + monitor
					QoSsStr + "]);" + "Util" + monitor.getName() + ".Set('chart.labels', [" + monitor
					.getTimes() + "]);Util" + monitor.getName() + ".Set('chart.colors', ['" + monitor
					.getColor() + "']);" +
					// "Util"+ monitor.getName() + ".Set('chart.tooltips', [" +
					// customTooltipsUtilAv + "']);" +
					"Util" + monitor.getName() + ".Set('chart.ymax', 100);	Util" + monitor
					.getName() + ".Set('chart.linewidth', 3);Util" + monitor
					.getName() + ".Set('chart.shadow', true);Util" + monitor
					.getName() + ".Draw();";
		}

		/**
		// Every: collecte
		List<Double> Var1 = new ArrayList<>();
		List<Double> Var2 = new ArrayList<>();
		List<Double> Var3 = new ArrayList<>();
		List[] Vars = new List[]{Var1, Var2, Var3};
		int counter = 0;
		// PoI =  position of interrupt
		List<Integer> PoI = new ArrayList<>();
		int tempPoI = 0;
		for (PowerMonitor monitor : monitorList) {
			hostnum = 0;

			for (double[] big : monitor.getAllInfo()) {
				double previoustime = monitor.getMaxSimTime();
				for (double[] host : monitor.getAllInfo()) {
					if (host[0] == big[0]) {
						if ((int) host[4] <= ((int) (previoustime + sched_int))) {
							Vars[counter].add(host[3]/100);
							Vars[counter].add(host[5]);
							tempPoI++;
							previoustime = host[4];
						} else {
							while (previoustime < (host[0] - 2*sched_int)) {
								previoustime += sched_int;
								Vars[counter].add((double)0);
								Vars[counter].add((double)0);
								tempPoI++;
							}
							Vars[counter].add(host[3]/100);
							Vars[counter].add(host[5]);
							tempPoI++;
							previoustime = host[4];
						}
					}
				}
				PoI.add(tempPoI);
				tempPoI = 0;

				if (hostnum == (monitor.getHostNum() - 1)) {
					break;
				}
				hostnum++;
			}
			counter++;
		}

		// Calculation
		List<Double> allVar = new ArrayList<>();
		for (double tempVar : Var1) {
			allVar.add(tempVar);
		}
		for (double tempVar : Var2) {
			allVar.add(tempVar);
		}
		for (double tempVar : Var3) {
			allVar.add(tempVar);
		}
		List<Double> Powers = IT_RBF.getPowerNN(allVar);
		*/

		List<Double> Powers = allPowers;
		List<Double> QoSs = allQoSs;

		List<Integer> PoI = new ArrayList<>();

		for(int i=0;i<Constant.NUMBER_HOST;i++){
			PoI.add(PoIs[1]-PoIs[0]);
		}
		for(int i=0;i<Constant.NUMBER_HOST;i++){
			PoI.add(PoIs[1+Constant.NUMBER_HOST]-PoIs[0+Constant.NUMBER_HOST]);
		}
		for(int i=0;i<Constant.NUMBER_HOST;i++){
			PoI.add(PoIs[1+Constant.NUMBER_HOST*2]-PoIs[0+Constant.NUMBER_HOST*2]);
		}

		String[] powersStr = new String[PoI.size()];
		String[] QoSsStr = new String[PoI.size()];

		int tempCounter = 0;
		int tempCounter2 = 0;
		boolean firstComeIn;
		for (int i = 0; i < PoI.size(); i++) {
			firstComeIn = true;
			for (int j = 0; j < PoI.get(i); j++) {
				if (firstComeIn) {
					powersStr[tempCounter2] = " " + Powers.get(j + tempCounter);
					QoSsStr[tempCounter2] = " " + QoSs.get(j + tempCounter);
				} else {
					powersStr[tempCounter2] += ", " + Powers.get(j + tempCounter);
					QoSsStr[tempCounter2] += ", " + QoSs.get(j + tempCounter);
				}
				firstComeIn = false;
			}
			tempCounter2++;
			tempCounter += PoI.get(i);
		}

		// Every: take it into String
		// Three times
		firstMonitor = true;
		int counter = 0;
		for (PowerMonitor monitor : monitorList) {
			hostnum = 0;
			for (double[] big : monitor.getAllInfo()) {
				int hostId = (int) big[0];
				String nameVar = hostId + monitor.getName();
				if (firstMonitor) {
					graphMonitor[0] += "<div class='row' style='border-top: 1px solid #eeeeee;margin-bottom:50px;margin-left:30px;'><div class='span6' style='margin-right:4%;'><h3 style='margin-top:40px;margin-bottom:30px;'>服务器 " + hostId + " 的评估值</h3><h4 style='margin-top:10px;'>评估值</h4><canvas id='util" + hostId + "' width='450' height='250'>[No canvas support]</canvas><h4 style='text-align:center;'>时间戳</h4><script></script></div>";
					graphMonitor[0] += "<div class='span6' style=''><h3 style='margin-top:40px;margin-bottom:30px;'>服务器 " + hostId + " 的功率(W)</h3><h4 style='margin-top:10px;'>功率/W</h4><canvas id='power" + hostId + "' width='450' height='250'>[No canvas support]</canvas><h4 style='text-align:center;'>时间戳</h4><script></script></div></div>";
				}
				boolean firstTime = true;
				String averUtil = "";
				String averPower = "";
				double previoustime = monitor.getMaxSimTime();
				int deigmata = (int) (monitor.getMaxSimTime() / sched_int / step);
				for (double[] host : monitor.getAllInfo()) {
					if (host[0] == big[0]) {
						if (!firstTime) {
							averUtil += ",";
							averPower += ",";
						}
						firstTime = false;
						if ((int) host[4] <= ((int) (previoustime + sched_int))) {
							if (pos == deigmata) {
								pos = 1;
							} else {
								pos++;
							}
							averPower +=" " + String.format("%.2f",  host[1]);
							averUtil +=" " + String.format("%.2f",  host[3]);
							previoustime = host[4];
						} else {
							while (previoustime < (host[0] - 2*sched_int)) {
								previoustime += sched_int;
								if (pos == deigmata) {
									pos = 1;
								} else {
									pos++;
								}
								averPower += "0,";
								averUtil += "0,";
							}
							if (pos == deigmata) {
								pos = 1;
							} else {
								pos++;
							}
							averPower +=" " + String.format("%.2f",  host[1]);
							averUtil +=" " + String.format("%.2f",  host[3]);
							previoustime = host[4];
						}
					}
				}

				String customTooltipsUtil = averUtil.replaceAll(" ",
						"'<b>" + monitor.getName() + "</b><br/>");
				customTooltipsUtil = customTooltipsUtil.replaceAll(",", "',");

				String customTooltipsPower = averPower.replaceAll(" ",
						"'<b>" + monitor.getName() + "</b><br/>");
				customTooltipsPower = customTooltipsPower.replaceAll(",", "',");
				// Last version: averPower
				graphMonitor[1] += "var Power" + nameVar + " = new RGraph.Line('power" + hostId + "', [" +
						powersStr[counter]
						+ "]);Power" + nameVar + ".Set('chart.labels', [" + monitor
						.getTimes() + "]);Power" + nameVar + ".Set('chart.colors', ['" + monitor
						//The Power (W) at host of Combined
						.getColor() + "']);Power" + nameVar + ".Set('chart.linewidth', 3);Power" + nameVar + ".Set('chart.shadow', true);Power" + nameVar + ".Set('chart.ymax', " + big[2] + ");" +
						// "Power" + nameVar + ".Set('chart.tooltips', [" +
						// customTooltipsPower + "']);" +
						//The Utilization (%) at host of Combined
						"Power" + nameVar + ".Draw();" + "var Util" + nameVar + " = new RGraph.Line('util" + hostId + "', [" +
//						averUtil +
						QoSsStr[counter++] +
						"]);Util" + nameVar + ".Set('chart.ymax', 100);Util" + nameVar + ".Set('chart.linewidth', 3);Util" + nameVar + ".Set('chart.colors', ['" + monitor
						.getColor() + "']);Util" + nameVar + ".Set('chart.shadow', true);" +
						// "Util" + nameVar + ".Set('chart.tooltips', [" +
						// customTooltipsUtil + "']);" +
						"Util" + nameVar + ".Set('chart.labels', [" + monitor
						.getTimes() + "]);Util" + nameVar + ".Draw();";

				if (hostnum == (monitor.getHostNum() - 1)) {
					break;
				}

				hostnum++;
			}
			firstMonitor = false;
		}
		graphMonitor[1] += powerUtilHtml;
		return graphMonitor;
	}
	
	//output/graphs
	private static void exportHostStats(List<PowerMonitor> monitors) throws IOException, MWException{
		
		System.out.println("Lam:OutputHtml.exportHostStats");

		GUI.setProgressBar("exportHostStats");
		LogThreadSendToArea l = new LogThreadSendToArea("正在 使用RBF神经网络进行运算……");
		l.start();

		//First: get the Vars
		List<Double> Vars = new ArrayList<>();
		int CounterOfVars = 0;

		PoIs = new int[Constant.NUMBER_HOST*3];
		int tempPoI = -1;

		threePoI = new int[3];
		int tempCounter=0;

		for (PowerMonitor monitor :monitors ) {
			for (Host hosta:  monitor.getHostList( )){
				PowerHostPlus host=(PowerHostPlus) hosta;
				for (HostStateHistoryEntry entry : host.getStateHistory() ){
					Vars.add(entry.getAllocatedMips()/host.getTotalMips());
					Vars.add((double)entry.getHostLoad());
					tempPoI++;
				}
				PoIs[CounterOfVars++] = tempPoI;
			}
			if(tempCounter == 0)
				threePoI[tempCounter++] = tempPoI;
			else if(tempCounter == 1){
				threePoI[tempCounter] = tempPoI;
				tempCounter++;
			}else
				threePoI[tempCounter] = tempPoI;
		}

		//Second:calculate
		allPowers = new ArrayList<>();
		allPowers = IT_RBF.getPowerNN(Vars);

		allQoSs = new ArrayList<>();
		allQoSs = Evaluation_QoS.getEvaluationByCpuAndHostload(Vars);

		//Third: take the power into the monitors
		int counter = 0;
		for (PowerMonitor monitor :monitors ) {
			boolean dir = (new File("output/graphs/"+monitor.getName())).mkdirs();	
			String time="";
			String history="";
			String historyPower="";

			for (Host hosta:  monitor.getHostList( )){
				PowerHostPlus host=(PowerHostPlus) hosta;
				historyPower="";
				history="";
				time="";	

				for (HostStateHistoryEntry entry : host.getStateHistory() ){
					time+= entry.getTime() + System.getProperty("line.separator");
					history+= allQoSs.get(counter) + System.getProperty("line.separator");
					historyPower+= allPowers.get(counter++) + System.getProperty("line.separator");
				}

				File yourFile = new File(
						"output/graphs/"+monitor.getName() +"/"+ host.getId() + "Util.txt");
				File yourFileT = new File(
						"output/graphs/"+monitor.getName()  +"/"+ host.getId() + "Power.txt");
				if (!yourFileT.exists()) {
					yourFileT.createNewFile();}
					try {

						PrintStream out = new PrintStream(new FileOutputStream(
								"output/graphs/"+monitor.getName() +"/"+ host.getId() + "Util.txt"));
						out.println(history);
						out.close();
						
						 out = new PrintStream(new FileOutputStream(
								"output/graphs/"+monitor.getName() +"/"+ host.getId() + "Power.txt"));
						out.println(historyPower);
						out.close();			

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				
				
				
				
			}
			File timeFile = new File(
					"output/graphs/"+monitor.getName()  +"/_Times.txt");
			if (!timeFile.exists()) {
				timeFile.createNewFile();}
				try {

					PrintStream out = new PrintStream(new FileOutputStream(
							"output/graphs/"+monitor.getName()  +"/_Times.txt"));
					out.println(time);
					out.close();					

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			
			
			
		}	
		
	}

}