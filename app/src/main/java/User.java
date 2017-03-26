import java.net.URL;

/**
 * Created by Darren Moriarty on 26/03/2017.
 */

public class User {

    private String username;
    private String weightGoal;
    private String currentActivity;
    private String gender;
    private String DOB;
    private String BMR;
    private String BMI;
    private String TDEE;
    private String calorieGoal;
    private String height;
    private String weight;
    private String emailAddress;
    private String password;

    public User() {

    }

    public User(String username, String weightGoal, String currentActivity, String gender, String DOB, String BMR, String BMI, String TDEE, String calorieGoal, String height, String weight, String emailAddress, String password) {
        this.username = username;
        this.weightGoal = weightGoal;
        this.currentActivity = currentActivity;
        this.gender = gender;
        this.DOB = DOB;
        this.BMR = BMR;
        this.BMI = BMI;
        this.TDEE = TDEE;
        this.calorieGoal = calorieGoal;
        this.height = height;
        this.weight = weight;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getBMR() {
        return BMR;
    }

    public void setBMR(String BMR) {
        this.BMR = BMR;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getTDEE() {
        return TDEE;
    }

    public void setTDEE(String TDEE) {
        this.TDEE = TDEE;
    }

    public String getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(String calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", weightGoal='" + weightGoal + '\'' +
                ", currentActivity='" + currentActivity + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", BMR='" + BMR + '\'' +
                ", BMI='" + BMI + '\'' +
                ", TDEE='" + TDEE + '\'' +
                ", calorieGoal='" + calorieGoal + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
