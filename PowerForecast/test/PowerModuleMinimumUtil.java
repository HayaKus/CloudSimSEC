/*
 * 
 * @author Stefanos Mountrakis (stvakis@gmail.com)
 */

package test;

import org.cloudbus.cloudsim.power.models.PowerModelLinear;

public class PowerModuleMinimumUtil extends PowerModelLinear {
	private double minUtil;

	public PowerModuleMinimumUtil(double maxPower, double staticPowerPercent,
			double minUtil) {
		super(maxPower, staticPowerPercent);
		this.minUtil = minUtil;
	}

	@Override
	public double getPower(double utilization) throws IllegalArgumentException {
		
		if (utilization < 0 || utilization > 1) {
			throw new IllegalArgumentException(
					"Utilization value must be between 0 and 1");
		}
		if (utilization == 0) {
			return 0;
		}
		if (utilization<minUtil) {
			return getStaticPower();
		}
		return getStaticPower() + getConstant() * utilization * 100;
	}

}
