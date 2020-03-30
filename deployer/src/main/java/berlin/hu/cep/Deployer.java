package berlin.hu.cep;

import berlin.hu.cep.connectclient.*;
import berlin.hu.cep.siddhi.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Hello world!
 *
 */
public class Deployer
{
    public static void main( String[] args ) throws Exception {

        if(args.length >= 2)
        {
            DeployerConfig deployer_config = null;
            ConnectClient cc = null;
            SiddhiRestClient sc = null;


            try {
                String config_json = Files.readString(Paths.get(args[1]), StandardCharsets.US_ASCII);
                ObjectMapper object_mapper = new ObjectMapper();
                deployer_config = object_mapper.readValue(config_json, DeployerConfig.class);

                cc = new ConnectClient(deployer_config.kafkaconnect_config);
                sc = new SiddhiRestClient(deployer_config.siddhi_config);
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
                    for(String name : deployer_config.siddhi_config.siddhiFiles)
                    {
                        sc.deleteApplication(name);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
