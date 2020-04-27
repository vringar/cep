package berlin.hu.cep.deployer;

import berlin.hu.cep.connector.*;
import berlin.hu.cep.siddhi.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
  * The main Class of the package used to run the cli application.
  *
  * */
public class Deployer
{
    /**
     * Main method of the project.
     *
     * The method resembles a simple CLI application.
     * It takes a general purpose configuration file and splits it into smaller configuration
     * files for the subsystems.
     *
     * @param args Command line arguments for the application: [options] [file]<br>
     *  -deploy to deploy configurations<br>
     *  -remove to remove configurations<br>
     *  [file] path to the configuration file
     * */
    public static void main( String[] args ) {

        if(args.length >= 2)
        {
            DeployerConfig deployer = null;
            ConnectClient cc = null;
            SiddhiRestClient sc = null;


            try {
                String config_json = Files.readString(Paths.get(args[1]), StandardCharsets.US_ASCII);
                ObjectMapper object_mapper = new ObjectMapper();
                deployer = object_mapper.readValue(config_json, DeployerConfig.class);

                cc = deployer.getKafkaconnect_config();
                sc = new SiddhiRestClient(deployer.getSiddhi_config());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(args[0].equals("-deploy"))
            {
                try
                {
                    cc.deploy();
                    sc.deployFiles();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(args[0].equals("-remove"))
            {
                try {
                    cc.delete();
                    for(String name : deployer.getSiddhi_config().siddhiFiles)
                    {
                        sc.deleteApplication(name);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(args[0].equals("-help"))
            {
                usage();
            }
        }
        else{
            //No config-file
            usage();
        }
    }

    private static void usage(){
            System.err.println("Usage:");
            System.err.println("deployer [options] [file]\n");
            System.err.println("-deploy deploy configuration");
            System.err.println("-remove remove configuration");
    }
}
