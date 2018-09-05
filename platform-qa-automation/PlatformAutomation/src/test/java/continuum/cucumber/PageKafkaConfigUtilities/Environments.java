package continuum.cucumber.PageKafkaConfigUtilities;


public enum Environments {
    INTEGRATION("INT", false),
    QAENV("QA", false),
    NONE("none", false);

    private String name;
    private Boolean isAllTests;


    Environments(String name, Boolean allTests) {
        this.name = name;
        this.isAllTests = allTests;
    }

    public String getName() {
        return name;
    }

    public Boolean isAllTests() {
        return isAllTests;
    }

    public static Environments fromString(String val) {
        for (Environments b : Environments.values()) {
            if (b.name.equalsIgnoreCase(val)){
                return b;
            }
        }
        return NONE;
    }
}
