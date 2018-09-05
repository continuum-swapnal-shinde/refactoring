package continuum.cucumber.Page;


public class JunoPageFactory {

	 public PerformanceAPI performanceAPI =  new PerformanceAPI();
	 public AgentMicroServiceAPI agentMicroServiceAPI = new AgentMicroServiceAPI();
	 
	 public CassandraConnector cassandra= new CassandraConnector();
	 public PluginHelper pluginHelper= new PluginHelper();
	 public MSIHelper MSIHelper= new MSIHelper();
	 public JenkinsJobVersion jenkinsJobVersion = new JenkinsJobVersion();
	 public LongRunningPluginHelper longPluginHelper = new LongRunningPluginHelper();
	 public CustomWait customWait = new CustomWait();
	 public FrameworkServices frameworkServices = new FrameworkServices();
	 
	 public PowershellScriptExecution psExecution = new PowershellScriptExecution();
	 
	 public AgentAutoUpdateHelper agentAutoUpdate = new AgentAutoUpdateHelper();
	 //Global Shared Ssh object
	 //public static Ssh gSSHSessionObj = null;
	 
	 public Ssh gSSHSessionObj;
	 
	 public Ssh Win7_32Bit=null;
	 public Ssh Win7_64Bit=null;
	 public Ssh Win8_32Bit=null; 
	 public Ssh Win8_64Bit=null;
	 public Ssh Win10_32Bit=null;
	 public Ssh Win10_64Bit=null;
	 public Ssh Win8_1_32Bit=null;
	 public Ssh WinXP_32Bit=null;
	 public Ssh WinXP_64Bit=null;
	 public Ssh WinVista_32Bit=null;
	 public Ssh WinVista_64Bit=null;
	 public Ssh WinServer8_64Bit=null;
	 public Ssh WinServer12_64Bit=null;
	 public Ssh WinServer16_64Bit=null; 
	 public Ssh WinServer12R2_64Bit=null;
	 public Ssh WinServer8_Standard=null;
	 public Ssh WinServer3_32Bit=null;
	 public Ssh WinServer3_64Bit=null;
	 public Ssh Linux_64Bit=null;
}
