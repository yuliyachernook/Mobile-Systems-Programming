package chernook.fit.bstu;

public class CaloriesCalculator {
    double weight;
    double height;
    int age;
    Gender gender;
    PhysicalActivity activity;

    public double calculate() {
        return Math.ceil(getBMR() * getAMR());
    }

    private double getBMR() {
        if (age > 20 && gender == Gender.FEMALE) {
            return 655.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age);
        }
        return 66.473 + (13.7516 * weight) + (5.0033 * height) - (6.775 * age);
    }

    private double getAMR() {
        switch (activity) {
            case SEDENTARY: {
                return 1.2;
            }
            case MODERATE: {
                return 1.375;
            }
            case AVERAGE: {
                return 1.55;
            }
            case INTENSIVE: {
                return 1.725;
            }
            case ATHLETE: {
                return 1.9;
            }
        }
        return 1;
    }
}
