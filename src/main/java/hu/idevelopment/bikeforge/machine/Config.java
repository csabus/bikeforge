package hu.idevelopment.bikeforge.machine;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "factory")
public class Config {
    private List<MachineConfig> machineGroups;

    public List<MachineConfig> getMachineGroups() {
        return machineGroups;
    }

    public void setMachineGroups(List<MachineConfig> machineGroups) {
        this.machineGroups = machineGroups;
    }

    public static class MachineConfig {
        List<Map<String, Integer>> times;
        List<String> names;

        public List<Map<String, Integer>> getTimes() {
            return times;
        }

        public void setTimes(List<Map<String, Integer>> times) {
            this.times = times;
        }

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }

        @Override
        public String toString() {
            return "MachineConfig{" +
                    "times=" + times +
                    ", names=" + names +
                    '}';
        }
    }


}