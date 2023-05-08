package data;

public enum BrowserData {
    CHROME("chrome");
    private final String name;

    BrowserData(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

