package dk.sdu.mmmi.cbse.splitpackages;


import dk.sdu.mmmi.cbse.splitpackageservices.ISplitPackage;

public class SplitPackage implements ISplitPackage {
    public String provide() {
        return "Asteroid";
    }
}
