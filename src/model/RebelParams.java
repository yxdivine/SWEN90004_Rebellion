package model;
/**
 * global parameters
 * @author Xinda Yu
 *
 */
public class RebelParams {
	// movement
	public static boolean enable_move = true;

	// government legitimacy
	public static double gov_legit = 0.82;

	// vision range
	public static double vision = 7.0;

	// initial cop density
	public static double init_cop_dens = 0.04;

	// initial agent density
	public static double init_agent_dens = 0.7;

	// the constant k
	public static double k = 2.3;

	// threshold of agent
	public static double threshold = 0.1;

	// the jail term
	public static int max_jail_term = 30;

	// map size
	public static int map_size = 40;

	public static String stringify() {
		String str = "params:\n";
		str += "map_size\t" + map_size + "\n";
		str += "init_cop_dens\t" + init_cop_dens + "\n";
		str += "init_agent_dens\t" + init_agent_dens + "\n";
		str += "k\t\t" + k + "\n";
		str += "gov_legit\t" + gov_legit + "\n";
		str += "threshold\t" + threshold + "\n";
		str += "max_jail_term\t" + max_jail_term + "\n";
		str += "vision\t\t" + vision + "\n";
		str += "enable_move\t" + enable_move + "\n";

		return str;
	}

	public static void set(String arg, String val) {
		switch (arg) {
		case "k": {
			k = Double.parseDouble(val);
			break;
		}
		case "init_cop_dens": {
			init_cop_dens = Double.parseDouble(val);
			break;
		}
		case "init_agent_dens": {
			init_agent_dens = Double.parseDouble(val);
			break;
		}
		case "threshold": {
			threshold = Double.parseDouble(val);
			break;
		}
		case "vision": {
			vision = Double.parseDouble(val);
			break;
		}
		case "gov_legit": {
			gov_legit = Double.parseDouble(val);
			break;
		}
		case "enable_move": {
			enable_move = Boolean.parseBoolean(val);
			break;
		}
		case "max_jail_term": {
			max_jail_term = Integer.parseInt(val);
			break;
		}
		case "map_size": {
			map_size = Integer.parseInt(val);
			break;
		}
		default: {
			System.out.println("unrecognized param " + arg + "\n");
		}
		}
	}

}
