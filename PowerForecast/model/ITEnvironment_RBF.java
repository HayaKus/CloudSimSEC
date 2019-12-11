package model;

import RBF_Load.RBFfc;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import structure.Constant;

import java.util.ArrayList;
import java.util.List;

public class ITEnvironment_RBF {
	public static List<Double> getPowerNN(List<Double> Vars) throws MWException {

		// the position of Matlab model
		String address = Constant.ITENVIRONMENT_ENERGYMODEL;

		double[][] example = new double[Vars.size()][1];
		for(int i=0;i<Vars.size();i++) {
			example[i][0] = Vars.get(i);
		}
		return ITEnvironment_RBF.getPowerByCpu(address, example);
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

	//Unit test
	public static void main(String[] args) throws MWException {

		List<Double> Vars =  new ArrayList<>();
		for(int i=0;i<100;i++) {
			Vars.add(100.0);
		}

		List<Double> power = ITEnvironment_RBF.getPowerNN(Vars);

		for(double p:power) {
			System.out.println("p:"+p);
		}
		System.out.println("The number of this result: "+power.size());
	}
}