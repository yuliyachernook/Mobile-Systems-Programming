package chernook.fit.lab3.data;

public enum EducationDegree {
    POSTGRADUATE("Послевузовское", 0),
    HIGHER("Высшее", 1),
    SPECIAL("Среднее специальное", 2),
    TECHNICAL("Профессионально-техническое", 3),
    GENERAL("Общее среднее", 4);

    private String text;
    private int id;

    EducationDegree(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public int getId() {
        return this.id;
    }

    public static int fromString(String text) {
        for (EducationDegree e : EducationDegree.values()) {
            if (e.text.equalsIgnoreCase(text)) {
                return e.getId();
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
