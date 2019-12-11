package model;

import RBF_Load.RBFfc;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import structure.Constant;

import java.util.ArrayList;
import java.util.List;

public class IT_RBF {
	public static List<Double> getPowerNN(List<Double> Vars) throws MWException {

		// the position of Matlab model
		String address = Constant.IT_ENERGYMODEL;

		//There are two kinds of input which can be used.
		String modelChoice = "Cpu";
		// String modelChoice = "CpuAndHostload";

		if (modelChoice == "Cpu") {
			double[][] example = new double[Vars.size()/2][1];
			for(int i=0;i<Vars.size()/2;i++) {
				example[i][0] = Vars.get(2*i);
			}
			return IT_RBF.getPowerByCpu(address, example);
		} else if (modelChoice == "CpuAndHostload") {
			double[][] example = new double[Vars.size()/2][2];
			for(int i=0;i<Vars.size()/2;i++) {
				example[i][0] = Vars.get(2*i);
				example[i][1] = Vars.get(2*i+1);
			}
			return IT_RBF.getPowerByCpuAndHostload(address, example);
		}
		return null;
	}

	public static List<Double> getPowerByCpu(String address,double[][] example) throws MWException {

		double[] row = new double[example.length];

		double[][] input = {row};

		for(int i=0;i<example.length;i++) {
			row[i]=example[i][0];
		}

		MWNumericArray ayNum = new MWNumericArray(input,MWClassID.DOUBLE);

		RBFfc rbffc = new RBFfc();
		Object[] resultsOb = rbffc.RBF_Load(1,address,ayNum);
		String[] resultsStr = resultsOb[0].toString().split("  |   |ÖÁ|ÁÐ");
		List<Double> resultsDou = new ArrayList<>();

		if (resultsStr[1].contains("1.0e")) {
			//make the multiply
			double multiply = 0;
			String[] strs = resultsStr[1].split(" ");
			for(String a:strs){
				if(a.contains("."))
					multiply = Double.parseDouble(a);
			}
			//get result
			for(String str:resultsStr) {
				if(str.contains(".")&&(!str.contains("*"))) {
					resultsDou.add(Double.parseDouble(str)*multiply);
				}
			}
		} else {
			//get result
			for(String str:resultsStr) {
				if(str.contains(".")&&(!str.contains("*"))) {
					resultsDou.add(Double.parseDouble(str));
				}
			}
		}

		return resultsDou;
	}

	public static List<Double> getPowerByCpuAndHostload(String address,double[][] example) throws MWException {

		double[] row1 = new double[example.length];
		double[] row2 = new double[example.length];

		double[][] input = {row1,row2};

		for(int i=0;i<example.length;i++) {
			row1[i]=example[i][0];
			row2[i]=example[i][1];
		}

		MWNumericArray ayNum = new MWNumericArray(input,MWClassID.DOUBLE);

		RBFfc rbffc = new RBFfc();
		Object[] resultsOb = rbffc.RBF_Load(1,address,ayNum);
		String[] resultsStr = resultsOb[0].toString().split("  |   |ÖÁ|ÁÐ");
		List<Double> resultsDou = new ArrayList<>();

		if (resultsStr[1].contains("1.0e")) {
			//make the multiply
			double multiply = 0;
			String[] strs = resultsStr[1].split(" ");
			for(String a:strs){
				if(a.contains("."))
					multiply = Double.parseDouble(a);
			}
			//get result
			for(String str:resultsStr) {
				if(str.contains(".")&&(!str.contains("*"))) {
					resultsDou.add(Double.parseDouble(str)*multiply);
				}
			}
		} else {
			//get result
			for(String str:resultsStr) {
				if(str.contains(".")&&(!str.contains("*"))) {
					resultsDou.add(Double.parseDouble(str));
				}
			}
		}

		return resultsDou;
	}

	//Unit test
	public static void main(String[] args) throws MWException {

		List<Double> Vars =  new ArrayList<>();
		for(int i=0;i<100;i++) {
			Vars.add(1.0);
		}

		List<Double> power = IT_RBF.getPowerNN(Vars);

		for(double p:power) {
			System.out.println("p:"+p);
		}
		System.out.println("The number of this result: "+power.size());
	}
}