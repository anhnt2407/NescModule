package br.cin.ufpe.nesc2cpn.nescModule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public class Configuration implements Nesc {
    private String name;
    private Map<String,String> interfaceProviders;
    private Map<String,String> modules;
    private List<ConfigurationLink> links;

    public Configuration(){
        interfaceProviders = new LinkedHashMap<String, String>();
        modules = new LinkedHashMap<String, String>();
        links = new ArrayList<ConfigurationLink>();
    }

    public Map<String,String> getInterfaceProviders() {
        return interfaceProviders;
    }

    public void setInterfaceProviders(Map<String,String> interfaceProviders) {
        this.interfaceProviders = interfaceProviders;
    }

    public List<ConfigurationLink> getLinks() {
        return links;
    }

    public void setLinks(List<ConfigurationLink> links) {
        this.links = links;
    }

    public Map<String, String> getModules() {
        return modules;
    }

    public void setModules(Map<String, String> modules) {
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
