package dk.sdu.mmmi.cbse.playersystem.splitpackages;

import dk.sdu.mmmi.cbse.splitpackageservices.ISplitPackage;

public class SplitPackage implements ISplitPackage {
    public String provide() {
        return "Asteroid";
    }
}
